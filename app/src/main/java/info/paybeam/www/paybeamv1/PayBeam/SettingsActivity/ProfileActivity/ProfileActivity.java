package info.paybeam.www.paybeamv1.PayBeam.SettingsActivity.ProfileActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.EditProfileActivityBinding;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.ProfileView
{
    private ProfilePresenter profilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        EditProfileActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.profile_activity);
        profilePresenter = new ProfilePresenter(this);
        binding.setEditProfilePresenter(profilePresenter);
    }
}
