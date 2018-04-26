package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Toast;

import java.util.Timer;
import java.util.TimerTask;

import info.paybeam.www.paybeamv1.PayBeam.GetCardsModule.GetCards;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

/**
 * Created by dflychew on 24/4/18.
 */

public class TopUpWalletPresenter implements TopUpWalletContract.TopUpWalletPresenter
{
    private TopUpWalletContract.TopUpWalletView topUpWalletView;

    TopUpWalletPresenter(TopUpWalletContract.TopUpWalletView view){ topUpWalletView = view; }

    String amount = "";

    @Override
    public void onTopUpWalletPageDisplayed() {
        //Toast.makeText(topUpWalletView.getActivity(),"hello", Toast.LENGTH_SHORT).show();
         topUpWalletView.displayCards(InternalStorage.readCardsFromFile(topUpWalletView.getActivity().getApplicationContext(),"cards"));

    }




    public String getLastFourDigits(String chosenCard)
    {
        String [] str = chosenCard.split(",");
        return str[0];
    }



    @Override
    public void TopUpWallet(String amount, String chosenCard){
        Toast.makeText(topUpWalletView.getActivity(),"Amount: "+ amount,Toast.LENGTH_SHORT).show();
        //add progress dialog here then only dismiss after processing

        final ProgressDialog dialog = ProgressDialog.show(topUpWalletView.getActivity(), "",
                "Making Payment. Please wait...", true);


        //Do processing here to draw the amount from card and add to the server
        String l4d = getLastFourDigits(chosenCard);

        //after the functions are done then close the activity
        dialog.dismiss();
    }

}
