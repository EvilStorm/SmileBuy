package com.autofactory.smilebuy.data.server;

import com.autofactory.smilebuy.data.model.UserData;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class UserDataUpdatedResult extends ServerResult {
    @JsonProperty("user")
    protected UserData mUserData;

    public UserData getUserData() { return mUserData; }
}
