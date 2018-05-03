package info.paybeam.www.paybeamv1.PayBeam.TransactionActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Cards;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.CardsAdapter;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Transaction;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.TransactionAdapter;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.TransactionActivityBinding;

public class TransactionActivity extends AppCompatActivity implements TransactionContract.TransactionView
{
    private TransactionPresenter transactionPresenter;

    ListView lst;
    ArrayList<JsonObject> transactionList;
    private TransactionAdapter transactionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_activity);
        TransactionActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.transaction_activity);
        transactionPresenter = new TransactionPresenter(this);
        binding.setTransactionPresenter(transactionPresenter);
    }


    @Override
    public void displayTransactions(JsonObject list) {
        //Set transaction list view
        lst= (ListView) findViewById(R.id.transaction_list_view);

        //Create a arraylist to store all the list
        ArrayList<Transaction> transactionList = new ArrayList<>();

        for(int i = 0 ; i < list.get("count").getAsInt() ;i++)
        {
           //transactionList.add(new Cards(list.get("amount").getAsString(),list.get("merchant").getAsString(),list.get("date").getAsString(),list.get("cardNum").getAsString());
        }

        //Put the cardList into cardAdapter
        transactionAdapter = new TransactionAdapter(this,transactionList);
        //set cardAdapter into the listview
        lst.setAdapter(transactionAdapter);
    }

}
