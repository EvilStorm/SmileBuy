package com.autofactory.smilebuy.ui.main.car.detail;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.server.CarFavoriteResult;
import com.autofactory.smilebuy.data.server.CarIsSoldResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.main.car.register.RegisterCarActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;

public class CarDetailActivity extends FragmentActivity {
    public static final int ACTIVITY_REQUEST_CODE_COMMENT = 99;
    private CarData mCarData = null;

    private TextView mTitle;
    private ImageView mFavorite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_car_detail);

        mCarData = getIntent().getParcelableExtra(Constant.DATA_CAR_DATA);
        if(mCarData == null) {
            finish();
            return;
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mTitle = (TextView) findViewById(R.id.title);
        mTitle.setText(mCarData.getUser().getNickName());
        mFavorite = (ImageView) findViewById(R.id.favoriteIcon);
        if(Application.get().isMyFavoriteCar(mCarData)) {
            mFavorite.setImageResource(R.drawable.icon_heart_n);
        } else {
            mFavorite.setImageResource(R.drawable.icon_heart);
        }
        findViewById(R.id.favorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequest.get().requestCarFavorite(mCarData.getID(), new Response.Listener<CarFavoriteResult>() {
                    @Override
                    public void onResponse(CarFavoriteResult response) {
                        mCarData.update(response.getCarData());
                        onCarDataUpdated();
                    }
                });
            }
        });

        ImageView bottomImage1 = (ImageView) findViewById(R.id.bottomImage1);
        ImageView bottomImage2 = (ImageView) findViewById(R.id.bottomImage2);
        TextView bottomText1 = (TextView) findViewById(R.id.bottomText1);
        final TextView bottomText2 = (TextView) findViewById(R.id.bottomText2);
        if(Application.get().isMe(mCarData.getUser())) {
            bottomImage1.setImageResource(R.drawable.icon_edit);
            bottomText1.setText(getString(R.string.action_sell_edit));
            bottomImage2.setImageResource(R.drawable.icon_sale);
            if(mCarData.isSold()) {
                bottomText2.setText(getString(R.string.action_unsold));
            } else {
                bottomText2.setText(getString(R.string.action_sold));
            }
            findViewById(R.id.bottomButton1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(CarDetailActivity.this, RegisterCarActivity.class);
                    i.putExtra(Constant.DATA_CAR_DATA, mCarData);
                    startActivity(i);
                    overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
                }
            });
            findViewById(R.id.bottomButton2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ServerRequest.get().requestCarIsSold(mCarData.getID(), new Response.Listener<CarIsSoldResult>() {
                        @Override
                        public void onResponse(CarIsSoldResult response) {
                            mCarData.update(response.getCarData());
                            onCarDataUpdated();
                            if(mCarData.isSold()) {
                                bottomText2.setText(getString(R.string.action_unsold));
                            } else {
                                bottomText2.setText(getString(R.string.action_sold));
                            }
                        }
                    });
                }
            });
        } else {
            findViewById(R.id.bottom).setVisibility(View.GONE);

            bottomImage1.setImageResource(R.drawable.icon_report);
            bottomText1.setText(getString(R.string.action_report));
            bottomImage2.setImageResource(R.drawable.icon_message);
            bottomText2.setText(getString(R.string.action_send_message));
            findViewById(R.id.bottomButton1).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showPopupOk(CarDetailActivity.this, getString(R.string.popup_message_not_ready_yet));
                }
            });
            findViewById(R.id.bottomButton2).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showPopupOk(CarDetailActivity.this, getString(R.string.popup_message_not_ready_yet));
                }
            });
        }



        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fragment = CarDetailFragment.newInstance(this, mCarData, false);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();

        setCurrent(fragment);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_REQUEST_CODE_COMMENT) {
            if(resultCode == RESULT_OK && data != null) {
                CarData carData = data.getParcelableExtra(Constant.DATA_CAR_DATA);
                mCarData.update(carData);
                onCarDataUpdated();
            }
        }
    }

    private void onCarDataUpdated() {
        mTitle.setText(mCarData.getUser().getNickName());
        if(Application.get().isMyFavoriteCar(mCarData)) {
            mFavorite.setImageResource(R.drawable.icon_heart_n);
        } else {
            mFavorite.setImageResource(R.drawable.icon_heart);
        }

        if(mCurrent != null) {
            Bundle data = new Bundle();
            data.putBoolean(CarDetailFragment.CAR_DATA_UPDATED, true);
            data.putParcelable(Constant.DATA_CAR_DATA, mCarData);
            mCurrent.onActivitySay(data);
        }
    }

    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra(Constant.DATA_CAR_DATA, mCarData);
        setResult(RESULT_OK, data);

        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_right);
    }

    @Override
    public void onFragmentSay(Bundle data) {

    }
}
