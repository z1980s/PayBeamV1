package info.paybeam.www.paybeamv1.PayBeam.SettingsActivity.ProfileActivity;

/**
 * Created by zicokuang on 3/4/18.
 */

public class ProfilePresenter implements ProfileContract.ProfilePresenter
{
    private ProfileContract.ProfileView profileView;

    ProfilePresenter(ProfileContract.ProfileView view)
    {
        profileView = view;
    }
}
