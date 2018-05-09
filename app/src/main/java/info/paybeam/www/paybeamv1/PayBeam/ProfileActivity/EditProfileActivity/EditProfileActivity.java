package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.JsonObject;

import info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ProfileActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.EditProfileActivityBinding;

public class EditProfileActivity extends AppCompatActivity implements EditProfileContract.EditProfileView
{
    private EditProfilePresenter editProfilePresenter;

    EditText name;
    EditText phoneNo;
    EditText email;
    EditText address;

    TextInputLayout nameLayout;
    TextInputLayout phoneNoLayout;
    TextInputLayout emailLayout;
    TextInputLayout addressLayout;



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

        nameLayout = (TextInputLayout) findViewById(R.id.text_edit_name_layout);
        phoneNoLayout = (TextInputLayout) findViewById(R.id.text_edit_phoneNo_layout);
        emailLayout = (TextInputLayout) findViewById(R.id.text_edit_email_layout);
        addressLayout = (TextInputLayout) findViewById(R.id.text_edit_address_layout);

    }



    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void extractValues() {
        //if not valid
        //show the dialog


        boolean nameValid = false;
        boolean phoneNoValid = false;
        boolean emailValid = false;
        boolean addressValid = false;


        //Name
        if(name.getText().length()==0)
        {
            nameLayout.setError("You need to enter a name");
        }
        else
        {
            nameValid = true;
        }

        //Phone
        if(phoneNo.getText().length()==0)
        {
            phoneNoLayout.setError("You need to enter a phone number");
        }
        else
        {
            phoneNoValid = true;
        }

        //Email
        if(email.getText().length()==0)
        {
            emailLayout.setError("You need to enter an email");
        }
        else
        {
            emailValid = isEmailValid(email.getText().toString());
            if(!emailValid)
            {
                emailLayout.setError("You need to enter a valid email");
            }
        }

        //Address
        if(address.getText().length()==0)
        {
            addressLayout.setError("You need to enter an address");
        }
        else
        {
            addressValid = true;
        }

        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                nameLayout.setError("");
            }
        });

        phoneNo.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                phoneNoLayout.setError("");
            }
        });

        email.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                emailLayout.setError("");
            }
        });

        address.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                addressLayout.setError("");
            }
        });


        if(nameValid && phoneNoValid && emailValid && addressValid) {

            JsonObject obj = new JsonObject();

            obj.addProperty("name", name.getText().toString());
            obj.addProperty("phoneNo", phoneNo.getText().toString());
            obj.addProperty("email", email.getText().toString());
            obj.addProperty("address", address.getText().toString());

            editProfilePresenter.submitProfileChanges(obj);
        }

    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
