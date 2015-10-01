package de.thm.mwdr.fmi2015shopapp;

import android.graphics.Bitmap;

/**
 * Created by Chilred-pc on 07.09.2015.
 */
public class CardItem {
    private String mName;
    private String mDes;
    private int mThumbnail;
    private String uuid;
    private String imageName;
    private Bitmap cachedImage;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDescription() {
        return mDes;
    }

    public void setDescription(String des) {
        this.mDes = des;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public String getUUID() {
        return uuid;
    }

    public void setUUID(String uuid) {
        this.uuid = uuid;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Bitmap getCachedImage() {
        return cachedImage;
    }

    public void setCachedImage(Bitmap image) {
        this.cachedImage = image;
    }
}
