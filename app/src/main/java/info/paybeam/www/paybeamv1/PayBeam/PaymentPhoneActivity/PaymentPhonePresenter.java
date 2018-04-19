package info.paybeam.www.paybeamv1.PayBeam.PaymentPhoneActivity;

import android.view.View;

import java.util.ArrayList;

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

    public void messageReceived()
    {
        ppView.handleIncomingMessage();
    }

    @Override
    public void onSubmitButtonClick(View view)
    {
        ppView.addMessage();
    }

    @Override
    public void messageSent()
    {
        //ppView.showSuccess();
    }

    @Override
    public void handleIncomingMessage(ArrayList<String> receivedMessages)
    {
        //extract components from arraylist
        //create connection to server
        //initiate transfer between both parties on PayBeam server and return response
        //if successful call mainView.showSuccess()
        //if fail call mainView.showFailure()
    }
}
