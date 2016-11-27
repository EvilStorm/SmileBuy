package com.autofactory.smilebuy.ui.main.car.detail;

import android.app.Activity;
import android.os.Bundle;

import android.view.View;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.component.PageIndicateTextView;
import com.autofactory.smilebuy.component.ViewPagerForPhoto;
import com.autofactory.smilebuy.data.model.PictureData;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Log;

import java.util.List;

public class CarPictureDetailActivity extends NormalActivity {
    private List<PictureData> mPictures;
    private CarPictureSlideAdapter mPictureSlideAdapter;
    private ViewPagerForPhoto mPictureSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_car_picture_detail);
        Log.d("CarPictureDetailActivity");
        mPictures = getIntent().getParcelableArrayListExtra(Constant.DATA_PICTURE_LIST);

        mPictureSlider = (ViewPagerForPhoto) findViewById(R.id.pictureSlider);

        mPictureSlideAdapter = new CarPictureSlideAdapter(this, mPictureSlider, true);
        mPictureSlideAdapter.setPictures(mPictures);
        mPictureSlider.setAdapter(mPictureSlideAdapter);
        mPictureSlider.setCurrentItem(getIntent().getIntExtra(Constant.DATA_PICTURE_CURRENT_INDEX, 0));

        PageIndicateTextView indicateTextView = (PageIndicateTextView) findViewById(R.id.pictureIndicator);
        indicateTextView.Init(mPictureSlider);

        mPictureSlideAdapter.addOnPageChangeListener(indicateTextView);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_from_left, R.anim.anim_out_to_right);
    }
}
