package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WithdrawWalletActivity;

import android.app.ProgressDialog;
import android.widget.Toast;

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
        //withdrawWalletView.getAmountDialog();


        //on page displayed
        //display the cards
        withdrawWalletView.displayCards(InternalStorage.readCardsFromFile(withdrawWalletView.getActivity().getApplicationContext(),"cards"));


    }

    public String getLastFourDigits(String chosenCard)
    {
        String [] str = chosenCard.split(",");
        return str[0];
    }

    @Override
    public void withdrawAmount(String amount,String chosenCard) {


        //Processing to be done for withdraw amount
        Toast.makeText(withdrawWalletView.getActivity(),"Amount: "+ amount,Toast.LENGTH_SHORT).show();

        //add progress dialog here then only dismiss after processing
        final ProgressDialog dialog = ProgressDialog.show(withdrawWalletView.getActivity(), "",
                "Making Payment. Please wait...", true);

        //get last 4 digits from card
        String l4d = getLastFourDigits(chosenCard);


        //Do processing here to draw the amount from wallet and add to the card


        //after the functions are done then close the activity
        dialog.dismiss();

    }


    @Override
    public String getWalletBalance() {
        return InternalStorage.readString(withdrawWalletView.getActivity().getApplicationContext(),"wallet");
    }

    @Override
    public boolean enoughValueInWallet(String amount) {
        InternalStorage.writeString(withdrawWalletView.getActivity().getApplicationContext(),"wallet","51");
        String amt= InternalStorage.readString(withdrawWalletView.getActivity().getApplicationContext(),"wallet");

        if(Float.parseFloat(amt)>=Float.parseFloat(amount))
        {
            return true;
        }
        else
        {
            return false;
        }

    }
}
