package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity;

import android.view.View;

/**
 * Created by zicokuang on 3/4/18.
 */

public interface CardContract
{
    interface CardView
    {
        void displayCards();
        void showAddCard();
        void showAddCardSuccessMessage();
        void showAddCardErrorMessage();
        void showRemoveCardSuccessMessage();
        void showRemoveCardErrorMessage();
    }

    interface CardPresenter
    {
        void onAddCardButtonClick(View view);
        void onRemoveCardButtonClick(View view);
    }
}
