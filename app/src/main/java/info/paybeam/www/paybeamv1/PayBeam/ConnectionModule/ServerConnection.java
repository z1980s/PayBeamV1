package info.paybeam.www.paybeamv1.PayBeam.ConnectionModule;

/**
 * Created by Dai Wei on 3/4/2018.
 */

//import android.os.AsyncTask;

import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ServerConnection implements Callable {//extends AsyncTask<Void, Void, Void>{
    private JsonObject msg;
    private String response;

    //empty
    public ServerConnection () { }

    public ServerConnection (JsonObject msg) {
        this.msg = msg;
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

    public String sendMessage(JsonObject msg) throws InterruptedException, ExecutionException {
        // creates thread pool with one thread
        final ExecutorService es = Executors.newSingleThreadExecutor();
        // callable thread starts to execute
        final Future<String> responseFuture = es.submit(new ServerConnection(msg));
        // gets value of callable thread
        final String response = responseFuture.get();
        return response;
    }

    @Override
    public String call() {
        try {
            System.out.println("ServerConnection Thread Started") ;
            //create socket to server
            Socket clientSocket = new Socket("182.55.236.211", 3333);

            //define outputstream
            OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream(), StandardCharsets.UTF_8);
            InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8);

            //send Message
            osw.write(msg.toString() + "\n");
            osw.flush();
            //wait for reply

            BufferedReader br = new BufferedReader(isr);
            response = br.readLine();
            System.out.println("response: " + response);
            clientSocket.close();
            return response;
        } catch (UnknownHostException uhe) {
            uhe.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return null;
    }
}
