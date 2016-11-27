package com.autofactory.smilebuy.data.server;

import com.autofactory.smilebuy.data.model.UserData;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class LoginResult extends ServerResult {
    @JsonProperty("login_token")
    protected String mLoginToken;
    @JsonProperty("user")
    protected UserData mUserData;
    @JsonProperty("client_latest_version")
    protected int mClientVersion;
    @JsonProperty("client_update_url")
    protected String mClientUpdateURL;

    public String getLoginToken() { return mLoginToken; }
    public UserData getUserData() { return mUserData; }
    public int getClientVersion() {
        return mClientVersion;
    }
    public String getClientUpdateURL() {
        return mClientUpdateURL;
    }
}
