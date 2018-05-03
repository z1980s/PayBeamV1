package info.paybeam.www.paybeamv1.PayBeam.TransactionActivity;

import android.annotation.SuppressLint;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

/**
 * Created by zicokuang on 3/4/18.
 */

public class TransactionPresenter implements TransactionContract.TransactionPresenter
{
    private TransactionContract.TransactionView transactionView;

    TransactionPresenter(TransactionContract.TransactionView view)
    {
        transactionView = view;
    }

    @Override
    public void onPageDisplayed() {
        //Get the transactions
        //Pass the transactions from server into function

        JsonObject msg = new JsonObject();

        final String[] credentials = InternalStorage.readString(transactionView.getActivity(),"Credentials").split(",");
        final String username = credentials[0];

        msg.addProperty("Header", "GetTransactions");
        msg.addProperty("LoginName", username);
        msg.addProperty("Token", InternalStorage.readToken(transactionView.getActivity(),"Token"));

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg, transactionView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject)jParser.parse(response);

                    if (jResponse.get("result").getAsString().equals("Success")) {
                        JsonObject transactionList = jResponse.getAsJsonObject("TransactionList");
                        int count = transactionList.get("Count").getAsInt();

                        ArrayList<JsonObject> transactions = new ArrayList<>();

                        for (int a = 1; a < count+1; a++) {
                            //membernames are "Name", "DateTime", "Card" and "Amount"
                            JsonObject transaction = transactionList.get("transaction_" + a).getAsJsonObject();
                            transactions.add(transaction);
                        }
                        //do whatever to display transactions.
                    } else {
                        //show error message
                        transactionView.showErrorMessage(jResponse.get("reason").getAsString());
                    }
                } catch (Exception e) {
                    transactionView.showErrorMessage("A System Error has occured");
                    e.printStackTrace();
                }

            }
        };

        sc.execute(null,null,null);

        //transactionView.displayTransactions();
    }
}
