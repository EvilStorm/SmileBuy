package com.autofactory.smilebuy.data.model;

/**
 * Created by Phebe on 16. 1. 24..
 */
public class NoticeData {
    protected String mSubject;
    protected String mContent;
    protected String mDate;
    protected int mImageResID = -1;
    protected boolean mOpened = false;

    public String getSubject() {
        return mSubject;
    }

    public void setSubject(String subject) {
        mSubject = subject;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public int getImageResID() {
        return mImageResID;
    }

    public void setImageResID(int imageResID) {
        mImageResID = imageResID;
    }

    public boolean isOpened() {
        return mOpened;
    }

    public void setOpened(boolean opened) {
        mOpened = opened;
    }
}
