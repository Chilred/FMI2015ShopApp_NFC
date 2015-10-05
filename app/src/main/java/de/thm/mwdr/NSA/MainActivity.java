package de.thm.mwdr.NSA;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "MainActivity";
    private NfcAdapter mNfcAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (mNfcAdapter == null) {
            Toast.makeText(this, R.string.NFC_not_supported, Toast.LENGTH_LONG).show();
            Log.d(TAG,"NFC not supported on device.");
            finish();
        }
    }

    public void onClick(View v){
        switch (v.getId()){
            case R.id.btnScanNFC:
                if (!mNfcAdapter.isEnabled()) {
                    // NFC can't be enabled programmatically (at least not easily: http://stackoverflow.com/questions/6509316/how-can-i-enable-nfc-reader-via-api)
                    Toast.makeText(getApplicationContext(), R.string.NFC_activated, Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(this,ScanNFC.class);
                    startActivity(intent);
                }
                break;

            case R.id.help:
                new MaterialDialog.Builder(this)
                        .title(R.string.popup_help_title)
                        .content(R.string.popup_help_content)
                        .positiveText(R.string.popup_help_OK)
                        .show();
                break;
        }
    }
}