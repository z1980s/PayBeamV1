package info.paybeam.www.paybeamv1.PayBeam.QRActivity;

import android.view.View;

public interface QRContract
{
    interface QRView
    {
        void showScanQRView();
        void showGenerateQRView();
    }

    interface QRPresenter
    {
        void onScanQRButtonClick(View view);
        void onGenerateQRButtonClick (View view);
    }
}
