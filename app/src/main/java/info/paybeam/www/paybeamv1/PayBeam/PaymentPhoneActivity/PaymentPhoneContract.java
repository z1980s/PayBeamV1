package info.paybeam.www.paybeamv1.PayBeam.PaymentPhoneActivity;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by zicokuang on 3/4/18.
 */

public interface PaymentPhoneContract
{
    interface PaymentPhoneView
    {
        void addMessage();
        void handleIncomingMessage();
        Activity getActivity();
        void showSuccess(String message);
    }

    interface PaymentPhonePresenter
    {
        void onSubmitButtonClick(View view);
        void messageSent();
        void messageReceived();
        void handleIncomingMessage(String receivedMessage);
    }
}
