package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ChangePasswordActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
import info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity.EditProfileActivity;
import info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ProfileActivity;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WithdrawWalletActivity.WithdrawWalletActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.ChangePasswordActivityBinding;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordContract.ChangePasswordView
{
    private ChangePasswordPresenter cpPresenter;


    EditText oldPass;
    EditText newPass;
    EditText retypePass;

    TextInputLayout oldPassLayout;
    TextInputLayout newPassLayout;
    TextInputLayout retypePassLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password_activity);
        ChangePasswordActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.change_password_activity);
        cpPresenter = new ChangePasswordPresenter(this);
        binding.setCpPresenter(cpPresenter);

        oldPass = findViewById(R.id.old_pass_text);
        newPass = findViewById(R.id.new_pass_text);
        retypePass = findViewById(R.id.retype_pass_text);

        //set the layout
        oldPassLayout = (TextInputLayout) findViewById(R.id.edit_oldPass_layout);
        newPassLayout = (TextInputLayout) findViewById(R.id.edit_newPass_layout);
        retypePassLayout = (TextInputLayout) findViewById(R.id.edit_retypePass_layout);
    }


    @Override
    public void extractValues() {


        boolean oldPassValid = false;
        boolean newPassValid = false;
        boolean retypePassValid = false;

        if(oldPass.getText().length()==0)
        {
            oldPassLayout.setError("You need to enter current password");
        }
        else
        {
            oldPassValid = true;
        }


        if(newPass.getText().length()==0)
        {
            newPassLayout.setError("You need to enter a new password");
        }
        else
        {
            newPassValid = true;
        }


        if(retypePass.getText().length()==0)
        {
            retypePassLayout.setError("You need to retype new password");
        }
        else
        {
            retypePassValid = true;
        }



        oldPass.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                oldPassLayout.setError("");
            }
        });


        newPass.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                newPassLayout.setError("");
            }
        });


        retypePass.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                retypePassLayout.setError("");
            }
        });

        if(oldPassValid && newPassValid && retypePassValid)
        {
            cpPresenter.localPasswordCheck(oldPass.getText().toString(), newPass.getText().toString(), retypePass.getText().toString());
        }

    }

    @Override
    public void showDialog(String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();
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

                        Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                });

        dlgAlert.create().show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
