package com.autofactory.smilebuy.component;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;


import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;
//import com.tsengvn.typekit.TypekitContextWrapper;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by AirPhebe on 2015. 10. 29..
 */
public abstract class FragmentActivity extends android.support.v4.app.FragmentActivity {
    public static final String KEY_SAY_ID = "KEY_SAY_ID";
    public static final String KEY_SAY_DATA = "KEY_SAY_DATA";

    public static final int SAY_ID_CAN_GO_NEXT = 100;
    public static final int SAY_ID_ON_NEXT = 101;

    protected FragmentManager mManager;
    protected Fragment mCurrent = null;

    public void setCurrent(Fragment fragment) {
        mCurrent = fragment;
    }
    //public Fragment getCurrent() { return mCurrent; }

    @Override
    protected void attachBaseContext(Context newBase) {
        //super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mManager = getSupportFragmentManager();
        //mCurrent = getCurrentFragment();
        mCurrent = null;
    }

//    protected Fragment getCurrentFragment() {
//        Log.d(Constant.TAG_LOG_TEST, "getCurrent 1");
//        if(mManager.getBackStackEntryCount() <= 0)  return null;
//        Log.d(Constant.TAG_LOG_TEST, "getCurrent 2");
//        FragmentManager.BackStackEntry backStackEntry = mManager.getBackStackEntryAt(mManager.getBackStackEntryCount() - 1);
//        Log.d(Constant.TAG_LOG_TEST, "getCurrent 3");
//        if(backStackEntry == null)  return null;
//        Log.d(Constant.TAG_LOG_TEST, "getCurrent 4");
//        Fragment f = (Fragment) mManager.findFragmentByTag(backStackEntry.getName());
//        Log.d(Constant.TAG_LOG_TEST, "getCurrent 5");
//        return f;
//    }


    @Override
    protected void onResume() {
        super.onResume();
        Application.get().setGAScreen(getClass().getSimpleName());
    }

    protected void changeFragment(int fragmentID) {
        //
        //mCurrent = getCurrentFragment();
    }

    @Override
    public void onBackPressed() {
        if (mManager.getBackStackEntryCount() <= 1) {
            if (getClass().getSimpleName().contains("MainActivity")) {
                Utility.showPopupYesOrNo(this, getString(R.string.popup_message_finish_confirm), new PopupBase.OnClickListener() {
                    @Override
                    public void onClick() {
                        finish();
                    }
                });
            } else {
                finish();
            }
            return;
        }

        //Log.d(Constant.TAG_LOG_TEST, "befor back : " + mCurrent.getFragmentName());
        super.onBackPressed();
        //mCurrent = getCurrentFragment();
        //Log.d(Constant.TAG_LOG_TEST, "after back : " + mCurrent.getFragmentName());
    }

    public abstract void onFragmentSay(Bundle data);
}
