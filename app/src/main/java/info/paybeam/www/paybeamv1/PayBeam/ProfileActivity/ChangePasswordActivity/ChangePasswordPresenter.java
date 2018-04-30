package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ChangePasswordActivity;

import android.view.View;
import android.widget.Toast;

import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

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
                changePassword(oldPass, newPass, retypePass);
        }
        else
        {
                //display Dialog
                cpView.showDialog("Error","New password does not match re-typed password");
        }
    }


    @Override
    public void changePassword(String oldPass, String newPass, String retypePass) {
        //Initiate connection with server
        //Check old password before changing to new password

        //Upon success write to Internal Storage

    }


}
