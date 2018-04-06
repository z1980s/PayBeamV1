package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.braintreepayments.cardform.view.CardForm;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.AddcardActivityBinding;

public class AddCardActivity extends AppCompatActivity implements AddCardContract.AddCardView
{
    private AddCardContract.AddCardPresenter addCardPresenter;
    private CardForm cardForm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addcard_activity);
        AddcardActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.addcard_activity);
        addCardPresenter = new AddCardPresenter(this);
        binding.setAddCardPresenter(addCardPresenter);

        cardForm = findViewById(R.id.card_form);
        Button buy = findViewById(R.id.button_addCard);

        cardForm.cardRequired(true)
                .expirationRequired(true)
                .cvvRequired(true)
                .postalCodeRequired(true)
                .mobileNumberRequired(true)
                .mobileNumberExplanation("SMS is required on this number")
                .setup(AddCardActivity.this);
    }

    @Override
    public void extractValues()
    {
        addCardPresenter.addCard(cardForm.getCardNumber(),
                                 cardForm.getExpirationMonth(),
                                 cardForm.getExpirationYear(),
                                 cardForm.getCvv(),
                                 cardForm.getPostalCode(),
                                 cardForm.getCountryCode(),
                                 cardForm.getMobileNumber());
    }

    @Override
    public void showSuccessMessage()
    {
        //add card success bring user to CardActivity page and refresh list of available cards

    }

    @Override
    public void showErrorMessage()
    {
        //show error message e.g. card is invalid or cannot be verified, bring user back to addCardActivity page
    }

    @Override
    public void showServerError()
    {
        //error from server e.g. server returns null object or no response, bring user back to addCardActivity page
    }


}
