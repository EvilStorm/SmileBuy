package com.autofactory.smilebuy.data.model;

import android.content.res.Resources;
import android.os.Parcel;

import com.autofactory.smilebuy.R;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 12..
 */
public class CarDataEdit extends CarData {
    protected List<Long> mToRemoveOnServerPictures = new ArrayList<>();

    protected int mRequestSmileManServiceType = -1;
    protected int mRequestSmileManPaymentAmount = -1;

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeList(mToRemoveOnServerPictures);

        dest.writeInt(mRequestSmileManServiceType);
        dest.writeInt(mRequestSmileManPaymentAmount);
    }

    @Override
    protected void readFromParcel(Parcel in){
        super.readFromParcel(in);

        in.readList(mToRemoveOnServerPictures, ClassLoader.getSystemClassLoader());

        mRequestSmileManServiceType = in.readInt();
        mRequestSmileManPaymentAmount = in.readInt();
    }

    public void update(CarDataEdit item) {
        super.update(item);

        this.mToRemoveOnServerPictures = item.mToRemoveOnServerPictures;

        this.mRequestSmileManServiceType = item.mRequestSmileManServiceType;
        this.mRequestSmileManPaymentAmount = item.mRequestSmileManPaymentAmount;
    }


    public CarDataEdit() {
        super();
        mCarDataType = CAR_DATA_TYPE_CAR;
    }

    public CarDataEdit(Parcel in) {
        readFromParcel(in);
        mCarDataType = CAR_DATA_TYPE_CAR;
    }

    public CarDataEdit(CarData carData) {
        this.update(carData);

        mCarDataType = CAR_DATA_TYPE_CAR;
    }

    @Override
    public int describeContents() {
        return 0;
    }
    public static final Creator CREATOR = new Creator() {
        public CarDataEdit createFromParcel(Parcel in) {
            return new CarDataEdit(in);
        }

        public CarDataEdit[] newArray(int size) {
            return new CarDataEdit[size];
        }
    };


    // GETTER AND SETTER
    public List<Long> getToRemoveOnServerPictures() {
        return mToRemoveOnServerPictures;
    }

    public void setToRemoveOnServerPictures(List<Long> toRemoveOnServerPictures) {
        mToRemoveOnServerPictures = toRemoveOnServerPictures;
    }

    public int getRequestSmileManServiceType() {
        return mRequestSmileManServiceType;
    }

    public void setRequestSmileManServiceType(int requestSmileManServiceType) {
        mRequestSmileManServiceType = requestSmileManServiceType;
    }

    public int getRequestSmileManPaymentAmount() {
        return mRequestSmileManPaymentAmount;
    }

    public void setRequestSmileManPaymentAmount(int requestSmileManPaymentAmount) {
        mRequestSmileManPaymentAmount = requestSmileManPaymentAmount;
    }
}
