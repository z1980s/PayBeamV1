package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardViewActivity;

import android.view.View;
import android.widget.Toast;

import com.google.gson.JsonObject;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardContract;
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
        Toast.makeText(cardViewView.getActivity(),"Delete",Toast.LENGTH_SHORT).show();


        //Insert Progress Dialog here
        //Set cancelable to false


        //Get the card details
        Cards card = cardViewView.getCard();

        //Implement Delete on Server



        //Implement delete on local file

        InternalStorage.deleteCard(cardViewView.getActivity(),"cards", card);

        cardViewView.getActivity().finish();

    }
}
