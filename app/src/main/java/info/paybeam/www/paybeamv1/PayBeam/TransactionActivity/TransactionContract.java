package info.paybeam.www.paybeamv1.PayBeam.TransactionActivity;

import com.google.gson.JsonObject;

/**
 * Created by zicokuang on 3/4/18.
 */

public interface TransactionContract
{
    interface TransactionView
    {
        void displayTransactions(JsonObject list);
    }

    interface TransactionPresenter
    {
        void onPageDisplayed();
    }
}
