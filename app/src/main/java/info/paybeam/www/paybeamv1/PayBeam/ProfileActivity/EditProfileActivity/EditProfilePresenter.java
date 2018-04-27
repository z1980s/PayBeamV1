package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.MalformedJsonException;

import org.json.JSONException;
import org.json.JSONObject;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

public class EditProfilePresenter implements EditProfileContract.EditProfilePresenter
{
    private EditProfileContract.EditProfileView editProfileView;

    EditProfilePresenter(EditProfileContract.EditProfileView view)
    {
        editProfileView = view;
    }

    @Override
    public void onPageDisplayed() {
        editProfileView.displayFieldDetails(InternalStorage.readProfileFromFile(editProfileView.getActivity(),"profile"));
    }


    @Override
    public void onSubmitButtonClick(View view) {
        //check for any changes
        //JSONObject currentObj= InternalStorage.readProfileFromFile(editProfileView.getActivity(),"profile");
        //JSONObject newObj= editProfileView.extractValues();
        final JsonObject currentObj = InternalStorage.readProfileFromFile(editProfileView.getActivity(),"profile");
        final JsonObject newObj = editProfileView.extractValues();

        boolean changed = false;

        // check if fields have been changed
        if(!newObj.get("name").getAsString().equals(currentObj.get("name").getAsString()) ||
            !newObj.get("phoneNo").getAsString().equals(currentObj.get("phoneNo").getAsString()) ||
            !newObj.get("email").getAsString().equals(currentObj.get("email").getAsString()) ||
            !newObj.get("address").getAsString().equals(currentObj.get("address").getAsString()))
        {
            changed = true;
        }


        //if any changes then write to server
        if(changed)
        {
            //Include username inside new json object
            newObj.addProperty("LoginName",currentObj.get("username").getAsString());
            newObj.addProperty("Token", InternalStorage.readToken(editProfileView.getActivity(),"Token"));
            newObj.addProperty("Header", "ChangeProfileDetails");
            //WRITE TO SERVER
            @SuppressLint("StaticFieldLeak")
            ServerConnection sc = new ServerConnection(newObj, editProfileView.getActivity()) {
                @Override
                public void receiveResponse(String response) {
                    try {
                        JsonParser jParser = new JsonParser();
                        JsonObject jResponse = (JsonObject) jParser.parse(response);

                        if (jResponse.get("result").getAsString().equals("Success")) {

                            //If approved by server, write to internal storage
                            //name, username, email, address, phoneNo
                            InternalStorage.writeProfileToFile(editProfileView.getActivity(),
                                    "profile",
                                    newObj.get("name").getAsString(),
                                    newObj.get("LoginName").getAsString(),
                                    newObj.get("email").getAsString(),
                                    newObj.get("address").getAsString(),
                                    newObj.get("phoneNo").getAsString());

                            editProfileView.showSuccess(jResponse.get("reason").getAsString());
                        } else {
                            editProfileView.showErrorMessage(jResponse.get("reason").getAsString());
                        }
                    } catch (com.google.gson.JsonSyntaxException jse) {
                        editProfileView.showErrorMessage("Failed to Connect to Server");
                        System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                    }
                }
            };
            sc.execute(null,null,null);

        }
        else
        {
            Toast.makeText(editProfileView.getActivity(),"Nothing changed",Toast.LENGTH_SHORT).show();
        }
    }
}
