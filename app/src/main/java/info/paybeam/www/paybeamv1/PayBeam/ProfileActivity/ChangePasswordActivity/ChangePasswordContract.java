package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ChangePasswordActivity;

import android.app.Activity;
import android.view.View;

/**
 * Created by zicokuang on 3/4/18.
 */

public interface ChangePasswordContract
{
    interface ChangePasswordView
    {
        void extractValues();
        void showDialog(String Title,String message);
        Activity getActivity();
    }

    interface ChangePasswordPresenter
    {
        void onSubmitChangePassButton(View view);
        void localPasswordCheck(String oldPass, String newPass, String retypePass);
        void changePassword(String oldPass, String newPass, String retypePass);
    }
}
