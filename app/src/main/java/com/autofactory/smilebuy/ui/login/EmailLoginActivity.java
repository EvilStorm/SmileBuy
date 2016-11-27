package com.autofactory.smilebuy.ui.login;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.LoginResult;
import com.autofactory.smilebuy.ui.main.MainActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class EmailLoginActivity extends NormalActivity {
    private EditText mEmail;
    private EditText mPassword;
    private Button mLogin;
    private CheckBox mAutologin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_email_login);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mAutologin = (CheckBox) findViewById(R.id.agreeAutologin);

        mLogin = (Button) findViewById(R.id.login);
        mLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                requestLogin();
            }
        });


        TextView findPw = (TextView) findViewById(R.id.findPw);
        findPw.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmailLoginActivity.this, FindPwActivity.class);
                i.putExtra(FindPwActivity.FROM_SETTING, false);
                startActivity(i);
            }
        });

        mAutologin.setChecked(true);
    }

    private void requestLogin() {
        mEmail.setError(null);
        mPassword.setError(null);

        View problemView = null;
        if(TextUtils.isEmpty(mEmail.getText())) {
            mEmail.setError(getString(R.string.error_field_required));
            problemView = mEmail;
        } else if(TextUtils.isEmpty(mPassword.getText())) {
            mPassword.setError(getString(R.string.error_field_required));
            problemView = mPassword;
        } else if(!Utility.isEmailValid(mEmail.getText())) {
            mEmail.setError(getString(R.string.error_invalid_email));
            problemView = mEmail;
        }

        if(problemView != null) {
            problemView.requestFocus();
        } else {
            ServerRequest.get().requestLogin(mEmail.getText().toString(), mPassword.getText().toString(), new Response.Listener<LoginResult>() {
                @Override
                public void onResponse(LoginResult response) {
                    if(response.isSuccess()) {
                        if(mAutologin.isChecked()) {
                            Application.get().setPreferenceBoolean(Constant.PREF_KEY_AUTO_LOGIN, true);
                            Application.get().setPreferenceString(Constant.PREF_KEY_EMAIL, mEmail.getText().toString());
                            Application.get().setPreferenceString(Constant.PREF_KEY_PWD, mPassword.getText().toString());
                        } else {
                            Application.get().setPreferenceBoolean(Constant.PREF_KEY_AUTO_LOGIN, false);
                            Application.get().setPreferenceString(Constant.PREF_KEY_EMAIL, null);
                            Application.get().setPreferenceString(Constant.PREF_KEY_PWD, null);
                        }
                        startActivity(MainActivity.class);
                    }
                }
            }, false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUEST_CODE_FINISH_ACTIVITY) {
            if(resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }
    private void startActivity(Class newActivity) {
        if(newActivity == MainActivity.class) {
            startActivity(new Intent(this, newActivity));
            setResult(RESULT_OK);
            finish();
        } else {
            startActivityForResult(new Intent(this, newActivity), Constant.REQUEST_CODE_FINISH_ACTIVITY);
        }
    }
}

