package info.paybeam.www.paybeamv1.PayBeam.HomeActivity;

/**
 * Handle home logic
 */

public class HomePresenter implements HomeContract.HomePresenter
{
    private HomeContract.HomeView homeView;

    HomePresenter(HomeContract.HomeView view)
    {
        homeView = view;
    }

    @Override
    public void onEditProfileImageClick()
    {
        homeView.showEditProfileView();
    }

    @Override
    public void onPaymentPhoneImageClick()
    {
        homeView.showPaymentPhoneView();
    }

    @Override
    public void onPaymentReaderImageClick()
    {
        homeView.showPaymentReaderView();
    }

    @Override
    public void onCardManagementImageClick()
    {
        homeView.showCardManagementView();
    }

    @Override
    public void onTransactionImageClick()
    {
        homeView.showTransactionView();
    }
}
