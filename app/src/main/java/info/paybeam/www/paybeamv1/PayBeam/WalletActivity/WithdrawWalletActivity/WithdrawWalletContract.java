package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WithdrawWalletActivity;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by dflychew on 24/4/18.
 */

public interface WithdrawWalletContract {

    interface WithdrawWalletView
    {
        void getAmountDialog(View view);
        void displayCards(ArrayList<String> cards);
        void showSuccess(String message);
        void showErrorMessage(String errorMsg);
        Activity getActivity();
        void finishActivity();
    }

    interface  WithdrawWalletPresenter
    {
        void pageDisplayed();
        void withdrawAmount(String amount, String chosenCard);
        String getWalletBalance();
        boolean enoughValueInWallet(String amount);
    }

}
