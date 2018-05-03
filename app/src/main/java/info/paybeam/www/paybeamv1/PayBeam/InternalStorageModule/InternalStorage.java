package info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule;

import android.app.Activity;
import android.content.Context;
import android.util.JsonReader;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonElement;
import com.google.gson.internal.JsonReaderInternalAccess;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.ListAdapter.Cards;

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

    public static void writeCredentials(Context context, String filename, String username, String password) {
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);
            //write username,password to file
            String str = username + "," + password;
            outputStream.write(str.getBytes());
            outputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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


    //Write a string array to a file
    public static void writeString(Context context, String filename, String str)
    {
        FileOutputStream outputStream;

        try {
            //MODE_PRIVATE FOR WRITE
            //MODE_APPEND FOR APPEND
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);

            outputStream.write(str.getBytes());

            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int countEntries(Context context, String filename){
        int count = 0 ;
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String line;
            while (( bufferedReader.readLine()) != null) {
                //sb.append(line);
                //Toast.makeText(context,line,Toast.LENGTH_SHORT).show();
                count++;
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return count;

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
    public static String readString (Context context, String filename)
    {
        String line = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            line = bufferedReader.readLine();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return line;
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
        /*
        File dir = context.getFilesDir();
        File file = new File(dir, filename);
        boolean deleted = file.delete();
        */
    }

/*
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
    */
public static void writeCardToFile(Context context, String filename, String cardNum, String expiryDate, String cardType, boolean primary)
{

    //Create JSON object containing the card details
    JsonObject obj = new JsonObject();
    obj.addProperty("cardNum",cardNum);
    obj.addProperty("expiryDate",expiryDate);
    obj.addProperty("cardType",cardType);
    obj.addProperty("primary",primary);


    FileOutputStream outputStream;

    try {
        //MODE_PRIVATE FOR WRITE
        //MODE_APPEND FOR APPEND
        outputStream = context.openFileOutput(filename, context.MODE_APPEND);
        outputStream.write(obj.toString().getBytes());
        outputStream.write("\n".toString().getBytes());
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    public static void writeCardListToFile(Context context, String filename, ArrayList<JsonObject> cardList)
    {


        FileOutputStream outputStream;

        try {
            //MODE_PRIVATE FOR WRITE
            //MODE_APPEND FOR APPEND
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);

            if (cardList.size() != 0) {
                for (JsonObject obj : cardList) {
                    outputStream.write(obj.toString().getBytes());
                    outputStream.write("\n".toString().getBytes());
                }
            } else {
                outputStream.write("".getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //name, username, email, address, phoneNo
    public static void writeProfileToFile(Context context, String filename, String name, String username,
                                          String email, String address, String phoneNo)
    {
        //Create JSON object containing the profile details
        JsonObject obj = new JsonObject();
        obj.addProperty("name",name);
        obj.addProperty("username",username);
        obj.addProperty("email",email);
        obj.addProperty("address",address);
        obj.addProperty("phoneNo",phoneNo);

        FileOutputStream outputStream;

        try {
            //MODE_PRIVATE FOR WRITE
            //MODE_APPEND FOR APPEND
            //Want to overwrite the existing details and input with the new ones
            outputStream = context.openFileOutput(filename, context.MODE_PRIVATE);

            outputStream.write(obj.toString().getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static JsonObject readProfileFromFile(Context context, String filename)
    {

        String line = null;
        JsonObject obj = null;
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            line = bufferedReader.readLine();
            JsonParser jParser = new JsonParser();
            obj = (JsonObject) jParser.parse(line);
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        return obj;
    }

    public static ArrayList<JsonObject> readCardsFromFile(Context context, String filename) {

        ArrayList<JsonObject> cards = new ArrayList<>();
        try {
            FileInputStream fis = context.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            //StringBuilder sb = new StringBuilder();
            String line;
            JsonObject obj;

            while ((line = bufferedReader.readLine()) != null) {
                //sb.append(line);
                //Toast.makeText(context,line,Toast.LENGTH_SHORT).show();
                //line = bufferedReader.readLine();
                JsonParser jParser = new JsonParser();
                obj = (JsonObject) jParser.parse(line);
                cards.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cards;
    }

    public static void deleteCard(Context context, String filename, Cards card){

        //create array to store cards from internal storage
        ArrayList<JsonObject> cardList;

        //Get cards and put into the array
        cardList = readCardsFromFile(context,filename);

        int index = -1;
        //find the index of the entry from the array
        for(int i =0; i < cardList.size();i++)
        {
            if(cardList.get(i).get("cardNum").getAsString().equals(card.getCardNum()))
            {
                index = i;
            }
        }

        //remove the card from array using index
        cardList.remove(index);

        writeCardListToFile(context,filename,cardList);

    }



    //set new default card
    public static void setNewDefaultCard(Context context, String filename, Cards card)
    {
        //create array to store cards from internal storage
        ArrayList<JsonObject> cardList;

        //Get cards and put into the array
        cardList = readCardsFromFile(context,filename);


        for(int i =0; i < cardList.size();i++)
        {
            if(cardList.get(i).get("primary").getAsBoolean()==true)
            {
                cardList.get(i).addProperty("primary",false);
            }
            else if(cardList.get(i).get("cardNum").getAsString().equals(card.getCardNum()))
            {

                cardList.get(i).addProperty("primary",true);
            }
        }

        writeCardListToFile(context,"cards" ,cardList);

    }

    public static JsonObject getDefaultCard(Context context, String filename)
    {
        //create array to store cards from internal storage
        ArrayList<JsonObject> cardList;

        //Get cards and put into the array
        cardList = readCardsFromFile(context,filename);

        //Jsonobject to store the old default card
        JsonObject defaultCard = null;

        for(int i =0; i < cardList.size();i++)
        {
            if(cardList.get(i).get("primary").getAsBoolean()==true)
            {
                defaultCard = cardList.get(i);
            }

        }

        return defaultCard;


    }

    public static JsonObject getCard(Context context, String filename, Cards card)
    {
        //create array to store cards from internal storage
        ArrayList<JsonObject> cardList;

        //Get cards and put into the array
        cardList = readCardsFromFile(context,filename);

        //Jsonobject to store the old default card
        JsonObject obj = null;

        for(int i =0; i < cardList.size();i++)
        {
            if(cardList.get(i).get("cardNum").getAsString().equals(card.getCardNum()))
            {
                obj = cardList.get(i);
            }

        }

        return obj;
    }


}
