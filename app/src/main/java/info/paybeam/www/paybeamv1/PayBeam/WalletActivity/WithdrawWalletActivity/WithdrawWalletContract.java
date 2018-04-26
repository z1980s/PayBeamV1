package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WithdrawWalletActivity;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by dflychew on 24/4/18.
 */

public interface WithdrawWalletContract {

    interface WithdrawWalletView
    {
        void getAmountDialog();
        void displayCards(ArrayList<String> cards);
        Activity getActivity();
        void finishActivity();
        void updateWithdrawButton();
    }

    interface  WithdrawWalletPresenter
    {
        void pageDisplayed();
        void withdrawAmount(String amount, String chosenCard);
        void amountRetrieved();
        String getWalletBalance();
        boolean enoughValueInWallet(String amount);
    }

}
