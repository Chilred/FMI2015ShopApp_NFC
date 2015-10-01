package de.thm.mwdr.NSA;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductView extends Activity {
    private String name, price, productText = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        SharedPreferences settings = getSharedPreferences(Config.SHARED_PREFS_FILE, 0);
        String productString = settings.getString(Config.SHARED_PREFS_PRODUCT_STRING, "");
        try {
            JSONObject product = new JSONObject(productString);
            name = product.getString("name");
            price = product.getString("price");
            productText = product.getString("productText");
            new DownloadImageTask(this).execute(product.getString("image"));
        }catch (JSONException e) {
            e.printStackTrace();
        }
        TextView title = (TextView) findViewById(R.id.productTitel);
        TextView productTextfield = (TextView) findViewById(R.id.productText);
        TextView priceTextfield = (TextView) findViewById(R.id.productPrice);

        title.setText(name);
        productTextfield.setText(productText);
        priceTextfield.setText(price);

    }

    public void setImage(Bitmap image) {
        ImageView imageView = (ImageView) findViewById(R.id.productimage);
        imageView.setImageBitmap(image);
    }
}
