package com.autofactory.smilebuy.ui.login;

import com.autofactory.smilebuy.component.NormalActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.LoginResult;
import com.autofactory.smilebuy.ui.main.MainActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.ContactData;
import com.autofactory.smilebuy.util.Utility;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;
import java.util.Random;

public class LoginActivity extends NormalActivity {

    private CallbackManager mFacebookCallback;
    private AccessToken mFaceToken = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.act_login);
        setRandomBG();

        mFacebookCallback = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(mFacebookCallback, new FacebookCallback<com.facebook.login.LoginResult>() {
            @Override
            public void onSuccess(com.facebook.login.LoginResult loginResult) {
                mFaceToken = loginResult.getAccessToken();
                Profile.fetchProfileForCurrentAccessToken();
                //Toast.makeText(LoginActivity.this, Profile.getCurrentProfile().getName(), Toast.LENGTH_LONG).show();
                Application.get().setFacebookToken(mFaceToken);
                if (mFaceToken != null) {    // Login
                    ServerRequest.get().requestLogin(mFaceToken, new Response.Listener<LoginResult>() {
                        @Override
                        public void onResponse(LoginResult response) {
                            if (response.isSuccess()) {
                                startActivity(MainActivity.class);
                            } else if (Constant.SERVER_ERROR_TYPE_NO_USER.equalsIgnoreCase(response.getErrorType())) {
                                startActivity(TermsActivity.class);
                            }
                        }
                    }, false);
                } else {                    // Logout
//                    Intent i = new Intent(LoginActivity.this, LoginActivity.class);
//                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                    startActivity(i);
                }
            }

            @Override
            public void onCancel() {
                // Maybe do nothing?
            }

            @Override
            public void onError(FacebookException error) {
                Utility.showPopupOk(LoginActivity.this, String.format(getString(R.string.popup_message_facebook_error), error.getLocalizedMessage()));
            }
        });

        findViewById(R.id.facebookLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mFaceToken == null) {
                    LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Arrays.asList("public_profile", "email", "user_friends"));
                } else {
                    ServerRequest.get().requestLogin(mFaceToken, new Response.Listener<LoginResult>() {
                        @Override
                        public void onResponse(LoginResult response) {
                            if (response.isSuccess()) {
                                startActivity(MainActivity.class);
                            } else if (Constant.SERVER_ERROR_TYPE_NO_USER.equalsIgnoreCase(response.getErrorType())) {
                                startActivity(TermsActivity.class);
                            }
                        }
                    }, false);
                }
            }
        });

//        LoginButton faceLogin = (LoginButton) findViewById(R.id.facebookLogin);
//        faceLogin.setReadPermissions(Arrays.asList("public_profile", "email", "user_friends"));
//        faceLogin.registerCallback(mFacebookCallback, new FacebookCallback<com.facebook.login.LoginResult>() {
//            @Override
//            public void onSuccess(com.facebook.login.LoginResult loginResult) {
//                mFaceToken = loginResult.getAccessToken();
//                Profile.fetchProfileForCurrentAccessToken();
//                //Toast.makeText(LoginActivity.this, Profile.getCurrentProfile().getName(), Toast.LENGTH_LONG).show();
//                Application.get().setFacebookToken(mFaceToken);
//                if(mFaceToken != null) {    // Login
//                    ServerRequest.get().requestLogin(mFaceToken.getToken(), new Response.Listener<LoginResult>() {
//                        @Override
//                        public void onResponse(LoginResult response) {
//                            if(response.isSuccess()) {
//                                startActivity(MainActivity.class);
//                            } else {
//                                startActivity(TermsActivity.class);
//                            }
//                        }
//                    });
//                } else {                    // Logout
////                    Intent i = new Intent(LoginActivity.this, LoginActivity.class);
////                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
////                    startActivity(i);
//                }
//            }
//
//            @Override
//            public void onCancel() {
//                // Maybe do nothing?
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Utility.showPopupOk(LoginActivity.this, error.getLocalizedMessage());
//            }
//        });

        /**
         * AutoLogin Is Moved To SplashActivity
         */
//        boolean autoLogin = Application.get().getPreferenceBoolean(Constant.PREF_KEY_AUTO_LOGIN);
//        if (autoLogin) {
//            mFaceToken = AccessToken.getCurrentAccessToken();
//            Application.get().setFacebookToken(mFaceToken);
//            if (mFaceToken != null) {
//                // Auto Login
//                ServerRequest.get().requestLogin(mFaceToken, new Response.Listener<LoginResult>() {
//                    @Override
//                    public void onResponse(LoginResult response) {
//                        if (response.isSuccess()) {
//                            startActivity(MainActivity.class);
//                        } else if (Constant.SERVER_ERROR_TYPE_NO_USER.equalsIgnoreCase(response.getErrorType())) {
//                            startActivity(TermsActivity.class);
//                        }
//                    }
//                });
//            } else {
//                String email = Application.get().getPreferenceString(Constant.PREF_KEY_EMAIL);
//                String pwd = Application.get().getPreferenceString(Constant.PREF_KEY_PWD);
//                if (email != null && pwd != null) {
//                    ServerRequest.get().requestLogin(email, pwd, new Response.Listener<LoginResult>() {
//                        @Override
//                        public void onResponse(LoginResult response) {
//                            if (response.isSuccess()) {
//                                startActivity(MainActivity.class);
//                            }
//                        }
//                    });
//                }
//            }
//        }


        // Email Login
        Button emailLogin = (Button) findViewById(R.id.login);
        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(EmailLoginActivity.class);
            }
        });

        // Register
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mFaceToken = null;
//                AccessToken.setCurrentAccessToken(mFaceToken);
//                Application.get().setFacebookToken(mFaceToken);

                startActivity(TermsActivity.class);
            }
        });

        //ContactData.getContactDataList(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        AppEventsLogger.deactivateApp(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mFacebookCallback.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUEST_CODE_FINISH_ACTIVITY) {
            if (resultCode == RESULT_OK) {
                setResult(RESULT_OK);
                finish();
            }
        }
    }

    private void startActivity(Class newActivity) {
        if (newActivity == MainActivity.class) {
            startActivity(new Intent(this, newActivity));
            setResult(RESULT_OK);
            finish();
        } else {
            startActivityForResult(new Intent(this, newActivity), Constant.REQUEST_CODE_FINISH_ACTIVITY);
        }
    }


    private void setRandomBG() {
        int picIndex = new Random().nextInt(11);
        int resID;
        switch (picIndex) {
            case 0:
                resID = R.drawable.main_bg01;
                break;
//            case 1: resID = R.drawable.main_bg02;   break;
//            case 2: resID = R.drawable.main_bg03;   break;
//            case 3: resID = R.drawable.main_bg04;   break;
//            case 4: resID = R.drawable.main_bg05;   break;
//            case 5: resID = R.drawable.main_bg06;   break;
//            case 6: resID = R.drawable.main_bg07;   break;
//            case 7: resID = R.drawable.main_bg08;   break;
//            case 8: resID = R.drawable.main_bg09;   break;
//            case 9: resID = R.drawable.main_bg10;   break;
//            case 10: resID = R.drawable.main_bg11;   break;
            default:
                resID = R.drawable.main_bg01;
                break;
        }

//        Glide.with(this)
//                .load(resID)
//                .into((ImageView)findViewById(R.id.mainBG));
        ((ImageView) findViewById(R.id.mainBG)).setImageResource(resID);
    }

}
