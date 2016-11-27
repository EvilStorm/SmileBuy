package com.autofactory.smilebuy.ui.main.setting.myfavoritecar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.ui.main.car.detail.CarDetailActivity;
import com.autofactory.smilebuy.ui.main.setting.mycar.MyCarGridAdapter;
import com.autofactory.smilebuy.util.Constant;

public class MyFavoriteCarActivity extends NormalActivity {
    private GridView mGridView;
    private View mNoCar;
    private MyCarGridAdapter mGridAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_favorite_car);

        mGridView = (GridView) findViewById(R.id.gridView);
        mGridAdapter = new MyCarGridAdapter(this, R.layout.item_my_car_grid);
        mGridAdapter.setList(Application.get().getUserData().getFavoriteCarList());
        mGridAdapter.setCarDataType(MyCarGridAdapter.Type.FAVORITE_CAR);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyFavoriteCarActivity.this, CarDetailActivity.class);
                intent.putExtra(Constant.DATA_CAR_DATA, (Parcelable) mGridAdapter.getItem(position));
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
            }
        });
        mGridView.setAdapter(mGridAdapter);

        ImageButton back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageButton delete = (ImageButton) findViewById(R.id.delete);
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGridAdapter.setRemovable(!mGridAdapter.isRemovable());
            }
        });

        mNoCar = findViewById(R.id.noCar);
        mNoCar.setVisibility(View.GONE);
//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ServerRequest.get().requestMyFavoriteCarList(new Response.Listener<ServerResult>() {
            @Override
            public void onResponse(ServerResult response) {
                mGridAdapter.setList(Application.get().getUserData().getFavoriteCarList());
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
