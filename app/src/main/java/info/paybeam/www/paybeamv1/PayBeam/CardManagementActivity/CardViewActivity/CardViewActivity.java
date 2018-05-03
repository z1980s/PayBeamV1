package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardViewActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.Timer;
import java.util.TimerTask;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity.AddCardActivity;
import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Cards;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.CardViewActivityBinding;

public class CardViewActivity extends AppCompatActivity implements CardViewContract.CardViewView {


    CardViewContract.CardViewPresenter cardViewPresenter;

    Cards card;

    ImageView card_image;
    TextView card_num_text;
    TextView expiry_text;
    TextView primary_text;
    Switch primary_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_view_activity);
        CardViewActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.card_view_activity);
        cardViewPresenter = new CardViewPresenter(this);
        binding.setCardViewPresenter(cardViewPresenter);

        card_image = findViewById(R.id.card_image);
        card_num_text = findViewById(R.id.card_num_text);
        expiry_text = findViewById(R.id.expiry_text);
        primary_text = findViewById(R.id.primary_text);
        primary_switch = findViewById(R.id.primary_switch);

        cardViewPresenter.onPageDisplayed();

        primary_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    //Toast.makeText(CardViewActivity.this,"TEXT HERE",Toast.LENGTH_SHORT).show();
                    //Pop up dialog to ask if they would like to set this as primary card
                    showDialog(card);
                }
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void showDialog(Cards card){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Set this card as Primary?");

        final Cards c = card;
        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.cancel();
                //dialog.dismiss();
                Toast.makeText(CardViewActivity.this,"Test ",Toast.LENGTH_SHORT);
                cardViewPresenter.setPrimaryCard(c);

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //dialog.cancel();
                primary_switch.setChecked(false);

            }
        });


        builder.show();
    }


    @Override
    public void setCard() {
        Bundle extras = getIntent().getExtras();
        card = (Cards) extras.getSerializable("card");
        Toast.makeText(this,  card.getCardNum() +" "+card.getExpiryDate()+" "+ card.getCardImage(),Toast.LENGTH_SHORT).show();

        card_image.setImageResource(card.getCardImage());
        card_num_text.setText("Card Number: " + card.getCardNum());
        expiry_text.setText("Expiry Date: "+ card.getExpiryDate());
        primary_text.setText("Primary: "+card.getPrimary().toString());

        if(card.getPrimary())
        {
            primary_switch.setChecked(true);
            primary_switch.setEnabled(false);
        }

    }







    @Override
    public void showSuccessMessage(String succMsg)
    {
        //add card success bring user to CardActivity page and refresh list of available cards
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(succMsg);
        dlgAlert.setTitle("Success");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);


        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent intent = new Intent(CardViewActivity.this, CardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);

                    }
                });
        dlgAlert.create().show();
    }

    @Override
    public void showErrorMessage(String errorMsg)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(errorMsg);
        dlgAlert.setTitle("Error");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public Cards getCard() {
        return card;
    }

    @Override
    public void finishActivity() {
        finish();
    }


    @Override
    public void refreshCardView(JsonObject obj) {

        card_image.setImageResource(card.getCardImage());
        card_num_text.setText("Card Number: " + card.getCardNum());
        expiry_text.setText("Expiry Date: "+ card.getExpiryDate());
        primary_text.setText("Primary: "+obj.get("primary").getAsBoolean());

        if(obj.get("primary").getAsBoolean())
        {
            primary_switch.setChecked(true);
            primary_switch.setEnabled(false);
        }

    }


}
