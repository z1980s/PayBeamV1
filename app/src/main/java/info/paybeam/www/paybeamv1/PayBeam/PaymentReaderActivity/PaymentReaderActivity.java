package info.paybeam.www.paybeamv1.PayBeam.PaymentReaderActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
    }
}
