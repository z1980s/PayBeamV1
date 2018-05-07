package info.paybeam.www.paybeamv1.PayBeam.PaymentReaderActivity;

import android.app.Activity;

/**
 * Created by zicokuang on 3/4/18.
 */

public interface PaymentReaderContract
{
    interface PaymentReaderView
    {
        Activity getActivity();
    }

    interface PaymentReaderPresenter
    {
        void setData();
    }
}
