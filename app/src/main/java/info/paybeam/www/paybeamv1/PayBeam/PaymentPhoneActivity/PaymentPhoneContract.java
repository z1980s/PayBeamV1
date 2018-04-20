package info.paybeam.www.paybeamv1.PayBeam.PaymentPhoneActivity;

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
        void showSuccess(String message);
    }

    interface PaymentPhonePresenter
    {
        void onSubmitButtonClick(View view);
        void messageSent();
        void messageReceived();
        void handleIncomingMessage(ArrayList<String> receivedMessages);
    }
}
