package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity;

import android.app.Activity;
import android.view.View;

import org.json.JSONObject;

public interface EditProfileContract
{
    interface EditProfileView
    {
        void displayFieldDetails(JSONObject obj);
        Activity getActivity();
        JSONObject extractValues();
        void finishActivity();
    }

    interface EditProfilePresenter
    {
        void onPageDisplayed();
        void onSubmitButtonClick(View view);
    }
}
