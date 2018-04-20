package info.paybeam.www.paybeamv1.PayBeam.HomeActivity;

import android.view.View;

/**
 * Interface between activity and presenter
 */

public interface HomeContract
{
    interface HomeView
    {
        void showProfileView();
        void showPaymentPhoneView();
        void showPaymentReaderView();
        void showCardManagementView();
        void showTransactionView();
        void showSettingsView();
        void showWalletView();
        void showScanQRView();
    }

    interface HomePresenter
    {
        void onProfileImageClick(View view);
        void onPaymentPhoneImageClick(View view);
        void onPaymentReaderImageClick(View view);
        void onCardManagementImageClick(View view);
        void onTransactionImageClick(View view);
        void onSettingsImageClick(View view);
        void onWalletImageClick(View view);
        void onScanQRImageClick(View view);

    }
}
