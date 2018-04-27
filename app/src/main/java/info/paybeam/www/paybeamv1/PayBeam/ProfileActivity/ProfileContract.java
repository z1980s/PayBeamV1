package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity;

import android.app.Activity;
import android.view.View;

import com.google.gson.JsonObject;

import org.json.JSONObject;

public interface ProfileContract
{
    interface ProfileView
    {
        void displayProfileDetails(JsonObject obj);
        Activity getActivity();
        void showEditProfileView();
    }

    interface ProfilePresenter
    {
        void onPageDisplayed();
        void onEditProfileButtonClick(View view);
    }
}
