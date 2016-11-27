package com.autofactory.smilebuy.data.server;

import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.model.CarDataShare;
import com.autofactory.smilebuy.data.model.CarDataSmileBuy;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Created by Phebe on 2015. 11. 15..
 */
public class MyCarListResult extends ServerResult {
    @JsonProperty("cars")
    protected List<CarData> mCarList;
    @JsonProperty("car_shares")
    protected List<CarDataShare> mCarShareList;
    @JsonProperty("car_smile_buy")
    protected List<CarDataSmileBuy> mCarSmileBuyList;

    public List<CarData> getCarList() {
        return mCarList;
    }

    public List<CarDataShare> getCarShareList() {
        return mCarShareList;
    }

    public List<CarDataSmileBuy> getCarSmileBuyList() {
        return mCarSmileBuyList;
    }
}
