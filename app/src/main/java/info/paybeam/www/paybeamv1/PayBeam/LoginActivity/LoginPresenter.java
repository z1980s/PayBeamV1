package info.paybeam.www.paybeamv1.PayBeam.LoginActivity;

import android.view.View;

/**
 * Presenter handles Login logic
 */

public class LoginPresenter implements LoginContract.LoginPresenter
{
    private LoginContract.LoginView loginView;

    LoginPresenter(LoginContract.LoginView view)
    {
        loginView = view;
    }

    @Override
    public void onLoginButtonClick(View view)
    {
        //establish connection with server and authenticate user
    }

    @Override
    public void onCreateAccountButtonClick(View view)
    {
        //show create account page
        loginView.showCreateAccountView();
    }
}
