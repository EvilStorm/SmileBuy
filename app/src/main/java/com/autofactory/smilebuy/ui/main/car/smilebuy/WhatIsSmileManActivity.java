package com.autofactory.smilebuy.ui.main.car.smilebuy;

import android.os.Bundle;
import android.view.View;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;

/**
 * Created by Phebe on 16. 1. 27..
 */
public class WhatIsSmileManActivity extends NormalActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_what_is_smileman);

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
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_bottom);
    }
}
