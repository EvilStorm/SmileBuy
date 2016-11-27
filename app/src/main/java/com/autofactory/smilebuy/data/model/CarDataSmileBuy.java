package com.autofactory.smilebuy.data.model;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.util.Constant;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 12..
 */
public class CarDataSmileBuy extends CarDataSimple {
    @JsonProperty("car_id")
    protected long mCarID = -1;

    @JsonProperty("manager")
    protected UserDataSimple mManager;

    @JsonProperty("name")
    protected String mName;
    @JsonProperty("car_num")
    protected String mCarNum;
    @JsonProperty("mileage")
    protected int mMileage;
    @JsonProperty("age_year")
    protected int mAgeYear;
    @JsonProperty("age_month")
    protected int mAgeMonth;
    @JsonProperty("engine")
    protected int mEngine;
    @JsonProperty("sunroof")
    protected boolean mSunroof;
    @JsonProperty("smart_key")
    protected boolean mSmartKey;
    @JsonProperty("black_box")
    protected boolean mBlackBox;
    @JsonProperty("rear_camera")
    protected boolean mRearCamera;
    @JsonProperty("navigation")
    protected boolean mNavigation;
    @JsonProperty("caffein")
    protected boolean mCaffein;
    @JsonProperty("accident")
    protected String mAccident;
    @JsonProperty("predicted_price")
    protected String mPredictedPrice;
    @JsonProperty("manager_comment")
    protected String mManagerComment;

    @JsonProperty("caffein_data")
    protected CaffeinData mCaffeinData;


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeLong(mCarID);

        dest.writeParcelable(mManager, flags);

        dest.writeString(mName);
        dest.writeString(mCarNum);
        dest.writeInt(mMileage);
        dest.writeInt(mAgeYear);
        dest.writeInt(mAgeMonth);
        dest.writeInt(mEngine);
        dest.writeByte((byte) (mSunroof ? 1 : 0));
        dest.writeByte((byte) (mSmartKey ? 1 : 0));
        dest.writeByte((byte) (mBlackBox ? 1 : 0));
        dest.writeByte((byte) (mRearCamera ? 1 : 0));
        dest.writeByte((byte) (mNavigation ? 1 : 0));
        dest.writeByte((byte) (mCaffein ? 1 : 0));
        dest.writeString(mAccident);
        dest.writeString(mPredictedPrice);
        dest.writeString(mManagerComment);

        dest.writeParcelable(mCaffeinData, flags);
    }
    @Override
    protected void readFromParcel(Parcel in){
        super.readFromParcel(in);

        mCarID = in.readLong();

        mManager = in.readParcelable(UserDataSimple.class.getClassLoader());

        mName = in.readString();
        mCarNum = in.readString();
        mMileage = in.readInt();
        mAgeYear = in.readInt();
        mAgeMonth = in.readInt();
        mEngine = in.readInt();
        mSunroof = in.readByte() != 0;
        mSmartKey = in.readByte() != 0;
        mBlackBox = in.readByte() != 0;
        mRearCamera = in.readByte() != 0;
        mNavigation = in.readByte() != 0;
        mCaffein = in.readByte() != 0;
        mAccident = in.readString();
        mPredictedPrice = in.readString();
        mManagerComment = in.readString();

        mCaffeinData = in.readParcelable(CaffeinData.class.getClassLoader());
    }

    public void update(CarDataSmileBuy item) {
        super.update(item);

        this.mCarID = item.mCarID;

        this.mManager = item.mManager;

        this.mName = item.mName;
        this.mCarNum = item.mCarNum;
        this.mMileage = item.mMileage;
        this.mAgeYear = item.mAgeYear;
        this.mAgeMonth = item.mAgeMonth;
        this.mEngine = item.mEngine;
        this.mSunroof = item.mSunroof;
        this.mSmartKey = item.mSmartKey;
        this.mBlackBox = item.mBlackBox;
        this.mRearCamera = item.mRearCamera;
        this.mNavigation = item.mNavigation;
        this.mCaffein = item.mCaffein;
        this.mAccident = item.mAccident;
        this.mPredictedPrice = item.mPredictedPrice;
        this.mManagerComment = item.mManagerComment;

        this.mCaffeinData = item.mCaffeinData;
    }

    public static final Creator CREATOR = new Creator() {
        public CarDataSmileBuy createFromParcel(Parcel in) {
            return new CarDataSmileBuy(in);
        }

        public CarDataSmileBuy[] newArray(int size) {
            return new CarDataSmileBuy[size];
        }
    };

    public CarDataSmileBuy() {
        super();
        mCarDataType = CAR_DATA_TYPE_CAR_SMILEBUY;
    }

    public CarDataSmileBuy(Parcel in) {
        readFromParcel(in);
        mCarDataType = CAR_DATA_TYPE_CAR_SMILEBUY;
    }




    // GETTER AND SETTER


    public long getCarID() {
        return mCarID;
    }

    public void setCarID(long carID) {
        mCarID = carID;
    }

    public UserDataSimple getManager() {
        return mManager;
    }

    public void setManager(UserDataSimple manager) {
        mManager = manager;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCarNum() {
        return mCarNum;
    }

    public void setCarNum(String carNum) {
        mCarNum = carNum;
    }

    public int getMileage() {
        return mMileage;
    }

    public void setMileage(int mileage) {
        mMileage = mileage;
    }

    public String getMileageAsString(Resources resources) {
        if(resources == null)   return "";
        return String.format("%,d%s", mMileage, resources.getString(R.string.unit_km));
    }

    public int getAgeYear() {
        return mAgeYear;
    }

    public void setAgeYear(int ageYear) {
        mAgeYear = ageYear;
    }

    public int getAgeMonth() {
        return mAgeMonth;
    }

    public void setAgeMonth(int ageMonth) {
        mAgeMonth = ageMonth;
    }

    public String getAgeAsString(Resources resources) {
        if(resources == null)   return "";
        return String.format("%d%s %d%s", mAgeYear, resources.getString(R.string.unit_year), mAgeMonth, resources.getString(R.string.unit_month));
    }

    public int getEngine() {
        return mEngine;
    }

    public void setEngine(int engine) {
        mEngine = engine;
    }

    public String getEngineAsString(Resources resources) {
        if(resources == null)   return "";
        return String.format("%,d%s", mEngine, resources.getString(R.string.unit_engine));
    }

    public boolean isSunroof() {
        return mSunroof;
    }

    public void setSunroof(boolean sunroof) {
        mSunroof = sunroof;
    }

    public boolean isSmartKey() {
        return mSmartKey;
    }

    public void setSmartKey(boolean smartKey) {
        mSmartKey = smartKey;
    }

    public boolean isBlackBox() {
        return mBlackBox;
    }

    public void setBlackBox(boolean blackBox) {
        mBlackBox = blackBox;
    }

    public boolean isRearCamera() {
        return mRearCamera;
    }

    public void setRearCamera(boolean rearCamera) {
        mRearCamera = rearCamera;
    }

    public boolean isNavigation() {
        return mNavigation;
    }

    public void setNavigation(boolean navigation) {
        mNavigation = navigation;
    }

    public boolean isCaffein() {
        return mCaffein;
    }

    public void setCaffein(boolean caffein) {
        mCaffein = caffein;
    }

    public String getAccident() {
        return mAccident;
    }

    public void setAccident(String accident) {
        mAccident = accident;
    }

    public String getPredictedPrice() {
        return mPredictedPrice;
    }

    public void setPredictedPrice(String predictedPrice) {
        mPredictedPrice = predictedPrice;
    }

    public String getManagerComment() {
        return mManagerComment;
    }

    public void setManagerComment(String managerComment) {
        mManagerComment = managerComment;
    }

    public CaffeinData getCaffeinData() {
        return mCaffeinData;
    }

    public String getCaffeinDataURL() {
        return mCaffeinData == null ? null : mCaffeinData.getFile();
    }
}
