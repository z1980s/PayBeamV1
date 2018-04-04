package info.paybeam.www.paybeamv1.PayBeam.OTPActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.OtpActivityBinding;

public class OtpActivity extends AppCompatActivity implements OtpContract.OtpView
{
    private OtpPresenter otpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_activity);
        OtpActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.otp_activity);
        otpPresenter = new OtpPresenter(this);
        binding.setOtpPresenter(otpPresenter);
    }

    public void showSuccessView()
    {
        //On successful validation, bring user to home screen
    }

    public void showFailureView()
    {
        //on failure, show message and bring user back to create account screen
    }
}
