package com.autofactory.smilebuy.data.model;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.util.Constant;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 12..
 */
public class CarDataShare extends CarDataSimple {
    @JsonProperty("user")
    protected UserDataSimple mUser;

    @JsonProperty("name")
    protected String mName;
    @JsonProperty("area_code")
    protected int mAreaType;
    @JsonProperty("car_wash")
    protected boolean mCarWash;
    @JsonProperty("smoking")
    protected boolean mSmoking;
    @JsonProperty("additional")
    protected String mAdditional;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeParcelable(mUser, flags);

        dest.writeString(mName);
        dest.writeInt(mAreaType);
        dest.writeByte((byte) (mCarWash ? 1 : 0));
        dest.writeByte((byte) (mSmoking ? 1 : 0));
        dest.writeString(mAdditional);
    }

    @Override
    protected void readFromParcel(Parcel in){
        super.readFromParcel(in);

        mUser = in.readParcelable(UserDataSimple.class.getClassLoader());

        mName = in.readString();
        mAreaType = in.readInt();
        mCarWash = in.readByte() != 0;
        mSmoking = in.readByte() != 0;
        mAdditional = in.readString();
    }

    public void update(CarDataShare item) {
        super.update(item);

        this.mName = item.mName;
        this.mAreaType = item.mAreaType;
        this.mCarWash = item.mCarWash;
        this.mSmoking = item.mSmoking;
        this.mAdditional = item.mAdditional;
    }

    public static final Creator CREATOR = new Creator() {
        public CarDataShare createFromParcel(Parcel in) {
            return new CarDataShare(in);
        }

        public CarDataShare[] newArray(int size) {
            return new CarDataShare[size];
        }
    };

    public CarDataShare() {
        super();
        mCarDataType = CAR_DATA_TYPE_CAR_SHARE;
    }
    public CarDataShare(Parcel in) {
        readFromParcel(in);
        mCarDataType = CAR_DATA_TYPE_CAR_SHARE;
    }




    // GETTER AND SETTER

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAreaType() {
        return mAreaType;
    }

    public String getAreaTypeAsString(Resources resources) {
        if(resources == null)   return "";
        String [] strings = resources.getStringArray(R.array.area);
        if(strings == null || strings.length <= 1) return "";
        if(mAreaType <= 0 || mAreaType >= strings.length) return strings[1];
        return strings[mAreaType];
    }

    public void setAreaType(int areaType) {
        mAreaType = areaType;
    }

    public String getAdditional() {
        return mAdditional;
    }

    public void setAdditional(String additional) {
        mAdditional = additional;
    }


    public UserDataSimple getUser() {
        return mUser;
    }

    public void setUser(UserDataSimple user) {
        mUser = user;
    }

    public boolean isCarWash() {
        return mCarWash;
    }

    public void setCarWash(boolean carWash) {
        mCarWash = carWash;
    }

    public boolean isSmoking() {
        return mSmoking;
    }

    public void setSmoking(boolean smoking) {
        mSmoking = smoking;
    }
}
