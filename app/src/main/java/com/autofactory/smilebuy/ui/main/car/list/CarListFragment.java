package com.autofactory.smilebuy.ui.main.car.list;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.component.OnScrollListener;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.ui.main.car.detail.CarDetailActivity;
import com.autofactory.smilebuy.ui.main.car.register.RegisterCarActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Log;
import com.autofactory.smilebuy.util.Utility;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.melnykov.fab.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class CarListFragment extends Fragment {
//    public static final int ACTIVITY_REQUEST_CODE_COMMENT = 97;
//    public static final int ACTIVITY_REQUEST_CODE_CAR_DETAIL = 98;
    public static final int ACTIVITY_REQUEST_CODE_FILTER_SET = 99;


    public static final String FILTER_CAR_TYPE = "FILTER_CAR_TYPE";
    public static final String FILTER_HAS_SMILEBUY = "FILTER_HAS_SMILEBUY";
    public static final String FILTER_PRICE_L = "FILTER_PRICE_L";
    public static final String FILTER_PRICE_H = "FILTER_PRICE_H";
    public static final String FILTER_MILEAGE_L= "FILTER_MILEAGE_L";
    public static final String FILTER_MILEAGE_H = "FILTER_MILEAGE_H";
    public static final String FILTER_AREA = "FILTER_AREA";


    private PullToRefreshListView mListView;
    private CarListAdapter mListAdapter;
    private FloatingActionButton mSell;
    private FloatingActionButton mAsk;

    private EditText mSearch;
    private ImageButton mFilter;

    private CarData mSelectedCarData = null;



    public CarListFragment() {
        // Required empty public constructor
    }

    public static CarListFragment newInstance(FragmentActivity fragmentActivity, CarListAdapter listAdapter) {
        CarListFragment fragment = new CarListFragment();
        fragment.init(fragmentActivity, 0, fragmentActivity.getString(R.string.title_car_list), listAdapter);
        return fragment;
    }

    protected void init(FragmentActivity fragmentActivity, int id, String name, CarListAdapter listAdapter) {
        super.init(fragmentActivity, id, name);
        mListAdapter = listAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fra_car_list, container, false);

        ImageButton eventButton = (ImageButton) findViewById(R.id.eventButton);
        eventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://goo.gl/Qs7ZgQ "));
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
            }
        });

        Date now = new Date(System.currentTimeMillis());
        Date endOfEvent = now;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        try {
            endOfEvent = dateFormat.parse("2016-09-30");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if(now.after(endOfEvent)) {
            eventButton.setVisibility(View.GONE);
        }



        mSearch = (EditText) findViewById(R.id.search);
        mSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    Log.d("REQUEST SERARCH : " + mSearch.getText().toString());
                    mListAdapter.refreshCarList(true);
                    return true;
                }
                return false;
            }
        });

        mFilter = (ImageButton) findViewById(R.id.filter);
        mFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CarListAdapter.FilterInfo filterInfo = mListAdapter.getFilterInfo();
                Intent i = new Intent(getActivity(), CarFilterActivity.class);
                i.putExtra(CarListFragment.FILTER_CAR_TYPE, filterInfo.getCarType());
                i.putExtra(CarListFragment.FILTER_HAS_SMILEBUY, filterInfo.isHasSmileBuy());
                i.putExtra(CarListFragment.FILTER_PRICE_L, filterInfo.getPriceL());
                i.putExtra(CarListFragment.FILTER_PRICE_H, filterInfo.getPriceH());
                i.putExtra(CarListFragment.FILTER_MILEAGE_L, filterInfo.getMileageL());
                i.putExtra(CarListFragment.FILTER_MILEAGE_H, filterInfo.getMileageH());
                i.putExtra(CarListFragment.FILTER_AREA, filterInfo.getArea());
                
                startActivityForResult(i, ACTIVITY_REQUEST_CODE_FILTER_SET);
                getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
            }
        });



        mListView = (PullToRefreshListView) findViewById(R.id.listView);
        mListView.setAdapter(mListAdapter);
        mListAdapter.setListView(mListView);
        mListAdapter.setSearch(mSearch);
        //mListView.setItemsCanFocus(false);
        mListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mListAdapter.refreshCarList(true);
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                mListAdapter.refreshCarList(false);
            }
        });

        mListView.setMode(PullToRefreshBase.Mode.BOTH);
        mListView.setShowIndicator(false);

        // TH TEMP
//        findViewById(R.id.sell).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), RegisterCarActivity.class);
//                startActivity(intent);
//                mFragmentActivity.overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
//            }
//        });

        mSell = (FloatingActionButton) findViewById(R.id.sell);
        mSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Application.get().getLoginToken() == null) {
                    Utility.showLoginPop();
                    return;
                }

                Intent intent = new Intent(getActivity(), RegisterCarActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
            }
        });

        mAsk = (FloatingActionButton) findViewById(R.id.ask);
        mAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://plus.kakao.com/home/mmxkcpwu"));
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
            }
        });

        mListView.setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollUp(AbsListView view) {
                mSell.show();
                mAsk.show();
            }

            @Override
            public void onScrollDown(AbsListView view) {
                mSell.hide();
                mAsk.hide();
            }

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mSelectedCarData = mListAdapter.getItem(position);
                Intent intent = new Intent(getActivity(), CarDetailActivity.class);
                intent.putExtra(Constant.DATA_CAR_DATA, mSelectedCarData);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
            }
        });




        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        mListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == ACTIVITY_REQUEST_CODE_FILTER_SET) {
            if(resultCode == Activity.RESULT_OK && data != null) {
                CarListAdapter.FilterInfo filterInfo = mListAdapter.getFilterInfo();

                filterInfo.setCarType(data.getIntExtra(FILTER_CAR_TYPE, -1));
                filterInfo.setHasSmileBuy(data.getBooleanExtra(FILTER_HAS_SMILEBUY, false));
                filterInfo.setPriceL(data.getIntExtra(FILTER_PRICE_L, -1));
                filterInfo.setPriceH(data.getIntExtra(FILTER_PRICE_H, -1));
                filterInfo.setMileageL(data.getIntExtra(FILTER_MILEAGE_L, -1));
                filterInfo.setMileageH(data.getIntExtra(FILTER_MILEAGE_H, -1));
                filterInfo.setArea(data.getIntExtra(FILTER_AREA, -1));

                updateFilter();
            }
        }
//        else if(requestCode == ACTIVITY_REQUEST_CODE_CAR_DETAIL || requestCode == ACTIVITY_REQUEST_CODE_COMMENT) {
//            if(resultCode == Activity.RESULT_OK && data != null && mSelectedCarData != null) {
//                CarData carData = data.getParcelableExtra(Constant.DATA_CAR_DATA);
//                mSelectedCarData.update(carData);
//                mListAdapter.notifyDataSetChanged();
//            }
//        }
    }

    private void updateFilter() {
        if(mListAdapter.getFilterInfo().isChanged()) {
            mListAdapter.refreshCarList(true);

            if(mListAdapter.getFilterInfo().isDefault()) {
                mFilter.setBackgroundResource(R.drawable.button_filter_off);
            } else {
                mFilter.setBackgroundResource(R.drawable.button_filter_on);
            }
        }
    }

    @Override
    public void onActivitySay(Bundle data) {

    }



}
