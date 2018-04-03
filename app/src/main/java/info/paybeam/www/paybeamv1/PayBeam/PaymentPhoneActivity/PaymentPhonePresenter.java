package info.paybeam.www.paybeamv1.PayBeam.PaymentPhoneActivity;

/**
 * Created by zicokuang on 3/4/18.
 */

public class PaymentPhonePresenter implements PaymentPhoneContract.PaymentPhonePresenter
{
    private PaymentPhoneContract.PaymentPhoneView ppView;

    PaymentPhonePresenter(PaymentPhoneContract.PaymentPhoneView view)
    {
        ppView = view;
    }
}
