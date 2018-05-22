package info.paybeam.www.paybeamv1.PayBeam.HomeActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.nfc.NfcAdapter;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
import info.paybeam.www.paybeamv1.PayBeam.PaymentPhoneActivity.PaymentPhoneActivity;
import info.paybeam.www.paybeamv1.PayBeam.PaymentReaderActivity.PaymentReaderActivity;
import info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ProfileActivity;
import info.paybeam.www.paybeamv1.PayBeam.QRActivity.QRActivity;
import info.paybeam.www.paybeamv1.PayBeam.SettingsActivity.SettingsActivity;
import info.paybeam.www.paybeamv1.PayBeam.TransactionActivity.TransactionActivity;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WalletActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.HomeActivityBinding;

public class HomeActivity extends AppCompatActivity implements HomeContract.HomeView
{
    private HomePresenter homePresenter;
    Button walletButton;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        HomeActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.home_activity);
        homePresenter = new HomePresenter(this);
        binding.setHomePresenter(homePresenter);

        //showNewWalletBalance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //showNewWalletBalance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        showNewWalletBalance();
    }

    @Override
    public void showProfileView()
    {
        //Toast.makeText(this,"Show Profile View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showPaymentPhoneView()
    {
        //Toast.makeText(this,"Show Payment Phone View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PaymentPhoneActivity.class);
        intent.setAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        startActivity(intent);
    }

    @Override
    public void showPaymentReaderView()
    {
        //Toast.makeText(this,"Payment Reader", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PaymentReaderActivity.class);
        startActivity(intent);
    }

    @Override
    public void showCardManagementView()
    {
        //Toast.makeText(this,"Show Card Management", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTransactionView()
    {
        //Toast.makeText(this,"Show Transaction View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TransactionActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSettingsView()
    {
        //Toast.makeText(this,"Show About View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showWalletView()
    {
        //Toast.makeText(this,"Show Wallet View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WalletActivity.class);
        startActivity(intent);
    }

    @Override
    public void showScanQRView()
    {
        //Toast.makeText(this,"Show QR View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, QRActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNewWalletBalance() {
        walletButton = findViewById(R.id.WalletButton);

        String credentials[] = InternalStorage.readString(this,"Credentials").split(",");
        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "GetWalletAmount");
        msg.addProperty("LoginName", credentials[0]);
        msg.addProperty("Token", InternalStorage.readString(this,"Token"));

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg,this) {
            @Override
            public void receiveResponse(String response) {
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject) jParser.parse(response);
                    if (jResponse.get("result").getAsString().equals("Success")) {
                        String balance = jResponse.get("NewAmount").getAsString();
                        walletButton.setText("Wallet\n($" + balance +")");
                    } else {
                        System.out.println(jResponse.get("reason").getAsString());
                        showErrorMessage(jResponse.get("reason").getAsString());
                    }
                } catch (Exception e) {
                    System.out.println("Unable to connect to server to retrieve Wallet balance");
                }
            }
        };
        sc.execute(null,null,null);
    }

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
                        if (message.contains("Token Invalid or Expired")) {
                            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    }
                });
        dlgAlert.create().show();
    }
}
