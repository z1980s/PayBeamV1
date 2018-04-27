package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity.EditProfileActivity;
import info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity.ScanQRActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.ProfileActivityBinding;

public class ProfileActivity extends AppCompatActivity implements ProfileContract.ProfileView
{
    private ProfilePresenter profilePresenter;


    TextView name;
    TextView username;
    TextView phoneNo;
    TextView email;
    TextView address;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ProfileActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.profile_activity);
        profilePresenter = new ProfilePresenter(this);
        binding.setProfilePresenter(profilePresenter);

        profilePresenter.onPageDisplayed();
    }


    @Override
    public void displayProfileDetails(JSONObject obj) {
        name = findViewById(R.id.name_text);
        username = findViewById(R.id.username_text);
        phoneNo = findViewById(R.id.phoneNo_text);
        email = findViewById(R.id.email_text);
        address = findViewById(R.id.address_text);

        try {
            name.setText("Name: " + obj.getString("name"));
            username.setText("Username: "+obj.getString("username"));
            email.setText("Email: "+obj.getString("email"));
            address.setText("Address: "+obj.getString("address"));
            phoneNo.setText("Phone No: "+obj.getString("phoneNo"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showEditProfileView() {
        Toast.makeText(this,"Show Edit Profile Activity", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, EditProfileActivity.class);
        startActivity(intent);
    }




}
