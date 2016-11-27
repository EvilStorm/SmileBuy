package com.autofactory.smilebuy.data.model;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.util.Constant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 12..
 */
public class CarDataSimple implements Parcelable {
    public static final int CAR_DATA_TYPE_CAR = 0;
    public static final int CAR_DATA_TYPE_CAR_SHARE = 1;
    public static final int CAR_DATA_TYPE_CAR_SMILEBUY = 2;

    protected int mCarDataType = CAR_DATA_TYPE_CAR;



    @JsonProperty("id")
    protected long mID = -1;

    @JsonProperty("car_type")
    protected int mCarType;

    @JsonProperty("price")
    protected int mPrice = -1;
    @JsonProperty("fuel_type")
    protected int mFuelType;
    @JsonProperty("transmission_type")
    protected int mTransmissionType;
    @JsonProperty("created_at")
    protected String mDate;

    @JsonProperty("images")
    protected List<PictureData> mPictures = new ArrayList<>();

    @JsonProperty("smile_buy_id")
    protected long mSmileBuyID;


    protected boolean mReversedPicture = false;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mID);
        dest.writeInt(mCarType);

        dest.writeInt(mPrice);
        dest.writeInt(mFuelType);
        dest.writeInt(mTransmissionType);
        dest.writeString(mDate);

        dest.writeTypedList(mPictures);

        dest.writeLong(mSmileBuyID);

        dest.writeByte((byte) (mReversedPicture ? 1 : 0));
    }

    protected void readFromParcel(Parcel in){
        mID = in.readLong();
        mCarType = in.readInt();

        mPrice = in.readInt();
        mFuelType = in.readInt();
        mTransmissionType = in.readInt();
        mDate = in.readString();

        in.readTypedList(mPictures, PictureData.CREATOR);

        mSmileBuyID = in.readLong();

        mReversedPicture = in.readByte() != 0;
    }

    public void update(CarDataSimple item) {
        this.mID = item.mID;
        this.mCarType = item.mCarType;
        this.mPrice = item.mPrice;
        this.mFuelType = item.mFuelType;
        this.mTransmissionType = item.mTransmissionType;
        this.mDate = item.mDate;

        this.mPictures = item.mPictures;

        this.mSmileBuyID = item.mSmileBuyID;

        this.mReversedPicture = item.mReversedPicture;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator CREATOR = new Creator() {
        public CarDataSimple createFromParcel(Parcel in) {
            return new CarDataSimple(in);
        }

        public CarDataSimple[] newArray(int size) {
            return new CarDataSimple[size];
        }
    };


    public CarDataSimple() {
        mReversedPicture = false;
        mPictures.clear();
    }

    public CarDataSimple(Parcel in) {
        mReversedPicture = false;
        readFromParcel(in);
    }

    public int addPicture(String url) {
        if(mPictures.size() >= Constant.ADD_PICTURE_LIMIT_USER)
            return -1;

        mPictures.add(new PictureData(url));
        return mPictures.size() - 1;
    }
    public void removePicture(int index) { mPictures.remove(index); }


    public int getPictureCount() { return mPictures.size(); }
    public String getMainPictureURL() {
        reversePictures();
        return (mPictures.size() > 0 && mPictures.get(0) != null) ? mPictures.get(0).getURL() : null;
    }
    public List<PictureData> getPictures() {
        reversePictures();
        return mPictures;
    }
    public PictureData getPicture(int index) {
        if(index < 0 || index >= mPictures.size()) {
            return mPictures.get(0);
        } else {
            return mPictures.get(index);
        }
    }

    private void reversePictures() {
        if(!mReversedPicture) {
            mReversedPicture = true;
            Collections.reverse(mPictures);
        }
    }




    // GETTER AND SETTER

    public int getFuelType() {
        return mFuelType;
    }

    public String getFuelTypeAsString(Resources resources) {
        if(resources == null)   return "";
        String [] strings = resources.getStringArray(R.array.fuel);
        if(strings == null || mFuelType <= 0 || mFuelType >= strings.length) return "";
        return strings[mFuelType];
    }

    public void setFuelType(int fuelType) {
        mFuelType = fuelType;
    }

    public int getTransmissionType() {
        return mTransmissionType;
    }

    public String getTransmissionTypeAsString(Resources resources) {
        if(resources == null)   return "";
        String [] strings = resources.getStringArray(R.array.transmission);
        if(strings == null || mTransmissionType <= 0 || mTransmissionType >= strings.length) return "";
        return strings[mTransmissionType];
    }

    public void setTransmissionType(int transmissionType) {
        mTransmissionType = transmissionType;
    }

    public long getID() {
        return mID;
    }

    public void setID(long ID) {
        mID = ID;
    }

    public void setPictures(List<PictureData> pictures) {
        mPictures = pictures;
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
        return String.format("%s/%s", s2[1], s2[2]);
    }

    public void setDate(String date) {
        mDate = date;
    }

    public int getPrice() {
        return mPrice;
    }
    public String getPriceAsString(Resources resources) {
        String price = String.format("%,d", mPrice);
        return price;
//        if(resources == null)   return price;
//        return String.format("%s%s", price, resources.getString(R.string.unit_price));
    }

    public void setPrice(int price) {
        mPrice = price;
    }

    public int getCarDataType() {
        return mCarDataType;
    }

    public int getCarType() {
        return mCarType;
    }

    public void setCarType(int carType) {
        mCarType = carType;
    }

    public long getSmileBuyID() {
        return mSmileBuyID;
    }

    public void setSmileBuyID(long smileBuyID) {
        mSmileBuyID = smileBuyID;
    }

}
