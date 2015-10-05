package de.thm.mwdr.NSA;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ScanNFC extends Activity {
    private static final String TAG = "ScanNFC";

    private NfcAdapter mNfcAdapter;
    private PendingIntent pendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_beacon);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mNfcAdapter.enableForegroundDispatch(this, pendingIntent, null, null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mNfcAdapter.disableForegroundDispatch(this);
    }

    public void onNewIntent(Intent intent) {
        //Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        Log.d(TAG, "NFC-Tag scanned");

        Parcelable[] rawMessages = intent.getParcelableArrayExtra(
                NfcAdapter.EXTRA_NDEF_MESSAGES);
        NdefMessage message = (NdefMessage) rawMessages[0];
        try {
            Log.d("msg", new String(message.getRecords()[0].getPayload(),"UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        byte[] payload = message.getRecords()[0].getPayload();
        /*
        payload[0] contains the "Status Byte Encodings" field, per the NFC Forum "Text Record Type Definition" section 3.2.1.
        bit7 is the Text Encoding Field.
        if (Bit_7 == 0): The text is encoded in UTF-8
        if (Bit_7 == 1): The text is encoded in UTF16
        Bit_6 is reserved for future use and must be set to zero.
        Bits 5 to 0 are the length of the IANA language code.
        */

        // get text encoding
        String textEncoding;
        if ((payload[0] & 0200) == 0) {
            textEncoding = "UTF-8";
        } else {
            textEncoding = "UTF-16";
        }

        // get language code
        int languageCodeLength = payload[0] & 0077;
        String languageCode = null;
        try {
            languageCode = new String(payload, 1, languageCodeLength, "US-ASCII");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // get text
        String text = null;
        try {
            text = new String(payload, languageCodeLength + 1, payload.length - languageCodeLength - 1, textEncoding);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.d(TAG,"text: " + text);
        Log.d(TAG, "languageCode: " + languageCode);

        if (text != null) {
            // build parameter string
            String charset = "UTF-8";
            String s = "";
            try {
                s += "uuidNFC=" + URLEncoder.encode(text, charset);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            // connect to server with get parameters
            new RetrieveJsonTask().execute(s);
        }
    }

    private class RetrieveJsonTask extends AsyncTask<String, Void, JSONObject> {
        /** The system calls this to perform work in a worker thread and
         * delivers it the parameters given to AsyncTask.execute() */
        protected JSONObject doInBackground(String... urlParameters) {
            Log.d(TAG,"+++++++++++++++++ doInBackground +++++++++++++++++");
            JSONObject retVal = null;
            try {
                // set up URL
                URL url = new URL(Config.BASE_SERVER_URL + "?" + urlParameters[0]);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();
                //TODO: what if connection fails?!

                // read content from URL
                BufferedReader reader = null;
                try {
                    reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String line;
                    if ( (line = reader.readLine()) != null) {
                        // create JSONObject from URL content (JSON-String)
                        retVal = new JSONObject(line);
                    }
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return retVal;
        }

        /** The system calls this to perform work in the UI thread and delivers
         * the result from doInBackground() */
        protected void onPostExecute(JSONObject jsonObject) {
            Log.d(TAG, "+++++++++++++++++ onPostExecute +++++++++++++++++");
            //System.out.println(jsonObject);
            //TODO: jsonObject can be null

            // save JSON-String to shared preferences
            SharedPreferences settings = getSharedPreferences(Config.SHARED_PREFS_FILE, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(Config.SHARED_PREFS_SHOP_JSON, jsonObject.toString());
            editor.apply();

            // start next activity
            Intent newIntent = new Intent(ScanNFC.this, ChooseShop.class);
            startActivity(newIntent);
        }
    }
}
