package info.paybeam.www.paybeamv1.PayBeam.QRActivity;

public class QRPresenter implements QRContract.QRPresenter
{
    private QRContract.QRView qrView;

    QRPresenter(QRContract.QRView view)
    {
        qrView = view;
    }
}
