package com.autofactory.smilebuy.data.server;

import com.fasterxml.jackson.annotation.*;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class ServerResult {
    @JsonProperty("success")
    protected boolean mIsSuccess;
    @JsonProperty("error_type")
    protected String mErrorType;
    @JsonProperty("error_message")
    protected String mErrorMessage;

    public void update(ServerResult serverResult) {
        this.mIsSuccess = serverResult.mIsSuccess;
        this.mErrorType = serverResult.mErrorType;
        this.mErrorMessage = serverResult.mErrorMessage;
    }

    public boolean isSuccess() {
        return mIsSuccess;
    }

    public String getErrorType() {
        return mErrorType;
    }

    public String getErrorMessage() {
        return mErrorMessage;
    }

    public void setIsSuccess(boolean isSuccess) {
        mIsSuccess = isSuccess;
    }

    public void setErrorType(String errorType) {
        mErrorType = errorType;
    }

    public void setErrorMessage(String errorMessage) {
        mErrorMessage = errorMessage;
    }
}
