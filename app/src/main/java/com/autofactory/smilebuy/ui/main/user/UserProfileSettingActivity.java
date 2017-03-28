package com.autofactory.smilebuy.ui.main.user;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.GlideCircleTransform;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.model.UserData;
import com.autofactory.smilebuy.data.model.UserDataSimple;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.UserDataUpdatedResult;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;
import com.autofactory.smilebuy.util.popup.PopupCamera;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by Phebe on 16. 1. 24..
 */
public class UserProfileSettingActivity extends NormalActivity {
    private final int REQUEST_PICK_PICTURE = 20;

    private UserData mUserData;

    private RoundedImageView mUserPic;

    private String mFormerUserName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_profile_setting);

//        Glide.with(this)
//                .load(R.drawable.profile_card_bg)
//                .into((ImageView) findViewById(R.id.profileBG));
        ((ImageView) findViewById(R.id.profileBG)).setImageResource(R.drawable.profile_card_bg);

        mUserData = Application.get().getUserData();

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mUserPic = (RoundedImageView) findViewById(R.id.userPic);
        Glide.with(this)
                .load(mUserData.getProfilePic())
                .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                .bitmapTransform(new GlideCircleTransform(this))
                .into(mUserPic);

        if(!mUserData.isFacebookMember()) {
            ((TextView) findViewById(R.id.userID)).setText(mUserData.getEmail());
        }

        ((TextView) findViewById(R.id.userCreatedAt)).setText(String.format(getString(R.string.text_user_created_at), mUserData.getDateWithUnit(this)));


        final EditText userName = (EditText) findViewById(R.id.userName);
        final EditText userSaying = (EditText) findViewById(R.id.userSaying);

        userName.setText(mUserData.getNickName());
        userSaying.setText(mUserData.getProfileSay());

        mFormerUserName = userName.getText().toString();
        userName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    if(userName.getText().toString().trim().length() <= 0) {
                        userName.setText(mUserData.getNickName());
                        Utility.showPopupOk(UserProfileSettingActivity.this, getString(R.string.popup_message_name_must_be_exist));
                    } else {
                        ServerRequest.get().requestUpdateNickname(userName.getText().toString(), new Response.Listener<UserDataUpdatedResult>() {
                            @Override
                            public void onResponse(UserDataUpdatedResult response) {
                                userName.setText(mUserData.getNickName());
                            }
                        });
                    }
                    return true;
                }
                return false;
            }
        });
        userSaying.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_GO
                        || (event.getAction() == KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_ENTER)) {

                    ServerRequest.get().requestUpdateSaying(userSaying.getText().toString(), new Response.Listener<UserDataUpdatedResult>() {
                        @Override
                        public void onResponse(UserDataUpdatedResult response) {
                            userSaying.setText(mUserData.getProfileSay());
                        }
                    });
                    return true;
                }
                return false;
            }
        });

        findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupCamera popupProfileCamera = new PopupCamera(UserProfileSettingActivity.this, new PopupBase.OnClickListener() {
                    @Override
                    public void onClick() {
                        PhotoPickerIntent intent = new PhotoPickerIntent(UserProfileSettingActivity.this);
                        intent.setPhotoCount(1);
                        intent.setShowCamera(true);
                        //intent.setShowGif(true);
                        startActivityForResult(intent, REQUEST_PICK_PICTURE);
                    }
                }, new PopupBase.OnClickListener() {
                    @Override
                    public void onClick() {
                        ServerRequest.get().requestUpdateProfilePic(null, false, new Response.Listener<UserDataUpdatedResult>() {
                            @Override
                            public void onResponse(UserDataUpdatedResult response) {
                                Glide.with(UserProfileSettingActivity.this)
                                        .load(mUserData.getProfilePic())
                                        .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                                        .into(mUserPic);
                            }
                        });
                    }
                }, mUserData.getProfilePic() == null || mUserData.getProfilePic().length() <= 0);
                popupProfileCamera.show();
            }
        });

        final CheckBox phoneAgree = (CheckBox) findViewById(R.id.phoneAgree);
        phoneAgree.setChecked(mUserData.getMobileNumPolicy() == UserDataSimple.MOBILE_NUM_POLICY_NOBODY ? false : true);
        phoneAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, final boolean isChecked) {
                ServerRequest.get().requestUpdateMobileNumPolicy(isChecked ? UserDataSimple.MOBILE_NUM_POLICY_ALL : UserDataSimple.MOBILE_NUM_POLICY_NOBODY, new Response.Listener<UserDataUpdatedResult>() {
                    @Override
                    public void onResponse(UserDataUpdatedResult response) {
                        if (response.isSuccess()) {
                            phoneAgree.setChecked(isChecked);
                        } else {
                            phoneAgree.setChecked(!isChecked);
                        }
                    }
                });
            }
        });
        findViewById(R.id.phoneAgreeText).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneAgree.setChecked(!phoneAgree.isChecked());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                List<String> pictureList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                if (pictureList != null && pictureList.size() > 0) {
                    ServerRequest.get().requestUpdateProfilePic("file:///" + pictureList.get(0), false, new Response.Listener<UserDataUpdatedResult>() {
                        @Override
                        public void onResponse(UserDataUpdatedResult response) {
                            Glide.with(UserProfileSettingActivity.this)
                                    .load(mUserData.getProfilePic())
                                    .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                                    .into(mUserPic);
                        }
                    });
                }
            }
        }
    }

}
