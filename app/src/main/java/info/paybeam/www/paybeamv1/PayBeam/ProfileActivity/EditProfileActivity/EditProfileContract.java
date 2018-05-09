package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity;

import android.app.Activity;
import android.view.View;

import com.google.gson.JsonObject;

public interface EditProfileContract
{
    interface EditProfileView
    {
        void displayFieldDetails(JsonObject obj);
        Activity getActivity();
        void extractValues();
        void showSuccess(String message);
        void showErrorMessage(String errorMsg);
        void finishActivity();
    }

    interface EditProfilePresenter
    {
        void onPageDisplayed();
        void onSubmitButtonClick(View view);
        void submitProfileChanges(JsonObject newObj);
    }
}
