package info.paybeam.www.paybeamv1.PayBeam.HomeActivity;

import android.view.View;

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
    public void onEditProfileImageClick(View view)
    {
        homeView.showEditProfileView();
    }

    @Override
    public void onPaymentPhoneImageClick(View view)
    {
        homeView.showPaymentPhoneView();
    }

    @Override
    public void onPaymentReaderImageClick(View view) { homeView.showPaymentReaderView(); }

    @Override
    public void onCardManagementImageClick(View view)
    {
        homeView.showCardManagementView();
    }

    @Override
    public void onTransactionImageClick(View view)
    {
        homeView.showTransactionView();
    }

    @Override
    public void onSettingsImageClick(View view)
    {
        homeView.showSettingsView();
    }

    @Override
    public void onWalletImageClick(View view) { homeView.showWalletView(); }

    @Override
    public void onScanQRImageClick(View view) { homeView.showScanQRView(); }

}




