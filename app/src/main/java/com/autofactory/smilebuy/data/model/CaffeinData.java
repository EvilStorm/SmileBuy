package com.autofactory.smilebuy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class CaffeinData implements Parcelable {
    @JsonProperty("id")
    protected long mID;
    @JsonProperty("result_file_url")
    protected String mFile;

    public CaffeinData() { }
    public CaffeinData(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mID);
        dest.writeString(mFile);
    }

    private void readFromParcel(Parcel in){
        mID = in.readLong();
        mFile = in.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public CaffeinData createFromParcel(Parcel in) {
            return new CaffeinData(in);
        }

        public CaffeinData[] newArray(int size) {
            return new CaffeinData[size];
        }
    };


    public long getID() {
        return mID;
    }

    public String getFile() {
        return mFile;
    }
}
