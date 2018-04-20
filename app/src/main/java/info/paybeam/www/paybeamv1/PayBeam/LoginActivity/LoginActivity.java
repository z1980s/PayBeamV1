package info.paybeam.www.paybeamv1.PayBeam.LoginActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity.CreateAccountActivity;
import info.paybeam.www.paybeamv1.PayBeam.HomeActivity.HomeActivity;
import info.paybeam.www.paybeamv1.PayBeam.HomeActivity.HomePresenter;
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
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);

    }

    public void handleAuthentication()
    {
        loginPresenter.handleAuthentication(username.getText().toString(), password.getText().toString());
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
}
