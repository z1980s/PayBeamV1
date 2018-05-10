package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.Filter.DecimalInputFilter;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Cards;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.CardsAdapter;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WalletActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.TopUpWalletActivityBinding;

public class TopUpWalletActivity extends AppCompatActivity implements TopUpWalletContract.TopUpWalletView {

    TopUpWalletPresenter topUpWalletPresenter;

    private ListView lst;
    private ArrayList<JsonObject> cards;
    private String chosenCard;

    private CardsAdapter cardAdapter;

    private String amount = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.top_up_wallet_activity);
        TopUpWalletActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.top_up_wallet_activity);
        topUpWalletPresenter = new TopUpWalletPresenter(this);
        binding.setTopUpWalletPresenter(topUpWalletPresenter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        topUpWalletPresenter.onTopUpWalletPageDisplayed();
    }

    @Override
    public void displayCards(ArrayList<JsonObject> cards)
    {
        //display all available cards on screen
        this.cards = cards;

        lst= (ListView) findViewById(R.id.cardListView);

        ArrayList<Cards> cardsList = new ArrayList<>();

        for(JsonObject card : cards)
        {
            int resID = 0;

            if(card.get("cardType").getAsString().equals("VISA")) {
                resID = R.drawable.bt_ic_visa;
            } else if (card.get("cardType").getAsString().equals("MASTERCARD")) {
                resID = R.drawable.bt_ic_mastercard;
            } else if (card.get("cardType").getAsString().equals("DISCOVER")) {
                resID = R.drawable.bt_ic_discover;
            } else if (card.get("cardType").getAsString().equals("AMEX")) {
                resID = R.drawable.bt_ic_amex;
            } else if (card.get("cardType").getAsString().equals("DINERS_CLUB")) {
                resID = R.drawable.bt_ic_diners_club;
            } else if (card.get("cardType").getAsString().equals("JCB")) {
                resID = R.drawable.bt_ic_jcb;
            } else if (card.get("cardType").getAsString().equals("MAESTRO")) {
                resID = R.drawable.bt_ic_maestro;
            } else if (card.get("cardType").getAsString().equals("UNIONPAY")) {
                resID = R.drawable.bt_ic_unionpay;
            } else if (card.get("cardType").getAsString().equals("UNKNOWN")) {
                resID = R.drawable.bt_ic_unknown;
            } else {
                resID = R.drawable.bt_ic_unknown;
            }

            cardsList.add(new Cards(resID, card.get("cardNum").getAsString() , card.get("expiryDate").getAsString(), card.get("primary").getAsBoolean(),R.drawable.ic_done_black_48dp));
        }

        //Put the cardList into cardAdapter
        cardAdapter = new CardsAdapter(this,cardsList);
        //set cardAdapter into the listview
        lst.setAdapter(cardAdapter);


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundColor(Color.parseColor("#B0BEC5"));


                Cards thisCard = (Cards) parent.getItemAtPosition(position);
                thisCard.getPrimary();
                //TextView tv= (TextView) view;
                //Toast.makeText(TopUpWalletActivity.this,thisCard.getCardNum()+"  "+position,Toast.LENGTH_SHORT).show();
                chosenCard = thisCard.getCardNum();
                //view.setBackgroundColor(Color.parseColor("#00000000"));

                //on click of the card
                //pass in view so that can set remove the background colour
                showDialog(view);
                //topUpWalletPresenter.onCardSelected(view);

                view.setSelected(false);


            }
        });
    }


    public String showDialog(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Amount to Top Up");
        final View v = view;
        // Set up the input
        final EditText input = new EditText(this);

        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        //input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        input.setInputType((InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL));
        //input.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(4,2)});
        input.setFilters(new InputFilter[] {new DecimalInputFilter()});
        builder.setView(input);


        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                boolean validInput = input.getText().toString().matches("-?\\d+(\\.\\d+)?");
                if(validInput && Double.parseDouble(input.getText().toString())>0)
                {
                    amount = input.getText().toString();
                    v.setBackgroundColor(Color.parseColor("#00000000"));
                    topUpWalletPresenter.TopUpWallet(amount, chosenCard);
                    v.setSelected(false);
                }
                else
                {
                    new AlertDialog.Builder(TopUpWalletActivity.this)
                            .setTitle("Insufficient Value")
                            .setMessage("Enter a valid amount! ")
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) {
                                    showDialog(v);
                                }
                            })
                            .create()
                            .show();
                }

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                v.setBackgroundColor(Color.parseColor("#00000000"));
                v.setSelected(false);
            }
        });


        builder.show();
        return amount;
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
                        Intent intent = new Intent(TopUpWalletActivity.this, WalletActivity.class);
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
