package info.paybeam.www.paybeamv1.PayBeam.WalletActivity;

public class WalletPresenter implements WalletContract.WalletPresenter
{
    private WalletContract.WalletView walletView;

    WalletPresenter(WalletContract.WalletView view)
    {
        walletView = view;
    }
}
