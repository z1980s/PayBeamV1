package info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity;

public class ScanQRPresenter implements ScanQRContract.ScanQRPresenter
{
    private ScanQRContract.ScanQRView scanQRView;

    ScanQRPresenter(ScanQRContract.ScanQRView view)
    {
        scanQRView = view;
    }
}
