package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity;

import android.view.View;

import com.braintreepayments.cardform.view.CardForm;

/**
 * Created by dflychew on 6/4/18.
 */

public interface AddCardContract {

    interface AddCardView
    {
        void extractValues();
        void showSuccessMessage();
        void showErrorMessage();
        void showServerError();
    }

    interface AddCardPresenter
    {
        void onAddCardButtonClick(View view);
        void addCard(String cardNumber, String expirationMonth, String expirationYear, String cvv, String postalCode, String countryCode, String mobileNo);
    }
}
