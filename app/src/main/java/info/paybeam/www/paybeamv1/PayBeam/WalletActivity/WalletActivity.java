package info.paybeam.www.paybeamv1.PayBeam.WalletActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
import info.paybeam.www.paybeamv1.PayBeam.HomeActivity.HomeActivity;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
import info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity.ScanQRActivity;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity.TopUpWalletActivity;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity.TopUpWalletPresenter;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WithdrawWalletActivity.WithdrawWalletActivity;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WithdrawWalletActivity.WithdrawWalletContract;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.WalletActivityBinding;
import info.paybeam.www.paybeamv1.databinding.WithdrawWalletActivityBinding;

public class WalletActivity extends AppCompatActivity implements WalletContract.WalletView
{
    private WalletPresenter walletPresenter;
    TextView walletAmount;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity);
        WalletActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.wallet_activity);
        walletPresenter = new WalletPresenter(this);
        binding.setWalletPresenter(walletPresenter);

        walletAmount = findViewById(R.id.walletAmount);
        //Retrieve the Wallet amount here and set to the textview
        //walletAmount.setText(walletPresenter.getWalletAmount());
    }



    @Override
    protected void onResume() {
        super.onResume();
        //walletAmount.setText(walletPresenter.getWalletAmount());
        walletPresenter.getWalletAmount();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showTopUpWalletView() {
        //Toast.makeText(this,"Show top up wallet activity", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TopUpWalletActivity.class);
        startActivity(intent);
    }

    @Override
    public void showWithdrawFromWalletView() {
        //Toast.makeText(this,"Show Withdraw wallet activity", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WithdrawWalletActivity.class);
        startActivity(intent);
    }

    @Override
    public void setWalletBalance(String amount) {
        walletAmount.setText(amount);
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
                            Intent intent = new Intent(WalletActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    }
                });
        dlgAlert.create().show();
    }
}

