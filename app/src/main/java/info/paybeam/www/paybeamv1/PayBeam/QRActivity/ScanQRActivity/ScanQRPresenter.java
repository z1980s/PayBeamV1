package info.paybeam.www.paybeamv1.PayBeam.QRActivity.ScanQRActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;

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

                    String credentials = InternalStorage.readString(scanQRView.getActivity(), "Credentials");
                    String[] userData = credentials.split(",");
                    String username = userData[0];

                    JsonObject msg = new JsonObject();
                    msg.addProperty("Header", "DecodeQR");
                    msg.addProperty("Payee", username);
                    JsonParser jParser = new JsonParser();
                    //username + DESPP(Json(username + Amount))
                    JsonObject data = (JsonObject) jParser.parse(result);
                    msg.add("Data", data);
                    //Header + Payee + username + encrypted

                    @SuppressLint("StaticFieldLeak")
                    ServerConnection sc = new ServerConnection(msg, scanQRView.getActivity()) {
                        @Override
                        public void receiveResponse(String response) {
                            try {
                                JsonParser jParser = new JsonParser();
                                JsonObject jResponse = (JsonObject) jParser.parse(response);
                                if (jResponse.get("result").getAsString().equals("Success")) {
                                    System.out.println("Success");
                                    //do success
                                    scanQRView.showSuccess(jResponse.get("reason").getAsString());
                                } else {
                                    //do failure
                                    System.out.println("Failed");
                                    scanQRView.showErrorMessage(jResponse.get("reason").getAsString());
                                }
                            } catch (com.google.gson.JsonSyntaxException jse){
                                System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                                scanQRView.showErrorMessage(response);
                            }
                        }
                    };

                    sc.execute(null,null,null);

                    //System.out.println("data: " + msg.toString());
                    //converting the data to json
                    //JSONObject obj = new JSONObject(result);
                    //setting values to textviews
                    //textViewName.setText(obj.getString("name"));
                    //textViewAddress.setText(obj.getString("address"));

                    //Toast.makeText(scanQRView.getActivity(), obj.getString("name"), Toast.LENGTH_SHORT).show();
                    //Toast.makeText(scanQRView.getActivity(), obj.getString("amount"), Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                    //if control comes here
                    //that means the encoded format not matches
                    //in this case you can display whatever data is available on the qrcode
                    //to a toast
                    //Toast.makeText(scanQRView.getActivity(), result, Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
