package info.paybeam.www.paybeamv1.PayBeam.ListAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.paybeam.www.paybeamv1.R;

/**
 * Created by dflychew on 3/5/18.
 */

public class TransactionAdapter extends ArrayAdapter<Transaction> {

    private Context mContext;
    private List<Transaction> transactionList = new ArrayList<>();

    public TransactionAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Transaction> list) {
        super(context, 0 , list);
        mContext = context;
        transactionList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.transaction_list_item,parent,false);

        Transaction currentTransaction = transactionList.get(position);


        //Amount
        TextView amount = (TextView) listItem.findViewById(R.id.textView_amount);
        amount.setText(currentTransaction.getAmount());

        //Merchant
        TextView merchant = (TextView) listItem.findViewById(R.id.textView_merchant);
        merchant.setText(currentTransaction.getMerchant());

        //Date
        TextView date = (TextView) listItem.findViewById(R.id.textView_date);
        date.setText(currentTransaction.getDate());

       //Card Num
        TextView cardNum = (TextView) listItem.findViewById(R.id.textView_cardNum);
        cardNum.setText(currentTransaction.getCardNum());

        return listItem;
    }
}
