package info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;

/**
 * Created by dflychew on 9/4/18.
 */

public class InternalStorage {

    public static void write(Context context, String filename, String string)
    {
        FileOutputStream outputStream;

        try {
            //MODE_PRIVATE FOR WRITE
            //MODE_APPEND FOR APPEND
            outputStream = context.openFileOutput(filename, context.MODE_APPEND);

            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void read(Context context, String filename)
    {
        //
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //sb.append(line);
                Toast.makeText(context,line,Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}
