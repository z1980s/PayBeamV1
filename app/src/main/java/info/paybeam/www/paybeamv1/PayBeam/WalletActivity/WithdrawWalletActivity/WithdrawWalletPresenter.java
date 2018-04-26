package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WithdrawWalletActivity;

import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WalletContract;

/**
 * Created by dflychew on 24/4/18.
 */

public class WithdrawWalletPresenter implements WithdrawWalletContract.WithdrawWalletPresenter
{
    private WithdrawWalletContract.WithdrawWalletView withdrawWalletView;

    WithdrawWalletPresenter(WithdrawWalletContract.WithdrawWalletView view)
    {
        withdrawWalletView = view;
    }


    @Override
    public void pageDisplayed() {
        withdrawWalletView.getAmountDialog();
    }



    @Override
    public void withdrawAmount(String amount,String chosenCard) {
        //Processing to be done for withdraw amount
    }

    @Override
    public void amountRetrieved() {
        withdrawWalletView.displayCards(InternalStorage.readCardsFromFile(withdrawWalletView.getActivity().getApplicationContext(),"cards"));

    }
}
