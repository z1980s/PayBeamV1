package info.paybeam.www.paybeamv1.PayBeam.HomeActivity;

import android.view.View;

/**
 * Interface between activity and presenter
 */

public interface HomeContract
{
    interface HomeView
    {
        void showEditProfileView();
        void showPaymentPhoneView();
        void showPaymentReaderView();
        void showCardManagementView();
        void showTransactionView();
        void showSettingsView();
    }

    interface HomePresenter
    {
        void onEditProfileImageClick(View view);
        void onPaymentPhoneImageClick(View view);
        void onPaymentReaderImageClick(View view);
        void onCardManagementImageClick(View view);
        void onTransactionImageClick(View view);
        void onSettingsImageClick(View view);

    }
}
