package com.autofactory.smilebuy.ui.main.car.smilebuy.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.data.model.CarDataSmileBuy;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.ui.main.MainActivity;
import com.autofactory.smilebuy.ui.main.car.detail.CarDetailFragment;
import com.autofactory.smilebuy.ui.main.car.register.RegisterCarActivity;
import com.autofactory.smilebuy.ui.main.car.smilebuy.CarSmileBuyFragment;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;

public class RegisterCarSmileBuyCompleteActivity extends FragmentActivity {
    private CarDataSmileBuy mCarDataSmileBuy;
    private boolean mIsUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register_car_smile_buy_complete);

        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSellActivity();
            }
        });
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequest.get().requestRegisterCarSmileBuy(mIsUpdate, mCarDataSmileBuy, new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        if (response.isSuccess()) {
                            Intent i = new Intent(RegisterCarSmileBuyCompleteActivity.this, MainActivity.class);
                            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                            overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
                        } else {
                            Utility.showPopupOk(RegisterCarSmileBuyCompleteActivity.this, response.getErrorMessage());
                        }
                    }
                });
            }
        });

        mCarDataSmileBuy = getIntent().getParcelableExtra(Constant.DATA_CAR_DATA);
        mIsUpdate = getIntent().getBooleanExtra(Constant.DATA_CAR_IS_UPDATE, false);

        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fragment = CarSmileBuyFragment.newInstance(this, mCarDataSmileBuy, true);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();

        setCurrent(fragment);
    }

    private void startSellActivity() {
        Intent i = new Intent(RegisterCarSmileBuyCompleteActivity.this, RegisterCarSmileBuyActivity.class);
        i.putExtra(Constant.DATA_CAR_DATA, mCarDataSmileBuy);
        startActivity(i);
        overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startSellActivity();
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
