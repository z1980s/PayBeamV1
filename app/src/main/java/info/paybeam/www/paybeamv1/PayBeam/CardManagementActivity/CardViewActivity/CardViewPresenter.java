package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardViewActivity;

import android.annotation.SuppressLint;
import android.annotation.SuppressLint;
import android.view.Display;
import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonParser;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardContract;
import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Cards;

/**
 * Created by dflychew on 30/4/18.
 */

public class CardViewPresenter implements CardViewContract.CardViewPresenter {



    CardViewContract.CardViewView cardViewView;
    CardViewPresenter(CardViewContract.CardViewView view)
    {
        cardViewView = view;
    }


    @Override
    public void onPageDisplayed() {
        cardViewView.setCard();
    }

    @Override
    public void onDeleteButtonClick(View view) {
        //Toast.makeText(cardViewView.getActivity(),"Delete",Toast.LENGTH_SHORT).show();


        //Insert Progress Dialog here
        //Set cancelable to false


        //Get the card details
        final Cards card = cardViewView.getCard();

        String[] credentials= InternalStorage.readString(cardViewView.getActivity(), "Credentials").split(",");
        final String username = credentials[0];
        final String token = InternalStorage.readToken(cardViewView.getActivity(),"Token");
        //Implement Delete on Server
        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "RemoveCard");
        msg.addProperty("LoginName", username);
        msg.addProperty("CardNo", card.getCardNum());
        msg.addProperty("Token", InternalStorage.readToken(cardViewView.getActivity(), "Token"));

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg, cardViewView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                try {
                    final JsonParser jParser = new JsonParser();
                    final JsonObject jResponse = (JsonObject) jParser.parse(response);
                    final String reason = jResponse.get("reason").getAsString();
                    if (jResponse.get("result").getAsString().equals("Success")) {

                        //Implement delete on local file
                        //InternalStorage.deleteCard(cardViewView.getActivity(),"cards", card);
                        JsonObject getCardsMsg = new JsonObject();
                        getCardsMsg.addProperty("Header", "GetCards");
                        getCardsMsg.addProperty("LoginName",username);
                        getCardsMsg.addProperty("Token", InternalStorage.readToken(cardViewView.getActivity(),"Token"));
                        ServerConnection sc = new ServerConnection(getCardsMsg, cardViewView.getActivity()) {
                            @Override
                            public void receiveResponse(String response) {
                                JsonObject jResponse = (JsonObject)jParser.parse(response);
                                if (jResponse.get("result").getAsString().equals("Success")) {
                                    int count = jResponse.get("count").getAsInt();

                                    JsonObject cardList = jResponse.get("CardList").getAsJsonObject();
                                    ArrayList<JsonObject> cardArrayList = new ArrayList<>();
                                    for (int a = 1; a < count+1; a++) {
                                        JsonObject card = cardList.get("Card_" + a).getAsJsonObject();
                                        cardArrayList.add(card);
                                    }

                                    InternalStorage.writeCardListToFile(cardViewView.getActivity(),"cards",cardArrayList);

                                    cardViewView.showSuccessMessage(reason);
                                } else {
                                    System.err.println("A system Error has occured");
                                    cardViewView.showErrorMessage("A system Error has occured");
                                }
                            }
                        };

                        sc.execute(null,null,null);
                    } else {
                        cardViewView.showErrorMessage(jResponse.get("reason").getAsString());
                    }

                } catch (Exception e) {
                    System.err.println("A system Error has occured");
                    cardViewView.showErrorMessage("A system Error has occured");
                }
            }
        };

        sc.execute(null,null,null);

    }

    @Override
    public void setPrimaryCard(final Cards card) {
        //set up progress dialog in view
        //set primary card in server

        //get old default card
        final JsonObject oldDefault = InternalStorage.getDefaultCard(cardViewView.getActivity(),"cards");
        final String cardNo = oldDefault.get("cardNum").getAsString();

        final String[] credentials = InternalStorage.readString(cardViewView.getActivity(),"Credentials").split(",");
        final String username = credentials[0];

        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "SetDefaultCard");
        msg.addProperty("LoginName", username);
        msg.addProperty("OldDefaultCardNo", cardNo);
        msg.addProperty("NewDefaultCardNo", card.getCardNum());
        msg.addProperty("Token", InternalStorage.readToken(cardViewView.getActivity(), "Token"));

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg, cardViewView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject) jParser.parse(response);

                    if (jResponse.get("result").getAsString().equals("Success")) {
                        //Upon success
                        //set primary card in local file
                        InternalStorage.setNewDefaultCard(cardViewView.getActivity(), "cards", card);

                        cardViewView.showSuccessMessage("Card '" + card.getCardNum() +"' successfully set as default");

                        //Refresh card view
                        cardViewView.refreshCardView(InternalStorage.getCard(cardViewView.getActivity(), "cards", card));
                    } else {
                        cardViewView.showErrorMessage("Failed to set default card.");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    cardViewView.showErrorMessage("A System Error has occured");
                }
            }
        };
        sc.execute(null,null,null);
    }


}
