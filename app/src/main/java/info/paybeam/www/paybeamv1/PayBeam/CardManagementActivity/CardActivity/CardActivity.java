package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Movie;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewParent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.CardForm;
import com.google.gson.JsonObject;

import java.io.Serializable;
import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity.AddCardActivity;
import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardViewActivity.CardViewActivity;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Cards;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.CardsAdapter;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.CardActivityBinding;

public class CardActivity extends AppCompatActivity implements CardContract.CardView
{
    CardPresenter cardPresenter;


    ListView lst;
    ArrayList<JsonObject> cards;
    String defaultCard;

    private CardsAdapter cardAdapter;

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
    public void displayCards(ArrayList<JsonObject> cards)
    {
        //display all available cards on screen
        //this.cards = cards;
        this.cards = InternalStorage.readCardsFromFile(this,"cards");
        //Toast.makeText(this, "There are "+ this.cards.size() + " cards in array", Toast.LENGTH_SHORT).show();
        int count = InternalStorage.countEntries(this,"cards");
        //Toast.makeText(this, "There are "+ count + " CARDS in file", Toast.LENGTH_SHORT).show();

        //String def = InternalStorage.readString(CardActivity.this,"defaultCard");
        //Toast.makeText(CardActivity.this,"Default Card is: "+ def, Toast.LENGTH_SHORT);

        lst= (ListView) findViewById(R.id.cardList);
        //ArrayAdapter to create a view for each array item
        //ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, this.cards);
        //---ListAdapter lAdapter = new ListAdapter(CardActivity.this, cards, expiryDate);


        ArrayList<Cards> cardsList = new ArrayList<>();

        for(JsonObject card : cards)
        {
            //String [] x = card.split(",");
            /*
            //Set the cardType
            CardType cardType = CardType.forCardNumber("4111111111111111");
            //Get the image
            int imageID = cardType.getFrontResource();
            */

            //Toast.makeText(this, x[2] ,Toast.LENGTH_SHORT).show();

            //Create each card then
            //Add to the Cards ArrayList
            //image, number then, expiry date

            //cardsList.add(new Cards(Integer.parseInt(x[2]), x[0] , x[1]));
            int drawableResourceId = this.getResources().getIdentifier("ic_done_black_48dp", "drawable", this.getPackageName());
            cardsList.add(new Cards(card.get("cardType").getAsInt(), card.get("cardNum").getAsString() , card.get("expiryDate").getAsString(), card.get("primary").getAsBoolean(),R.drawable.ic_done_black_48dp));
            //Toast.makeText(this,card.get("cardNum").getAsString()+ " "+card.get("primary").getAsBoolean(),Toast.LENGTH_SHORT).show();
        }


        //cardsList.add(new Cards(R.drawable.bt_ic_camera, "1111" , "2019"));

        //Put the cardList into cardAdapter
        cardAdapter = new CardsAdapter(this,cardsList);
        //set cardAdapter into the listview
        lst.setAdapter(cardAdapter);


        //Set the adapter to the listview
        //lst.setAdapter(arrayadapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                //Open card view here
                //pass in the card number, expiry date & card type
                //int str = parent.getAdapter().getItemViewType(position);

                Cards card = (Cards) parent.getItemAtPosition(position);

                //Toast.makeText(CardActivity.this,  card.getCardNum() +" "+card.getExpiryDate()+" "+ card.getCardImage(),Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(CardActivity.this, CardViewActivity.class);
                intent.putExtra("card", (Serializable) card);
                startActivity(intent);

                //TextView tv= (TextView) view;
                //Toast.makeText(CardActivity.this,tv.getText()+"  "+position,Toast.LENGTH_SHORT).show();
                //defaultCard = tv.getText().toString();
            }
        });

    }


    @Override
    public void onPause() {
        super.onPause();
        //defaultCard = InternalStorage.readString(CardActivity.this,"defaultCard");
        //Toast.makeText(CardActivity.this, "Default card is: "+ defaultCard, Toast.LENGTH_SHORT).show();

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
