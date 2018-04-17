package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
}
