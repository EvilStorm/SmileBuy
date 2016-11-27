package com.autofactory.smilebuy.ui.login;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.server.LoginResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.ui.main.MainActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Log;
import com.autofactory.smilebuy.util.SmsReceiver;
import com.autofactory.smilebuy.util.Utility;
import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;


/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends NormalActivity {
    private static final int VERIFY_PHONE_DURATION = 180;

    private EditText mName;
    private EditText mEmail;
    private EditText mPassword;
    private EditText mPasswordReEnter;
    private EditText mPhoneNum;
    private EditText mSecretNum;
    private Button mVerifyPhone;
    private Button mCheckSecret;
    private View mCheckSecretWrapper;
    private TextView mSecretTimer;
    private Button mRegister;

    private boolean mFromFacebook = false;
    private boolean mSecretNumChecked = false;

    private Phonenumber.PhoneNumber mPhoneNumber = null;
    private TimerHandler mTimer;
    private BroadcastReceiver mSecretNumReceiver = null;

    private class TimerHandler extends Handler {
        private int mTickChannel = -1;

        public void startTick() {
            mTickChannel += 1;

            sendMessageDelayed(Message.obtain(this, mTickChannel, VERIFY_PHONE_DURATION-1, 0), 1000);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSecretTimer.setText(String.format(getString(R.string.prompt_secret_num_timer), Utility.getTimeFromSec(VERIFY_PHONE_DURATION)));
                    mSecretTimer.setVisibility(View.VISIBLE);
                }
            });
        }

        public void stopTick() {
            mTickChannel += 1;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSecretTimer.setVisibility(View.GONE);
                }
            });
        }

        @Override
        public void handleMessage(Message msg) {
            if(msg.what == mTickChannel) {
                final int time = msg.arg1;
                if(time <= 0) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSecretTimer.setText(getString(R.string.prompt_secret_num_timer_end));
                        }
                    });
                } else {
                    sendMessageDelayed(Message.obtain(this, msg.what, time-1, 0), 1000);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSecretTimer.setText(String.format(getString(R.string.prompt_secret_num_timer), Utility.getTimeFromSec(time)));
                        }
                    });
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register);

        Intent i = getIntent();
        mFromFacebook = i.getBooleanExtra("facebook", false);
        String name = i.getStringExtra("name");
        String email = i.getStringExtra("email");
        String phoneNum = Utility.getPhoneNumber(this);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mName = (EditText) findViewById(R.id.name);
        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);
        mPasswordReEnter = (EditText) findViewById(R.id.passwordReenter);
        mPhoneNum = (EditText) findViewById(R.id.phoneNum);
        mSecretNum = (EditText) findViewById(R.id.secretNum);
        mSecretTimer = (TextView) findViewById(R.id.secretTimer);

        mPhoneNum.addTextChangedListener(new PhoneNumberFormattingTextWatcher());

        if (name != null) {
            mName.setText(name);
        }
        if (email != null) {
            mEmail.setText(email);
        }
        if (phoneNum != null) {
            mPhoneNum.setText(phoneNum);
        }

        if (mFromFacebook) {
            mEmail.setVisibility(View.GONE);
            mPassword.setVisibility(View.GONE);
            mPasswordReEnter.setVisibility(View.GONE);
        }

        mCheckSecretWrapper = findViewById(R.id.checkSecretWrapper);
        mCheckSecretWrapper.setVisibility(View.GONE);

        mVerifyPhone = (Button) findViewById(R.id.verifyPhone);
        mVerifyPhone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                requestVerifyPhone();
            }
        });
        mCheckSecret = (Button) findViewById(R.id.checkSecret);
        mCheckSecret.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                requestCheckSecret();
            }
        });

        mTimer = new TimerHandler();

        mRegister = (Button) findViewById(R.id.register);
        mRegister.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                requestRegister();
            }
        });

        mSecretNumChecked = false;


        mSecretNumReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (SmsReceiver.RECEIVE_SECRET_CODE.equalsIgnoreCase(intent.getAction())) {
                    final String secretNum = intent.getStringExtra(SmsReceiver.RECEIVE_SECRET_CODE);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mSecretNum.setText(secretNum);
                        }
                    });
                }
            }
        };
        registerReceiver(mSecretNumReceiver, new IntentFilter(SmsReceiver.RECEIVE_SECRET_CODE));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mSecretNumReceiver);
    }

    private void requestVerifyPhone() {
        mCheckSecretWrapper.setVisibility(View.GONE);
        mSecretNumChecked = false;
        try {
            mPhoneNumber = PhoneNumberUtil.getInstance().parse(mPhoneNum.getText().toString(), "KR");
        } catch (NumberParseException e) {
            e.printStackTrace();
            mPhoneNumber = null;
            mPhoneNum.setError(getString(R.string.error_incorrect_phone_num));
            mPhoneNum.requestFocus();
            return;
        }
        if (mPhoneNumber != null) {
            Log.d("COUNTRY : " + mPhoneNumber.getCountryCode() + " , NUM : " + Utility.getNationalNumberIncludeLeadingZero(mPhoneNumber));
            ServerRequest.get().requestVerifyPhone("" + mPhoneNumber.getCountryCode(), Utility.getNationalNumberIncludeLeadingZero(mPhoneNumber), new Response.Listener<ServerResult>() {
                @Override
                public void onResponse(ServerResult response) {
                    if (response.isSuccess()) {
                        mCheckSecretWrapper.setVisibility(View.VISIBLE);
                        mSecretNum.setText("");
                        mTimer.startTick();

                        Utility.showPopupOk(RegisterActivity.this, String.format(getString(R.string.message_verify_phone_complete), "+" + mPhoneNumber.getCountryCode() + " " + Utility.getNationalNumberIncludeLeadingZero(mPhoneNumber)));
                    } else {
                        mPhoneNumber = null;
                    }
                }
            });
        } else {
            mPhoneNum.setError(getString(R.string.error_incorrect_phone_num));
            mPhoneNum.requestFocus();
        }
//        mCheckSecretWrapper.setVisibility(View.GONE);
//
//        if(!Utility.isPhoneNumValid(mPhoneNum.getText())) {
//            mPhoneNum.setError(getString(R.string.error_incorrect_phone_num));
//            mPhoneNum.requestFocus();
//        } else {
//            mPhoneNum.setEnabled(false);
//            ServerRequest.get().requestVerifyPhone(mPhoneNum.getText().toString(), new Response.Listener<ServerResult>() {
//                @Override
//                public void onResponse(ServerResult response) {
//                    Utility.hideProgressDialog();
//                    if (response.isSuccess()) {
//                        mCheckSecretWrapper.setVisibility(View.VISIBLE);
//                    } else {
//                        mPhoneNum.setEnabled(true);
//                        Utility.showPopupOk(RegisterActivity.this, response.getErrorMessage());
//                    }
//                }
//            });
//        }
    }

    private void requestCheckSecret() {
        mSecretNumChecked = false;
        boolean isOk = true;
        int secretNum = 0;
        try {
            secretNum = Integer.parseInt(mSecretNum.getText().toString());
        } catch (Exception e) {
            isOk = false;
        }

        if (!isOk || TextUtils.isEmpty(mSecretNum.getText()) || mSecretNum.getText().length() != 6 || mPhoneNumber == null) {
            mSecretNum.setError(getString(R.string.error_incorrect_secret_num));
            mSecretNum.requestFocus();
        } else {
            ServerRequest.get().requestCheckSecret("" + mPhoneNumber.getCountryCode(), Utility.getNationalNumberIncludeLeadingZero(mPhoneNumber), secretNum, new Response.Listener<ServerResult>() {
                @Override
                public void onResponse(ServerResult response) {
                    if (response.isSuccess()) {
                        mSecretNumChecked = true;
                        mTimer.stopTick();

                        Utility.showPopupOk(RegisterActivity.this, getString(R.string.message_check_secret_complete));

                        mPhoneNum.setEnabled(false);
                        mVerifyPhone.setEnabled(false);
                        mCheckSecret.setEnabled(false);
                        mSecretNum.setEnabled(false);
                    } else {
                        mSecretNum.requestFocus();
                    }
                }
            });
        }
    }

    private void requestRegister() {
        mName.setError(null);
        mEmail.setError(null);
        mPassword.setError(null);
        mPasswordReEnter.setError(null);
        mPhoneNum.setError(null);

        View problemView = null;
        if (TextUtils.isEmpty(mName.getText())) {
            mName.setError(getString(R.string.error_field_required));
            problemView = mName;
        } else if (!mFromFacebook && TextUtils.isEmpty(mEmail.getText())) {
            mEmail.setError(getString(R.string.error_field_required));
            problemView = mEmail;
        } else if (!mFromFacebook && TextUtils.isEmpty(mPassword.getText())) {
            mPassword.setError(getString(R.string.error_field_required));
            problemView = mPassword;
        } else if (!mFromFacebook && TextUtils.isEmpty(mPasswordReEnter.getText())) {
            mPasswordReEnter.setError(getString(R.string.error_field_required));
            problemView = mPasswordReEnter;
        } else if (!mFromFacebook && !Utility.isEmailValid(mEmail.getText())) {
            mEmail.setError(getString(R.string.error_invalid_email));
            problemView = mEmail;
        } else if (!mFromFacebook && (mPassword.getText().length() < 6 || mPassword.getText().length() > 20)) {
            mPassword.setError(getString(R.string.error_invalid_password));
            problemView = mPassword;
        } else if (!mFromFacebook && !mPassword.getText().toString().equals(mPasswordReEnter.getText().toString())) {
            mPasswordReEnter.setError(getString(R.string.error_incorrect_password));
            problemView = mPasswordReEnter;
        } else if (!mSecretNumChecked) {
            Utility.showPopupOk(RegisterActivity.this, getString(R.string.error_need_to_check_secret));
            problemView = mSecretNum;
        }

        if (problemView != null) {
            problemView.requestFocus();
        } else {
            if (mFromFacebook) {
                ServerRequest.get().requestRegister(Application.get().getFacebookToken(), mName.getText().toString(), Constant.USER_TYPE_USER, "" + mPhoneNumber.getCountryCode(), Utility.getNationalNumberIncludeLeadingZero(mPhoneNumber), new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        ServerRequest.get().requestLogin(Application.get().getFacebookToken(), new Response.Listener<LoginResult>() {
                            @Override
                            public void onResponse(LoginResult response) {
                                if (response.isSuccess()) {
                                    startActivity(MainActivity.class);
                                }
                            }
                        }, false);
                    }
                });
            } else {
                ServerRequest.get().requestRegister(mEmail.getText().toString(), mPassword.getText().toString(), mName.getText().toString(), Constant.USER_TYPE_USER, "" + mPhoneNumber.getCountryCode(), Utility.getNationalNumberIncludeLeadingZero(mPhoneNumber), new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        ServerRequest.get().requestLogin(mEmail.getText().toString(), mPassword.getText().toString(), new Response.Listener<LoginResult>() {
                            @Override
                            public void onResponse(LoginResult response) {
                                if (response.isSuccess()) {
                                    startActivity(MainActivity.class);
                                }
                            }
                        }, false);
                    }
                });
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
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

}

