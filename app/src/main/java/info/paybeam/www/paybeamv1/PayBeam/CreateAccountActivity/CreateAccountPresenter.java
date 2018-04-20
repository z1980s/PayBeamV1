package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

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
            //caView.onSuccessView();
            //server connection set account to activated
            String[] memberNames = new String[]{"Name", "LoginName", "Password", "Email", "Address", "PhoneNumber"};
            String[] values = caView.extractCredentials();
            try
            {
                //change this line, upon OTP verification, store account details
                String response = new ServerConnection().sendMessage(ServerConnection.createMessage("CreateAccount", "User", memberNames, values), caView.getActivity());
                JsonParser jParser = new JsonParser();
                JsonObject jResponse = (JsonObject) jParser.parse(response);

                if(jResponse.get("result").getAsString().equals("Success")) {
                    caView.onSuccessView();
                } else {
                    //do failure
                    caView.onFailureView(jResponse.get("reason").getAsString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            caView.otpFailure("Invalid OTP, please try again ...");
        }
    }

    @Override
    public void otpFailure()
    {
        sendSMSMessage();
        caView.requestOTP();
    }

    @Override
    public void verifyDetails(String name, String username, String password, String email, String address, String phoneNo)
    {
        //caView.showProgressDialog("Verifying details ...");
        //create connection to server
        //verify details, check if username, email, phone number already exists
        //return boolean value
        String[] memberNames = new String[]{"Name", "LoginName", "Password", "Email", "Address", "PhoneNumber"};
        String[] values = new String[]{name, username, password, email, address, phoneNo};

        try {
            //change this line to only verfiy details and not store
            String response = new ServerConnection().sendMessage(ServerConnection.createMessage("CheckAccountExists", "User", memberNames, values), caView.getActivity());
            JsonParser jParser = new JsonParser();
            JsonObject jResponse = (JsonObject) jParser.parse(response);
            if(jResponse.get("result").getAsString().equals("Success")) {
                //do success
                sendSMSMessage();
                //caView.hideProgressDialog();
                caView.requestOTP();
            } else {
                //do failure
                //caView.hideProgressDialog();
                caView.onFailureView(jResponse.get("reason").getAsString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void sendSMSMessage()
    {
        OTP = new GenerateOTP().getOTP();
        String message = "PayBeam OTP: "+OTP;

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
