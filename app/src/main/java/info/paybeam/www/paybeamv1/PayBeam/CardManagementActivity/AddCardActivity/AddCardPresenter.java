package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.AddCardActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.view.View;
import android.widget.Toast;

import com.braintreepayments.cardform.*;
import com.braintreepayments.cardform.utils.CardType;
import com.braintreepayments.cardform.view.CardForm;
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
    public void onAddCardButtonClick(View view)
    {
        boolean valid = addCardView.validate();
        if (valid)
        {
            addCardView.extractValues();
        }




    }

    @Override
    public void addCard(final String cardNumber, final String expirationMonth, final String expirationYear, final String cvv, final String postalCode, final String countryCode, final String mobileNo)
    {

        //store values locally
        //extract last 4 digits and relevant display information, store locally into a list so it can be reloaded on cardActivity page
        Toast.makeText(addCardView.getActivity(), cardNumber, Toast.LENGTH_SHORT);
        int length = cardNumber.length();


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

        final CardType cardType = CardType.forCardNumber(cardNumber);


        //last 4 digits, expiry date, cardType enum
        final String maskCardNum = cardNumber.substring(length-4,length);
                //+ "," + (expirationMonth+"/"+expirationYear) + "," + cardType.getFrontResource() +'\n';
        //Toast.makeText(addCardView.getActivity(),"CARD TYPE: " + cardType, Toast.LENGTH_SHORT).show();


        String[] credentials = InternalStorage.readString(addCardView.getActivity(), "Credentials").split(",");
        String token = InternalStorage.readToken(addCardView.getActivity(), "Token");


        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "AddCard");
        msg.addProperty("LoginName", credentials[0]);
        msg.addProperty("CardNo", cardNumber.substring(length-4,length));
        msg.addProperty("CardType", Integer.toString(cardType.getFrontResource()));
        msg.addProperty("ExpiryDate", expirationMonth + "/" + expirationYear);
        JsonObject cardInfo = new JsonObject();
        cardInfo.addProperty("FullCardNo", cardNumber);
        cardInfo.addProperty("ExpiryDate", expirationMonth + "/" + expirationYear);
        cardInfo.addProperty("CVV", cvv);
        try {
            byte[] encrypted = new RSA(addCardView.getActivity()).encrypt(cardInfo.toString().getBytes());
            String encryptedString = Base64.encodeToString(encrypted, Base64.NO_WRAP);

            msg.addProperty("encrypted", encryptedString);
            msg.addProperty("Token", token);

            @SuppressLint("StaticFieldLeak")
            ServerConnection sc = new ServerConnection(msg, addCardView.getActivity()) {
                @Override
                public void receiveResponse(String response) {
                    try {
                        JsonParser jParser = new JsonParser();
                        JsonObject jResponse = (JsonObject) jParser.parse(response);
                        if (jResponse.get("result").getAsString().equals("Success")) {
                            System.out.println(jResponse.get("reason").getAsString());
                            //do success

                            //if there are no cards
                            //write masked cardnumber to the default card file

                            int count = InternalStorage.countEntries(addCardView.getActivity(),"cards");
                            boolean primary = false;
                            if(count==0)
                            {
                                //InternalStorage.writeString(addCardView.getActivity(),"defaultCard", maskCardNum);
                                primary = true;
                            }

                            //write masked cardnumber to the card file
                            InternalStorage.writeCardToFile(addCardView.getActivity(),"cards", maskCardNum, (expirationMonth+"/"+expirationYear), Integer.toString(cardType.getFrontResource()), primary);
                            addCardView.showSuccessMessage(jResponse.get("reason").getAsString());
                            //InternalStorage.delete(addCardView.getActivity().getApplicationContext(),"card");

                            //add card success bring user to CardActivity page and refresh list of available cards
                            //InternalStorage.read(addCardView.getActivity().getApplicationContext(),"cards");
                            //concatenate all values and encrypt with bank's public key, send to payBeam server for storage
                            //payBeam server should verify and validate card information and send back a response
                            //upon successfully response, call addCardView.showSuccessMessage and bring user back to cardActivity page
                            //upon failure response, call addCardView.showErrorMessage and bring user back to addCardActivity page
                            //upon receive no response or null object, call addCardView.showServerError and bring user back to addCardActivity page
                        } else {
                            //do failure
                            System.out.println(jResponse.get("reason").getAsString());
                            addCardView.showErrorMessage(jResponse.get("reason").getAsString());
                        }
                    } catch (com.google.gson.JsonSyntaxException jse){
                        System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                        addCardView.showErrorMessage(response);
                    }
                }
            };

            sc.execute(null,null,null);

        } catch (Exception e){
            e.printStackTrace();
        }

    }




}
