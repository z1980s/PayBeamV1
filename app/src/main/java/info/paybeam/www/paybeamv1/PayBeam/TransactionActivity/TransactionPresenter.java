package info.paybeam.www.paybeamv1.PayBeam.TransactionActivity;

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
}
