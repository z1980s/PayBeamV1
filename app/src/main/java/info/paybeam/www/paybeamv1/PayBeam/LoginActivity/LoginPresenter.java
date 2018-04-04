package info.paybeam.www.paybeamv1.PayBeam.LoginActivity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;

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
        loginView.handleAuthentication();



    }

    @Override
    public void onCreateAccountButtonClick(View view)
    {
        //show create account page
        loginView.showCreateAccountView();
    }

    public void handleAuthentication(String username, String password)
    {
        //create server connection, check username and password
        //if valid, call loginView.showHomeView
        //if invalid, call loginView.showErrorMessage
    }
}
