package com.autofactory.smilebuy.data.server;

import com.autofactory.smilebuy.data.model.PictureData;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by AirPhebe on 2015. 11. 13..
 */
public class UploadPictureSmileBuyResult extends ServerResult {
    @JsonProperty("car_smile_buy_id")
    protected long mCarID;
    @JsonProperty("image")
    protected PictureData mPictureData;

    public long getCarID() {
        return mCarID;
    }
    public PictureData getPictureData() {
        return mPictureData;
    }
}
