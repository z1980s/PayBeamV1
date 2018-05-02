package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ChangePasswordActivity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.SecurityModule.DESPassPhrase;

/**
 * Created by zicokuang on 3/4/18.
 */

public class ChangePasswordPresenter implements ChangePasswordContract.ChangePasswordPresenter
{
    private ChangePasswordContract.ChangePasswordView cpView;

    ChangePasswordPresenter(ChangePasswordContract.ChangePasswordView view)
    {
        cpView = view;
    }

    @Override
    public void onSubmitChangePassButton(View view) {
        cpView.extractValues();
    }

    @Override
    public void localPasswordCheck(String oldPass, String newPass, String retypePass) {

        //Check for blank fields
        if(oldPass.equals("")||newPass.equals("")||retypePass.equals(""))
        {
            cpView.showDialog("Error","Empty fields detected");
        }
        //newPass equals retypePass
        else if(newPass.equals(retypePass))
        {
                //to be sent to server for changing password
                changePassword(oldPass, newPass);
        }
        else
        {
                //display Dialog
                cpView.showDialog("Error","New password does not match re-typed password");
        }
    }


    @Override
    public void changePassword(final String oldPass, final String newPass) {
        //Initiate connection with server
        //Check old password before changing to new password
        final String[] credentials = InternalStorage.readString(cpView.getActivity(), "Credentials").split(",");
        JsonObject ghmsg = new JsonObject();
        ghmsg.addProperty("Header", "GetPasswordHash");
        JsonObject data = new JsonObject();
        data.addProperty("LoginName", credentials[0]);
        data.addProperty("Password", oldPass);
        ghmsg.add("User", data);

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(ghmsg, cpView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                JsonParser jParser = new JsonParser();
                JsonObject responseObj = (JsonObject) jParser.parse(response);
                if (responseObj.get("result").getAsString().equals("Success")) {
                    try {
                        String hash = responseObj.get("hash").getAsString();
                        JsonObject msg = new JsonObject();
                        msg.addProperty("Header", "ChangePassword");
                        msg.addProperty("LoginName", credentials[0]);
                        msg.addProperty("NewPassword", newPass);
                        msg.addProperty("encrypted", new DESPassPhrase(hash).encrypt(newPass));
                        msg.addProperty("Token", InternalStorage.readToken(cpView.getActivity(),"Token"));

                        ServerConnection sc = new ServerConnection(msg, cpView.getActivity()) {
                            @Override
                            public void receiveResponse(String response) {
                                JsonParser jParser = new JsonParser();
                                JsonObject responseObj = (JsonObject) jParser.parse(response);
                                if (responseObj.get("result").getAsString().equals("Success")) {
                                    cpView.showSuccess(responseObj.get("reason").getAsString());
                                } else {
                                    cpView.showErrorMessage(responseObj.get("reason").getAsString());
                                }
                            }
                        };
                        sc.execute(null,null,null);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    cpView.showErrorMessage(responseObj.get("reason").getAsString());
                }
            }
        };

        sc.execute(null,null,null);
        //Upon success write to Internal Storage

    }


}
