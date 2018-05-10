package info.paybeam.www.paybeamv1.PayBeam.LoginActivity;

import android.annotation.SuppressLint;
import android.util.MalformedJsonException;
import android.view.View;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnectionCallback;
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
            JsonObject msg = ServerConnection.createMessage("Login", "User", memberNames, values);
            final String user = username;
            final String pass = password;

            @SuppressLint("StaticFieldLeak")
            ServerConnection sc = new ServerConnection(msg, loginView.getActivity()) {
                @Override
                public void receiveResponse(String response) {
                    try {
                        JsonParser jParser = new JsonParser();
                        JsonObject jResponse = (JsonObject) jParser.parse(response);
                        if (jResponse.get("result").getAsString().equals("Success")) {
                            //do success
                            String token = jResponse.get("token").getAsString();
                            InternalStorage.writeToken(loginView.getActivity(), "Token", token);
                            InternalStorage.writeCredentials(loginView.getActivity(), "Credentials", user, pass);
                            InternalStorage.writeString(loginView.getActivity(), "wallet", jResponse.get("balance").getAsBigDecimal().toString());
                            //String data = InternalStorage.readString(loginView.getActivity(), "Credentials");
                            //String tokendata = InternalStorage.readString(loginView.getActivity(), "Credentials");
                            File profileFile = new File("profile");
                            if (!profileFile.exists()) {
                                System.out.println("Profile Does not exist, writing...");
                                JsonObject userProfile = jResponse.getAsJsonObject("UserProfile");
                                InternalStorage.writeProfileToFile(loginView.getActivity(), "profile",
                                        userProfile.get("Name").getAsString(),
                                        user,
                                        userProfile.get("Email").getAsString(),
                                        userProfile.get("Address").getAsString(),
                                        userProfile.get("PhoneNumber").getAsString());
                            }

                            ArrayList<JsonObject> cardArrayList = new ArrayList<JsonObject>();
                            JsonObject cardList = jResponse.getAsJsonObject("CardList");
                            if (cardList.get("count").getAsInt() > 0) {
                                for (int a = 0; a < cardList.get("count").getAsInt(); a++) {
                                    JsonObject card = cardList.get("Card_" + (a + 1)).getAsJsonObject();
                                    String cardNo = card.get("CardNo").getAsString();
                                    String cardExpiry = card.get("CardExpiry").getAsString();
                                    String cardType = card.get("CardType").getAsString();
                                    Boolean isDefault = card.get("Default").getAsBoolean();
                                    JsonObject newCard = new JsonObject();
                                    newCard.addProperty("cardNum", cardNo);
                                    newCard.addProperty("expiryDate", cardExpiry);
                                    newCard.addProperty("cardType", cardType);
                                    newCard.addProperty("primary", isDefault);
                                    cardArrayList.add(newCard);
                                }

                            }
                            InternalStorage.writeCardListToFile(loginView.getActivity(),"cards", cardArrayList);
                            loginView.showHomeView();
                        } else {
                            //do failure
                            loginView.showErrorMessage(jResponse.get("reason").getAsString());
                        }
                    } catch (com.google.gson.JsonSyntaxException jse){
                        System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                        loginView.showErrorMessage(response);
                    }
                }
            };

            sc.execute(null,null,null);

        } catch (Exception e) {
            loginView.showServerError();
            e.printStackTrace();
        }
        //create server connection, check username and password
        //if valid, call loginView.showHomeView
        //if invalid, call loginView.showErrorMessage
        //if response null, show server error
    }

    public void onForgotPasswordClick(View view)
    {
        loginView.forgotPassword();
    }

    public void getPhoneNo(final String username)
    {
        //retrieve phone number from server and return string value
        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "GetPhoneNumber");
        msg.addProperty("LoginName", username);

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg, loginView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject)jParser.parse(response);

                    if (jResponse.get("result").getAsString().equals("Success")) {
                        String phoneNumber = jResponse.get("PhoneNumber").getAsString();
                        InternalStorage.writeString(loginView.getActivity(), "PhoneNumber", phoneNumber);
                        //System.out.println("PhoneNumber");
                        loginView.setPhoneNo(phoneNumber);
                        loginView.sendSMSMessage();
                        loginView.verifyOTP();
                    } else {
                        //loginView.showErrorMessage("Failed to retr");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    loginView.showErrorMessage("A System Error has Occurred, Please try again later.");
                }
            }
        };
        sc.execute(null,null,null);
    }

    public void getNewPassword(String username)
    {
        //retrieve phone number from server and return string value
        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "GetNewPassword");
        msg.addProperty("LoginName", username);

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg, loginView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject)jParser.parse(response);

                    if (jResponse.get("result").getAsString().equals("Success")) {
                        String newPassword = jResponse.get("NewPassword").getAsString();
                        String message = "PayBeam new password: " + newPassword;
                        loginView.setMessage(message);
                        loginView.sendSMSMessage2();
                        //InternalStorage.writeString(loginView.getActivity(), "NewPassword", newPassword);
                    } else {
                        //loginView.showErrorMessage("Failed to retr");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    loginView.showErrorMessage("A System Error has Occurred, Please try again later.");
                }
            }
        };
        sc.execute(null,null,null);
    }
}
