package info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity;

import android.app.Activity;

public interface ScanQRContract
{
    interface ScanQRView
    {
        Activity getActivity();
        void showErrorMessage(String message);
        void showSuccess(String message);
    }

    interface ScanQRPresenter
    {
        void getResult(String result);
    }
}
