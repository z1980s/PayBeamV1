package info.paybeam.www.paybeamv1.PayBeam.PaymentPhoneActivity;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.nio.charset.Charset;
import java.util.ArrayList;

import info.paybeam.www.paybeamv1.PayBeam.Filter.DecimalInputFilter;
import info.paybeam.www.paybeamv1.PayBeam.HomeActivity.HomeActivity;
import info.paybeam.www.paybeamv1.PayBeam.InternalStorageModule.InternalStorage;
import info.paybeam.www.paybeamv1.PayBeam.SecurityModule.MD5;
import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.PaymentPhoneActivityBinding;

public class PaymentPhoneActivity extends AppCompatActivity implements PaymentPhoneContract.PaymentPhoneView,
        NfcAdapter.OnNdefPushCompleteCallback,
        NfcAdapter.CreateNdefMessageCallback
{
    private PaymentPhonePresenter ppPresenter;
    private EditText amountText;
    ProgressDialog progressDialog;

    private PaymentPhonePresenter paymentPhonePresenter;
    //The array lists to hold our messages
    private ArrayList<String> messagesToSendArray = new ArrayList<>();
    private ArrayList<String> messagesReceivedArray = new ArrayList<>();

    //Text boxes to add and display our messages
    private EditText txtBoxAddMessage;
    private TextView txtReceivedMessages;
    private TextView txtMessagesToSend;

    private NfcAdapter mNfcAdapter;
    private PendingIntent nfcPendingIntent;

    @Override
    public void addMessage()
    {
        String token = InternalStorage.readToken(this,"Token");
        String[] credentials = InternalStorage.readString(this,"Credentials").split(",");
        String user = credentials[0];

        JsonObject paymentData = new JsonObject();
        paymentData.addProperty("SenderLoginName", user);
        paymentData.addProperty("Amount", amountText.getText().toString());
        String hash = new MD5().getHash(token + amountText.getText().toString());
        paymentData.addProperty("hash", hash);

        //String newMessage = amountText.getText().toString();
        //messagesToSendArray.add(newMessage);
        messagesToSendArray.add(paymentData.toString());

        //txtBoxAddMessage.setText(null);
        //updateTextViews();

        //Toast.makeText(this, "Added Message", Toast.LENGTH_LONG).show();
        progressDialog.setMessage("Please hold phones close together ... ");
        progressDialog.show();
    }

    @Override
    public void onNdefPushComplete(NfcEvent event)
    {
        messagesToSendArray.clear();
        //This is called when the system detects that our NdefMessage was
        //Successfully sent
        progressDialog.dismiss();

        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |  Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);

        //showDialog();
    }

    public void showDialog()
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage("Information transmitted successfully!");
        dlgAlert.setTitle("Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //CreateAccountActivity.this.finish();
                        finish();
                    }
                });

        dlgAlert.create().show();
    }

    public NdefRecord[] createRecords()
    {
        NdefRecord[] records = new NdefRecord[messagesToSendArray.size() + 1];
        //To Create Messages Manually if API is less than
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
        {
            for (int i = 0; i < messagesToSendArray.size(); i++)
            {
                byte[] payload = messagesToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));
                NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,      //Our 3-bit Type name format
                        NdefRecord.RTD_TEXT,            //Description of our payload
                        new byte[0],                    //The optional id for our Record
                        payload);                       //Our payload for the Record

                records[i] = record;
            }
        }
        //Api is high enough that we can use createMime, which is preferred.
        else
        {
            for (int i = 0; i < messagesToSendArray.size(); i++)
            {
                byte[] payload = messagesToSendArray.get(i).
                        getBytes(Charset.forName("UTF-8"));

                NdefRecord record = NdefRecord.createMime("text/plain", payload);
                records[i] = record;
            }
        }
        records[messagesToSendArray.size()] = NdefRecord.createApplicationRecord(getPackageName());
        return records;
    }


    @Override
    public NdefMessage createNdefMessage(NfcEvent event)
    {
        //This will be called when another NFC capable device is detected.
        if (messagesToSendArray.size() == 0)
        {
            return null;
        }
        //We'll write the createRecords() method in just a moment
        NdefRecord[] recordsToAttach = createRecords();
        //When creating an NdefMessage we need to provide an NdefRecord[]
        return new NdefMessage(recordsToAttach);
    }

    private void handleNfcIntent(Intent NfcIntent)
    {
        String message = new String();

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction()))
        {
            Parcelable[] receivedArray = NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if (receivedArray != null)
            {
                messagesReceivedArray.clear();
                NdefMessage receivedMessage = (NdefMessage) receivedArray[0];
                NdefRecord[] attachedRecords = receivedMessage.getRecords();

                for (NdefRecord record : attachedRecords)
                {
                    String string = new String(record.getPayload());
                    //Make sure we don't pass along our AAR (Android Applicatoin Record)
                    if (string.equals(getPackageName()))
                    {
                        continue;
                    }

                    //messagesReceivedArray.add(string);

                    message = string;

                }
                //Toast.makeText(this, "Received " + messagesReceivedArray.size() + " Messages", Toast.LENGTH_LONG).show();
                //updateTextViews();

                /*
                AlertDialog.Builder dlgAlert = new AlertDialog.Builder(this);

                dlgAlert.setMessage("Received: $" + message);
                dlgAlert.setTitle("Message");
                dlgAlert.setPositiveButton("OK", null);
                dlgAlert.setCancelable(true);

                dlgAlert.setPositiveButton("Ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                dlgAlert.create().show();
    */

                ppPresenter.handleIncomingMessage(message);

            }
            else
            {
                //Toast.makeText(this, "Received Blank Parcel", Toast.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onNewIntent(Intent intent)
    {
        handleNfcIntent(intent);
    }


    @Override
    public void onResume()
    {
        super.onResume();
        //updateTextViews();
        handleNfcIntent(getIntent());
    }


    private void updateTextViews()
    {
        txtMessagesToSend.setText("Messages To Send:\n");
        //Populate Our list of messages we want to send
        if (messagesToSendArray.size() > 0)
        {
            for (int i = 0; i < messagesToSendArray.size(); i++)
            {
                txtMessagesToSend.append(messagesToSendArray.get(i));
                txtMessagesToSend.append("\n");
            }
        }

        txtReceivedMessages.setText("Messages Received:\n");
        //Populate our list of messages we have received
        if (messagesReceivedArray.size() > 0)
        {
            for (int i = 0; i < messagesReceivedArray.size(); i++)
            {
                txtReceivedMessages.append(messagesReceivedArray.get(i));
                txtReceivedMessages.append("\n");
            }
        }
    }

    //Save our Array Lists of Messages for if the user navigates away
    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("messagesToSend", messagesToSendArray);
        savedInstanceState.putStringArrayList("lastMessagesReceived", messagesReceivedArray);
    }

    //Load our Array Lists of Messages for when the user navigates back
    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        messagesToSendArray = savedInstanceState.getStringArrayList("messagesToSend");
        messagesReceivedArray = savedInstanceState.getStringArrayList("lastMessagesReceived");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.payment_phone_activity);
        PaymentPhoneActivityBinding binding = DataBindingUtil.setContentView(this, R.layout.payment_phone_activity);
        ppPresenter = new PaymentPhonePresenter(this);
        binding.setPpPresenter(ppPresenter);

        //Check if NFC is available on device
        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        nfcPendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, this.getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

        if (mNfcAdapter != null && mNfcAdapter.isEnabled())
        {
            //This will refer back to createNdefMessage for what it will send
            mNfcAdapter.setNdefPushMessageCallback(this, this);

            //This will be called if the message is sent successfully
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);
        }
        else
        {
            //Toast.makeText(this, "NFC not available on this device", Toast.LENGTH_SHORT).show();
            AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

            dlgAlert.setMessage("NFC Disabled! Please enable NFC and try again.");
            dlgAlert.setTitle("NFC Error");
            dlgAlert.setPositiveButton("OK", null);
            dlgAlert.setCancelable(true);

            dlgAlert.setPositiveButton("Ok",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            //CreateAccountActivity.this.finish();
                            finish();
                        }
                    });

            dlgAlert.create().show();
        }

        amountText = findViewById(R.id.amountText);
        amountText.setInputType((InputType.TYPE_CLASS_NUMBER + InputType.TYPE_NUMBER_FLAG_DECIMAL));
        //amountText.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(3,2)});
        amountText.setFilters(new InputFilter[] {new DecimalInputFilter()});

        progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage("Loading...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        progressDialog.setCancelable(false);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                progressDialog.dismiss();
            }
        });
        //progressDialog.show();
        /*
        txtBoxAddMessage = (EditText) findViewById(R.id.txtBoxAddMessage);
        txtMessagesToSend = (TextView) findViewById(R.id.txtMessageToSend);
        txtReceivedMessages = (TextView) findViewById(R.id.txtMessagesReceived);
        Button btnAddMessage = (Button) findViewById(R.id.buttonAddMessage);

        btnAddMessage.setText("Add Message");
        updateTextViews();
        */
        if (getIntent().getAction().equals(NfcAdapter.ACTION_NDEF_DISCOVERED))
        {
            handleNfcIntent(getIntent());
            //Toast.makeText(this, "Handling intent", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void handleIncomingMessage()
    {

    }

    @Override
    public void showErrorMessage(String message) {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        finish();
                    }
                });

        dlgAlert.create().show();
    }

    @Override
    public void checkAmount() {
        amountText.getText().toString();
        boolean validInput = amountText.getText().toString().matches("-?\\d+(\\.\\d+)?");
        if(validInput && Double.parseDouble(amountText.getText().toString())>0)
        {
            addMessage();
        }
        else
        {
            showDialog("Please enter a valid amount");
        }
    }

    public void showDialog(String text) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(text);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    @Override
    public void showSuccess(String message)
    {
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);

        dlgAlert.setMessage(message);
        dlgAlert.setTitle("Message");
        dlgAlert.setPositiveButton("OK", null);
        dlgAlert.setCancelable(true);

        dlgAlert.setPositiveButton("Ok",
                new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //CreateAccountActivity.this.finish();
                        finish();
                    }
                });

        dlgAlert.create().show();
    }

    @Override
    public Activity getActivity() {
        return this;
    }
}
