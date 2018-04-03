package info.paybeam.www.paybeamv1.PayBeam.SettingsActivity.EditProfileActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.EditProfileActivityBinding;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.EditProfileView
{
    private EditProfilePresenter editProfilePresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        EditProfileActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.edit_profile_activity);
        editProfilePresenter = new EditProfilePresenter(this);
        binding.setEditProfilePresenter(editProfilePresenter);
    }
}
