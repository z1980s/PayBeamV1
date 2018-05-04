package info.paybeam.www.paybeamv1.PayBeam.WalletActivity;

import android.view.View;

import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

public class WalletPresenter implements WalletContract.WalletPresenter
{
    private WalletContract.WalletView walletView;


    WalletPresenter(WalletContract.WalletView view)
    {
        walletView = view;
    }

    @Override
    public String getWalletAmount() {
        return "$"+InternalStorage.readString(walletView.getActivity(), "wallet");
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
