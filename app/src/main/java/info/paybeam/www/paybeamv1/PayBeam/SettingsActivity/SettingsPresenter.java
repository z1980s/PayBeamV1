package info.paybeam.www.paybeamv1.PayBeam.SettingsActivity;

/**
 * Created by zicokuang on 3/4/18.
 */

public class SettingsPresenter implements SettingsContract.SettingsPresenter
{
    private SettingsContract.SettingsView settingsView;

    SettingsPresenter(SettingsContract.SettingsView view)
    {
        settingsView = view;
    }
}
