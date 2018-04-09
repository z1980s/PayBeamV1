package info.paybeam.www.paybeamv1.PayBeam.ConnectionModule;

/**
 * Created by Dai Wei on 3/4/2018.
 */

//import android.os.AsyncTask;

import android.app.Activity;
import android.content.Context;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
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

public class ServerConnection implements Callable {//extends AsyncTask<Void, Void, Void>{
    private JsonObject msg;
    private String response;

    private Context context;

    //empty
    public ServerConnection () { }

    public ServerConnection (JsonObject msg, Context context) {
        this.msg = msg;
        this.context = context;
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

    public String sendMessage(JsonObject msg, Context context) throws InterruptedException, ExecutionException {
        // creates thread pool with one thread
        final ExecutorService es = Executors.newSingleThreadExecutor();
        // callable thread starts to execute
        final Future<String> responseFuture = es.submit(new ServerConnection(msg, context));
        // gets value of callable thread
        final String response = responseFuture.get();
        return response;
    }

    @Override
    public String call() {
        try {
            System.out.println("ServerConnection Thread Started") ;
            //get application context and open the keystore file
            InputStream stream = context.getAssets().open("www_paybeam_info.bks");
            KeyStore trustStore;
            trustStore = KeyStore.getInstance("BKS");
            trustStore.load(stream, "Se4wyhv8@".toCharArray());

            KeyManagerFactory kmfactory = KeyManagerFactory.getInstance(
                    KeyManagerFactory.getDefaultAlgorithm());
            kmfactory.init(trustStore, "Se4wyhv8@".toCharArray());
            KeyManager[] keymanagers =  kmfactory.getKeyManagers();

            TrustManagerFactory tmf=TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init(trustStore);
            SSLContext sslContext=SSLContext.getInstance("TLSv1.2");
            sslContext.init(keymanagers, tmf.getTrustManagers(), new SecureRandom());
            SSLSocketFactory factory=sslContext.getSocketFactory();

            //create socket to server
            //SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            SSLSocket clientSSLSocket = (SSLSocket) factory.createSocket("182.55.236.211", 3333);
            clientSSLSocket.startHandshake();

            //define outputstream
            OutputStreamWriter osw = new OutputStreamWriter(clientSSLSocket.getOutputStream(), StandardCharsets.UTF_8);
            InputStreamReader isr = new InputStreamReader(clientSSLSocket.getInputStream(), StandardCharsets.UTF_8);

            //send Message
            osw.write(msg.toString() + "\n");
            osw.flush();
            //wait for reply

            BufferedReader br = new BufferedReader(isr);
            response = br.readLine();
            System.out.println("response: " + response);
            clientSSLSocket.close();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
