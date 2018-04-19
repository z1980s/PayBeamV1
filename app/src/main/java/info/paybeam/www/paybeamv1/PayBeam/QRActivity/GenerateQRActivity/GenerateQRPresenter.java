package info.paybeam.www.paybeamv1.PayBeam.QRActivity.GenerateQRActivity;

public class GenerateQRPresenter implements GenerateQRContract.GenerateQRPresenter
{
    private GenerateQRContract.GenerateQRView generateQRView;

    GenerateQRPresenter(GenerateQRContract.GenerateQRView view)
    {
        generateQRView = view;
    }
}
