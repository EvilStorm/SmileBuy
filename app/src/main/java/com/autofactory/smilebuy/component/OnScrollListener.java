package com.autofactory.smilebuy.component;

import android.widget.AbsListView;

import com.autofactory.smilebuy.util.Utility;

/**
 * Created by AirPhebe on 2015. 10. 18..
 */
public abstract class OnScrollListener implements AbsListView.OnScrollListener {
    private int mPrevScrollY = 0;

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int scrollY = Utility.getScrollY(view);
        int diff = mPrevScrollY - scrollY;
        if(diff > 0) {
            onScrollUp(view);
        } else if(diff < 0) {
            onScrollDown(view);
        }

        mPrevScrollY = scrollY;
    }

    public abstract void onScrollUp(AbsListView view);
    public abstract void onScrollDown(AbsListView view);
}
