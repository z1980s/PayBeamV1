package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity;

import android.provider.ContactsContract;

public class ProfilePresenter implements ProfileContract.ProfilePresenter
{
    private ProfileContract.ProfileView profileView;

    ProfilePresenter(ProfileContract.ProfileView view)
    {
        profileView = view;
    }
}
