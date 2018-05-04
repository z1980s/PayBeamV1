package info.paybeam.www.paybeamv1.PayBeam.QRActivity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import info.paybeam.www.paybeamv1.PayBeam.QRActivity.GenerateQRActivity.GenerateQRActivity;
import info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity.ScanQRActivity;
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

    @Override
    public void showScanQRView() {
        //Toast.makeText(this,"Show Scan QR View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ScanQRActivity.class);
        startActivity(intent);
    }

    @Override
    public void showGenerateQRView() {
        //Toast.makeText(this,"Show Generate QR View", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, GenerateQRActivity.class);
        startActivity(intent);
    }
}
