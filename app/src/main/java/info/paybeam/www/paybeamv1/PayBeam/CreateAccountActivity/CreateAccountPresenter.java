package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.OTPModule.GenerateOTP;

/**
 * presenter handles create account logic
 */

public class CreateAccountPresenter implements CreateAccountContract.CreateAccountPresenter
{
    private CreateAccountContract.CreateAccountView caView;
    private Activity caActivity;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final int MY_PERMISSIONS_READ_PHONE_STATE = 1;
    private int OTP;

    CreateAccountPresenter(CreateAccountContract.CreateAccountView view)
    {
        caView = view;
        caActivity = caView.getActivity();
    }

    //create account, check server, on success, receive OTP message
    //name, username, password (security policy), email, phone number, date of birth, address, postal code

    public void onSubmitButtonClick(View view)
    {
        /* Writing credentials to local file for testing purposes
        String [] cred = caView.extractCredentials();
        InternalStorage.writeProfileToFile(caView.getActivity(),"profile", cred[0],cred[1], cred[3],cred[4], cred[5]);
        Toast.makeText(caView.getActivity(),cred[0]+cred[1],Toast.LENGTH_SHORT).show();
        */

        //call verify details
        //if true, show success screen leading to OTP verification
        //if false, show error message
        caView.extractValues();
        //caView.onFailureView();
    }

    @Override
    public void checkOTP(int otp)
    {
        if(OTP == otp)
        {
            //caView.onSuccessView();
            //server connection set account to activated
            String[] memberNames = new String[]{"Name", "LoginName", "Password", "Email", "Address", "PhoneNumber"};
            String[] values = caView.extractCredentials();

            try
            {
                //change this line, upon OTP verification, store account details
                //String response = new ServerConnection().sendMessage(ServerConnection.createMessage("CreateAccount", "User", memberNames, values), caView.getActivity());
                JsonObject msg = ServerConnection.createMessage("CreateAccount", "User", memberNames, values);
                @SuppressLint("StaticFieldLeak")
                ServerConnection sc = new ServerConnection(msg, caView.getActivity()) {
                    @Override
                    public void receiveResponse(String response) {
                        try {
                            JsonParser jParser = new JsonParser();
                            JsonObject jResponse = (JsonObject) jParser.parse(response);

                            if (jResponse.get("result").getAsString().equals("Success")) {
                                caView.onSuccessView();


                                //Write to internal storage here
                                String [] cred = caView.extractCredentials();
                                InternalStorage.writeProfileToFile(caView.getActivity(),"profile", cred[0],cred[1], cred[3],cred[4], cred[5]);

                            } else {
                                //do failure
                                caView.onFailureView(jResponse.get("reason").getAsString());
                            }
                        } catch (com.google.gson.JsonSyntaxException jse){
                            System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                            caView.onFailureView(response);
                        }
                    }
                };
                sc.execute(null,null,null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            caView.otpFailure("Invalid OTP, please try again ...");
        }
    }

    @Override
    public void otpFailure()
    {
        sendSMSMessage();
        caView.requestOTP();
    }

    @Override
    public void verifyDetails(String name, String username, String password, String email, String address, String phoneNo)
    {
        //caView.showProgressDialog("Verifying details ...");
        //create connection to server
        //verify details, check if username, email, phone number already exists
        //return boolean value
        Pattern password_policy = Pattern.compile("\\A(?=\\S*?[0-9])(?=\\S*?[a-z])(?=\\S*?[A-Z])\\S{8,}\\z");
        Matcher pwMatcher = password_policy.matcher(password);
        if (pwMatcher.matches()) {
            String[] memberNames = new String[]{"Name", "LoginName", "Password", "Email", "Address", "PhoneNumber"};
            String[] values = new String[]{name, username, password, email, address, phoneNo};

            try {
                //change this line to only verfiy details and not store
                //String response = new ServerConnection().sendMessage(ServerConnection.createMessage("CheckAccountExists", "User", memberNames, values), caView.getActivity());
                JsonObject msg = ServerConnection.createMessage("CheckAccountExists", "User", memberNames, values);
                @SuppressLint("StaticFieldLeak")
                ServerConnection sc = new ServerConnection(msg, caView.getActivity()) {
                    @Override
                    public void receiveResponse(String response) {

                        try {
                            JsonParser jParser = new JsonParser();
                            JsonObject jResponse = (JsonObject) jParser.parse(response);
                            if (jResponse.get("result").getAsString().equals("Success")) {
                                //do success
                                sendSMSMessage();
                                //caView.hideProgressDialog();
                                caView.requestOTP();
                            } else {
                                //do failure
                                //caView.hideProgressDialog();
                                caView.onFailureView(jResponse.get("reason").getAsString());
                            }
                        } catch (com.google.gson.JsonSyntaxException jse){
                            System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                            caView.onFailureView(response);
                        }
                    }
                };
                sc.execute(null,null,null);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            caView.onFailureView("Password needs to be 8 characters long, with at least 1 Upper-case character and 1 digit.");
        }
    }

    protected void sendSMSMessage()
    {
        OTP = new GenerateOTP().getOTP();
        String message = "PayBeam OTP: "+OTP;

        caView.setVariables(message);

        if (ContextCompat.checkSelfPermission(caActivity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(caActivity, Manifest.permission.READ_PHONE_STATE))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(caActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_READ_PHONE_STATE);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(caActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, MY_PERMISSIONS_READ_PHONE_STATE);
        }

        if (ContextCompat.checkSelfPermission(caActivity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(caActivity, Manifest.permission.SEND_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(caActivity, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(caActivity, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }
}
