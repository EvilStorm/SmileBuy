package com.autofactory.smilebuy.data.server;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class ServerResultError extends VolleyError {
    protected ServerResult mServerResult = null;

    public ServerResultError() {
        super();
    }
    public ServerResultError(NetworkResponse response) {
        super(response);
    }

    public ServerResultError(String exceptionMessage) {
        super(exceptionMessage);
    }

    public ServerResultError(String exceptionMessage, Throwable reason) {
        super(exceptionMessage, reason);
    }

    public ServerResultError(Throwable cause) {
        super(cause);
    }

    public ServerResultError(ServerResult result) {
        super();
        mServerResult = result;
    }

    public ServerResult getCommonResult() {
        return mServerResult;
    }
}
