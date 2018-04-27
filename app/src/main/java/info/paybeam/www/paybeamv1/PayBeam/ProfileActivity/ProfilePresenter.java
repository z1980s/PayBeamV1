package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity;

import android.provider.ContactsContract;
import android.view.View;

import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

public class ProfilePresenter implements ProfileContract.ProfilePresenter
{
    private ProfileContract.ProfileView profileView;

    ProfilePresenter(ProfileContract.ProfileView view)
    {
        profileView = view;
    }

    @Override
    public void onPageDisplayed() {
        profileView.displayProfileDetails(InternalStorage.readProfileFromFile(profileView.getActivity(),"profile"));
    }

    @Override
    public void onEditProfileButtonClick(View view) {
        profileView.showEditProfileView();
    }


}
