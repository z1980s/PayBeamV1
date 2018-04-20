package info.paybeam.www.paybeamv1.PayBeam.QRActivity;

import android.view.View;

public class QRPresenter implements QRContract.QRPresenter
{
    private QRContract.QRView qrView;

    QRPresenter(QRContract.QRView view)
    {
        qrView = view;
    }

    @Override
    public void onScanQRButtonClick(View view) { qrView.showScanQRView(); }

    @Override
    public void onGenerateQRButtonClick(View view) { qrView.showGenerateQRView(); }


}
