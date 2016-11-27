package com.autofactory.smilebuy.ui.login;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.server.LoginResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.main.MainActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Log;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

/**
 * Created by Phebe on 16. 1. 27..
 */
public class SplashActivity extends NormalActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_splash);

        // Init Facebook
        FacebookSdk.sdkInitialize(getApplicationContext());

        checkPermissions();
    }

    private void checkPermissions() {
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Log.d("Permission Granted");
                Application.get().initAfterGotPermissions();
                startSmileBuy();
            }

            @Override
            public void onPermissionDenied(ArrayList<String> arrayList) {
                for(int i=0; i<arrayList.size(); i++) {
                    Log.d("Permission Denied : " + arrayList.get(i));
                }
                finish();
            }
        };

        new TedPermission(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage(R.string.popup_message_permission_denied)
                .setPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE, Manifest.permission.RECEIVE_SMS, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    private void startSmileBuy() {
        boolean autoLogin = Application.get().getPreferenceBoolean(Constant.PREF_KEY_AUTO_LOGIN);
        if (autoLogin) {
            AccessToken mFaceToken = AccessToken.getCurrentAccessToken();
            Application.get().setFacebookToken(mFaceToken);
            if (mFaceToken != null) {
                // Auto Login
                ServerRequest.get().requestLogin(mFaceToken, new Response.Listener<LoginResult>() {
                    @Override
                    public void onResponse(LoginResult response) {
                        if (response.isSuccess()) {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                            finish();
                        } else {
                            moveToLoginActivity(true);
                        }
                    }
                }, true);
            } else {
                String email = Application.get().getPreferenceString(Constant.PREF_KEY_EMAIL);
                String pwd = Application.get().getPreferenceString(Constant.PREF_KEY_PWD);
                if (email != null && pwd != null) {
                    ServerRequest.get().requestLogin(email, pwd, new Response.Listener<LoginResult>() {
                        @Override
                        public void onResponse(LoginResult response) {
                            if (response.isSuccess()) {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            } else {
                                moveToLoginActivity(true);
                            }
                        }
                    }, true);
                }
            }
        } else {
            moveToLoginActivity(false);
        }
    }


    private void moveToLoginActivity(boolean immediately) {
        if(immediately) {
            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            finish();
        } else {
            Handler handler = new Handler();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                            finish();
                        }
                    });
                }
            };
            handler.postDelayed(runnable, 2000);
        }
    }
}
