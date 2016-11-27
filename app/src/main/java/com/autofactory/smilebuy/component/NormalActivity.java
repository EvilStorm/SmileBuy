package com.autofactory.smilebuy.component;

import android.content.Context;
import android.os.Bundle;

//import com.tsengvn.typekit.TypekitContextWrapper;

import com.autofactory.smilebuy.application.Application;
import com.google.android.gms.analytics.Tracker;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by Phebe on 16. 1. 8..
 */
public class NormalActivity extends android.app.Activity {
    @Override
    protected void attachBaseContext(Context newBase) {
        //super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d("TH", "ACTIVITY : " + getClass().getName() + " , " + getClass().getSimpleName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Application.get().setGAScreen(getClass().getSimpleName());
    }

    //    @Override
//    public void finish() {
//        super.finish();
//        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_right);
//    }
//
//    @Override
//    public void startActivity(Intent intent) {
//        super.startActivity(intent);
//        overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
//    }
}
