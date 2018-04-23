package info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity;

import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ScanQRPresenter implements ScanQRContract.ScanQRPresenter
{
    private ScanQRContract.ScanQRView scanQRView;

    ScanQRPresenter(ScanQRContract.ScanQRView view)
    {
        scanQRView = view;
    }


    @Override
    public void getResult(String result) {
        if (result != null) {
            //if qrcode has nothing in it
            if (result == null) {
                Toast.makeText(scanQRView.getActivity(), "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {
                    //converting the data to json
                    JSONObject obj = new JSONObject(result);
                    //setting values to textviews
                    //textViewName.setText(obj.getString("name"));
                    //textViewAddress.setText(obj.getString("address"));
                    Toast.makeText(scanQRView.getActivity(), obj.getString("name"), Toast.LENGTH_SHORT).show();
                    Toast.makeText(scanQRView.getActivity(), obj.getString("amount"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    Toast.makeText(scanQRView.getActivity(), result, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
