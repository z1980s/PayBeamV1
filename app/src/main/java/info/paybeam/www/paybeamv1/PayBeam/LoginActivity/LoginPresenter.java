package info.paybeam.www.paybeamv1.PayBeam.LoginActivity;

import android.view.View;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

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
            JsonParser jParser = new JsonParser();
            JsonObject jResponse = (JsonObject)jParser.parse(response);
            if(jResponse.get("result").getAsString().equals("Success")) {
                //do success
                String token = jResponse.get("token").getAsString();

                InternalStorage.writeToken(loginView.getActivity(), "Token", token);
                String iToken = InternalStorage.readToken(loginView.getActivity(), "Token");
                if (iToken != null) {
                    System.out.println("Base64 Token: " + iToken);
                } else {
                    System.out.println("Token file doesn't Exist");
                }
                loginView.showHomeView();
            } else {
                //do failure
                loginView.showErrorMessage(jResponse.get("reason").getAsString());
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
