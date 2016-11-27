package com.autofactory.smilebuy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class CommentData implements Parcelable {
    @JsonProperty("id")
    protected long mID;
    @JsonProperty("car_id")
    protected long mCarID;

    @JsonProperty("user")
    protected UserDataSimple mUser;

    @JsonProperty("picture_url")
    protected String mPicture;
    @JsonProperty("message")
    protected String mMessage;
    @JsonProperty("created_at")
    protected String mDate;

    public CommentData() { }
    public CommentData(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mID);
        dest.writeLong(mCarID);

        dest.writeParcelable(mUser, flags);

        dest.writeString(mPicture);
        dest.writeString(mMessage);
        dest.writeString(mDate);
    }

    private void readFromParcel(Parcel in){
        mID = in.readLong();
        mCarID = in.readLong();
        mUser = in.readParcelable(UserDataSimple.class.getClassLoader());
        mPicture = in.readString();
        mMessage = in.readString();
        mDate = in.readString();
    }

    public static final Creator CREATOR = new Creator() {
        public CommentData createFromParcel(Parcel in) {
            return new CommentData(in);
        }

        public CommentData[] newArray(int size) {
            return new CommentData[size];
        }
    };


    public long getID() {
        return mID;
    }

    public long getCarID() {
        return mCarID;
    }

    public UserDataSimple getUser() {
        return mUser;
    }

    public String getPicture() {
        return mPicture;
    }

    public String getMessage() {
        return mMessage;
    }

    public String getDate() {
        return mDate;
    }
    public String getDateAsShort() {
        if(mDate == null || mDate.length() <= 0)    return "";
        String [] s1 = mDate.split(" ");
        if(s1 == null || s1.length <= 1)    return "";
        String [] s2 = s1[0].split("-");
        if(s2 == null || s2.length <= 2)     return "";
        return String.format("%s.%s.%s", s2[0], s2[1], s2[2]);
    }
}
