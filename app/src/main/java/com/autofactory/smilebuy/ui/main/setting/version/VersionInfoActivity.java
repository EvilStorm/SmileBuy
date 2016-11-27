package com.autofactory.smilebuy.ui.main.setting.version;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.ui.main.setting.account.ChangePwdActivity;
import com.autofactory.smilebuy.ui.main.setting.account.WithdrawActivity;
import com.autofactory.smilebuy.ui.main.setting.terms.TermsSettingActivity;

/**
 * Created by Phebe on 16. 1. 25..
 */
public class VersionInfoActivity extends NormalActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_version_info);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ((TextView)findViewById(R.id.currentVersion)).setText(Application.get().getVersionAsString());

        TextView latestTitle = (TextView) findViewById(R.id.latestVersionTitle);
        TextView latestVersion = (TextView) findViewById(R.id.latestVersion);
        View latestArrow = findViewById(R.id.latestVersionArrow);
        ImageButton update = (ImageButton) findViewById(R.id.update);

        latestVersion.setText(Application.get().getLatestVersionAsString());
        if(Application.get().isThereUpdateExist()) {
            latestTitle.setTextColor(getResources().getColor(R.color.text_fb));
            latestVersion.setTextColor(getResources().getColor(R.color.text_fb));
            latestArrow.setVisibility(View.VISIBLE);
            update.setVisibility(View.VISIBLE);
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Application.get().DoUpdate();
                }
            });
        } else {
            latestTitle.setTextColor(getResources().getColor(R.color.text_66));
            latestVersion.setTextColor(getResources().getColor(R.color.text_a2));
            latestArrow.setVisibility(View.GONE);
            update.setVisibility(View.GONE);
        }


        findViewById(R.id.termsService).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VersionInfoActivity.this, TermsSettingActivity.class);
                i.putExtra(TermsSettingActivity.TERMS_TYPE, TermsSettingActivity.TERMS_TYPE_SERVICE);
                startActivity(i);
            }
        });

        findViewById(R.id.termsPrivate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(VersionInfoActivity.this, TermsSettingActivity.class);
                i.putExtra(TermsSettingActivity.TERMS_TYPE, TermsSettingActivity.TERMS_TYPE_PRIVATE);
                startActivity(i);
            }
        });
    }
}
