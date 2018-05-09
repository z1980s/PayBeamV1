package info.paybeam.www.paybeamv1.PayBeam.QRActivity.GenerateQRActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import info.paybeam.www.paybeamv1.PayBeam.ConnectionModule.ServerConnection;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.SecurityModule.DESPassPhrase;
import info.paybeam.www.paybeamv1.R;

public class GenerateQRPresenter implements GenerateQRContract.GenerateQRPresenter
{
    private GenerateQRContract.GenerateQRView generateQRView;

    GenerateQRPresenter(GenerateQRContract.GenerateQRView view)
    {
        generateQRView = view;
    }

    @Override
    public void generateQRCodeButtonClick(View view) {
        InputMethodManager imm = (InputMethodManager) generateQRView.getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (null != generateQRView.getActivity().getCurrentFocus())
            imm.hideSoftInputFromWindow(generateQRView.getActivity().getCurrentFocus()
                    .getApplicationWindowToken(), 0);
        String amount = generateQRView.getAmount();
        if(!(amount==null))
        {
            generateQRimage(amount);
        }
        else
        {
            generateQRView.showDialog("Please enter a valid amount!");
        }


    }

    @Override
    public void generateQRimage(final String amount) {
        //Concatenate the amount with the account ID
        //JSON format
        //For the QR Code

        //String text="{\"name\":\"Dave\",\"amount\":\""+ amount +"\"}";

        String data = InternalStorage.readString(generateQRView.getActivity(), "Credentials");
        String[] credentials = data.split(",");
        final String username = credentials[0];
        final String password = credentials[1];

        //retrieve hash
        JsonObject msg = new JsonObject();
        msg.addProperty("Header", "GetPasswordHash");
        JsonObject userData = new JsonObject();
        userData.addProperty("LoginName", username);
        userData.addProperty("Password", password);
        msg.add("User", userData);

        @SuppressLint("StaticFieldLeak")
        ServerConnection sc = new ServerConnection(msg, generateQRView.getActivity()) {
            @Override
            public void receiveResponse(String response) {
                String hash = null;
                try {
                    JsonParser jParser = new JsonParser();
                    JsonObject jResponse = (JsonObject) jParser.parse(response);
                    if (jResponse.get("result").getAsString().equals("Success")) {
                        //do success
                        hash = jResponse.get("hash").getAsString();
                        //generateQRView.showHomeView();
                    } else {
                        //do failure
                        //generateQRView.showErrorMessage(jResponse.get("reason").getAsString());
                    }

                    if (hash != null) {
                        JsonObject qrText = new JsonObject();

                        // username + DESPP(Json(username + Amount))
                        qrText.addProperty("LoginName", username);
                        //String qrText = username;
                        JsonObject desTextObj = new JsonObject();
                        desTextObj.addProperty("LoginName", username);
                        desTextObj.addProperty("Amount", amount);

                        try {
                            String encrypted = new DESPassPhrase(hash).encrypt(desTextObj.toString()    );
                            qrText.addProperty("encrypted", encrypted);
                            System.out.println(qrText.toString());
                            //qrText += "," + encrypted;
                            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                            try {
                                //Generate QR code with the string text
                                BitMatrix bitMatrix = multiFormatWriter.encode(qrText.toString(), BarcodeFormat.QR_CODE, 250, 250);
                                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                                //Send qr code and amount to activity for display
                                generateQRView.displayQRImage(bitmap, amount);
                            } catch (WriterException e) {
                                e.printStackTrace();
                            }
                        } catch (Exception e) {
                            System.out.println("Unknown error");
                            //generateQRView.showErrorMessage(response);
                        }
                    } else {
                        System.out.println("NO HASH DETECTED");
                    }
                } catch (com.google.gson.JsonSyntaxException jse) {
                    System.err.println("[ERROR] Malformed Json Received! Server is most likely offline.");
                    //generateQRView.showErrorMessage(response);
                }
            }
        };

        sc.execute(null,null,null);
    }
}
