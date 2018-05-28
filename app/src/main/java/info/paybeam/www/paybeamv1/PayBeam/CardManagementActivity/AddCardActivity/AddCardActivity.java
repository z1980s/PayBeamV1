package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.braintreepayments.cardform.OnCardFormSubmitListener;
import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.CardForm;
import com.braintreepayments.cardform.view.SupportedCardTypesView;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
import info.paybeam.www.paybeamv1.PayBeam.OTPModule.GenerateOTP;
import info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ChangePasswordActivity.ChangePasswordActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.AddcardActivityBinding;

public class AddCardActivity extends AppCompatActivity implements AddCardContract.AddCardView, OnCardFormSubmitListener
{
    private AddCardContract.AddCardPresenter addCardPresenter;
    private CardForm cardForm;

    private ProgressDialog progressDialog;
    private String message;
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private String userName;
    private String OTP;
    private String phoneNo;

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
    public void showErrorMessage(final String errorMsg)
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
                        if (errorMsg.contains("Invalid or Expired Session Token")) {
                            Intent intent = new Intent(AddCardActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
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

    public void verifyOTP()
    {
        //progressDialog.dismiss();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please Enter OTP to validate card");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();

                if(m_Text.equals(OTP))
                {
                    extractValues();

                    //message = "PayBeam new password: "+loginPresenter.getNewPassword(userName);

                    dialog.dismiss();

                    //sendSMSMessage2();
                }
                else
                {
                    dialog.dismiss();
                    showErrorMessage("OTP invalid, please try again");
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.cancel();
            }
        });
        AlertDialog otp_dialog = builder.create();
        otp_dialog.show();

        input.setFocusable(true);
        input.setFocusableInTouchMode(true);
        input.requestFocus();
    }

    public void sendSMSMessage()
    {
        OTP = Integer.toString(new GenerateOTP().getOTP());
        message = "Bank OTP: "+OTP;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.SEND_SMS))
            {

            }
            else
            {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
            }
        }
        else
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, MY_PERMISSIONS_REQUEST_SEND_SMS);
        }
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults)
    {
        switch (requestCode)
        {
            case MY_PERMISSIONS_REQUEST_SEND_SMS:
            {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, message, null, null);
                    //Toast.makeText(context, "SMS sent.", Toast.LENGTH_LONG).show();
                }
                else
                {
                    //Toast.makeText(context, "SMS faild, please try again.", Toast.LENGTH_LONG).show();
                    return;
                }
            }
        }
    }
}
