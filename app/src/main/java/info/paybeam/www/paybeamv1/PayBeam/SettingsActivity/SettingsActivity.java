package info.paybeam.www.paybeamv1.PayBeam.SettingsActivity;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.SettingsActivityBinding;

public class SettingsActivity extends AppCompatActivity implements SettingsContract.SettingsView
{
    private SettingsPresenter settingsPresenter;
    WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        SettingsActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.settings_activity);
        settingsPresenter = new SettingsPresenter(this);
        binding.setSettingsPresenter(settingsPresenter);

        webView = (WebView) findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.paybeam.info/about/");
    }
}
