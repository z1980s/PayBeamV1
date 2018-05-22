package info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import info.paybeam.www.paybeamv1.PayBeam.HomeActivity.HomeActivity;
import info.paybeam.www.paybeamv1.PayBeam.LoginActivity.LoginActivity;
import info.paybeam.www.paybeamv1.PayBeam.ProfileActivity.ChangePasswordActivity.ChangePasswordActivity;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.ScanQrActivityBinding;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import static android.Manifest.permission.CAMERA;


public class ScanQRActivity extends AppCompatActivity implements ScanQRContract.ScanQRView, ZXingScannerView.ResultHandler
{
    private ScanQRPresenter scanQRPresenter;

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView mScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.scan_qr_activity);
        ScanQrActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.scan_qr_activity);
        scanQRPresenter = new ScanQRPresenter(this);
        binding.setScanQRPresenter(scanQRPresenter);


    }

    @Override
    public void onResume() {
        super.onResume();

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.M) {
            if (checkPermission()) {
                if(mScannerView == null) {
                    mScannerView = new ZXingScannerView(this);
                    setContentView(mScannerView);
                }
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            } else {
                requestPermission();
            }
        }
    }


    private boolean checkPermission() {
        return ( ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA ) == PackageManager.PERMISSION_GRANTED);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    @Override
    public void showErrorMessage(final String message) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Error");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        if (message.contains("Token Invalid or Expired")) {
                            Intent intent = new Intent(ScanQRActivity.this, LoginActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                            startActivity(intent);
                        }
                    }
                });
        dlgAlert.create().show();
    }

    @Override
    public void showSuccess(String message)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //CreateAccountActivity.this.finish();
                        //finish();

                        Intent intent = new Intent(ScanQRActivity.this, HomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        startActivity(intent);
                    }
                });

        dlgAlert.create().show();
    }

    /*
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(ScanQRActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {

                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                    requestPermissions(new String[]{CAMERA},
                                                            REQUEST_CAMERA);
                                                }
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }
    */

    @Override
    public void handleResult(Result rawResult) {

        final String result = rawResult.getText();
        //Log.d("QRCodeScanner", rawResult.getText());
        //Log.d("QRCodeScanner", rawResult.getBarcodeFormat().toString());

        //remove all the views, it will make the Activity have no View
        mScannerView.removeAllViews();
        //<- then stop the camera
        mScannerView.stopCamera();

        //send the results to presenter for processing
        scanQRPresenter.getResult(result);

    }


    @Override
    public Activity getActivity() {
        return this;
    }
}
