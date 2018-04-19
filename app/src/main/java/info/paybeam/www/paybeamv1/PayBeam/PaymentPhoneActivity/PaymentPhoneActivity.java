package info.paybeam.www.paybeamv1.PayBeam.PaymentPhoneActivity;

import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcEvent;
import android.nfc.NfcManager;
import android.os.Build;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;

import info.paybeam.www.paybeamv1.R;
import info.paybeam.www.paybeamv1.databinding.PaymentPhoneActivityBinding;

public class PaymentPhoneActivity extends AppCompatActivity implements PaymentPhoneContract.PaymentPhoneView,
                                                                                                                                   NfcAdapter.OnNdefPushCompleteCallback,
                                                                                                                                   NfcAdapter.CreateNdefMessageCallback
{
    private PaymentPhonePresenter ppPresenter;

    private PaymentPhonePresenter paymentPhonePresenter;
    private EditText amountText;
    //The array lists to hold our messages
    private ArrayList<String> messagesToSendArray = new ArrayList<>();
    private ArrayList<String> messagesReceivedArray = new ArrayList<>();

    private NfcAdapter mNfcAdapter;
    private ProgressDialog progressDialog;

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

        NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();

        if (adapter != null && adapter.isEnabled())
        {
            //This will refer back to createNdefMessage for what it will send
            mNfcAdapter.setNdefPushMessageCallback(this, this);

            //This will be called if the message is sent successfully
            mNfcAdapter.setOnNdefPushCompleteCallback(this, this);

            //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);

            //mNfcAdapter.enableReaderMode(this, this, NfcAdapter.FLAG_READER_NFC_A, null);
            //mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
        }
        else
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("NFC diabled! Please enable NFC and try again ...");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.cancel();
                            finish();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

        amountText = findViewById(R.id.amountText);
        amountText.setGravity(Gravity.CENTER);
        //testText = findViewById(R.id.testText);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        //progressDialog.setMessage("Hold phones close together");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                messagesToSendArray.clear();
                messagesReceivedArray.clear();
            }
        });
    }

    @Override
    public void addMessage()
    {
        //mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        NfcManager manager = (NfcManager) this.getSystemService(Context.NFC_SERVICE);
        NfcAdapter adapter = manager.getDefaultAdapter();

        if(adapter != null && adapter.isEnabled())
        {
            messagesToSendArray.clear();
            progressDialog.setMessage("Please hold phones close together");
            progressDialog.show();
            String newMessage = amountText.getText().toString();
            messagesToSendArray.add(newMessage);
            //need to include other components required to secure information
            //call presenter to handle server connection and request transfer

            //amountText.setText(null);
            //testText.setText(newMessage);
            //Toast.makeText(this, "Added Message", Toast.LENGTH_LONG).show();
        }
        else
        {
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage("NFC diabled! Please enable NFC and try again ...");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "OK",
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int id)
                        {
                            dialog.cancel();
                            //showDialog();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }
    }

    @Override
    public void onNdefPushComplete(NfcEvent event)
    {
        progressDialog.dismiss();
        //showSuccess();
        messagesToSendArray.clear();
        //This is called when the system detects that our NdefMessage was
        //Successfully sent
        //Toast.makeText(this, "Message has been sent", Toast.LENGTH_LONG).show();
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

    public NdefRecord[] createRecords()
    {
        NdefRecord[] records = new NdefRecord[messagesToSendArray.size() + 1];
        //To Create Messages Manually if API is less than
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN)
        {
            for (int i = 0; i < messagesToSendArray.size(); i++)
            {
                byte[] payload = messagesToSendArray.get(i).getBytes(Charset.forName("UTF-8"));

                NdefRecord record = new NdefRecord(NdefRecord.TNF_WELL_KNOWN,      //Our 3-bit Type name format
                        NdefRecord.RTD_TEXT,                       //Description of our payload
                        new byte[0],                                          //The optional id for our Record
                        payload);                                                //Our payload for the Record

                records[i] = record;
            }
        }
        //Api is high enough that we can use createMime, which is preferred.
        else
        {
            for (int i = 0; i < messagesToSendArray.size(); i++)
            {
                byte[] payload = messagesToSendArray.get(i).getBytes(Charset.forName("UTF-8"));

                NdefRecord record = NdefRecord.createMime("text/plain",payload);
                records[i] = record;
            }
        }

        records[messagesToSendArray.size()] = NdefRecord.createApplicationRecord(getPackageName());
        return records;
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
        //may not be needed on resume as operation should have been completed
        handleNfcIntent(getIntent());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        adapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        NfcAdapter adapter = NfcAdapter.getDefaultAdapter(this);
        adapter.disableForegroundDispatch(this);
    }

    @Override
    public void handleIncomingMessage()
    {
        /*
        txtMessagesToSend.setText("Messages To Send:\n");
        //Populate Our list of messages we want to send
        if(messagesToSendArray.size() > 0) {
            for (int i = 0; i < messagesToSendArray.size(); i++) {
                txtMessagesToSend.append(messagesToSendArray.get(i));
                txtMessagesToSend.append("\n");
            }
        }
        */

        //the text that we received, need to extract and manipulate
        //txtReceivedMessages.setText("Messages Received:\n");
        //Populate our list of messages we have received
        if (messagesReceivedArray.size() > 0)
        {
            for (int i = 0; i < messagesReceivedArray.size(); i++) {
                //txtReceivedMessages.append(messagesReceivedArray.get(i));
                //txtReceivedMessages.append("\n");

                //testText.setText(messagesReceivedArray.get(i));

                //handle messages received, manipulate
                //make server connection to start transfer of funds between components
            }
            progressDialog.hide();
            //call presenter handleMessage
            paymentPhonePresenter.handleIncomingMessage(messagesReceivedArray);
        }
    }

    private void handleNfcIntent(Intent NfcIntent)
    {
        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(NfcIntent.getAction()))
        {
            Parcelable[] receivedArray = NfcIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

            if(receivedArray != null)
            {
                progressDialog.setMessage("Receiving incoming message");
                progressDialog.show();
                messagesReceivedArray.clear();
                NdefMessage receivedMessage = (NdefMessage) receivedArray[0];
                NdefRecord[] attachedRecords = receivedMessage.getRecords();

                for (NdefRecord record:attachedRecords)
                {
                    String string = new String(record.getPayload());
                    //Make sure we don't pass along our AAR (Android Applicatoin Record)
                    if (string.equals(getPackageName()))
                    {
                        continue;
                    }
                    messagesReceivedArray.add(string);
                }

                paymentPhonePresenter.messageReceived();

                /*
                Toast.makeText(this, "Received " + messagesReceivedArray.size() +
                        " Messages", Toast.LENGTH_LONG).show();
                updateTextViews();
                */
            }
            else
            {
                //Toast.makeText(this, "Received Blank Parcel", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putStringArrayList("messagesToSend", messagesToSendArray);
        savedInstanceState.putStringArrayList("lastMessagesReceived",messagesReceivedArray);
    }

    //Load our Array Lists of Messages for when the user navigates back
    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        messagesToSendArray = savedInstanceState.getStringArrayList("messagesToSend");
        messagesReceivedArray = savedInstanceState.getStringArrayList("lastMessagesReceived");
    }
}
