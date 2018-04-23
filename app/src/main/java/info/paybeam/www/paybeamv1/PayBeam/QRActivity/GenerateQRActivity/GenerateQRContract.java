package info.paybeam.www.paybeamv1.PayBeam.QRActivity.GenerateQRActivity;

import android.graphics.Bitmap;
import android.view.View;

public interface GenerateQRContract
{
    interface GenerateQRView
    {
        String getAmount();
        void displayQRImage(Bitmap qrImage, String amount);

    }

    interface GenerateQRPresenter
    {
        void generateQRCodeButtonClick(View view);
        void generateQRimage(String amount);
    }
}
