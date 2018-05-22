package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.widget.EditText;

import java.util.Timer;
import java.util.TimerTask;

import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
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

    TextInputLayout nameLayout;
    TextInputLayout usernameLayout;
    TextInputLayout passwordLayout;
    TextInputLayout emailLayout;
    TextInputLayout phoneNoLayout;
    TextInputLayout addressLayout;

    private ProgressDialog progressDialog;
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

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);


        nameLayout = findViewById(R.id.edit_name_layout);
        usernameLayout = findViewById(R.id.edit_username_layout);
        passwordLayout = findViewById(R.id.edit_password_layout);
        emailLayout = findViewById(R.id.edit_email_layout);
        phoneNoLayout = findViewById(R.id.edit_phoneNo_layout);
        addressLayout = findViewById(R.id.edit_address_layout);

    }

    public void onSuccessView()
    {
        //progressDialog.hide();
        //this.finish();
        //bring user to OTP screen, validate OTP
        //remain on the same screen, show failure message
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage("Account Successfully Created and Activated");
        dlgAlert.setTitle("Success");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //CreateAccountActivity.this.finish();
                        finish();
                    }
                });

        dlgAlert.create().show();
    }

    public void finishActivity()
    {
        this.finish();
    }

    @Override
    public void onFailureView(final String errorMessage)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(errorMessage);
        dlgAlert.setTitle("Error");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (errorMessage.contains("Token Invalid or Expired")) {
                            Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    }
                });
        dlgAlert.create().show();
    }

    @Override
    public void otpFailure(String errorMessage)
    {
        //remain on the same screen, show failure message
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(errorMessage);
        dlgAlert.setTitle("Error");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        dlgAlert.create().show();
    }

    @Override
    public void startLoginActivity()
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgressDialog(String message)
    {
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    @Override
    public void hideProgressDialog()
    {
        progressDialog.dismiss();
    }

    @Override
    public void extractValues() {
        //do checks here before getting the
        boolean nameValid = false;
        boolean usernameValid = false;
        boolean passwordValid = false;
        boolean emailValid = false;
        boolean phoneNoValid = false;
        boolean addressValid = false;

        if(name.getText().length()==0)
        {
            nameLayout.setError("You need to enter a name");
        }
        else
        {
            nameValid = true;
        }

        if(username.getText().length()==0)
        {
            usernameLayout.setError("You need to enter a username");
        }
        else
        {
            usernameValid = true;
        }

        if(password.getText().length()==0)
        {
            passwordLayout.setError("You need to enter a password");
        }
        else
        {
            passwordValid = true;
        }

        if(email.getText().length()==0)
        {
            emailLayout.setError("You need to enter an email");
        }
        else
        {
            emailValid = isEmailValid(email.getText().toString());
            if(!emailValid)
            {
                emailLayout.setError("You need to enter a valid email");
            }
        }

        if(phoneNo.getText().length()==0)
        {
            phoneNoLayout.setError("You need to enter a phone number");
        }
        else
        {
            phoneNoValid = true;
        }

        if(address.getText().length()==0)
        {
            addressLayout.setError("You need to enter an address");
        }
        else
        {
            addressValid = true;
        }



        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                nameLayout.setError("");
            }
        });

        username.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                usernameLayout.setError("");
            }
        });

        password.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                passwordLayout.setError("");
            }
        });

        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                emailLayout.setError("");
            }
        });

        phoneNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                phoneNoLayout.setError("");
            }
        });

        address.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                addressLayout.setError("");
            }
        });


        if(nameValid && usernameValid && passwordValid && emailValid && phoneNoValid && addressValid)
        {
            caPresenter.verifyDetails(name.getText().toString(),
                    username.getText().toString(),
                    password.getText().toString(),
                    email.getText().toString(),
                    address.getText().toString(),
                    phoneNo.getText().toString());
        }

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public String[] extractCredentials() {
        return new String[] {name.getText().toString(),
                username.getText().toString(),
                password.getText().toString(),
                email.getText().toString(),
                address.getText().toString(),
                phoneNo.getText().toString()};
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
        //progressDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Enter OTP to verify your phone no.");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                //progressDialog.setMessage("Verifying OTP ...");
                //progressDialog.show();

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
