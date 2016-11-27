package com.autofactory.smilebuy.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.util.Utility;

public class FindPwResultActivity extends NormalActivity {
    private boolean mFromSetting = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        String email = i.getStringExtra("email");
        mFromSetting = i.getBooleanExtra(FindPwActivity.FROM_SETTING, false);

        if(mFromSetting) {
            setContentView(R.layout.act_find_pw_from_setting);
        } else {
            setContentView(R.layout.act_find_pw_result);
        }

        ((TextView) findViewById(R.id.successText)).setText(String.format(getResources().getString(R.string.content_success_find_pw), email));
        findViewById(R.id.back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

}

