package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity.AddCardActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.CardActivityBinding;

public class CardActivity extends AppCompatActivity implements CardContract.CardView
{
    CardPresenter cardPresenter;


    ListView lst;
    ArrayList<String> cards;
    String defaultCard;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_activity);
        CardActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.card_activity);
        cardPresenter = new CardPresenter(this);
        binding.setCardPresenter(cardPresenter);

        cardPresenter.onCardPageDisplayed();
    }

    @Override
    protected void onResume() {

        super.onResume();
        cardPresenter.onCardPageDisplayed();
    }

    @Override
    public void displayCards(ArrayList<String> cards)
    {
        //display all available cards on screen
        Toast.makeText(this, "There are "+ cards.size() + " cards", Toast.LENGTH_SHORT).show();
        this.cards = cards;

        lst= (ListView) findViewById(R.id.cardList);
        //ArrayAdapter to create a view for each array item
        ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, this.cards);
        //Set the adapter to the listview
        lst.setAdapter(arrayadapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                TextView tv= (TextView) view;
                Toast.makeText(CardActivity.this,tv.getText()+"  "+position,Toast.LENGTH_SHORT).show();
                defaultCard = tv.getText().toString();
            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(CardActivity.this, "Default card is: "+ defaultCard, Toast.LENGTH_SHORT).show();
        //When paused, set the default card somewhere to be used in payment
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
