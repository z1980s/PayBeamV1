package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity;

import android.app.Activity;
import android.view.View;

import java.util.ArrayList;

/**
 * Created by zicokuang on 3/4/18.
 */

public interface CardContract
{
    interface CardView
    {
        void displayCards(ArrayList<String> cards);
        void showAddCard();
        void showAddCardSuccessMessage();
        void showAddCardErrorMessage();
        void showRemoveCardSuccessMessage();
        void showRemoveCardErrorMessage();
        Activity getActivity();
    }

    interface CardPresenter
    {
        void onCardPageDisplayed();
        void onAddCardButtonClick(View view);
        void onRemoveCardButtonClick(View view);
    }
}
