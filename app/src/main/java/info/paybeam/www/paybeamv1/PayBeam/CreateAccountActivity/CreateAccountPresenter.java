package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.OTPModule.GenerateOTP;

/**
 * presenter handles create account logic
 */

public class CreateAccountPresenter implements CreateAccountContract.CreateAccountPresenter
{
    private CreateAccountContract.CreateAccountView caView;
    private Activity caActivity;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private int OTP;

    CreateAccountPresenter(CreateAccountContract.CreateAccountView view)
    {
        caView = view;
        caActivity = caView.getActivity();
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
    public void checkOTP(int otp)
    {
        if(OTP == otp)
        {
            caView.onSuccessView();
            //server connection set account to activated
        }
        else
        {
            caView.onFailureView("Invalid OTP, please try again ...");
        }
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
                //caView.onSuccessView();
                sendSMSMessage();
                caView.requestOTP();
            } else {
                //do failure
                caView.onFailureView(response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sendSMSMessage()
    {
        OTP = new GenerateOTP().getOTP();
        String message = "PayBeam OTP: , "+OTP;

        caView.setVariables(message);

        if (ContextCompat.checkSelfPermission(caActivity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(caActivity, Manifest.permission.SEND_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(caActivity, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(caActivity, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }
}
