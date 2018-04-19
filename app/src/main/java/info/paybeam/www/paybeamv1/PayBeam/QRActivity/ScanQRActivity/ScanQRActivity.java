package info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.ScanQrActivityBinding;

public class ScanQRActivity extends AppCompatActivity implements ScanQRContract.ScanQRView
{
    private ScanQRPresenter scanQRPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_qr_activity);
        ScanQrActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.scan_qr_activity);
        scanQRPresenter = new ScanQRPresenter(this);
        binding.setScanQRPresenter(scanQRPresenter);
    }
}
