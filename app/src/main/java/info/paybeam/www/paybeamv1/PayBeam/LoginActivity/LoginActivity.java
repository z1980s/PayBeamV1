package info.paybeam.www.paybeamv1.PayBeam.LoginActivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity.CreateAccountActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.LoginActivityBinding;

/*
*Activity handles views
 */

public class LoginActivity extends AppCompatActivity implements LoginContract.LoginView
{
    private LoginPresenter loginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        LoginActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.login_activity);
        loginPresenter = new LoginPresenter(this);
        binding.setLoginPresenter(loginPresenter);
    }


    @Override
    public void showHomeView()
    {

    }

    @Override
    public void showCreateAccountView()
    {
        Intent createAccountView = new Intent(LoginActivity.this, CreateAccountActivity.class);
        startActivity(createAccountView);
    }

    @Override
    public void showErrorMessage()
    {

    }
}
