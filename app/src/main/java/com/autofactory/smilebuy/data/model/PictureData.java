package com.autofactory.smilebuy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class PictureData implements Parcelable {
    public static final int ID_NOT_ON_SERVER = -1;

    @JsonProperty("id")
    protected long mID = ID_NOT_ON_SERVER;
    @JsonProperty("image")
    protected String mURL = null;

    public PictureData() {
        setEmpty();
    }
    public PictureData(String url) {
        mID = ID_NOT_ON_SERVER;
        mURL = url;
    }
    public PictureData(PictureData pictureData) {
        this.mID = pictureData.mID;
        this.mURL = pictureData.mURL;
    }
    public PictureData(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mID);
        dest.writeString(mURL);
    }

    private void readFromParcel(Parcel in){
        mID = in.readLong();
        mURL = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public PictureData createFromParcel(Parcel in) {
            return new PictureData(in);
        }

        public PictureData[] newArray(int size) {
            return new PictureData[size];
        }
    };


    public long getID() {
        return mID;
    }

    public String getURL() {
        return mURL;
    }

    public void setURL(String URL) {
        mURL = URL;
    }

    public boolean isEmpty() { return mID == ID_NOT_ON_SERVER && (mURL == null || mURL.length() <= 0); }
    public void setEmpty() {
        mID = ID_NOT_ON_SERVER;
        mURL = null;
    }
    public boolean isOnServer() { return mID != ID_NOT_ON_SERVER; }
}
