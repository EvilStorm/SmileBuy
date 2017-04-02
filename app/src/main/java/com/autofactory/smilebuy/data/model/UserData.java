package com.autofactory.smilebuy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 11. 11..
 */

public class UserData extends UserDataFriend {
    @JsonProperty("email")
    String mEmail = null;
    @JsonProperty("facebook_token")
    String mFacebookToken = null;

    @JsonProperty("favorite_car_list")
    List<CarData> mFavoriteCarList = new ArrayList<>();
    @JsonProperty("smile_buy_list")
    List<CarDataSmileBuy> mCarSmileBuyList = new ArrayList<>();
    @JsonProperty("requested_car_list")
    List<CarData> mRequestCarList = new ArrayList<>();



    public UserData() { super(); }
    public UserData(UserData userData) {
        this.update(userData);
    }
    public UserData(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return super.describeContents();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeString(mEmail);
        dest.writeString(mFacebookToken);
        dest.writeTypedList(mFavoriteCarList);
        dest.writeTypedList(mCarSmileBuyList);
        dest.writeTypedList(mRequestCarList);
    }

    @Override
    protected void readFromParcel(Parcel in){
        super.readFromParcel(in);

        mEmail = in.readString();
        mFacebookToken = in.readString();
        in.readTypedList(mFavoriteCarList, CarData.CREATOR);
        in.readTypedList(mCarSmileBuyList, CarDataSmileBuy.CREATOR);
        in.readTypedList(mRequestCarList, CarData.CREATOR);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public UserData createFromParcel(Parcel in) {
            return new UserData(in);
        }

        public UserData[] newArray(int size) {
            return new UserData[size];
        }
    };

    public void update(UserData userData) {
        super.update(userData);
        mEmail = userData.mEmail;
        mFacebookToken = userData.mFacebookToken;
        mFavoriteCarList = userData.mFavoriteCarList;
        mCarSmileBuyList = userData.mCarSmileBuyList;
        mRequestCarList = userData.mRequestCarList;
    }




    public List<CarData> getFavoriteCarList() {
//        if(mFavoriteCarList != null) {
//            for(int i=0; i<mFavoriteCarList.size(); i++) {
//                if(mFavoriteCarList.get(i).getUser() == null) {
//                    mFavoriteCarList.get(i).setUser(this);
//                }
//            }
//        }
        return mFavoriteCarList;
    }

    public void setFavoriteCarList(List<CarData> favoriteCarList) {
        mFavoriteCarList = favoriteCarList;
    }

    public List<CarDataSmileBuy> getCarSmileBuyList() {
//        if(mCarSmileBuyList != null) {
//            for(int i=0; i<mCarSmileBuyList.size(); i++) {
//                if(mCarSmileBuyList.get(i).getManager() == null) {
//                    mCarSmileBuyList.get(i).setManager(this);
//                }
//            }
//        }
        return mCarSmileBuyList;
    }

    public void setCarSmileBuyList(List<CarDataSmileBuy> carSmileBuyList) {
        mCarSmileBuyList = carSmileBuyList;
    }

    public List<CarData> getRequestCarList() {
//        if(mRequestCarList != null) {
//            for(int i=0; i<mRequestCarList.size(); i++) {
//                if(mRequestCarList.get(i).getUser() == null) {
//                    mRequestCarList.get(i).setUser(this);
//                }
//            }
//        }
        return mRequestCarList;
    }

    public void setRequestCarList(List<CarData> requestCarList) {
        mRequestCarList = requestCarList;
    }

    public boolean isMyRequestedCar(long carID) {
        if(mRequestCarList == null) {
            return false;
        }
        for(int i=0; i<mRequestCarList.size(); i++) {
            if(mRequestCarList.get(i).getID() == carID) {
                return true;
            }
        }
        return false;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getFacebookToken() {
        return mFacebookToken;
    }

    public boolean isFacebookMember() {
        return mFacebookToken != null && mFacebookToken.length() > 0;
    }
}
