package info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.EditProfileActivity;

public class EditProfilePresenter implements EditProfileContract.EditProfilePresenter
{
    private EditProfileContract.EditProfileView editProfileView;

    EditProfilePresenter(EditProfileContract.EditProfileView view)
    {
        editProfileView = view;
    }
}
