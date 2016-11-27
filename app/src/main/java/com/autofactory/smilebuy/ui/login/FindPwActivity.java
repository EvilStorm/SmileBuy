package com.autofactory.smilebuy.ui.login;

import android.annotation.TargetApi;
import com.autofactory.smilebuy.component.NormalActivity;

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
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.util.Utility;

import static android.Manifest.permission.READ_CONTACTS;

public class FindPwActivity extends NormalActivity {
    public static final String FROM_SETTING = "FROM_SETTING";
    private EditText mName;
    private EditText mEmail;
    private Button mConfirm;

    private boolean mFromSetting = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFromSetting = getIntent().getBooleanExtra(FROM_SETTING, false);

        if(mFromSetting) {
            setContentView(R.layout.act_find_pw_from_setting);
        } else {
            setContentView(R.layout.act_find_pw);
        }

        findViewById(R.id.back).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        mName = (EditText) findViewById(R.id.name);
        mEmail = (EditText) findViewById(R.id.email);


        mConfirm = (Button) findViewById(R.id.confirm);
        mConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                requestFindPw();
            }
        });
    }


    private void requestFindPw() {
        mName.setError(null);
        mEmail.setError(null);

        View problemView = null;
        if(TextUtils.isEmpty(mName.getText())) {
            mName.setError(getString(R.string.error_field_required));
            problemView = mName;
        } else if(TextUtils.isEmpty(mEmail.getText())) {
            mEmail.setError(getString(R.string.error_field_required));
            problemView = mEmail;
        } else if(!Utility.isEmailValid(mEmail.getText())) {
            mEmail.setError(getString(R.string.error_invalid_email));
            problemView = mEmail;
        }

        if(problemView != null) {
            problemView.requestFocus();
        } else {
            ServerRequest.get().requestFindPW(mEmail.getText().toString(), mName.getText().toString(), new Response.Listener<ServerResult>() {
                @Override
                public void onResponse(ServerResult response) {
                    Intent i = new Intent(FindPwActivity.this, FindPwResultActivity.class);
                    i.putExtra("email", mEmail.getText().toString());
                    i.putExtra(FROM_SETTING, mFromSetting);
                    startActivity(i);
                    finish();
                }
            });
        }
    }
}

