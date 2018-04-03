package info.paybeam.www.paybeamv1.PayBeam.SettingsActivity.EditProfileActivity;

/**
 * Created by zicokuang on 3/4/18.
 */

public class EditProfilePresenter implements EditProfileContract.EditProfilePresenter
{
    private EditProfileContract.EditProfileView editProfileView;

    EditProfilePresenter(EditProfileContract.EditProfileView view)
    {
        editProfileView = view;
    }
}
