package de.thm.mwdr.fmi2015shopapp;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.EstimoteSDK;
import com.estimote.sdk.Nearable;
import com.estimote.sdk.Region;

import java.util.List;

public class ScanBeacon extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_beacon);
        //Radar Animation
        ImageView clock = (ImageView) findViewById(R.id.clock);
        Animation clockTurn = AnimationUtils.loadAnimation(this, R.anim.clock_turn);
        clock.startAnimation(clockTurn);


        NfcAdapter nfcAdapter = NfcAdapter.getDefaultAdapter(this);
        if (nfcAdapter != null && nfcAdapter.isEnabled()) {
            Toast.makeText(this, "NFC is online", Toast.LENGTH_LONG).show();
        }
        else{
            Toast.makeText(this, "NFC is offline", Toast.LENGTH_LONG).show();
        }
        // Intent intent = new Intent(ScanBeacon.this, ChooseShop.class);
       // startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scan_beacon, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    protected void onDestroy(){
        super.onDestroy();
    }
}
