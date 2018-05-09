package info.paybeam.www.paybeamv1.PayBeam.LoginActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.widget.EditText;

import info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity.CreateAccountActivity;
import info.paybeam.www.paybeamv1.PayBeam.HomeActivity.HomeActivity;
import info.paybeam.www.paybeamv1.PayBeam.HomeActivity.HomePresenter;
import info.paybeam.www.paybeamv1.PayBeam.OTPModule.GenerateOTP;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.HomeActivityBinding;
import info.paybeam.www.paybeamv1.databinding.LoginActivityBinding;

/*
*Activity handles views
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView
{
    private LoginPresenter loginPresenter;
    private EditText username;
    private EditText password;

    private ProgressDialog progressDialog;
    private String message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private String phoneNo;
    private String OTP;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        loginPresenter = new LoginPresenter(this);
        binding.setLoginPresenter(loginPresenter);

        username = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);


        //For testing purposes we assume successful login and call menu here
        //Intent intent = new Intent(this, HomeActivity.class);
        //startActivity(intent);


    }

    public void handleAuthentication()
    {
        if(username == null ||password == null)
        {
            showErrorMessage("Empty Fields");
        }
        else
        {
            loginPresenter.handleAuthentication(username.getText().toString(), password.getText().toString());
        }
    }

    @Override
    public void showServerError()
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage("Server error, Please try again later...");
        dlgAlert.setTitle("Error");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
    }

    @Override
    public void showHomeView()
    {
        //bring user to home screen
        Intent homeView = new Intent(LoginActivity.this, HomeActivity.class);
        startActivity(homeView);
    }

    @Override
    public void showCreateAccountView()
    {
        Intent createAccountView = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(createAccountView);
    }

    @Override
    public void showErrorMessage(String errorMsg)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(errorMsg);
        dlgAlert.setTitle("Error Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    protected void sendSMSMessage()
    {
        OTP = Integer.toString(new GenerateOTP().getOTP());
        message = "PayBeam OTP: "+OTP;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    //Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Toast.makeText(context, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }

    public void forgotPassword()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter username ...");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();

                phoneNo = loginPresenter.getPhoneNo(m_Text);

                sendSMSMessage();

                dialog.cancel();

                verifyOTP();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void verifyOTP()
    {
        //progressDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Enter OTP to receive new password");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();

                if(m_Text.equals(OTP))
                {
                    message = "PayBeam new password: "+loginPresenter.getNewPassword();

                    dialog.cancel();
                    
                    sendSMSMessage2();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });

        builder.show();
    }

    protected void sendSMSMessage2()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }
}
