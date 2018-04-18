package info.paybeam.www.paybeamv1.PayBeam.CreateAccountActivity;

import android.app.Activity;
import android.view.View;

/**
 * Created by zicokuang on 2/4/18.
 */

public interface CreateAccountContract
{
    interface CreateAccountView
    {
        void onSuccessView();
        void onFailureView(String errorMessage);
        void extractValues();
        String[] extractCredentials();
        Activity getActivity();
        void setVariables(String message);
        void requestOTP();
        void otpFailure(String errorMessage);
        void startLoginActivity();
        void showProgressDialog(String message);
        void hideProgressDialog();
    }

    interface CreateAccountPresenter
    {
        void onSubmitButtonClick(View view);
        void verifyDetails(String name, String username, String password, String email, String address, String phoneNo);
        void checkOTP(int OTP);
        void otpFailure();
    }
}
