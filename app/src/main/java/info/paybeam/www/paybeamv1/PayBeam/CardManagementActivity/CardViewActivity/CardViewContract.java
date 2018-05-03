package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardViewActivity;

import android.app.Activity;
import android.view.View;

import com.google.gson.JsonObject;

import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Cards;

/**
 * Created by dflychew on 30/4/18.
 */

public interface CardViewContract {

    interface CardViewView{
        void setCard();
        void showSuccessMessage(String succMsg);
        void showErrorMessage(String errorMsg);
        Activity getActivity();
        Cards getCard();
        void finishActivity();
        void refreshCardView(JsonObject obj);

    }

    interface CardViewPresenter{
        void onPageDisplayed();
        void onDeleteButtonClick(View view);
        void setPrimaryCard(Cards card);
    }
}
