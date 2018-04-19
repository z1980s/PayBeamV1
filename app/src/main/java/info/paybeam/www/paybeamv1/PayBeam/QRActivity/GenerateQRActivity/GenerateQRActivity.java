package info.paybeam.www.paybeamv1.PayBeam.QRActivity.GenerateQRActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.GenerateQrActivityBinding;

public class GenerateQRActivity extends AppCompatActivity implements GenerateQRContract.GenerateQRView
{
    private GenerateQRPresenter generateQRPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generate_qr_activity);
        GenerateQrActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.generate_qr_activity);
        generateQRPresenter = new GenerateQRPresenter(this);
        binding.setGenerateQRPresenter(generateQRPresenter);
    }
}
