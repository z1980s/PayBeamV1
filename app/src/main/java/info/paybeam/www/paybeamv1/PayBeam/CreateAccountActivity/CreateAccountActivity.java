package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.CreateAccountActivityBinding;

public class CreateAccountActivity extends AppCompatActivity implements CreateAccountContract.CreateAccountView
{
    private CreateAccountPresenter caPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account_activity);
        CreateAccountActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.create_account_activity);
        caPresenter = new CreateAccountPresenter(this);
        binding.setCreateAccountPresenter(caPresenter);
    }

    public void onSuccessView()
    {
        //bring user to OTP screen, validate OTP
    }

    public void onFailureView()
    {
        //remain on the same screen, show failure message
    }
}
