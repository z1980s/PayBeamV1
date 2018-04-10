package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity;

import android.view.View;
import android.widget.Toast;

import com.braintreepayments.cardform.view.CardForm;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardPresenter;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

/**
 * Created by dflychew on 6/4/18.
 */

public class AddCardPresenter implements AddCardContract.AddCardPresenter{


    AddCardContract.AddCardView addCardView;


    AddCardPresenter(AddCardContract.AddCardView view)
    {
        addCardView = view;
    }

    CardPresenter cardPresenter;

    @Override
    public void onAddCardButtonClick(View view){
        //establish connection to the server
        //store information

        Toast.makeText(addCardView.getActivity(), "WHAT THE FUCK", Toast.LENGTH_SHORT);
        addCardView.extractValues();
    }

    @Override
    public void addCard(String cardNumber, String expirationMonth, String expirationYear, String cvv, String postalCode, String countryCode, String mobileNo)
    {

        //store values locally
        //extract last 4 digits and relevant display information, store locally into a list so it can be reloaded on cardActivity page
        Toast.makeText(addCardView.getActivity(), cardNumber, Toast.LENGTH_SHORT);
        int length = cardNumber.length();
        String maskCardNum = cardNumber;

        /* Just for testing
        String maskCardNum = "1234 1234 1234 1234";
        int length = maskCardNum.length();
        */

        char[] chars = maskCardNum.toCharArray();
        for(int i = 0;i<(length-4);i++)
        {
            chars[i]='X';
        }
        maskCardNum = String.valueOf(chars)+ " , " + (expirationMonth+"/"+expirationYear) +'\n';

        //write masked cardnumber and the
        InternalStorage.writeCardToFile(addCardView.getActivity().getApplicationContext(),"cards", maskCardNum);





        addCardView.finishAddCard();
        //InternalStorage.read(addCardView.getActivity().getApplicationContext(),"cards");

        //concatenate all values and encrypt with bank's public key, send to payBeam server for storage
        //payBeam server should verify and validate card information and send back a response
        //upon successfully response, call addCardView.showSuccessMessage and bring user back to cardActivity page
        //upon failure response, call addCardView.showErrorMessage and bring user back to addCardActivity page
        //upon receive no response or null object, call addCardView.showServerError and bring user back to addCardActivity page
    }




}
