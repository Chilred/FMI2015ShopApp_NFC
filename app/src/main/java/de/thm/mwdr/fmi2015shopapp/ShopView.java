package de.thm.mwdr.fmi2015shopapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

public class ShopView extends Activity implements CardClickedReceiver {
    private JSONObject products = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_view);

        SharedPreferences settings = getSharedPreferences(Config.SHARED_PREFS_FILE, 0);
        String currentUUID = settings.getString(Config.SHARED_PREFS_UUID, "");

        settings = getSharedPreferences(Config.SHARED_PREFS_FILE, 0);
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

        try {
            if (jsonObject != null) {
                products = jsonObject.getJSONObject(currentUUID).getJSONObject("products");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView.Adapter mAdapter = new CardAdapter(products, this);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void itemClicked(String uuid){
        JSONObject chosenProduct;
        String productString = null;
        try {
            chosenProduct = products.getJSONObject(uuid);
            productString = chosenProduct.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(ShopView.this,ProductView.class);
        SharedPreferences sharedpreferences = getSharedPreferences(Config.SHARED_PREFS_FILE, 0);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(Config.SHARED_PREFS_PRODUCT_STRING, productString);
        editor.apply();
        startActivity(intent);
    }

}
