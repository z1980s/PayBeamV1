package info.paybeam.www.paybeamv1.PayBeam.WalletActivity;

import android.app.Activity;
import android.view.View;

public interface WalletContract
{
    interface WalletView
    {
        Activity getActivity();
        void showTopUpWalletView();
        void showWithdrawFromWalletView();
        void setWalletBalance(String amount);
        void showErrorMessage(String message);
    }

    interface WalletPresenter
    {
        void getWalletAmount();
        void onWithdrawFromWalletButtonClick(View view);
        void onTopUpWalletButtonClick(View view);
    }
}
