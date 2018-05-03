package info.paybeam.www.paybeamv1.PayBeam.TransactionActivity;

/**
 * Created by zicokuang on 3/4/18.
 */

public interface TransactionContract
{
    interface TransactionView
    {
        void displayTransactions();
    }

    interface TransactionPresenter
    {
        void onPageDisplayed();
    }
}
