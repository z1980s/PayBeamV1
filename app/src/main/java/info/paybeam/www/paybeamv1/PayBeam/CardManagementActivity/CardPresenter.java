package info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity;

/**
 * Created by zicokuang on 3/4/18.
 */

public class CardPresenter implements CardContract.CardPresenter
{
    CardContract.CardView cardView;

    CardPresenter(CardContract.CardView view)
    {
        cardView = view;
    }

    @Override
    public void onAddCardButtonClick()
    {
        //storing of card information to be decided
        //if not, create server connection, store encrypted card information
    }

    @Override
    public void onRemoveCardButtonClick()
    {
        //removal of card information
        //requires creating connection to server, identify card record and deleting it
    }
}
