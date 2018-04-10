package info.paybeam.www.paybeamv1.PayBeam.LoginActivity;

import android.view.View;
import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;

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
        String[] memberNames = new String[]{"LoginName", "Password"};
        String[] values = new String[]{username, password};
        try {
            String response = new ServerConnection().sendMessage(ServerConnection.createMessage("Login", "User", memberNames, values), loginView.getActivity());
            if(response.contains("Success")) {
                //do success
                System.out.println("Login Success");
                loginView.showHomeView();
            } else {
                //do failure
                System.out.println("Login Failure");
                loginView.showErrorMessage();
            }
        } catch (Exception e) {
            loginView.showServerError();
            e.printStackTrace();
        }
        //create server connection, check username and password
        //if valid, call loginView.showHomeView
        //if invalid, call loginView.showErrorMessage
        //if response null, show server error
    }
}
