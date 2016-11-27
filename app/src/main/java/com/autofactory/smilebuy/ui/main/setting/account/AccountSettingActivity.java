package com.autofactory.smilebuy.ui.main.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.NormalActivity;

/**
 * Created by Phebe on 16. 1. 25..
 */
public class AccountSettingActivity extends NormalActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_account_setting);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        View changePwdFrame = findViewById(R.id.changePwdFrame);

        TextView accoutType = (TextView) findViewById(R.id.accountType);
        if(!Application.get().isLoginWithFacebook()) {
            accoutType.setText(""+Application.get().getUserData().getID());
            changePwdFrame.setVisibility(View.VISIBLE);
        } else {
            accoutType.setText(getString(R.string.text_account_type_facebook));
            changePwdFrame.setVisibility(View.GONE);
        }


        findViewById(R.id.changePwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettingActivity.this, ChangePwdActivity.class));
            }
        });

        findViewById(R.id.withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountSettingActivity.this, WithdrawActivity.class));
            }
        });
    }
}
