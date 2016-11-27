package com.autofactory.smilebuy.ui.main.car.smilebuy;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.model.CarDataSmileBuy;
import com.autofactory.smilebuy.data.server.GetCarSmileBuyResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.main.car.detail.CarDetailFragment;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;

public class CarSmileBuyActivity extends FragmentActivity {
    private CarDataSmileBuy mCarData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_car_smile_buy);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ServerRequest.get().requestGetCarSmileBuy(getIntent().getLongExtra(Constant.DATA_CAR_SMILEBUY_ID, -1), new Response.Listener<GetCarSmileBuyResult>() {
            @Override
            public void onResponse(GetCarSmileBuyResult response) {
                Utility.hideProgressDialog();
                if (response.isSuccess()) {
                    mCarData = response.getCarDataSmileBuy();

                    FragmentTransaction transaction = mManager.beginTransaction();
                    Fragment fragment = CarSmileBuyFragment.newInstance(CarSmileBuyActivity.this, mCarData, false);
                    transaction.replace(R.id.fragmentContainer, fragment);
                    transaction.commit();

                    setCurrent(fragment);
                } else {
                    Utility.showPopupOk(CarSmileBuyActivity.this, response.getErrorMessage());
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_from_left, R.anim.anim_out_to_right);
    }

    @Override
    public void onFragmentSay(Bundle data) {

    }
}
