package info.paybeam.www.paybeamv1.PayBeam.SettingsActivity.ChangePasswordActivity;

/**
 * Created by zicokuang on 3/4/18.
 */

public class ChangePasswordPresenter implements ChangePasswordContract.ChangePasswordPresenter
{
    private ChangePasswordContract.ChangePasswordView cpView;

    ChangePasswordPresenter(ChangePasswordContract.ChangePasswordView view)
    {
        cpView = view;
    }
}
