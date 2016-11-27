package com.autofactory.smilebuy.component;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by AirPhebe on 2015. 10. 27..
 */
public class ViewPagerForPhoto extends ViewPager {
    public ViewPagerForPhoto(Context context) {
        super(context);
    }

    public ViewPagerForPhoto(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        try {
            return super.onInterceptTouchEvent(ev);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return false;
        }
    }
}
