package com.autofactory.smilebuy.data.server;

import com.autofactory.smilebuy.data.model.CarData;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Phebe on 2015. 11. 15..
 */
public class CarListResult extends ServerResult {
    @JsonProperty("cars")
    protected List<CarData> mCarList;

    public List<CarData> getCarList() {
        return mCarList;
    }
}
