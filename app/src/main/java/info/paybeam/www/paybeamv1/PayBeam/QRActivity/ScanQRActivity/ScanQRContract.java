package info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity;

import android.app.Activity;

public interface ScanQRContract
{
    interface ScanQRView
    {
        Activity getActivity();
    }

    interface ScanQRPresenter
    {
        void getResult(String result);
    }
}
