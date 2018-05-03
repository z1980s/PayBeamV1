package info.paybeam.www.paybeamv1.PayBeam.ListAdapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
 * Created by dflychew on 26/4/18.
 */

public class CardsAdapter extends ArrayAdapter<Cards>{

    private Context mContext;
    private List<Cards> cardsList = new ArrayList<>();

    public CardsAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Cards> list) {
        super(context, 0 , list);
        mContext = context;
        cardsList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.list_item,parent,false);

        Cards currentCard = cardsList.get(position);

        ImageView image = (ImageView)listItem.findViewById(R.id.imageView_cardImage);
        image.setImageResource(currentCard.getCardImage());

        TextView name = (TextView) listItem.findViewById(R.id.textView_cardNum);
        name.setText(currentCard.getCardNum());

        TextView expiry = (TextView) listItem.findViewById(R.id.textView_expiryDate);
        expiry.setText(currentCard.getExpiryDate());

        ImageView tick = (ImageView) listItem.findViewById(R.id.textView_tick);

        if(currentCard.getPrimary())
        {
            tick.setImageResource(currentCard.getTickImage());
        }

        return listItem;
    }
}
