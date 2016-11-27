package com.autofactory.smilebuy.component;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by AirPhebe on 2015. 10. 20..
 */
public class PageIndicateTextView extends TextView implements ViewPager.OnPageChangeListener {
    private ViewPager mViewPager;


    public PageIndicateTextView(Context context) {
        super(context);
    }

    public PageIndicateTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PageIndicateTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void Init(ViewPager viewPager) {
        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
        update(mViewPager.getCurrentItem());
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        update(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    private void update(int position) {
        setText(String.format("%d/%d", position+1, mViewPager.getAdapter().getCount()));
    }
}
