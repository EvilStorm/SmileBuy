package com.autofactory.smilebuy.data.server;

import com.autofactory.smilebuy.data.model.UserData;
import com.autofactory.smilebuy.data.model.UserDataFriend;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class GetUserDataResult extends ServerResult {
    @JsonProperty("user")
    protected UserDataFriend mUserDataFriend;

    public UserDataFriend getUserDataFriend() {
        return mUserDataFriend;
    }
}
