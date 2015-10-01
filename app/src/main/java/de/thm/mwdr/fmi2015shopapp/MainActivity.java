package de.thm.mwdr.fmi2015shopapp;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

public class MainActivity extends Activity implements View.OnClickListener{

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onClick(View v){
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        switch (v.getId()){
            case R.id.btnScanBeacon:
                if (!mBluetoothAdapter.isEnabled()) {
                    mBluetoothAdapter.enable();
                    Toast.makeText(getApplicationContext(), R.string.bluetooth_activated, Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent(this,ScanBeacon.class);
                startActivity(intent);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
            Toast.makeText(getApplicationContext(), R.string.bluetooth_deactivated, Toast.LENGTH_SHORT).show();
        }
    }
}
