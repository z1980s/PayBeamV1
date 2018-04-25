package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity;

import android.widget.Toast;

import info.paybeam.www.paybeamv1.PayBeam.GetCardsModule.GetCards;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

/**
 * Created by dflychew on 24/4/18.
 */

public class TopUpWalletPresenter implements TopUpWalletContract.TopUpWalletPresenter
{
    private TopUpWalletContract.TopUpWalletView topUpWalletView;

    TopUpWalletPresenter(TopUpWalletContract.TopUpWalletView view){ topUpWalletView = view; }

    @Override
    public void onTopUpWalletPageDisplayed() {
        Toast.makeText(topUpWalletView.getActivity(),"hello", Toast.LENGTH_SHORT).show();
         topUpWalletView.displayCards(InternalStorage.readCardsFromFile(topUpWalletView.getActivity().getApplicationContext(),"cards"));
    }
}
