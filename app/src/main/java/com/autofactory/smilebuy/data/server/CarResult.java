package com.autofactory.smilebuy.data.server;

import com.autofactory.smilebuy.data.model.CarData;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Phebe on 16. 1. 25..
 */
public class CarResult extends ServerResult {
    @JsonProperty("car")
    protected CarData mCarData = new CarData();

    public void update(CarResult carResult) {
        super.update(carResult);
        this.mCarData.update(carResult.getCarData());
    }

    public CarData getCarData() {
        return mCarData;
    }
}
