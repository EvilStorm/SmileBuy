package com.autofactory.smilebuy.component;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.View;

/**
 * Created by AirPhebe on 2015. 10. 29..
 */
public abstract class Fragment extends android.support.v4.app.Fragment {
    protected FragmentActivity mFragmentActivity;
    protected int mID;
    protected String mName;
    protected View mView = null;
    protected void init(FragmentActivity fragmentActivity, int id, String name) {
        mFragmentActivity = fragmentActivity;
        mID = id;
        mName = name;
    }

    public int getFragmentID() { return mID; }
    public String getFragmentName() { return mName; }
    public abstract void onActivitySay(Bundle data);

    @Override
    public void onResume() {
        super.onResume();
        if(mFragmentActivity != null) {
            mFragmentActivity.setCurrent(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mView = null;
    }

    public final View findViewById(int id) {
        if(mView == null)   return null;
        return mView.findViewById(id);
    }
}
