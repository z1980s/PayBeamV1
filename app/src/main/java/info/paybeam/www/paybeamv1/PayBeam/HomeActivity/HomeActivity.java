package info.paybeam.www.paybeamv1.PayBeam.HomeActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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

    }

    @Override
    public void showPaymentPhoneView()
    {

    }

    @Override
    public void showPaymentReaderView()
    {

    }

    @Override
    public void showCardManagementView()
    {

    }

    @Override
    public void showTransactionView()
    {

    }
}
