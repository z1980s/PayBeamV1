package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity;

import android.app.Activity;
import android.view.View;
import android.widget.ListView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by dflychew on 24/4/18.
 */

public interface TopUpWalletContract {

    interface TopUpWalletView
    {
        void displayCards(ArrayList<JsonObject> cards);
        void showSuccess(String message);
        void showErrorMessage(String errorMsg);
        String showDialog(View view);
        Activity getActivity();

    }

    interface TopUpWalletPresenter
    {
        void TopUpWallet(String amount, String chosenCard);
        void onTopUpWalletPageDisplayed();


    }
}
