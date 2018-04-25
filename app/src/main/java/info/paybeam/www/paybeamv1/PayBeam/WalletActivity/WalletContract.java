package info.paybeam.www.paybeamv1.PayBeam.WalletActivity;

import android.app.Activity;
import android.view.View;

public interface WalletContract
{
    interface WalletView
    {
        Activity getActivity();
        void showTopUpWalletView();
    }

    interface WalletPresenter
    {
        String getWalletAmount();
        void onTopUpWalletButtonClick(View view);
    }
}
