package info.paybeam.www.paybeamv1.PayBeam.OTPActivity;

import android.view.View;

/**
 * Created by zicokuang on 2/4/18.
 */

public class OtpPresenter implements OtpContract.OtpPresenter
{
    OtpContract.OtpView otpView;

    OtpPresenter(OtpContract.OtpView view)
    {
        otpView = view;
    }

    public void onSubmitButtonClick(View view)
    {
        //check if OTP is valid
        //if valid show successView()
        //if false show FailureView()
    }

}
