package info.paybeam.www.paybeamv1.PayBeam.ConnectionModule;

/**
 * Created by Dai Wei on 3/4/2018.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManagerFactory;

import static android.app.ProgressDialog.STYLE_SPINNER;

public abstract class ServerConnection extends AsyncTask<Void, Void, String> implements ServerConnectionCallback { //implements Callable {
    private JsonObject msg;
    private String response;

    private Context context;
    ProgressDialog pd;

    private static final int DEFAULT_CONNECT_TIMEOUT = 5000;

    public ServerConnection (JsonObject msg, Context context) {
        //msg is the JSONObject containing the message that needs to be sent.
        this.msg = msg;
        //context is used to open file from Assets folder!
        this.context = context;
        this.pd = new ProgressDialog(this.context, STYLE_SPINNER);
        this.pd.setCancelable(false);
    }

    public static JsonObject createMessage(String header, String dataType, String[] memberNames ,String[] data) {
        JsonObject msg = new JsonObject();
        //add the header
        msg.addProperty("Header", header);
        //add all the memberNames:data;
        JsonObject msgData = new JsonObject();
        for(int a = 0; a < memberNames.length; a++) {
            msgData.addProperty(memberNames[a], data[a]);
        }
        msg.add(dataType, msgData);
        System.out.println("Resulting JSON: " + msg.toString());
        return msg;
    }

    public abstract void receiveResponse(String response);

    @Override
    protected void onPreExecute() {
        this.pd.setMessage("Please Wait...");
        this.pd.show();
    }

    @Override
    protected String doInBackground(Void... params) {
        try {
            System.out.println("ServerConnection Thread Started");

            //get application context and open the truststore file
            InputStream stream = context.getAssets().open("www_paybeam_info.bks");
            KeyStore trustStore;
            trustStore = KeyStore.getInstance("BKS");
            trustStore.load(stream, "Se4wyhv8@".toCharArray());

            //Create a Key Manager Factory to load the TrustStore using an instance of key manager
            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            kmfactory.init(trustStore, "Se4wyhv8@".toCharArray());
            KeyManager[] keymanagers = kmfactory.getKeyManagers();

            //Create a custom trust manager to import the trustStore and initialise the trust manager with the trustStore
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);

            //define protocol to be TLSv1.2 (SSL is no longer secure) and initialise SSLContext
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(keymanagers, tmf.getTrustManagers(), new SecureRandom());

            //create SSLSocketfactory and establih SSLSocket to server
            SSLSocketFactory factory = sslContext.getSocketFactory();
            SSLSocket clientSSLSocket = (SSLSocket) factory.createSocket();
            try {
                clientSSLSocket.connect(new InetSocketAddress("www.paybeam.info", 3333), DEFAULT_CONNECT_TIMEOUT);
                clientSSLSocket.startHandshake();

                //define outputstream
                OutputStreamWriter osw = new OutputStreamWriter(clientSSLSocket.getOutputStream(), StandardCharsets.UTF_8);
                InputStreamReader isr = new InputStreamReader(clientSSLSocket.getInputStream(), StandardCharsets.UTF_8);

                //Write msg to outputstreamwriter and send it
                osw.write(msg.toString() + "\n");
                osw.flush();

                //Create a BufferedReader to read from the InputStreamReader and print out the response.
                BufferedReader br = new BufferedReader(isr);

                //read 1st line
                response = br.readLine();
                //read every other line
                String temp;
                while ((temp = br.readLine()) != null) {
                    response += "\n" + temp;
                }

                //Close Socket
                clientSSLSocket.close();
                //return the response to the calling function.
                return response;
            } catch (SocketTimeoutException ste) {
                System.err.println("[ERROR] Connection timed out (5 seconds)");
                return "Server Connection Timed Out";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String response) {
        if (this.pd.isShowing()) {
            this.pd.dismiss();
        }

        if (response != null) {
            receiveResponse(response);
        }
        //Take action based on result
    }
}
