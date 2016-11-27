package com.autofactory.smilebuy.component;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 29..
 */
public abstract class PagerAdapterWithListeners extends PagerAdapter {
    protected Activity mActivity;
    protected Context mContext;
    protected LayoutInflater mLayoutInflater;
    protected ViewPager mViewPager;
    protected List<ViewPager.OnPageChangeListener> mPageChangeListeners = new ArrayList<>();

    public PagerAdapterWithListeners(Activity activity, ViewPager viewPager) {
        mActivity = activity;
        mContext = activity;
        mLayoutInflater = LayoutInflater.from(mContext);
        mViewPager = viewPager;
        mPageChangeListeners.clear();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

    public void addOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mPageChangeListeners.add(onPageChangeListener);
    }
    public void removeOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        mPageChangeListeners.remove(onPageChangeListener);
    }
    public void clearOnPageChageListeners() {
        mPageChangeListeners.clear();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        for(int i=0; i<mPageChangeListeners.size(); i++) {
            mPageChangeListeners.get(i).onPageSelected(mViewPager.getCurrentItem());
        }
    }
}
