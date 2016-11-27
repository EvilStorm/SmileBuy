package com.autofactory.smilebuy.ui.main.setting.account;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.ui.login.FindPwActivity;

/**
 * Created by Phebe on 16. 1. 25..
 */
public class ChangePwdActivity extends NormalActivity {
    private EditText mPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_change_pwd);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        TextView accoutType = (TextView) findViewById(R.id.accountType);
        if(!Application.get().isLoginWithFacebook()) {
            accoutType.setText(""+Application.get().getUserData().getID());
        } else {
            accoutType.setText(getString(R.string.text_account_type_facebook));
        }

        mPassword = (EditText) findViewById(R.id.password);

        findViewById(R.id.findPw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ChangePwdActivity.this, FindPwActivity.class);
                i.putExtra(FindPwActivity.FROM_SETTING, true);
                startActivity(i);
            }
        });

        findViewById(R.id.next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestNext();
            }
        });

    }

    private void requestNext() {
        mPassword.setError(null);

        View problemView = null;
        if(TextUtils.isEmpty(mPassword.getText())) {
            mPassword.setError(getString(R.string.error_field_required));
            problemView = mPassword;
        }

        if(problemView != null) {
            problemView.requestFocus();
        } else {
            ServerRequest.get().requestChangePwdLogin(mPassword.getText().toString(), new Response.Listener<ServerResult>() {
                @Override
                public void onResponse(ServerResult response) {
                    startActivity(new Intent(ChangePwdActivity.this, NewPwdActivity.class));
                    finish();
                }
            });
        }
    }
}
