package info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by dflychew on 9/4/18.
 */

public class InternalStorage {
    //Write a string to a file
    public static void writeToken(Context context, String filename, String token)
    {
        FileOutputStream outputStream;
        try {
            //MODE_PRIVATE FOR WRITE
            //MODE_APPEND FOR APPEND
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);
            outputStream.write(token.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readToken (Context context, String filename)
    {
        //
        String token = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            token = bufferedReader.readLine();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return token;
    }

    //Write a string array to a file
    public static void write(Context context, String filename, ArrayList<String>stringArray)
    {
        FileOutputStream outputStream;

        try {
            //MODE_PRIVATE FOR WRITE
            //MODE_APPEND FOR APPEND
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);

            //for loop through the ArrayList
            for(String str: stringArray) {
                outputStream.write(str.getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //Append a string array to a file
    public static void append(Context context, String filename, ArrayList<String>stringArray)
    {
        FileOutputStream outputStream;

        try {
            //MODE_PRIVATE FOR WRITE
            //MODE_APPEND FOR APPEND
            outputStream = context.openFileOutput(filename, context.MODE_APPEND);

            //for loop through the ArrayList
            for(String str: stringArray) {
                outputStream.write(str.getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //get String
    public static void readString (Context context, String filename)
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

    //read the file and return in a ArrayList
    public static ArrayList<String> read(Context context, String filename) {

        ArrayList<String> list = new ArrayList<String>();
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //sb.append(line);
                //Toast.makeText(context,line,Toast.LENGTH_SHORT).show();
                list.add(line);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    //delete the file
    public static void delete(Context context, String filename)
    {
        context.deleteFile(filename);
    }


    public static void writeCardToFile(Context context, String filename, String string)
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

    public static ArrayList<String> readCardsFromFile(Context context, String filename) {

        ArrayList<String> cards = new ArrayList<String>();
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                //sb.append(line);
                //Toast.makeText(context,line,Toast.LENGTH_SHORT).show();
                cards.add(line);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return cards;
    }
}
