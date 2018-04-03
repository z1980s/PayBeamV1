package info.paybeam.www.paybeamv1.PayBeam.HomeActivity;

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
    }

    interface HomePresenter
    {
        void onEditProfileImageClick();
        void onPaymentPhoneImageClick();
        void onPaymentReaderImageClick();
        void onCardManagementImageClick();
        void onTransactionImageClick();
    }
}
