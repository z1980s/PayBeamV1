package info.paybeam.www.paybeamv1.PayBeam.TransactionActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Cards;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.CardsAdapter;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Transaction;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.TransactionAdapter;
import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.TransactionActivityBinding;

public class TransactionActivity extends AppCompatActivity implements TransactionContract.TransactionView
{
    private TransactionPresenter transactionPresenter;

    ListView lst;
    TextView month;
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
        transactionPresenter.onPageDisplayed();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showErrorMessage(final String errorMsg)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(errorMsg);
        dlgAlert.setTitle("Error");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (errorMsg.contains("Invalid or Expired Session Token")) {
                            Intent intent = new Intent(TransactionActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    }
                });
        dlgAlert.create().show();
    }

    @Override
    public void displayMonth() {
        month = findViewById(R.id.textView_month);


        String[] monthName = {"January", "February",
                "March", "April", "May", "June", "July",
                "August", "September", "October", "November",
                "December"};

        Calendar cal = Calendar.getInstance();
        String m = monthName[cal.get(Calendar.MONTH)];


        month.setText(m.toString());
    }

    @Override
    public void showNoTransactions() {
        
    }


    @Override
    public void displayTransactions(ArrayList<JsonObject>transactions) {
        //Set transaction list view
        lst= (ListView) findViewById(R.id.transaction_list_view);

        //Create a arraylist to store all the list
        ArrayList<Transaction> transactionList = new ArrayList<>();

        //membernames are "Name", "DateTime", "Card" and "Amount"
        for(JsonObject obj:transactions)
        {
           transactionList.add(new Transaction(obj.get("Amount").getAsString(),obj.get("Name").getAsString(),obj.get("DateTime").getAsString(),"Card No: " + obj.get("Card").getAsString()));
            //Toast.makeText(this,obj.get("Amount").getAsString(),Toast.LENGTH_SHORT).show();
        }


        //Put the cardList into cardAdapter
        transactionAdapter = new TransactionAdapter(this,transactionList);
        //set cardAdapter into the listview
        lst.setAdapter(transactionAdapter);
    }

}
