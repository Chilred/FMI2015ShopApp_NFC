package de.thm.mwdr.fmi2015shopapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

public class ChooseShop extends Activity implements CardClickedReceiver{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_shop);

        // fetch JSON-String from shared preferences
        SharedPreferences settings = getSharedPreferences(Config.SHARED_PREFS_FILE, 0);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(settings.getString(Config.SHARED_PREFS_SHOP_JSON, ""));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new CardAdapter(jsonObject, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void itemClicked(String uuid){
        Intent intent = new Intent(ChooseShop.this,ShopView.class);
        SharedPreferences sharedpreferences = getSharedPreferences(Config.SHARED_PREFS_FILE, 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Config.SHARED_PREFS_UUID, uuid);
        editor.apply();

        startActivity(intent);

    }

}
