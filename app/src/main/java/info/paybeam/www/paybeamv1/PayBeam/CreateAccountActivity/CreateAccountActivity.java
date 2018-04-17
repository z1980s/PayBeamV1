package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.widget.EditText;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.CreateAccountActivityBinding;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountContract.CreateAccountView
{
    private CreateAccountPresenter caPresenter;
    private EditText name;
    private EditText username;
    private EditText password;
    private EditText email;
    private EditText phoneNo;
    private EditText address;

    private String message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);
        CreateAccountActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.create_account_activity);
        caPresenter = new CreateAccountPresenter(this);
        binding.setCreateAccountPresenter(caPresenter);

        name = findViewById(R.id.nameEditText);
        username = findViewById(R.id.usernameEditText);
        password = findViewById(R.id.passwordEditText);
        email = findViewById(R.id.emailEditText);
        phoneNo = findViewById(R.id.phoneNoEditText);
        address = findViewById(R.id.addressEditText);
    }

    public void onSuccessView()
    {
        //bring user to OTP screen, validate OTP
        //remain on the same screen, show failure message
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage("Account Successfully Created");
        dlgAlert.setTitle("Success");
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
    public void onFailureView(String errorMessage)
    {
        //remain on the same screen, show failure message
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(errorMessage);
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

        //check and remove trace of added account
    }

    @Override
    public void extractValues() {
        caPresenter.verifyDetails(name.getText().toString(),
                                  username.getText().toString(),
                                  password.getText().toString(),
                                  email.getText().toString(),
                                  address.getText().toString(),
                                  phoneNo.getText().toString());
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void setVariables(String message)
    {
        this.message = message;
    }

    @Override
    public void requestOTP()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Enter OTP to verify your phone no.");

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
                caPresenter.checkOTP(Integer.parseInt(m_Text));
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
                    smsManager.sendTextMessage(phoneNo.getText().toString(), null, message, null, null);
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
}
