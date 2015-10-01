package de.thm.mwdr.NSA;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by David on 25.09.2015.
 */

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private CardAdapter.ViewHolder viewHolder;
    private CardItem cardItem;

    private ProductView productView;

    private static final String TAG = "DownloadImageTask";

    public DownloadImageTask(CardAdapter.ViewHolder viewHolder, CardItem cardItem) {
        this.viewHolder = viewHolder;
        this.cardItem = cardItem;
    }

    public DownloadImageTask(ProductView productView) {
        this.productView = productView;
    }

    /**
     * The system calls this to perform work in a worker thread and
     * delivers it the parameters given to AsyncTask.execute()
     */
    protected Bitmap doInBackground(String... urlParameters) {
        Log.d(TAG, "+++++++++++++++++ doInBackground +++++++++++++++++");
        Bitmap returnVal = null;
        try {
            System.out.println(Config.BASE_SERVER_URL + "/img/" + urlParameters[0]);
            InputStream in = new java.net.URL(Config.BASE_SERVER_URL + "/img/" + urlParameters[0]).openStream();
            returnVal = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return returnVal;
    }

    /**
     * The system calls this to perform work in the UI thread and delivers
     * the result from doInBackground()
     */
    protected void onPostExecute(Bitmap image) {
        Log.d(TAG, "+++++++++++++++++ onPostExecute +++++++++++++++++");
        if (cardItem != null) {
            cardItem.setCachedImage(image);
        }
        if (viewHolder != null) {
            viewHolder.setImage(image);
        }
        if(productView != null) {
            productView.setImage(image);
        }
    }
}