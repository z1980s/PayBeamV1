package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity;

import android.app.Activity;

import java.util.ArrayList;

/**
 * Created by dflychew on 24/4/18.
 */

public interface TopUpWalletContract {

    interface TopUpWalletView
    {
        void displayCards(ArrayList<String> cards);
        Activity getActivity();
    }

    interface TopUpWalletPresenter
    {
        void onTopUpWalletPageDisplayed();
    }
}
