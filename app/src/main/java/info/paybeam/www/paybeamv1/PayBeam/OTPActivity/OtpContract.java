package info.paybeam.www.paybeamv1.PayBeam.OTPActivity;

import android.view.View;

/**
 * Handles interactions between OTP activity and presenter
 */

public interface OtpContract
{
    interface OtpView
    {
        void showSuccessView();
        void showFailureView();
    }

    interface OtpPresenter
    {
        void onSubmitButtonClick(View view);
    }
}
