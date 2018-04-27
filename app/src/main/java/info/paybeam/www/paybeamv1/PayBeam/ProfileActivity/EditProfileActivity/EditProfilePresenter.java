package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity;

import android.view.View;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

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
        JSONObject currentObj= InternalStorage.readProfileFromFile(editProfileView.getActivity(),"profile");
        JSONObject newObj= editProfileView.extractValues();

        boolean changed = false;

        // check if fields have been changed
        try {

            if(!newObj.getString("name").equals(currentObj.getString("name")))
            {
                changed = true;
            }
            if(!newObj.getString("phoneNo").equals(currentObj.getString("phoneNo")))
            {
                changed = true;
            }
            if(!newObj.getString("email").equals(currentObj.getString("email")))
            {
                changed = true;
            }
            if(!newObj.getString("address").equals(currentObj.getString("address")))
            {
                changed = true;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        //if any changes then write to server
        if(changed)
        {
            //Include username inside new json object
            try {
                newObj.put("username",currentObj.getString("username"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //WRITE TO SERVER

        }
        else
        {
            Toast.makeText(editProfileView.getActivity(),"Nothing changed",Toast.LENGTH_SHORT).show();
        }

        //Need to add boolean return from server on success!!!!






        //If approved by server, write to internal storage
        //name, username, email, address, phoneNo
        try {
            InternalStorage.writeProfileToFile(editProfileView.getActivity(),
                                                "profile",
                                                newObj.getString("name"),
                                                newObj.getString("username"),
                                                newObj.getString("email"),
                                                newObj.getString("address"),
                                                newObj.getString("phoneNo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
