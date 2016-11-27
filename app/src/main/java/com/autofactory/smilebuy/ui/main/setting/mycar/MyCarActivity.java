package com.autofactory.smilebuy.ui.main.setting.mycar;

import android.content.Intent;
import android.os.Parcelable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.component.OnScrollListener;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.model.CarDataShare;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.ui.main.car.detail.CarDetailActivity;
import com.autofactory.smilebuy.ui.main.car.register.RegisterCarActivity;
import com.autofactory.smilebuy.util.Constant;
import com.melnykov.fab.FloatingActionButton;

public class MyCarActivity extends NormalActivity {
    private View[] mTabs;
    private ImageButton[] mTabButtons;

    private int mCurrentTabIndex = Constant.MY_CAR_TAB_SELL_CAR;

    private GridView mGridView;
    private View mNoCar;
    private MyCarGridAdapter mGridAdapter;

    private FloatingActionButton mSell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_my_car);

        mTabs = new View[Constant.MY_CAR_TAB_COUNT];
        mTabButtons = new ImageButton[Constant.MY_CAR_TAB_COUNT];

        mTabs[Constant.MY_CAR_TAB_SELL_CAR] = findViewById(R.id.tab1);
        mTabs[Constant.MY_CAR_TAB_SHARE_CAR] = findViewById(R.id.tab2);

        mTabButtons[Constant.MY_CAR_TAB_SELL_CAR] = (ImageButton) findViewById(R.id.tab1Button);
        mTabButtons[Constant.MY_CAR_TAB_SHARE_CAR] = (ImageButton) findViewById(R.id.tab2Button);

        mTabButtons[Constant.MY_CAR_TAB_SELL_CAR].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTab(Constant.MY_CAR_TAB_SELL_CAR);
            }
        });
        mTabButtons[Constant.MY_CAR_TAB_SHARE_CAR].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTab(Constant.MY_CAR_TAB_SHARE_CAR);
            }
        });

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


        mGridView = (GridView) findViewById(R.id.gridView);
        mGridAdapter = new MyCarGridAdapter(this, R.layout.item_my_car_grid);
        mGridView.setAdapter(mGridAdapter);
        mNoCar = findViewById(R.id.noCar);
        mNoCar.setVisibility(View.GONE);

        mSell = (FloatingActionButton) findViewById(R.id.sell);
        mSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyCarActivity.this, RegisterCarActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
            }
        });
        mGridView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollUp(AbsListView view) {
                mSell.show();
            }

            @Override
            public void onScrollDown(AbsListView view) {
                mSell.hide();
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }
        });

        setTab(Constant.MY_CAR_TAB_SELL_CAR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ServerRequest.get().requestGetMyCarList(new Response.Listener<ServerResult>() {
            @Override
            public void onResponse(ServerResult response) {
                setTab(mCurrentTabIndex);
            }
        });
    }

    private void setTab(int tabIndex) {
        mTabs[mCurrentTabIndex].setBackgroundResource(R.drawable.btn_bottom);

        mCurrentTabIndex = tabIndex;
        mTabs[mCurrentTabIndex].setBackgroundResource(R.drawable.btn_bottom_click);

        if (mCurrentTabIndex == Constant.MY_CAR_TAB_SHARE_CAR) {
            mSell.setVisibility(View.GONE);
            mGridAdapter.setList(Application.get().getUserData().getCarShareList());
            mGridAdapter.setCarDataType(MyCarGridAdapter.Type.SHARE_CAR);
            // TODO Start Activity For Share Car
        } else {
            mSell.setVisibility(View.VISIBLE);
            mGridAdapter.setList(Application.get().getUserData().getCarList());
            mGridAdapter.setCarDataType(MyCarGridAdapter.Type.SELL_CAR);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(MyCarActivity.this, CarDetailActivity.class);
                    intent.putExtra(Constant.DATA_CAR_DATA, (Parcelable) mGridAdapter.getItem(position));
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
                }
            });
        }

        if (mGridAdapter.getCount() <= 0) {
            mNoCar.setVisibility(View.VISIBLE);
        } else {
            mNoCar.setVisibility(View.GONE);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_right);
    }
}
