package com.autofactory.smilebuy.ui.main.car.list;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.component.RangeSeekBar;
import com.autofactory.smilebuy.util.Constant;

/**
 * Created by Phebe on 16. 1. 22..
 */
public class CarFilterActivity extends NormalActivity {
    private static final int SORT_ALL = 0;
    private static final int SORT_SMILEBUY = 1;
    private static final int SORT_ALLIANCE = 2;
    private static final int SORT_COMMISSION = 3;
    private static final int SORT_FRIEND = 4;
    private static final int SORT_COUNT = 5;

    private String [] mPriceUnit;
    private String [] mMileageUnit;

    private ImageButton [] mSortButton;
    private TextView [] mSortText;
    private int [] mSortResID;
    private int [] mSortNResID;
    private int mSortIndex = SORT_ALL;

    private RangeSeekBar<Integer> mPriceBar;
    private View mPriceMinBalloon;
    private TextView mPriceMin;
    private TextView mPriceMinUnit;
    private View mPriceMaxBalloon;
    private TextView mPriceMax;
    private TextView mPriceMaxUnit;
    private RangeSeekBar<Integer> mMileageBar;
    private View mMileageMinBalloon;
    private TextView mMileageMin;
    private TextView mMileageMinUnit;
    private View mMileageMaxBalloon;
    private TextView mMileageMax;
    private TextView mMileageMaxUnit;
    private Spinner mArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_car_filter);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mPriceUnit = new String[4];
        mMileageUnit = new String[2];
        mPriceUnit[0] = getString(R.string.balloon_car_filter_price_unit);
        mPriceUnit[1] = getString(R.string.balloon_car_filter_price_unit_10000);
        mPriceUnit[2] = getString(R.string.balloon_car_filter_price_unit_10000000);
        mPriceUnit[3] = getString(R.string.balloon_car_filter_price_unit_100000000);
        mMileageUnit[0] = getString(R.string.balloon_car_filter_mileage_unit);
        mMileageUnit[1] = getString(R.string.balloon_car_filter_mileage_unit_10000);

        mSortButton = new ImageButton[SORT_COUNT];
        mSortText = new TextView[SORT_COUNT];
        mSortButton[0] = (ImageButton) findViewById(R.id.sortButton1);
        mSortButton[1] = (ImageButton) findViewById(R.id.sortButton2);
        mSortButton[2] = (ImageButton) findViewById(R.id.sortButton3);
        mSortButton[3] = (ImageButton) findViewById(R.id.sortButton4);
        mSortButton[4] = (ImageButton) findViewById(R.id.sortButton5);
        mSortButton[4].setEnabled(false);

        mSortText[0] = (TextView) findViewById(R.id.sortText1);
        mSortText[1] = (TextView) findViewById(R.id.sortText2);
        mSortText[2] = (TextView) findViewById(R.id.sortText3);
        mSortText[3] = (TextView) findViewById(R.id.sortText4);
        mSortText[4] = (TextView) findViewById(R.id.sortText5);

        mSortResID = new int[SORT_COUNT];
        mSortNResID = new int[SORT_COUNT];
        mSortResID[0] = R.drawable.icon_sell_1_n;
        mSortResID[1] = R.drawable.icon_sell_2_n;
        mSortResID[2] = R.drawable.icon_sell_3_n;
        mSortResID[3] = R.drawable.icon_sell_4_n;
        mSortResID[4] = R.drawable.icon_sell_5_n;
        mSortNResID[0] = R.drawable.icon_sell_1;
        mSortNResID[1] = R.drawable.icon_sell_2;
        mSortNResID[2] = R.drawable.icon_sell_3;
        mSortNResID[3] = R.drawable.icon_sell_4;
        mSortNResID[4] = R.drawable.icon_sell_5;

        for(int i=0; i<SORT_COUNT; i++) {
            final int index = i;
            mSortButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectSort(index);
                }
            });
        }

        selectSort(mSortIndex);

        mPriceMinBalloon = findViewById(R.id.priceMinBalloon);
        mPriceMin = (TextView) findViewById(R.id.priceMin);
        mPriceMinUnit = (TextView) findViewById(R.id.priceMinUnit);

        mPriceMaxBalloon = findViewById(R.id.priceMaxBalloon);
        mPriceMax = (TextView) findViewById(R.id.priceMax);
        mPriceMaxUnit = (TextView) findViewById(R.id.priceMaxUnit);

        mMileageMinBalloon = findViewById(R.id.mileageMinBalloon);
        mMileageMin = (TextView) findViewById(R.id.mileageMin);
        mMileageMinUnit = (TextView) findViewById(R.id.mileageMinUnit);

        mMileageMaxBalloon = findViewById(R.id.mileageMaxBalloon);
        mMileageMax = (TextView) findViewById(R.id.mileageMax);
        mMileageMaxUnit = (TextView) findViewById(R.id.mileageMaxUnit);

        mPriceBar = (RangeSeekBar) findViewById(R.id.priceBar);
        mPriceBar.setNotifyWhileDragging(true);
        mPriceBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue, double minX, double maxX) {
                mPriceMinBalloon.setX((float) minX - mPriceMinBalloon.getWidth() / 2);
                mPriceMaxBalloon.setX((float) maxX - mPriceMaxBalloon.getWidth() / 2);


                setPrice(mPriceMin, mPriceMinUnit, minValue.intValue());
                setPrice(mPriceMax, mPriceMaxUnit, maxValue.intValue());
            }
        });

        mPriceBar.setSelectedMinValue(mPriceBar.getAbsoluteMinValue());
        mPriceBar.setSelectedMaxValue(mPriceBar.getAbsoluteMaxValue());


        mMileageBar = (RangeSeekBar) findViewById(R.id.mileageBar);
        mMileageBar.setNotifyWhileDragging(true);
        mMileageBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue, double minX, double maxX) {
                mMileageMinBalloon.setX((float) minX - mMileageMinBalloon.getWidth() / 2);
                mMileageMaxBalloon.setX((float) maxX - mMileageMaxBalloon.getWidth() / 2);

                setMileage(mMileageMin, mMileageMinUnit, minValue.intValue());
                setMileage(mMileageMax, mMileageMaxUnit, maxValue.intValue());
            }
        });

        mMileageBar.setSelectedMinValue(mMileageBar.getAbsoluteMinValue());
        mMileageBar.setSelectedMaxValue(mMileageBar.getAbsoluteMaxValue());


        mArea = (Spinner) findViewById(R.id.area);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(this, R.layout.item_spinner, getResources().getStringArray(R.array.area));
        mArea.setAdapter(spinnerAdapter);

        findViewById(R.id.reset).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSortButton[0].performClick();

                mPriceBar.setSelectedMinValue(mPriceBar.getAbsoluteMinValue());
                mPriceBar.setSelectedMaxValue(mPriceBar.getAbsoluteMaxValue());

                mMileageBar.setSelectedMinValue(mMileageBar.getAbsoluteMinValue());
                mMileageBar.setSelectedMaxValue(mMileageBar.getAbsoluteMaxValue());

                mArea.setSelection(0);

                apply();
            }
        });

        findViewById(R.id.apply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                apply();
            }
        });


        init(getIntent());
    }
    private void init(Intent data) {
        if(data != null) {
            int carType = data.getIntExtra(CarListFragment.FILTER_CAR_TYPE, -1);
            boolean hasSmileBuy = data.getBooleanExtra(CarListFragment.FILTER_HAS_SMILEBUY, false);
            int priceL = data.getIntExtra(CarListFragment.FILTER_PRICE_L, -1);
            int priceH = data.getIntExtra(CarListFragment.FILTER_PRICE_H, -1);
            int mileageL = data.getIntExtra(CarListFragment.FILTER_MILEAGE_L, -1);
            int mileageH = data.getIntExtra(CarListFragment.FILTER_MILEAGE_H, -1);
            int area = data.getIntExtra(CarListFragment.FILTER_AREA, -1);

            if(hasSmileBuy) {
                selectSort(SORT_SMILEBUY);
            } else {
                if(carType == Constant.CAR_TYPE_ALL) {
                    selectSort(SORT_ALL);
                } else if(carType == Constant.CAR_TYPE_ALLIANCE) {
                    selectSort(SORT_ALLIANCE);
                } else if(carType == Constant.CAR_TYPE_COMMISSION) {
                    selectSort(SORT_COMMISSION);
                } else {
                    selectSort(SORT_ALL);
                }
            }

            mPriceBar.setSelectedMinValue(getValueFromPrice(priceL, true));
            mPriceBar.setSelectedMaxValue(getValueFromPrice(priceH, false));
            mMileageBar.setSelectedMinValue(getValueFromMileage(mileageL, true));
            mMileageBar.setSelectedMaxValue(getValueFromMileage(mileageH, false));
            mArea.setSelection(area <= 0 ? 0 : area);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    private void setPrice(TextView price, TextView unit, int value) {
        if(value <= 0) {
            value = 0;
            unit.setText(mPriceUnit[0]);
        } else if(value < 20) {
            value = value * 100;
            unit.setText(mPriceUnit[1]);
        } else if(value < 28){
            value = value - 18;
            unit.setText(mPriceUnit[2]);
        } else {
            value = 1;
            unit.setText(mPriceUnit[3]);
        }
        price.setText(String.format("%,d", value));
    }
    private int getPrice(int value, boolean low) {
        if(value <= 0) {
            return low ? -1 : 0;
        } else if(value < 20) {
            return value * 100;
        } else if(value < 28){
            return (value - 18) * 1000;
        } else {
            return low ? 10000 : -1;
        }
    }
    private int getValueFromPrice(int price, boolean low) {
        if(price == -1) {
            return low ? mPriceBar.getAbsoluteMinValue() : mPriceBar.getAbsoluteMaxValue();
        } else if(price == 0) {
            return low ? mPriceBar.getAbsoluteMinValue() : mPriceBar.getAbsoluteMinValue();
        } else if(price < 2000) {
            return price / 100;
        } else if(price < 10000) {
            return (price / 1000) + 18;
        } else {
            return mPriceBar.getAbsoluteMaxValue();
        }
    }
    private void setMileage(TextView mileage, TextView unit, int value) {
        if(value <= 0) {
            value = 0;
            unit.setText(mMileageUnit[0]);
        } else {
            unit.setText(mMileageUnit[1]);
        }
        mileage.setText(String.format("%,d", value));
    }
    private int getMileage(int value, boolean low) {
        if(value <= 0) {
            return low ? -1 : 0;
        } else if(value >= mMileageBar.getAbsoluteMaxValue()){
            return low ? value : -1;
        } else {
            return value * 10000;
        }
    }
    private int getValueFromMileage(int mileage, boolean low) {
        if(mileage <= 0) {
            return low ? mMileageBar.getAbsoluteMinValue() : mMileageBar.getAbsoluteMaxValue();
        } else {
            return mileage / 10000;
        }
    }

    private void selectSort(int index) {
        mSortButton[mSortIndex].setImageResource(mSortResID[mSortIndex]);
        mSortText[mSortIndex].setTextColor(getResources().getColor(R.color.text_a2));

        mSortIndex = index;
        mSortButton[mSortIndex].setImageResource(mSortNResID[mSortIndex]);
        mSortText[mSortIndex].setTextColor(getResources().getColor(R.color.text_66));
    }

    private void apply() {
        Intent data = new Intent();
        if(mSortIndex == SORT_ALL) {
            data.putExtra(CarListFragment.FILTER_CAR_TYPE, Constant.CAR_TYPE_ALL);
            data.putExtra(CarListFragment.FILTER_HAS_SMILEBUY, false);
        } else if(mSortIndex == SORT_SMILEBUY) {
            data.putExtra(CarListFragment.FILTER_HAS_SMILEBUY, true);
            data.putExtra(CarListFragment.FILTER_CAR_TYPE, Constant.CAR_TYPE_ALL);
        } else if(mSortIndex == SORT_ALLIANCE) {
            data.putExtra(CarListFragment.FILTER_CAR_TYPE, Constant.CAR_TYPE_ALLIANCE);
            data.putExtra(CarListFragment.FILTER_HAS_SMILEBUY, false);
        } else if(mSortIndex == SORT_COMMISSION) {
            data.putExtra(CarListFragment.FILTER_CAR_TYPE, Constant.CAR_TYPE_COMMISSION);
            data.putExtra(CarListFragment.FILTER_HAS_SMILEBUY, false);
        } else if(mSortIndex == SORT_FRIEND) {
            // TODO WITH FRIEND
            data.putExtra(CarListFragment.FILTER_CAR_TYPE, Constant.CAR_TYPE_ALL);
            data.putExtra(CarListFragment.FILTER_HAS_SMILEBUY, false);
        }

        data.putExtra(CarListFragment.FILTER_PRICE_L, getPrice(mPriceBar.getSelectedMinValue(), true));
        data.putExtra(CarListFragment.FILTER_PRICE_H, getPrice(mPriceBar.getSelectedMaxValue(), false));
        data.putExtra(CarListFragment.FILTER_MILEAGE_L, getMileage(mMileageBar.getSelectedMinValue(), true));
        data.putExtra(CarListFragment.FILTER_MILEAGE_H, getMileage(mMileageBar.getSelectedMaxValue(), false));
        data.putExtra(CarListFragment.FILTER_AREA, mArea.getSelectedItemPosition());

        setResult(RESULT_OK, data);

        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_bottom);
    }
}
