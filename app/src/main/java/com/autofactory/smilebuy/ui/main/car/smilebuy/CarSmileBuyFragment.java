package com.autofactory.smilebuy.ui.main.car.smilebuy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.component.PageIndicateTextView;
import com.autofactory.smilebuy.data.model.CarDataSmileBuy;
import com.autofactory.smilebuy.data.model.InterviewData;
import com.autofactory.smilebuy.ui.main.car.detail.CarPictureSlideAdapter;


public class CarSmileBuyFragment extends Fragment {
    private CarDataSmileBuy mCarData = null;
    private boolean mIsPreview = false;

    private CarPictureSlideAdapter mPictureSlideAdapter;
    private ViewPager mPictureSlider;

    public CarSmileBuyFragment() {
        // Required empty public constructor
    }

    public static CarSmileBuyFragment newInstance(FragmentActivity fragmentActivity, CarDataSmileBuy carData, boolean isPreview) {
        CarSmileBuyFragment fragment = new CarSmileBuyFragment();
        fragment.init(fragmentActivity, 0, fragmentActivity.getString(R.string.title_activity_smile_buy));
        fragment.mCarData = carData;
        fragment.mIsPreview = isPreview;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fra_car_smile_buy, container, false);

        mPictureSlider = (ViewPager) findViewById(R.id.pictureSlider);

        mPictureSlideAdapter = new CarPictureSlideAdapter(mFragmentActivity, mPictureSlider, false);
        mPictureSlider.setAdapter(mPictureSlideAdapter);

        PageIndicateTextView indicateTextView = (PageIndicateTextView) findViewById(R.id.pictureIndicator);
        indicateTextView.Init(mPictureSlider);

        mPictureSlideAdapter.addOnPageChangeListener(indicateTextView);
        mPictureSlideAdapter.setPictures(mCarData.getPictures());

        ((TextView) findViewById(R.id.name)).setText(mCarData.getName());
        ((TextView) findViewById(R.id.age)).setText(mCarData.getAgeAsString(getResources()));
        ((TextView) findViewById(R.id.mileage)).setText(mCarData.getMileageAsString(getResources()));
        ((TextView) findViewById(R.id.fuelType)).setText(mCarData.getFuelTypeAsString(getResources()));
        ((TextView) findViewById(R.id.transmissionType)).setText(mCarData.getTransmissionTypeAsString(getResources()));
        ((TextView) findViewById(R.id.engine)).setText(mCarData.getEngineAsString(getResources()));
        ((TextView) findViewById(R.id.carNum)).setText(mCarData.getCarNum());

        ((ImageView) findViewById(R.id.sunroof)).setImageResource(mCarData.isSunroof() ? R.drawable.checkbox_n : R.drawable.checkbox);
        ((ImageView) findViewById(R.id.smartKey)).setImageResource(mCarData.isSmartKey() ? R.drawable.checkbox_n : R.drawable.checkbox);
        ((ImageView) findViewById(R.id.blackBox)).setImageResource(mCarData.isBlackBox() ? R.drawable.checkbox_n : R.drawable.checkbox);
        ((ImageView) findViewById(R.id.rearCamera)).setImageResource(mCarData.isRearCamera() ? R.drawable.checkbox_n : R.drawable.checkbox);
        ((ImageView) findViewById(R.id.navigation)).setImageResource(mCarData.isNavigation() ? R.drawable.checkbox_n : R.drawable.checkbox);
        ((ImageView) findViewById(R.id.caffein)).setImageResource(mCarData.isCaffein() ? R.drawable.checkbox_n : R.drawable.checkbox);

        ((TextView) findViewById(R.id.predictedPrice)).setText(mCarData.getPredictedPrice());
        ((TextView) findViewById(R.id.accident)).setText(mCarData.getAccident());
        ((TextView) findViewById(R.id.managerComment)).setText(mCarData.getManagerComment());

        ((TextView) findViewById(R.id.smileManInfo)).setText(String.format(getString(R.string.text_sell_info_manager_info), mCarData.getManager().getNickName(), mCarData.getManager().getMobileNum()));


        Button showCaffein = (Button) findViewById(R.id.showCaffein);
        if (mCarData.isCaffein()) {
            showCaffein.setVisibility(View.VISIBLE);
            showCaffein.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(getActivity(), CaffeinResultActivity.class);
                    i.putExtra(CaffeinResultActivity.CAFFEIN_URL, mCarData.getCaffeinDataURL());
                    getActivity().startActivity(i);
                    getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
                }
            });
        } else {
            showCaffein.setVisibility(View.GONE);
        }

        if (mIsPreview) {
            showCaffein.setEnabled(false);
        }


        showCaffein.setVisibility(View.GONE);

        return mView;
    }


    @Override
    public void onActivitySay(Bundle data) {

    }
}
