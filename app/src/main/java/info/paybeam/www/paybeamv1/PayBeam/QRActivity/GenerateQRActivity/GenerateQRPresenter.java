package info.paybeam.www.paybeamv1.PayBeam.QRActivity.GenerateQRActivity;

import android.graphics.Bitmap;
import android.view.View;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import info.paybeam.www.paybeamv1.R;

public class GenerateQRPresenter implements GenerateQRContract.GenerateQRPresenter
{
    private GenerateQRContract.GenerateQRView generateQRView;

    GenerateQRPresenter(GenerateQRContract.GenerateQRView view)
    {
        generateQRView = view;
    }

    @Override
    public void generateQRCodeButtonClick(View view) {
        generateQRimage(generateQRView.getAmount());
    }

    @Override
    public void generateQRimage(String amount) {
        //Concatenate the amount with the account ID
        //JSON format
        //For the QR Code
        String text="{\"name\":\"Dave\",\"amount\":\""+ amount +"\"}";


        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            //Generate QR code with the string text
            BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,250,250);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            //Send qr code and amount to activity for display
            generateQRView.displayQRImage(bitmap,amount);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}
