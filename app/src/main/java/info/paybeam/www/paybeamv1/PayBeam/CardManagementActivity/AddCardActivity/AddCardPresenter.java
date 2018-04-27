package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity;

import android.annotation.SuppressLint;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.braintreepayments.cardform.*;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardPresenter;
import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.SecurityModule.RSA;

/**
 * Created by dflychew on 6/4/18.
 */

public class AddCardPresenter implements AddCardContract.AddCardPresenter{


    AddCardContract.AddCardView addCardView;


    AddCardPresenter(AddCardContract.AddCardView view)
    {
        addCardView = view;
    }

    CardPresenter cardPresenter;

    @Override
    public void onAddCardButtonClick(View view){
        //establish connection to the server
        //store information

        Toast.makeText(addCardView.getActivity(), "WHAT THE FUCK", Toast.LENGTH_SHORT);
        addCardView.extractValues();
    }

    @Override
    public void addCard(String cardNumber, String expirationMonth, String expirationYear, String cvv, String postalCode, String countryCode, String mobileNo)
    {

        //store values locally
        //extract last 4 digits and relevant display information, store locally into a list so it can be reloaded on cardActivity page
        Toast.makeText(addCardView.getActivity(), cardNumber, Toast.LENGTH_SHORT);
        int length = cardNumber.length();
        String maskCardNum = cardNumber;

        /* Just for testing
        String maskCardNum = "1234 1234 1234 1234";
        int length = maskCardNum.length();
        */
        /*
        char[] chars = maskCardNum.toCharArray();
        for(int i = 0;i<(length-4);i++)
        {
            chars[i]='X';
        }
        maskCardNum = String.valueOf(chars)+ " , " + (expirationMonth+"/"+expirationYear) +'\n';
        */

        maskCardNum = maskCardNum.substring(length-5,length-1)+ " , " + (expirationMonth+"/"+expirationYear) +'\n';

        String[] credentials = InternalStorage.readString(addCardView.getActivity(), "Credentials").split(",");
        String token = InternalStorage.readToken(addCardView.getActivity(), "Token");


        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "AddCard");
        msg.addProperty("LoginName", credentials[0]);
        msg.addProperty("CardNo", cardNumber.substring(length-5,length-1));
        JsonObject cardInfo = new JsonObject();
        cardInfo.addProperty("FullCardNo", cardNumber);
        cardInfo.addProperty("ExpiryMonth", expirationMonth);
        cardInfo.addProperty("ExpiryYear", expirationYear);
        cardInfo.addProperty("CVV", cvv);
        try {
            byte[] encrypted = new RSA(addCardView.getActivity()).encrypt(cardInfo.toString().getBytes());
            String encryptedString = Base64.encodeToString(encrypted, Base64.NO_WRAP);
            //System.out.println(encryptedString);
            msg.addProperty("encrypted", encryptedString);
            msg.addProperty("token", token);

            @SuppressLint("StaticFieldLeak")
            ServerConnection sc = new ServerConnection(msg, addCardView.getActivity()) {
                @Override
                public void receiveResponse(String response) {
                    try {
                        JsonParser jParser = new JsonParser();
                        JsonObject jResponse = (JsonObject) jParser.parse(response);
                        if (jResponse.get("result").getAsString().equals("Success")) {
                            System.out.println(jResponse.get("reason").getAsString());
                            addCardView.showSuccessMessage(jResponse.get("reason").getAsString());
                            //do success
                        } else {
                            //do failure
                            System.out.println(jResponse.get("reason").getAsString());
                            addCardView.showErrorMessage(jResponse.get("reason").getAsString());
                        }
                    } catch (com.google.gson.JsonSyntaxException jse){
                        System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                        addCardView.showErrorMessage("ERROR SERVER OFFLINE");
                    }
                }
            };

            sc.execute(null,null,null);

        } catch (Exception e){
            e.printStackTrace();
        }
        //if there are no cards
        //write masked cardnumber to the default card file

        int count = InternalStorage.countEntries(addCardView.getActivity(),"cards");
        if(count==0)
        {
            InternalStorage.writeString(addCardView.getActivity(),"defaultCard", maskCardNum);
        }

        //write masked cardnumber to the card file
        InternalStorage.writeCardToFile(addCardView.getActivity(),"cards", maskCardNum);

        //InternalStorage.delete(addCardView.getActivity().getApplicationContext(),"card");


        addCardView.finishAddCard();
        //InternalStorage.read(addCardView.getActivity().getApplicationContext(),"cards");
        //concatenate all values and encrypt with bank's public key, send to payBeam server for storage
        //payBeam server should verify and validate card information and send back a response
        //upon successfully response, call addCardView.showSuccessMessage and bring user back to cardActivity page
        //upon failure response, call addCardView.showErrorMessage and bring user back to addCardActivity page
        //upon receive no response or null object, call addCardView.showServerError and bring user back to addCardActivity page
    }




}
