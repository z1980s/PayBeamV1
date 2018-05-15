package info.paybeam.www.paybeamv1.PayBeam.TransactionActivity;

import android.app.Activity;

import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Created by zicokuang on 3/4/18.
 */

public interface TransactionContract
{
    interface TransactionView
    {
        void displayTransactions(ArrayList<JsonObject>transactions);
        Activity getActivity();
        void showErrorMessage(String errorMsg);
        void displayMonth();
        void showNoTransactions();
    }

    interface TransactionPresenter
    {
        void onPageDisplayed();
    }
}
