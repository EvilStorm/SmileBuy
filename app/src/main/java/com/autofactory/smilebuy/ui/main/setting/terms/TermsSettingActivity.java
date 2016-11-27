package com.autofactory.smilebuy.ui.main.setting.terms;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;

/**
 * Created by Phebe on 16. 1. 25..
 */
public class TermsSettingActivity extends NormalActivity {
    public static final String TERMS_TYPE = "TERMS_TYPE";
    public static final int TERMS_TYPE_SERVICE = 0;
    public static final int TERMS_TYPE_PRIVATE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_terms_setting);

        TextView title = (TextView) findViewById(R.id.title);
        TextView content = (TextView) findViewById(R.id.content);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        int type = getIntent().getIntExtra(TERMS_TYPE, TERMS_TYPE_SERVICE);
        if(type == TERMS_TYPE_SERVICE) {
            title.setText(getString(R.string.subtitle_service));
            content.setText(getString(R.string.terms_service));
        } else {
            title.setText(getString(R.string.subtitle_private));
            content.setText(getString(R.string.terms_private_info));
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_right);
    }
}
