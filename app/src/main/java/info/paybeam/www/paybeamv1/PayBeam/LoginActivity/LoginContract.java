package info.paybeam.www.paybeamv1.PayBeam.LoginActivity;

import android.view.View;

/**
 * Contract betweem activity and presenter
 */

public interface LoginContract
{
    interface LoginView
    {
        void showHomeView();
        void showCreateAccountView();
        void showErrorMessage();
        void handleAuthentication();
    }

    interface LoginPresenter
    {
        void onLoginButtonClick(View view);
        void onCreateAccountButtonClick(View view);
        void handleAuthentication(String username, String password);
    }
}
