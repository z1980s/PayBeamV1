package info.paybeam.www.paybeamv1.PayBeam.TransactionActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.TransactionActivityBinding;

public class TransactionActivity extends AppCompatActivity implements TransactionContract.TransactionView
{
    private TransactionPresenter transactionPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.transaction_activity);
        TransactionActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.transaction_activity);
        transactionPresenter = new TransactionPresenter(this);
        binding.setTransactionPresenter(transactionPresenter);
    }
}
