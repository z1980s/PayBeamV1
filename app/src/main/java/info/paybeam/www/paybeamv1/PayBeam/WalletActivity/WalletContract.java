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
    }

    interface WalletPresenter
    {
        String getWalletAmount();
        void onWithdrawFromWalletButtonClick(View view);
        void onTopUpWalletButtonClick(View view);
    }
}
