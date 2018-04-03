package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity;

/**
 * Created by zicokuang on 3/4/18.
 */

public interface CardContract
{
    interface CardView
    {
        void displayCards();
        void showAddCardSuccessMessage();
        void showAddCardErrorMessage();
        void showRemoveCardSuccessMessage();
        void showRemoveCardErrorMessage();
    }

    interface CardPresenter
    {
        void onAddCardButtonClick();
        void onRemoveCardButtonClick();
    }
}
