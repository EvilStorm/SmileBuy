package com.autofactory.smilebuy.data.model;

import android.content.res.Resources;
import android.os.Parcel;
import android.os.Parcelable;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 12..
 */
public class CarData extends CarDataSimple {
    @JsonProperty("user")
    protected UserDataSimple mUser = new UserDataSimple();

    @JsonProperty("name")
    protected String mName;
    @JsonProperty("car_num")
    protected String mCarNum;
    @JsonProperty("area_code")
    protected int mAreaType;

    @JsonProperty("mileage")
    protected int mMileage = -1;
    @JsonProperty("age_year")
    protected int mAgeYear = -1;
    @JsonProperty("age_month")
    protected int mAgeMonth = -1;
    @JsonProperty("engine")
    protected int mEngine = -1;
    @JsonProperty("additional")
    protected String mAdditional;
    @JsonProperty("can_smile_buy")
    protected boolean mCanSmileBuy = true;
    @JsonProperty("is_sold")
    protected boolean mIsSold;

    @JsonProperty("interviews")
    protected List<InterviewData> mInterviews = new ArrayList<>();
    @JsonProperty("favorite_users")
    protected List<UserDataSimple> mFavorites = new ArrayList<>();
    @JsonProperty("comment")
    protected List<CommentData> mComments = new ArrayList<>();


    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);

        dest.writeParcelable(mUser, flags);

        dest.writeString(mName);
        dest.writeString(mCarNum);
        dest.writeInt(mAreaType);
        dest.writeInt(mMileage);
        dest.writeInt(mAgeYear);
        dest.writeInt(mAgeMonth);
        dest.writeInt(mEngine);
        dest.writeString(mAdditional);
        dest.writeByte((byte) (mCanSmileBuy ? 1 : 0));
        dest.writeByte((byte) (mIsSold ? 1 : 0));
        dest.writeTypedList(mInterviews);
        dest.writeTypedList(mFavorites);
        dest.writeTypedList(mComments);
    }

    @Override
    protected void readFromParcel(Parcel in){
        super.readFromParcel(in);

        mUser = in.readParcelable(UserDataSimple.class.getClassLoader());

        mName = in.readString();
        mCarNum = in.readString();
        mAreaType = in.readInt();
        mMileage = in.readInt();
        mAgeYear = in.readInt();
        mAgeMonth = in.readInt();
        mEngine = in.readInt();
        mAdditional = in.readString();
        mCanSmileBuy = in.readByte() != 0;
        mIsSold = in.readByte() != 0;

        in.readTypedList(mInterviews, InterviewData.CREATOR);
        in.readTypedList(mFavorites, UserDataSimple.CREATOR);
        in.readTypedList(mComments, CommentData.CREATOR);
    }

    public void update(CarData item) {
        super.update(item);

        this.mUser = item.mUser;

        this.mName = item.mName;
        this.mCarNum = item.mCarNum;
        this.mAreaType = item.mAreaType;
        this.mMileage = item.mMileage;
        this.mAgeYear = item.mAgeYear;
        this.mAgeMonth = item.mAgeMonth;
        this.mEngine = item.mEngine;
        this.mAdditional = item.mAdditional;
        this.mCanSmileBuy = item.mCanSmileBuy;
        this.mIsSold = item.mIsSold;

        this.mInterviews = item.mInterviews;
        this.mFavorites = item.mFavorites;
        this.mComments = item.mComments;
    }


    public CarData() {
        super();
        mCarDataType = CAR_DATA_TYPE_CAR;
    }

    public CarData(Parcel in) {
        readFromParcel(in);
        mCarDataType = CAR_DATA_TYPE_CAR;
    }




    public List<InterviewData> getInterviews() {
        return mInterviews;
    }
    public String getInterviewAsJson() {
        ObjectMapper mapper = new ObjectMapper();
        String resultJson = "";
        try {
            resultJson = mapper.writeValueAsString(mInterviews);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            resultJson = "";
        }


        return resultJson;
    }





    @Override
    public int describeContents() {
        return 0;
    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public CarData createFromParcel(Parcel in) {
            return new CarData(in);
        }

        public CarData[] newArray(int size) {
            return new CarData[size];
        }
    };


    // GETTER AND SETTER

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

    public int getMileage() {
        return mMileage;
    }

    public void setMileage(int mileage) {
        mMileage = mileage;
    }

    public String getMileageAsString(Resources resources) {
        if(resources == null)   return null;
        if(mMileage < 0) {
            return null;
        }
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
        if(resources == null)   return null;
        if(mAgeYear <= 0 || mAgeMonth <= 0) {
            return null;
        }
        return String.format("%d%s %d%s", mAgeYear, resources.getString(R.string.unit_year), mAgeMonth, resources.getString(R.string.unit_month));
    }

    public int getEngine() {
        return mEngine;
    }

    public void setEngine(int engine) {
        mEngine = engine;
    }

    public String getEngineAsString(Resources resources) {
        if(resources == null)   return null;
        if(mEngine < 0) {
            return null;
        }
        return String.format("%,d%s", mEngine, resources.getString(R.string.unit_engine));
    }

    public String getAdditional() {
        return mAdditional;
    }

    public void setAdditional(String additional) {
        mAdditional = additional;
    }


    public void setInterviews(List<InterviewData> interviews) {
        mInterviews = interviews;
    }




    public boolean isSold() {
        return mIsSold;
    }

    public void setIsSold(boolean isSold) {
        mIsSold = isSold;
    }

    public boolean isCanSmileBuy() {
        return mCanSmileBuy;
    }

    public void setCanSmileBuy(boolean canSmileBuy) {
        mCanSmileBuy = canSmileBuy;
    }

    public List<UserDataSimple> getFavorites() {
        return mFavorites;
    }

    public int getFavoriteCount() {
        return mFavorites == null ? 0 : mFavorites.size();
    }

    public void setFavorites(List<UserDataSimple> favorites) {
        mFavorites = favorites;
    }

    public boolean isFavoriteUser(long userID) {
        if(mFavorites == null) {
            return false;
        }
        for(int i=0; i<mFavorites.size(); i++) {
            if(mFavorites.get(i).getID() == userID) {
                return true;
            }
        }
        return false;
    }

    public UserDataSimple getUser() {
        return mUser;
    }

    public void setUser(UserDataSimple user) {
        mUser = user;
    }

    public List<CommentData> getComments() {
        return mComments;
    }

    public int getCommentCount() {
        return mComments == null ? 0 : mComments.size();
    }

    public void setComments(List<CommentData> comments) {
        mComments = comments;
    }

}
