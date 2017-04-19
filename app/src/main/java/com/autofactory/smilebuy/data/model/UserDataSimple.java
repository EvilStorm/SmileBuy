package com.autofactory.smilebuy.data.model;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.autofactory.smilebuy.util.Constant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by AirPhebe on 2015. 11. 11..
 */
public class UserDataSimple implements Parcelable {
    public static final int MOBILE_NUM_POLICY_NOBODY = 0;
    public static final int MOBILE_NUM_POLICY_FRIEND = 1;
    public static final int MOBILE_NUM_POLICY_ALL = 2;

    @JsonProperty("id")
    protected long mID;
    @JsonProperty("type")
    protected int mType;
    @JsonProperty("nickname")
    protected String mNickName;
    @JsonProperty("profile_picture_url")
    protected String mProfilePic;
    @JsonProperty("profile_bg_url")
    protected String mProfileBG;
    @JsonProperty("profile_say")
    protected String mProfileSay;

    @JsonProperty("mobile_num")
    protected String mMobileNum;
    @JsonProperty("country_code")
    protected String mCountryCode;
    @JsonProperty("mobile_num_policy")
    protected int mMobileNumPolicy;

    @JsonProperty("created_at")
    protected String mDate;



    public UserDataSimple() { }
    public UserDataSimple(UserDataSimple userDataSimple) {
        this.update(userDataSimple);
    }
    public UserDataSimple(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mID);
        dest.writeInt(mType);
        dest.writeString(mNickName);
        dest.writeString(mProfilePic);
        dest.writeString(mProfileBG);
        dest.writeString(mProfileSay);

        dest.writeString(mMobileNum);
        dest.writeString(mCountryCode);
        dest.writeInt(mMobileNumPolicy);

        dest.writeString(mDate);
    }

    protected void readFromParcel(Parcel in){
        mID = in.readLong();
        mType = in.readInt();
        mNickName = in.readString();
        mProfilePic = in.readString();
        mProfileBG = in.readString();
        mProfileSay = in.readString();

        mMobileNum = in.readString();
        mCountryCode = in.readString();
        mMobileNumPolicy = in.readInt();

        mDate = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserDataSimple createFromParcel(Parcel in) {
            return new UserDataSimple(in);
        }

        public UserDataSimple[] newArray(int size) {
            return new UserDataSimple[size];
        }
    };

    public void update(UserDataSimple userDataSimple) {
        mID = userDataSimple.mID;
        mType = userDataSimple.mType;
        mNickName = userDataSimple.mNickName;
        mProfilePic = userDataSimple.mProfilePic;
        mProfileBG= userDataSimple.mProfileBG;
        mProfileSay = userDataSimple.mProfileSay;

        mMobileNum = userDataSimple.mMobileNum;
        mCountryCode = userDataSimple.mCountryCode;
        mMobileNumPolicy = userDataSimple.mMobileNumPolicy;

        mDate = userDataSimple.getDate();
    }


    public long getID() { return mID; }

    public int getType() { return mType; }

    public String getNickName() { return mNickName; }

    public String getProfilePic() {
        if(mProfilePic!= null && !mProfilePic.toLowerCase().startsWith("http")) {
            mProfilePic = Constant.IMAGE_DOMAIN_S3_TOKYO + mProfilePic;
        }
        return mProfilePic;

    }

    public String getDate() {
        return mDate;
    }

    public String getDateWithUnit(Context context) {
        Date date = null;
        try {
            date = new Date(Long.parseLong(mDate));
        } catch (NumberFormatException e) {
            return "";
        }

        Calendar c = Calendar.getInstance();
        c.setTime(date);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
        return dateFormat.format(c.getTime());
    }

    public String getProfileBG() {
        return mProfileBG;
    }

    public String getProfileSay() {
        return mProfileSay;
    }

    public String getMobileNum() {
        return mMobileNum;
    }

    public String getCountryCode() {
        return mCountryCode;
    }

    public int getMobileNumPolicy() {
        return mMobileNumPolicy;
    }
}
