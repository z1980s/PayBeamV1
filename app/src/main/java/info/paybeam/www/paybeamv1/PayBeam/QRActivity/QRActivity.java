package info.paybeam.www.paybeamv1.PayBeam.QRActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.QrActivityBinding;

public class QRActivity extends AppCompatActivity implements QRContract.QRView
{
    private QRPresenter qrPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.qr_activity);
        QrActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.qr_activity);
        qrPresenter = new QRPresenter(this);
        binding.setQrPresenter(qrPresenter);
    }
}
