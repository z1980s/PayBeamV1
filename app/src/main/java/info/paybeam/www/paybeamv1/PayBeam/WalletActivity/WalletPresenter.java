package info.paybeam.www.paybeamv1.PayBeam.WalletActivity;

import android.annotation.SuppressLint;
import android.view.View;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

public class WalletPresenter implements WalletContract.WalletPresenter
{
    private WalletContract.WalletView walletView;


    WalletPresenter(WalletContract.WalletView view)
    {
        walletView = view;
    }

    @Override
    public void getWalletAmount() {
        //return "$"+InternalStorage.readString(walletView.getActivity(), "wallet");


        String credentials[] = InternalStorage.readString(walletView.getActivity(),"Credentials").split(",");
        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "GetWalletAmount");
        msg.addProperty("LoginName", credentials[0]);
        msg.addProperty("Token", InternalStorage.readString(walletView.getActivity(),"Token"));

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg,walletView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject) jParser.parse(response);
                    if (jResponse.get("result").getAsString().equals("Success")) {
                        String balance = jResponse.get("NewAmount").getAsString();
                        walletView.setWalletBalance(balance);
                    } else {
                        System.out.println(jResponse.get("reason").getAsString());
                        walletView.showErrorMessage(jResponse.get("reason").getAsString());
                    }
                } catch (Exception e) {
                    System.out.println("Unable to connect to server to retrieve Wallet balance");
                    walletView.showErrorMessage("Unable to connect to server to retrieve Wallet balance");
                }
            }
        };
        sc.execute(null,null,null);


    }

    @Override
    public void onWithdrawFromWalletButtonClick(View view) {
        walletView.showWithdrawFromWalletView();
    }

    @Override
    public void onTopUpWalletButtonClick(View view) {
        walletView.showTopUpWalletView();
    }
}
