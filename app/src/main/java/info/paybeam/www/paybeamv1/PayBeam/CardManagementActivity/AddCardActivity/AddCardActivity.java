package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.CardForm;
import com.braintreepayments.cardform.view.SupportedCardTypesView;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
import info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ChangePasswordActivity.ChangePasswordActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.AddcardActivityBinding;

public class AddCardActivity extends AppCompatActivity implements AddCardContract.AddCardView, OnCardFormSubmitListener
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

            cardForm.setOnCardFormSubmitListener(AddCardActivity.this);
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
    public void showSuccessMessage(String succMsg)
    {
        //add card success bring user to CardActivity page and refresh list of available cards
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(succMsg);
        dlgAlert.setTitle("Success");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(AddCardActivity.this, CardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                });
        dlgAlert.create().show();
    }

    @Override
    public void showErrorMessage(String errorMsg)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(errorMsg);
        dlgAlert.setTitle("Error");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

        dlgAlert.create().show();
    }

    @Override
    public void showServerError()
    {
        //error from server e.g. server returns null object or no response, bring user back to addCardActivity page
    }

    @Override
    public Activity getActivity() {
        return this;
    }


    @Override
    public void finishAddCard() {
        finish();
    }

    @Override
    public boolean validate() {
        boolean valid = false;
        if (cardForm.isValid()) {
            //Toast.makeText(this, "VALID", Toast.LENGTH_SHORT).show();
            valid = true;
        } else {
            cardForm.validate();
            //Toast.makeText(this, "INVALID", Toast.LENGTH_SHORT).show();
            valid = false;
        }
        return valid;
    }


    @Override
    public void onCardFormSubmit() {
        addCardPresenter.onAddCardButtonClick(findViewById(R.id.card_form));
    }
}
