package info.paybeam.www.paybeamv1.PayBeam.PaymentPhoneActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.PaymentPhoneActivityBinding;

public class PaymentPhoneActivity extends AppCompatActivity implements PaymentPhoneContract.PaymentPhoneView
{
    private PaymentPhonePresenter ppPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_phone_activity);
        PaymentPhoneActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.payment_phone_activity);
        ppPresenter = new PaymentPhonePresenter(this);
        binding.setPpPresenter(ppPresenter);
    }
}
