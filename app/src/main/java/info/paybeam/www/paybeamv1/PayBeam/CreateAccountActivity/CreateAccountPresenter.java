package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.view.View;

/**
 * presenter handles create account logic
 */

public class CreateAccountPresenter implements CreateAccountContract.CreateAccountPresenter
{
    private CreateAccountContract.CreateAccountView caView;

    CreateAccountPresenter(CreateAccountContract.CreateAccountView view)
    {
        caView = view;
    }

    //create account, check server, on success, receive OTP message
    //name, username, password (security policy), email, phone number, date of birth, address, postal code

    public void onSubmitButtonClick(View view)
    {
        //call verify details
        //if true, show success screen leading to OTP verification
        //if false, show error message
        caView.onFailureView();
    }

    public boolean verifyDetails()
    {
        //create connection to server
        //verify details, check if username, email, phone number already exists
        //return boolean value
        return true;
    }
}
