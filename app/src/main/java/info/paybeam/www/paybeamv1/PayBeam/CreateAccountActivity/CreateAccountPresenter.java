package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.view.View;

import java.security.MessageDigest;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.SecurityModule.MD5;

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
        caView.extractValues();
        //caView.onFailureView();
    }

    @Override
    public void verifyDetails(String name, String username, String password, String email, String address, String phoneNo)
    {
        //create connection to server
        //verify details, check if username, email, phone number already exists
        //return boolean value
        String[] memberNames = new String[]{"Name", "LoginName", "Password", "Email", "Address", "PhoneNumber"};
        String[] values = new String[]{name, username, password, email, address, phoneNo};

        try {
            String response = new ServerConnection().sendMessage(ServerConnection.createMessage("CreateAccount", "User", memberNames, values), caView.getActivity());
            System.out.println("Response: " + response);
            if(response.contains("Success")) {
                //do success
                caView.onSuccessView();
            } else {
                //do failure
                caView.onFailureView(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
