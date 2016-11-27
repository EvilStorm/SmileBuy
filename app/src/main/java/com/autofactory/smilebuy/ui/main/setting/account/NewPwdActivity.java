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
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;

/**
 * Created by Phebe on 16. 1. 25..
 */
public class NewPwdActivity extends NormalActivity {
    private EditText mPassword;
    private EditText mPasswordReEnter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_new_pwd);

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
        mPasswordReEnter = (EditText) findViewById(R.id.passwordReenter);
        findViewById(R.id.done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestChangePwd();
            }
        });

    }

    private void requestChangePwd() {
        mPassword.setError(null);
        mPasswordReEnter.setError(null);

        View problemView = null;
        if(TextUtils.isEmpty(mPassword.getText())) {
            mPassword.setError(getString(R.string.error_field_required));
            problemView = mPassword;
        } else if(TextUtils.isEmpty(mPasswordReEnter.getText())) {
            mPasswordReEnter.setError(getString(R.string.error_field_required));
            problemView = mPasswordReEnter;
        } else if((mPassword.getText().length() < 6 || mPassword.getText().length() > 20)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            problemView = mPassword;
        } else if(!mPassword.getText().toString().equals(mPasswordReEnter.getText().toString())) {
            mPasswordReEnter.setError(getString(R.string.error_incorrect_password));
            problemView = mPasswordReEnter;
        }

        if(problemView != null) {
            problemView.requestFocus();
        } else {
            ServerRequest.get().requestChangePwdSave(mPassword.getText().toString(), new Response.Listener<ServerResult>() {
                @Override
                public void onResponse(ServerResult response) {
                    Utility.showPopupOk(NewPwdActivity.this, getString(R.string.popup_message_change_pwd_success), new PopupBase.OnClickListener() {
                        @Override
                        public void onClick() {
                            Application.get().logout();
                        }
                    });
                }
            });
        }
    }
}
