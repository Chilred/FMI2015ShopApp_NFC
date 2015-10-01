package de.thm.mwdr.fmi2015shopapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.RemoteException;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.estimote.sdk.Beacon;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class ScanBeacon extends Activity {
    private static final Region ALL_ESTIMOTE_BEACONS = new Region("regionId", null, null, null);
    private static final String TAG = "ScanBeacon";

    private BeaconManager beaconManager;
    private boolean beaconsFound = false;
    private int beaconsFoundCounter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_beacon);

        //Radar Animation
        ImageView clock = (ImageView) findViewById(R.id.clock);
        Animation clockTurn = AnimationUtils.loadAnimation(this, R.anim.clock_turn);
        clock.startAnimation(clockTurn);

        //Beacon Connection
        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> beacons) {
                // TODO: Add support for multiple Beacons (currently after first is found search is stopped)
                Log.d(TAG, "Ranged beacons: " + beacons);
                if ((!beacons.isEmpty() /*|| true*/) && !beaconsFound) {
                    Log.d(TAG, "beacon(s) found!");
                    beaconsFoundCounter++;
                    if (beaconsFoundCounter > 3) {
                        beaconsFound = true;

                        ArrayList<String> beaconIDs = new ArrayList<>();
                        for (int i = 0; i < beacons.size(); i++) {
                            beaconIDs.add(beacons.get(i).getProximityUUID());
                        }

                        /* // debugging only
                        beaconIDs.add("UUID");
                        beaconIDs.add("b9407f30-f5f8-466e-aff9-25556b57fe6d");
                        */

                        // build parameter string
                        String charset = "UTF-8";
                        String s = "";
                        for (int i = 0; i < beaconIDs.size(); i++) {
                            if (i != 0) {
                                s += "&";
                            }
                            try {
                                s += "uuid" + String.valueOf(i) + "=" + URLEncoder.encode(beaconIDs.get(i), charset);
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }

                        // connect to server with get parameters
                        new RetrieveJsonTask().execute(s);
                    }
                }
            }
        });
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
            Intent newIntent = new Intent(ScanBeacon.this, ChooseShop.class);
            startActivity(newIntent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                try {
                    beaconManager.startRanging(ALL_ESTIMOTE_BEACONS);
                } catch (RemoteException e) {
                    Log.e(TAG, "Cannot start ranging", e);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            beaconManager.stopRanging(ALL_ESTIMOTE_BEACONS);
        } catch (RemoteException e) {
            Log.e(TAG, "Cannot stop but it does not matter now", e);
        }
    }

    protected void onDestroy(){
        super.onDestroy();
        beaconManager.disconnect();
    }
}
