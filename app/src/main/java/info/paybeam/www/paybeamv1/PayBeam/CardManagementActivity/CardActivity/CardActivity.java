package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity.AddCardActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.CardActivityBinding;

public class CardActivity extends AppCompatActivity implements CardContract.CardView
{
    CardPresenter cardPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);
        CardActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.card_activity);
        cardPresenter = new CardPresenter(this);
        binding.setCardPresenter(cardPresenter);


    }

    @Override
    public void displayCards()
    {
        //display all available cards on screen
    }

    @Override
    public void showAddCard()
    {
        Toast.makeText(this,"Show Add Card", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddCardActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAddCardSuccessMessage()
    {

    }

    @Override
    public void showAddCardErrorMessage()
    {

    }

    @Override
    public void showRemoveCardSuccessMessage()
    {

    }

    @Override
    public void showRemoveCardErrorMessage()
    {

    }

    @Override
    public Activity getActivity() {
        return this;
    }

}
