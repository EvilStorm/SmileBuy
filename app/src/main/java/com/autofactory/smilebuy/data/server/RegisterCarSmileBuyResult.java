package com.autofactory.smilebuy.data.server;

import com.autofactory.smilebuy.data.model.CarData;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by AirPhebe on 2015. 11. 12..
 */
public class RegisterCarSmileBuyResult extends ServerResult {
    @JsonProperty("car_smile_buy")
    protected CarData mCarData;




    public CarData getCarData() {
        return mCarData;
    }
}
