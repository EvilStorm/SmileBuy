package com.autofactory.smilebuy.data.model;


import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import com.autofactory.smilebuy.R;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 11. 11..
 */
public class UserDataFriend extends UserDataSimple {
    @JsonProperty("sell_car_list")
    List<CarData> mCarList = new ArrayList<>();
    @JsonProperty("share_car_list")
    List<CarDataShare> mCarShareList = new ArrayList<>();


    public UserDataFriend() { super(); }
    public UserDataFriend(UserDataFriend userDataFriend) {
        this.update(userDataFriend);
    }
    public UserDataFriend(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeTypedList(mCarList);
        dest.writeTypedList(mCarShareList);
    }

    protected void readFromParcel(Parcel in){
        super.readFromParcel(in);

        in.readTypedList(mCarList, CarData.CREATOR);
        in.readTypedList(mCarShareList, CarDataShare.CREATOR);
    }

    public static final Creator CREATOR = new Creator() {
        public UserDataFriend createFromParcel(Parcel in) {
            return new UserDataFriend(in);
        }

        public UserDataFriend[] newArray(int size) {
            return new UserDataFriend[size];
        }
    };

    public void update(UserDataFriend userDataFriend) {
        super.update(userDataFriend);
        mCarList = userDataFriend.mCarList;
        mCarShareList = userDataFriend.mCarShareList;
    }


    public List<CarData> getCarList() {
//        if(mCarList != null) {
//            for(int i=0; i<mCarList.size(); i++) {
//                if(mCarList.get(i).getUser() == null) {
//                    mCarList.get(i).setUser(this);
//                }
//            }
//        }
        return mCarList;
    }

    public void setCarList(List<CarData> carList) {
        mCarList = carList;
    }

    public List<CarDataShare> getCarShareList() {
//        if(mCarShareList != null) {
//            for(int i=0; i<mCarShareList.size(); i++) {
//                if(mCarShareList.get(i).getUser() == null) {
//                    mCarShareList.get(i).setUser(this);
//                }
//            }
//        }
        return mCarShareList;
    }

    public void setCarShareList(List<CarDataShare> carShareList) {
        mCarShareList = carShareList;
    }

}
