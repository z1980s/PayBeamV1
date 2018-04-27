package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import info.paybeam.www.paybeamv1.PayBeam.HomeActivity.HomeActivity;
import info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ProfileActivity;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity.TopUpWalletActivity;
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
    public void displayFieldDetails(JsonObject obj) {
        //Toast.makeText(this, "WHAT IS THIS", Toast.LENGTH_SHORT).show();
        name = findViewById(R.id.edit_name);
        phoneNo = findViewById(R.id.edit_phoneNo);
        email = findViewById(R.id.edit_email);
        address = findViewById(R.id.edit_address);

        name.setText(obj.get("name").getAsString());
        phoneNo.setText(obj.get("phoneNo").getAsString());
        email.setText(obj.get("email").getAsString());
        address.setText(obj.get("address").getAsString());
    }



    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public JsonObject extractValues() {
        JsonObject obj = new JsonObject();

        obj.addProperty("name",name.getText().toString());
        obj.addProperty("phoneNo",phoneNo.getText().toString());
        obj.addProperty("email",email.getText().toString());
        obj.addProperty("address",address.getText().toString());

        return obj;
    }

    @Override
    public void showErrorMessage(String message) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                });

        dlgAlert.create().show();
    }

    @Override
    public void showSuccess(String message)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //CreateAccountActivity.this.finish();
                        //finish();

                        Intent intent = new Intent(EditProfileActivity.this, ProfileActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                });

        dlgAlert.create().show();
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
