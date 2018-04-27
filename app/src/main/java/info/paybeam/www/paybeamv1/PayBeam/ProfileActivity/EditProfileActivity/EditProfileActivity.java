package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.EditProfileActivityBinding;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.EditProfileView
{
    private EditProfilePresenter editProfilePresenter;

    EditText name;
    EditText phoneNo;
    EditText email;
    EditText address;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile_activity);
        EditProfileActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.edit_profile_activity);
        editProfilePresenter = new EditProfilePresenter(this);
        binding.setEditProfilePresenter(editProfilePresenter);

        editProfilePresenter.onPageDisplayed();
    }


    @Override
    public void displayFieldDetails(JSONObject obj) {
        //Toast.makeText(this, "WHAT IS THIS", Toast.LENGTH_SHORT).show();
        name = findViewById(R.id.edit_name);
        phoneNo = findViewById(R.id.edit_phoneNo);
        email = findViewById(R.id.edit_email);
        address = findViewById(R.id.edit_address);

        try {
            name.setText(obj.getString("name"));
            phoneNo.setText(obj.getString("phoneNo"));
            email.setText(obj.getString("email"));
            address.setText(obj.getString("address"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }



    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public JSONObject extractValues() {
        JSONObject obj = new JSONObject();

        try {
            obj.put("name",name.getText());
            obj.put("phoneNo",phoneNo.getText());
            obj.put("email",email.getText());
            obj.put("address",address.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return obj;
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
