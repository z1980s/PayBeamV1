package info.paybeam.www.paybeamv1.PayBeam.WalletActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.WalletActivityBinding;

public class WalletActivity extends AppCompatActivity implements WalletContract.WalletView
{
    private WalletPresenter walletPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wallet_activity);
        WalletActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.wallet_activity);
        walletPresenter = new WalletPresenter(this);
        binding.setWalletPresenter(walletPresenter);
    }
}
