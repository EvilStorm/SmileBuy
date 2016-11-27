package com.autofactory.smilebuy.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class InterviewData implements Parcelable {
    @JsonProperty("question")
    protected String mQuestion;
    @JsonProperty("answer")
    protected String mAnswer;

    public InterviewData() { }
    public InterviewData(String question, String answer) {
        mQuestion = question;
        mAnswer = answer;
    }
    public InterviewData(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mQuestion);
        dest.writeString(mAnswer);
    }

    private void readFromParcel(Parcel in){
        mQuestion = in.readString();
        mAnswer = in.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public InterviewData createFromParcel(Parcel in) {
            return new InterviewData(in);
        }

        public InterviewData[] newArray(int size) {
            return new InterviewData[size];
        }
    };


    // GETTER
    public String getQuestion() {
        return mQuestion;
    }

    public String getAnswer() {
        return mAnswer;
    }
}
