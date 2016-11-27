package com.autofactory.smilebuy.data.server;

import com.autofactory.smilebuy.data.model.CarDataSmileBuy;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Phebe on 2015. 11. 20..
 */
public class GetCarSmileBuyResult extends ServerResult {
    @JsonProperty("car_smile_buy")
    protected CarDataSmileBuy mCarDataSmileBuy;


    public CarDataSmileBuy getCarDataSmileBuy() {
        return mCarDataSmileBuy;
    }
}
