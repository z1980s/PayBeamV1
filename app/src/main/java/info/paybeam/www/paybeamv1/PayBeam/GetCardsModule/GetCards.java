package info.paybeam.www.paybeamv1.PayBeam.GetCardsModule;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.CardManagementActivity.CardActivity.CardActivity;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.WalletActivity.TopUpWalletActivity.TopUpWalletActivity;
import info.paybeam.www.paybeamv1.R;

/**
 * Created by dflychew on 24/4/18.
 */

public class GetCards extends AppCompatActivity {

//Get the cards and pass return

    public ArrayList<JsonObject> getCards()
    {
        return InternalStorage.readCardsFromFile(this,"cards");
    }



}
