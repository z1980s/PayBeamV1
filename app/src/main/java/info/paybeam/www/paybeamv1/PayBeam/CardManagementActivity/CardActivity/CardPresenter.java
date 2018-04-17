package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity;

import android.view.View;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

/**
 * Created by zicokuang on 3/4/18.
 */

public
class CardPresenter implements CardContract.CardPresenter
{
    CardContract.CardView cardView;

    CardPresenter(CardContract.CardView view)
    {
        cardView = view;
    }


    @Override
    public void onCardPageDisplayed() {
        //get the cards from internal storage return in an array
        //InternalStorage.write(cardView.getActivity().getApplicationContext(),"cards","XXXXXXXXXXXX1234");
        cardView.displayCards(InternalStorage.readCardsFromFile(cardView.getActivity().getApplicationContext(),"cards"));

        /*ArrayList<String> cards = new ArrayList<String>() ;
        cards.add("XXXX XXXX XXXX 1234");
        cards.add("XXXX XXXX XXXX 4578");
        cards.add("XXXX XXXX XXXX 7893");
        cards.add("XXXX XXXX XXXX 8277");

        //send to the view to display
        cardView.displayCards(cards);*/
    }


    @Override
    public void onAddCardButtonClick(View view)
    {
        //storing of card information to be decided
        //if not, create server connection, store encrypted card information
        cardView.showAddCard();
    }

    @Override
    public void onRemoveCardButtonClick(View view)
    {
        //removal of card information
        //requires creating connection to server, identify card record and deleting it
    }
}
