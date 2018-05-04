package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Timer;
import java.util.TimerTask;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.GetCardsModule.GetCards;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.SecurityModule.DESPassPhrase;

/**
 * Created by dflychew on 24/4/18.
 */

public class TopUpWalletPresenter implements TopUpWalletContract.TopUpWalletPresenter
{
    private TopUpWalletContract.TopUpWalletView topUpWalletView;

    TopUpWalletPresenter(TopUpWalletContract.TopUpWalletView view){ topUpWalletView = view; }

    String amount = "";

    @Override
    public void onTopUpWalletPageDisplayed() {
        //Toast.makeText(topUpWalletView.getActivity(),"hello", Toast.LENGTH_SHORT).show();
         topUpWalletView.displayCards(InternalStorage.readCardsFromFile(topUpWalletView.getActivity(),"cards"));

    }

    public String getLastFourDigits(String chosenCard)
    {
        String [] str = chosenCard.split(",");
        return str[0];
    }

    @Override
    public void TopUpWallet(final String amount, String chosenCard){
        Toast.makeText(topUpWalletView.getActivity(),"Amount: "+ amount,Toast.LENGTH_SHORT).show();
        //add progress dialog here then only dismiss after processing
        final ProgressDialog dialog = ProgressDialog.show(topUpWalletView.getActivity(), "",
                "Making Payment. Please wait...", true);
        //Do processing here to draw the amount from card and add to the server
        final String l4d = getLastFourDigits(chosenCard);

        final String token = InternalStorage.readToken(topUpWalletView.getActivity(),"Token");
        final String[] credentials = InternalStorage.readString(topUpWalletView.getActivity(),"Credentials").split(",");

        //get pwd hash
        JsonObject getHashMsg = new JsonObject();
        getHashMsg.addProperty("Header", "GetPasswordHash");
        JsonObject userData = new JsonObject();
        userData.addProperty("LoginName", credentials[0]);
        userData.addProperty("Password", credentials[1]);
        getHashMsg.add("User", userData);

        final String desText = credentials[0] + "," + amount;

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(getHashMsg, topUpWalletView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                String hash = null;
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject) jParser.parse(response);
                    if (jResponse.get("result").getAsString().equals("Success")) {
                        //do success
                        hash = jResponse.get("hash").getAsString();
                        //generateQRView.showHomeView();
                    } else {
                        //do failure
                        dialog.dismiss();
                        topUpWalletView.showErrorMessage(jResponse.get("reason").getAsString());
                    }

                    if(hash != null) {
                        String encrypted = new DESPassPhrase(hash).encrypt(desText);
                        JsonObject topUpMsg = new JsonObject();
                        topUpMsg.addProperty("Header", "TopUpWallet");
                        topUpMsg.addProperty("LoginName", credentials[0]);
                        topUpMsg.addProperty("Amount", amount);
                        topUpMsg.addProperty("CardNo", l4d);
                        topUpMsg.addProperty("Token", token);
                        topUpMsg.addProperty("encrypted", encrypted);

                        ServerConnection sc = new ServerConnection(topUpMsg, topUpWalletView.getActivity()) {
                            @Override
                            public void receiveResponse(String response) {
                                try {
                                    JsonParser jParser = new JsonParser();
                                    JsonObject jResponse = (JsonObject) jParser.parse(response);

                                    if (jResponse.get("result").getAsString().equals("Success")) {
                                        System.out.println("Successfully topped up.");
                                        InternalStorage.writeString(topUpWalletView.getActivity(),"wallet",jResponse.get("NewAmount").getAsBigDecimal().toString());
                                        dialog.dismiss();
                                        topUpWalletView.showSuccess(jResponse.get("reason").getAsString());

                                    } else {
                                        dialog.dismiss();
                                        System.out.println("Failed to top up.");
                                        topUpWalletView.showErrorMessage(jResponse.get("reason").getAsString());
                                    }
                                } catch (com.google.gson.JsonSyntaxException jse) {
                                    System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                                }
                            }
                        };
                        sc.execute(null,null,null);
                    }

                } catch (com.google.gson.JsonSyntaxException jse) {
                    System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        sc.execute(null,null,null);
    }

}
