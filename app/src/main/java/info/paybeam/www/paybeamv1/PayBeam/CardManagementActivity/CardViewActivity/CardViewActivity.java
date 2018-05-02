package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardViewActivity;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

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

        primary_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // do something, the isChecked will be
                // true if the switch is in the On position
                if(isChecked){
                    Toast.makeText(CardViewActivity.this,"TEXT HERE",Toast.LENGTH_SHORT).show();
                    //Pop up dialog to ask if they would like to set this as primary card
                }
            }
        });

        cardViewPresenter.onPageDisplayed();
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
    public Activity getActivity() {
        return this;
    }

    @Override
    public Cards getCard() {
        return card;
    }
}
