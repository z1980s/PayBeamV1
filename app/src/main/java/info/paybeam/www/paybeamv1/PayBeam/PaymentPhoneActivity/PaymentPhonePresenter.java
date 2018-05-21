package info.paybeam.www.paybeamv1.PayBeam.PaymentPhoneActivity;

import android.annotation.SuppressLint;
import android.view.View;

import com.braintreepayments.cardform.utils.CardType;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.SecurityModule.MD5;

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
        ppView.hideProgressDialog();
        ppView.handleIncomingMessage();
    }

    @Override
    public void onSubmitButtonClick(View view)
    {
        //ppView.addMessage();

        //Here we check the amount added before we call addMessage
        //ppView.checkAmount();
        ppView.getAmount();

    }

    public void onReceiveButtonClick(View view)
    {
        ppView.showReceiveDialog();
    }

    @Override
    public void messageSent()
    {
        //ppView.showSuccess();
    }

    @Override
    public void handleIncomingMessage(String receivedMessage)
    {
        String token = InternalStorage.readToken(ppView.getActivity(),"Token");
        String[] credentials = InternalStorage.readString(ppView.getActivity(),"Credentials").split(",");
        String user = credentials[0];

        JsonObject msg = new JsonObject();
        msg.addProperty("Header","P2PPayment");
        //add loginName of sender
        msg.addProperty("PayeeLoginName", user);
        //parse the amount and generate the hash
        JsonParser jParser = new JsonParser();
        System.out.println("received: " + receivedMessage);
        JsonObject paymentData = (JsonObject)jParser.parse(receivedMessage);
        String amount = paymentData.get("Amount").getAsString();
        String hash = new MD5().getHash(token + amount);
        //add the hash to the msg
        msg.addProperty("PayeeHash", hash);
        //add the message from the sender.
        msg.add("PaymentData", (JsonObject)jParser.parse(receivedMessage));

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg, ppView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject) jParser.parse(response);
                    if (jResponse.get("result").getAsString().equals("Success")) {
                        //do success
                        ppView.showSuccess(jResponse.get("reason").getAsString());
                        System.out.println("Success!");
                    } else {
                        System.out.println("Failure!");
                        //do failure
                        ppView.showErrorMessage(jResponse.get("reason").getAsString());
                    }
                } catch (com.google.gson.JsonSyntaxException jse){
                    System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                    ppView.showErrorMessage(response);
                }
            }
        };

        sc.execute(null,null,null);
        //extract components from arraylist
        //create connection to server
        //initiate transfer between both parties on PayBeam server and return response
        //if successful call mainView.showSuccess()
        //if fail call mainView.showFailure()

        //ppView.showSuccess(receivedMessages.get(0));
    }
}
