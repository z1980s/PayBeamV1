package info.paybeam.www.paybeamv1.PayBeam.PaymentReaderActivity;

import android.annotation.SuppressLint;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.NFCReaderModule.CardInfo;
import info.paybeam.www.paybeamv1.PayBeam.SecurityModule.DESPassPhrase;

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

    public void setData() {
        String[] credentials = InternalStorage.readString(prView.getActivity(), "Credentials").split(",");
        final String username = credentials[0];
        final String password = credentials[1];
        final String token = InternalStorage.readToken(prView.getActivity(), "Token");

        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "GetPasswordHash");
        JsonObject user = new JsonObject();
        user.addProperty("LoginName", username);
        user.addProperty("Password", password);
        msg.add("User", user);
        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg, prView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject) jParser.parse(response);

                    if (jResponse.get("result").getAsString().equals("Success")) {
                        String hash = jResponse.get("hash").getAsString();
                        String DES_Token = new DESPassPhrase(hash).encrypt(token);

                        JsonObject phoneMsg = new JsonObject();
                        phoneMsg.addProperty("IP", "182.55.236.211");
                        phoneMsg.addProperty("Port", 3333);
                        phoneMsg.addProperty("Customer", username);
                        phoneMsg.addProperty("Encrypted", DES_Token);

                        CardInfo.SetData(phoneMsg.toString());
                    } else {
                        System.out.println("Failed to receieve hash");
                    }

                } catch (Exception e) {
                    System.out.println("A System Error has occured");
                }
            }
        };
        sc.execute(null,null,null);
    }
}
