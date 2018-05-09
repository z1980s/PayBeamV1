package info.paybeam.www.paybeamv1.PayBeam.QRActivity.GenerateQRActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;

public interface GenerateQRContract
{
    interface GenerateQRView
    {
        String getAmount();
        Activity getActivity();
        void displayQRImage(Bitmap qrImage, String amount);
        void showDialog(String text);

    }

    interface GenerateQRPresenter
    {
        void generateQRCodeButtonClick(View view);
        void generateQRimage(String amount);
    }
}
