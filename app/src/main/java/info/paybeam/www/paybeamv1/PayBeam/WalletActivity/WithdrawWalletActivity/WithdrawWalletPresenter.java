package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WithdrawWalletActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.SecurityModule.DESPassPhrase;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WalletContract;

/**
 * Created by dflychew on 24/4/18.
 */

public class WithdrawWalletPresenter implements WithdrawWalletContract.WithdrawWalletPresenter
{
    private WithdrawWalletContract.WithdrawWalletView withdrawWalletView;

    WithdrawWalletPresenter(WithdrawWalletContract.WithdrawWalletView view)
    {
        withdrawWalletView = view;
    }


    @Override
    public void pageDisplayed() {
        //withdrawWalletView.getAmountDialog();


        //on page displayed
        //display the cards
        withdrawWalletView.displayCards(InternalStorage.readCardsFromFile(withdrawWalletView.getActivity().getApplicationContext(),"cards"));


    }

    public String getLastFourDigits(String chosenCard)
    {
        String [] str = chosenCard.split(",");
        return str[0];
    }

    @Override
    public void withdrawAmount(final String amount,String chosenCard) {
        //Processing to be done for withdraw amount
        Toast.makeText(withdrawWalletView.getActivity(),"Amount: "+ amount,Toast.LENGTH_SHORT).show();

        //add progress dialog here then only dismiss after processing
        final ProgressDialog dialog = ProgressDialog.show(withdrawWalletView.getActivity(), "",
                "Making Payment. Please wait...", true);

        //get last 4 digits from card
        final String l4d = getLastFourDigits(chosenCard);
        //Do processing here to draw the amount from wallet and add to the card
        final String token = InternalStorage.readToken(withdrawWalletView.getActivity(),"Token");
        final String[] credentials = InternalStorage.readString(withdrawWalletView.getActivity(),"Credentials").split(",");

        //get pwd hash
        JsonObject getHashMsg = new JsonObject();
        getHashMsg.addProperty("Header", "GetPasswordHash");
        JsonObject userData = new JsonObject();
        userData.addProperty("LoginName", credentials[0]);
        userData.addProperty("Password", credentials[1]);
        getHashMsg.add("User", userData);

        final String desText = credentials[0] + "," + amount;

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(getHashMsg, withdrawWalletView.getActivity()) {
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
                        withdrawWalletView.showErrorMessage(jResponse.get("reason").getAsString());
                    }

                    if(hash != null) {
                        String encrypted = new DESPassPhrase(hash).encrypt(desText);
                        JsonObject topUpMsg = new JsonObject();
                        topUpMsg.addProperty("Header", "WithdrawFromWallet");
                        topUpMsg.addProperty("LoginName", credentials[0]);
                        topUpMsg.addProperty("Amount", amount);
                        topUpMsg.addProperty("CardNo", l4d);
                        topUpMsg.addProperty("Token", token);
                        topUpMsg.addProperty("encrypted", encrypted);

                        ServerConnection sc = new ServerConnection(topUpMsg, withdrawWalletView.getActivity()) {
                            @Override
                            public void receiveResponse(String response) {
                                try {
                                    JsonParser jParser = new JsonParser();
                                    JsonObject jResponse = (JsonObject) jParser.parse(response);

                                    if (jResponse.get("result").getAsString().equals("Success")) {
                                        System.out.println("Successfully withdrawn from wallet.");
                                        InternalStorage.writeString(withdrawWalletView.getActivity(),"wallet",jResponse.get("NewAmount").getAsBigDecimal().toString());
                                        dialog.dismiss();
                                        withdrawWalletView.showSuccess(jResponse.get("reason").getAsString());

                                    } else {
                                        dialog.dismiss();
                                        System.out.println("Failed to withdraw from wallet.");
                                        withdrawWalletView.showErrorMessage(jResponse.get("reason").getAsString());
                                    }
                                } catch (com.google.gson.JsonSyntaxException jse) {
                                    System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                                    withdrawWalletView.showErrorMessage("Unable to Connect to Server!");
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


    @Override
    public String getWalletBalance() {
        return InternalStorage.readString(withdrawWalletView.getActivity().getApplicationContext(),"wallet");
    }

    @Override
    public boolean enoughValueInWallet(String amount) {
        //InternalStorage.writeString(withdrawWalletView.getActivity().getApplicationContext(),"wallet","51");
        String amt= InternalStorage.readString(withdrawWalletView.getActivity().getApplicationContext(),"wallet");

        if(Float.parseFloat(amt)>=Float.parseFloat(amount))
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}
