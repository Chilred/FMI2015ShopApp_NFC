package de.thm.mwdr.fmi2015shopapp;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Chilred-pc on 07.09.2015.
 */

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    List<CardItem> cardList;
    JSONObject jsonObject;
    CardClickedReceiver ccr;

    public CardAdapter(JSONObject jsonObj, CardClickedReceiver ccr) {
        super();
        this.jsonObject = jsonObj;
        this.ccr = ccr;
        cardList = new ArrayList<>();
        CardItem card;
        JSONObject js;

        Iterator<String> iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            try {
                String currentUUID = iterator.next();
                js = jsonObject.getJSONObject(currentUUID);
                card = new CardItem();
                card.setName(js.get("name").toString());
                if(js.has("location")) {
                    card.setDescription(js.get("location").toString());
                } else{
                    card.setDescription(js.get("price").toString());
                }
                card.setThumbnail(R.drawable.audioguru);
                card.setImageName(js.get("image").toString());
                card.setUUID(currentUUID);
                cardList.add(card);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recycler_view_card_item, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        CardItem cardItem = cardList.get(i);
        viewHolder.name.setText(cardItem.getName());
        viewHolder.description.setText(cardItem.getDescription());
        viewHolder.imgThumbnail.setImageResource(cardItem.getThumbnail());
        Bitmap image = cardItem.getCachedImage();
        if (image == null) {
            new DownloadImageTask(viewHolder, cardItem).execute(cardItem.getImageName());
        } else {
            viewHolder.setImage(image);
        }
    }

    @Override
    public int getItemCount() {
        return cardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgThumbnail;
        public TextView name;
        public TextView description;

        public ViewHolder(final View itemView) {
            super(itemView);
            imgThumbnail = (ImageView)itemView.findViewById(R.id.img_thumbnail);
            name = (TextView)itemView.findViewById(R.id.card_name);
            description = (TextView)itemView.findViewById(R.id.card_description);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ccr.itemClicked(cardList.get(getAdapterPosition()).getUUID());
                }
            });
        }

        public void setImage(Bitmap image) {
            imgThumbnail.setImageBitmap(image);
        }
    }


}
