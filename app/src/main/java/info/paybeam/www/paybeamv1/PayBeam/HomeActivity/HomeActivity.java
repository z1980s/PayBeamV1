package info.paybeam.www.paybeamv1.PayBeam.HomeActivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.nfc.NfcAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        HomeActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.home_activity);
        homePresenter = new HomePresenter(this);
        binding.setHomePresenter(homePresenter);
    }

    @Override
    public void showEditProfileView()
    {
        Toast.makeText(this,"Show Edit Profile View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void showPaymentPhoneView()
    {
        Toast.makeText(this,"Show Payment Phone View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PaymentPhoneActivity.class);
        intent.setAction(NfcAdapter.ACTION_TAG_DISCOVERED);
        startActivity(intent);
    }

    @Override
    public void showPaymentReaderView()
    {
        Toast.makeText(this,"Payment Reader", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, PaymentReaderActivity.class);
        startActivity(intent);
    }

    @Override
    public void showCardManagementView()
    {
        Toast.makeText(this,"Show Card Management", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, CardActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTransactionView()
    {
        Toast.makeText(this,"Show Transaction View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, TransactionActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSettingsView()
    {
        Toast.makeText(this,"Show Settings View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void showWalletView()
    {
        Toast.makeText(this,"Show Wallet View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, WalletActivity.class);
        startActivity(intent);
    }

    @Override
    public void showScanQRView()
    {
        Toast.makeText(this,"Show Scan QR View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, QRActivity.class);
        startActivity(intent);
    }
}
