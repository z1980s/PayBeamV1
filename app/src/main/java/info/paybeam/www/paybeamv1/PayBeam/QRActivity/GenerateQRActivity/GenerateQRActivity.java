package info.paybeam.www.paybeamv1.PayBeam.QRActivity.GenerateQRActivity;

import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.GenerateQrActivityBinding;

public class GenerateQRActivity extends AppCompatActivity implements GenerateQRContract.GenerateQRView
{
    ImageView qrCode;
    EditText editText;
    TextView textView;


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


    @Override
    public String getAmount() {
        //Get the amount and change to 2dp
        editText = findViewById(R.id.amount_text_field);
        String amount = String.format("%.2f", Float.parseFloat(editText.getText().toString()));
        //pass the amount to the presenter to generate the QR image
        //generateQRPresenter.generateQRimage(amount);
        return amount;
    }

    @Override
    public void displayQRImage(Bitmap qrImage, String amount) {
        //Set qrCode image view
        qrCode = findViewById(R.id.QRCode);

        //Hide the textbox and the send button
        editText.setVisibility(View.GONE);
        findViewById(R.id.generate_qr_button).setVisibility(View.GONE);

        //show amount to be sent
        textView = findViewById(R.id.amount_text_view);
        textView.setText("$"+amount);
        textView.setVisibility(View.VISIBLE);

        //Display the bitmap image on the image view
        qrCode.setImageBitmap(qrImage);
    }


}
