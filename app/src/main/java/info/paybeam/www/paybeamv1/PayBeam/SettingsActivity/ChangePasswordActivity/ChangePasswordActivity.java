package info.paybeam.www.paybeamv1.PayBeam.SettingsActivity.ChangePasswordActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.ChangePasswordActivityBinding;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.ChangePasswordView
{
    private ChangePasswordPresenter cpPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);
        ChangePasswordActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.change_password_activity);
        cpPresenter = new ChangePasswordPresenter(this);
        binding.setCpPresenter(cpPresenter);
    }
}
