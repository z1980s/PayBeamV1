package info.paybeam.www.paybeamv1.PayBeam.PaymentReaderActivity;

/**
 * Created by zicokuang on 3/4/18.
 */

public class PaymentReaderPresenter implements PaymentReaderContract.PaymentReaderPresenter
{
    private PaymentReaderContract.PaymentReaderView prView;

    PaymentReaderPresenter(PaymentReaderContract.PaymentReaderView view)
    {
        prView = view;
    }
}
