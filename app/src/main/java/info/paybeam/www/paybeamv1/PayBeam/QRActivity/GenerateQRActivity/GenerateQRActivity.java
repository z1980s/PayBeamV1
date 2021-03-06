package info.paybeam.www.paybeamv1.PayBeam.QRActivity.GenerateQRActivity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import info.paybeam.www.paybeamv1.PayBeam.Filter.DecimalInputFilter;
import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity.TopUpWalletActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.GenerateQrActivityBinding;

public class GenerateQRActivity extends AppCompatActivity implements GenerateQRContract.GenerateQRView
{
    ImageView qrCode;
    EditText editText;
    TextView textView;


    private GenerateQRPresenter generateQRPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr_activity);
        GenerateQrActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.generate_qr_activity);
        generateQRPresenter = new GenerateQRPresenter(this);
        binding.setGenerateQRPresenter(generateQRPresenter);

        editText = findViewById(R.id.amount_text_field);
        editText.setInputType((InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL));
        //editText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3,2)});
        editText.setFilters(new InputFilter[] {new DecimalInputFilter()});

        class DoneOnEditorActionListener implements TextView.OnEditorActionListener {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_NEXT) {
                    InputMethodManager imm = (InputMethodManager)v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        }

        editText.setOnEditorActionListener(new DoneOnEditorActionListener());
    }



    @Override
    public String getAmount() {
        //Check the amount first
        //if its not valid then return a null
        boolean validInput = editText.getText().toString().matches("-?\\d+(\\.\\d+)?");
        if(validInput && Double.parseDouble(editText.getText().toString())>0)
        {
            String amount = String.format("%.2f", Float.parseFloat(editText.getText().toString()));
            //pass the amount to the presenter to generate the QR image
            //generateQRPresenter.generateQRimage(amount);
            return amount;
        }
        return null;
    }

    @Override
    public void showErrorMessage(final String message) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Error");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (message.contains("Invalid or Expired Session Token")) {
                            Intent intent = new Intent(GenerateQRActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    }
                });
        dlgAlert.create().show();
    }

    public void showDialog(String text) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(text);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void displayQRImage(Bitmap qrImage, String amount) {
        //Set qrCode image view
        qrCode = findViewById(R.id.QRCode);

        //Hide the textbox and the send button
        editText.setVisibility(View.GONE);
        findViewById(R.id.generate_qr_button).setVisibility(View.GONE);

        //show amount to be sent
        textView = findViewById(R.id.amount_text_view);
        textView.setText("$"+amount);
        textView.setVisibility(View.VISIBLE);

        //Display the bitmap image on the image view
        qrCode.setImageBitmap(qrImage);
        //qrCode.setImageResource(R.drawable.border);

    }


}
