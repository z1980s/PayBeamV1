package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity;

import android.app.Activity;
import android.view.View;

import org.json.JSONObject;

public interface ProfileContract
{
    interface ProfileView
    {
        void displayProfileDetails(JSONObject obj);
        Activity getActivity();
        void showEditProfileView();
    }

    interface ProfilePresenter
    {
        void onPageDisplayed();
        void onEditProfileButtonClick(View view);
    }
}
