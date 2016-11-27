package com.autofactory.smilebuy.ui.main.setting.myrequest;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.server.RequestedCarListResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.main.car.detail.CarDetailActivity;
import com.autofactory.smilebuy.ui.main.car.smilebuy.CarSmileBuyActivity;
import com.autofactory.smilebuy.ui.main.setting.mycar.MyCarGridAdapter;
import com.autofactory.smilebuy.util.Constant;

public class MyRequestActivity extends NormalActivity {
    private GridView mGridView;
    private View mNoCar;
    private MyCarGridAdapter mGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_request);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mGridView = (GridView) findViewById(R.id.gridView);
        mGridAdapter = new MyCarGridAdapter(this, R.layout.item_my_car_grid);
        mGridAdapter.setList(Application.get().getUserData().getRequestCarList());
        mGridAdapter.setCarDataType(MyCarGridAdapter.Type.SELL_CAR);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CarData carData = (CarData) mGridAdapter.getItem(position);
                if (carData == null || carData.getSmileBuyID() < 0) {
                    Toast.makeText(MyRequestActivity.this, getString(R.string.toast_message_smilebuy_not_yet), Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(MyRequestActivity.this, CarSmileBuyActivity.class);
                    intent.putExtra(Constant.DATA_CAR_SMILEBUY_ID, carData.getSmileBuyID());
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
                }
            }
        });
        mGridView.setAdapter(mGridAdapter);


        mNoCar = findViewById(R.id.noCar);
        mNoCar.setVisibility(View.GONE);

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        ServerRequest.get().requestGetRequestedCarList(new Response.Listener<RequestedCarListResult>() {
            @Override
            public void onResponse(RequestedCarListResult response) {
                mGridAdapter.setList(Application.get().getUserData().getRequestCarList());
                if(mGridAdapter.getCount() <= 0) {
                    mNoCar.setVisibility(View.VISIBLE);
                } else {
                    mNoCar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_right);
    }
}
