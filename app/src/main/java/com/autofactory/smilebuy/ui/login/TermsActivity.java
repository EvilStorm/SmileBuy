package com.autofactory.smilebuy.ui.login;

import com.autofactory.smilebuy.component.NormalActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.LoginResult;
import com.autofactory.smilebuy.ui.main.MainActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

public class TermsActivity extends NormalActivity {

    private CheckBox mService;
    private CheckBox mPrivate;
    private CheckBox mAll;
    private Button mAgree;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_terms);

        mService = (CheckBox) findViewById(R.id.agreeService);
        mPrivate = (CheckBox) findViewById(R.id.agreePrivate);
        mAll = (CheckBox) findViewById(R.id.agreeAll);

        mService.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkAgreeState();
            }
        });
        findViewById(R.id.agreeServiceText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mService.setChecked(!mService.isChecked());
            }
        });
        mPrivate.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                checkAgreeState();
            }
        });
        findViewById(R.id.agreePrivateText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPrivate.setChecked(!mPrivate.isChecked());
            }
        });
        mAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mService.setChecked(isChecked);
                mPrivate.setChecked(isChecked);
                checkAgreeState();
            }
        });
        findViewById(R.id.agreeAllText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAll.setChecked(!mAll.isChecked());
            }
        });

        mAgree = (Button) findViewById(R.id.next);
        mAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Application.get().getFacebookToken() != null) {
                    if (Profile.getCurrentProfile() != null) {
                        startRegisterActivity(Profile.getCurrentProfile().getName(), null);
                    } else {
                        GraphRequest graphRequest = GraphRequest.newMeRequest(Application.get().getFacebookToken(), new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                try {
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    startRegisterActivity(name, email);
                                } catch (JSONException e) {
                                    Utility.showPopupOk(TermsActivity.this, e.getLocalizedMessage());
                                }
                            }
                        });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id, name, email");
                        graphRequest.setParameters(parameters);
                        graphRequest.executeAsync();
                    }
                } else {
                    startRegisterActivity(null, null);
                }
            }
        });

        checkAgreeState();
    }

    private void checkAgreeState() {
        if (mService.isChecked() && mPrivate.isChecked()) {
            mAll.setChecked(true);
            mAgree.setEnabled(true);
        } else if (!mService.isChecked() && !mPrivate.isChecked()) {
            mAll.setChecked(false);
            mAgree.setEnabled(false);
        } else {
            mAgree.setEnabled(false);
        }
    }

    private void startRegisterActivity(String name, String email) {
        Intent i = new Intent(this, RegisterActivity.class);
        if (name != null) {
            i.putExtra("name", name);
            i.putExtra("facebook", true);
        }
        if (email != null) {
            i.putExtra("email", email);
        }
        startActivityForResult(i, Constant.REQUEST_CODE_FINISH_ACTIVITY);
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
//
//    private void startActivity(Class newActivity, String name, String email) {
//        if(newActivity == MainActivity.class) {
//            startActivity(new Intent(this, newActivity));
//            setResult(RESULT_OK);
//            finish();
//        } else {
//            startActivityForResult(new Intent(this, newActivity), Constant.REQUEST_CODE_FINISH_ACTIVITY);
//        }
//    }

}
