package info.paybeam.www.paybeamv1.PayBeam.PaymentReaderActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity.TopUpWalletActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.PaymentPhoneActivityBinding;
import info.paybeam.www.paybeamv1.databinding.PaymentReaderActivityBinding;

public class PaymentReaderActivity extends AppCompatActivity implements PaymentReaderContract.PaymentReaderView
{
    private PaymentReaderPresenter prPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_reader_activity);
        PaymentReaderActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.payment_reader_activity);
        prPresenter = new PaymentReaderPresenter(this);
        binding.setPrPresenter(prPresenter);
        prPresenter.setData();
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
                            Intent intent = new Intent(PaymentReaderActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    }
                });
        dlgAlert.create().show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

}
