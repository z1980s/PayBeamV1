package info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
import info.paybeam.www.paybeamv1.PayBeam.TransactionActivity.TransactionPresenter;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.TopUpWalletActivityBinding;
import info.paybeam.www.paybeamv1.databinding.TransactionActivityBinding;

public class TopUpWalletActivity extends AppCompatActivity implements TopUpWalletContract.TopUpWalletView {

    TopUpWalletPresenter topUpWalletPresenter;

    private ListView lst;
    private ArrayList<String> cards;
    private String chosenCard;

    private String m_Text = "";

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
    public void displayCards(ArrayList<String> cards)
    {

        //display all available cards on screen
        Toast.makeText(this, "There are "+ cards.size() + " cards", Toast.LENGTH_SHORT).show();
        this.cards = cards;

        lst= (ListView) findViewById(R.id.cardListView);
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
                Toast.makeText(TopUpWalletActivity.this,tv.getText()+"  "+position,Toast.LENGTH_SHORT).show();
                chosenCard = tv.getText().toString();

                //show dialog to get amount
                //pass in view so that can set remove the background colour
                showDialog(view);

                view.setSelected(false);


            }
        });
    }

    void showDialog(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Amount to send");
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
                m_Text = input.getText().toString();
                v.setBackgroundColor(Color.parseColor("#00000000"));
                //v.setSelected(false);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                v.setBackgroundColor(Color.parseColor("#00000000"));
                //v.setSelected(false);
            }
        });

        builder.show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }


}
