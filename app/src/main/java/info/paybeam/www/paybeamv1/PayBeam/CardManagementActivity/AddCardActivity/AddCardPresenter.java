package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity;

import android.view.View;

import com.braintreepayments.cardform.view.CardForm;

/**
 * Created by dflychew on 6/4/18.
 */

public class AddCardPresenter implements AddCardContract.AddCardPresenter{


    AddCardContract.AddCardView addCardView;


    AddCardPresenter(AddCardContract.AddCardView view)
    {
        addCardView = view;
    }


    @Override
    public void onAddCardButtonClick(View view){
        //establish connection to the server
        //store information

        addCardView.extractValues();

    }

    @Override
    public void addCard(String cardNumber, String expirationMonth, String expirationYear, String cvv, String postalCode, String countryCode, String mobileNo)
    {

        //store values locally
        //extract last 4 digits and relevant display information, store locally into a list so it can be reloaded on cardActivity page
        //concatenate all values and encrypt with bank's public key, send to payBeam server for storage
        //payBeam server should verify and validate card information and send back a response
        //upon successfully response, call addCardView.showSuccessMessage and bring user back to cardActivity page
        //upon failure response, call addCardView.showErrorMessage and bring user back to addCardActivity page
        //upon receive no response or null object, call addCardView.showServerError and bring user back to addCardActivity page
    }
}
