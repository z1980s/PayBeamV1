package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.WithdrawWalletActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.WithdrawWalletActivityBinding;

public class WithdrawWalletActivity extends AppCompatActivity implements WithdrawWalletContract.WithdrawWalletView {



    private WithdrawWalletPresenter withdrawWalletPresenter;

    private ListView lst;
    private ArrayList<String> cards;
    private String chosenCard;

    Button withdrawButton;

    String amount ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.withdraw_wallet_activity);
        WithdrawWalletActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.withdraw_wallet_activity);
        withdrawWalletPresenter = new WithdrawWalletPresenter(this);
        binding.setWithdrawWalletPresenter(withdrawWalletPresenter);
        withdrawWalletPresenter.pageDisplayed();
        //getAmountDialog();
    }

    @Override
    public void displayCards(ArrayList<String> cards)
    {

        //display all available cards on screen
        Toast.makeText(this, "There are "+ cards.size() + " cards", Toast.LENGTH_SHORT).show();
        //this.cards = cards;
        this.cards = InternalStorage.readCardsFromFile(this,"cards");

        lst= (ListView) findViewById(R.id.cardlistView2);
        //ArrayAdapter to create a view for each array item
        final ArrayAdapter<String> arrayadapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, this.cards);
        //Set the adapter to the listview
        lst.setAdapter(arrayadapter);

        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                view.setSelected(true);
                view.setBackgroundColor(Color.parseColor("#B0BEC5"));
                TextView tv= (TextView) view;
                Toast.makeText(WithdrawWalletActivity.this,tv.getText()+"  "+position,Toast.LENGTH_SHORT).show();
                chosenCard = tv.getText().toString();

                //on click of the card
                //pass in view so that can set remove the background colour
                //showDialog(view);

                //withdrawWalletPresenter.onCardSelected();

                getAmountDialog(view);
                //withdrawWalletPresenter.withdrawAmount(amount,chosenCard);

                view.setSelected(false);


            }
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void finishActivity() {
        finish();
    }



    @Override
    public void getAmountDialog(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Amount to Withdraw");
        builder.setMessage("Wallet balance: "+ withdrawWalletPresenter.getWalletBalance());
        final View v = view;

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                amount = input.getText().toString();

                //CHECK AMOUNT HERE
                if(withdrawWalletPresenter.enoughValueInWallet(amount))
                {
                    //if enough balance then proceed with withdrawing
                    withdrawWalletPresenter.withdrawAmount(amount,chosenCard);

                }
                else
                {

                    new AlertDialog.Builder(WithdrawWalletActivity.this)
                            .setTitle("Insufficient Value")
                            .setMessage("Enter an amount within wallet amount: $"+ withdrawWalletPresenter.getWalletBalance())
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override public void onClick(DialogInterface dialog, int which) {
                                    getAmountDialog(v);
                                }
                            })
                            .create()
                            .show();

                }
                v.setBackgroundColor(Color.parseColor("#00000000"));

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                v.setBackgroundColor(Color.parseColor("#00000000"));
                //finishActivity();
            }
        });

        builder.show();


    }



}
