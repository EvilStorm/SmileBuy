package com.autofactory.smilebuy.ui.main.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.ui.main.setting.account.AccountSettingActivity;
import com.autofactory.smilebuy.ui.main.setting.ask.AskActivity;
import com.autofactory.smilebuy.ui.main.setting.notice.FAQActivity;
import com.autofactory.smilebuy.ui.main.setting.version.VersionInfoActivity;

/**
 * Created by Phebe on 16. 1. 25..
 */
public class SettingSettingActivity extends NormalActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_setting_setting);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingSettingActivity.this, AccountSettingActivity.class));
            }
        });

        findViewById(R.id.ask).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(SettingSettingActivity.this, AskActivity.class));
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://me2.do/54ceWxQL")));
            }
        });

        findViewById(R.id.faq).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingSettingActivity.this, FAQActivity.class));
            }
        });

        findViewById(R.id.version).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingSettingActivity.this, VersionInfoActivity.class));
            }
        });

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Application.get().logout();
            }
        });


        if(Application.get().isThereUpdateExist()) {
            findViewById(R.id.versionNew).setVisibility(View.VISIBLE);
        } else {
            findViewById(R.id.versionNew).setVisibility(View.GONE);
        }
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_bottom);
    }
}
