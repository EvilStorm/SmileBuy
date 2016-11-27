package com.autofactory.smilebuy.ui.main.user;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.GlideCircleTransform;
import com.autofactory.smilebuy.component.HorizontalListView;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.model.UserDataFriend;
import com.autofactory.smilebuy.data.model.UserDataSimple;
import com.autofactory.smilebuy.data.server.GetUserDataResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.ui.main.car.detail.CarDetailActivity;
import com.autofactory.smilebuy.ui.main.setting.mycar.MyCarActivity;
import com.autofactory.smilebuy.ui.main.setting.mycar.MyCarGridAdapter;
import com.autofactory.smilebuy.ui.main.setting.notice.NoticeActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.bumptech.glide.Glide;
import com.makeramen.roundedimageview.RoundedImageView;

import org.lucasr.twowayview.TwoWayView;

/**
 * Created by Phebe on 16. 1. 24..
 */
public class UserProfileActivity extends NormalActivity {
    public static final String USER_DATA_SIMPLE = "USER_DATA_SIMPLE";
    private UserDataSimple mUserDataSimple = null;
    private UserDataFriend mUserData = null;

    private View[]mTabs;
    private ImageButton[]mTabButtons;

    private int mCurrentTabIndex = Constant.MY_CAR_TAB_SELL_CAR;

    private TwoWayView mGridView;
    //private HorizontalListView mGridView;
    private View mNoCar;
    private View mCarGradation;
    private MyCarGridAdapter mGridAdapter;

    private RoundedImageView mUserPic;
    private TextView mUserName;
    private TextView mUserSaying;
    private TextView mUserCreatedAt;

    private View mMyCarFrame;
    private View mMyCar;

    private ImageButton mCall;
    private ImageButton mSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_user_profile);

//        Glide.with(this)
//                .load(R.drawable.profile_card_bg)
//                .into((ImageView) findViewById(R.id.profileBG));
        ((ImageView) findViewById(R.id.profileBG)).setImageResource(R.drawable.profile_card_bg);

        mUserDataSimple = getIntent().getParcelableExtra(USER_DATA_SIMPLE);
        if(mUserDataSimple == null) {
            finish();
            return;
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mUserPic = (RoundedImageView) findViewById(R.id.userPic);
        mUserSaying = (TextView) findViewById(R.id.userSaying);
        mUserName = (TextView) findViewById(R.id.userName);
        mUserCreatedAt = (TextView) findViewById(R.id.userCreatedAt);

        mMyCarFrame = findViewById(R.id.myCarFrame);
        mMyCar = findViewById(R.id.myCar);
        mMyCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this, MyCarActivity.class));
            }
        });

        mSetting = (ImageButton) findViewById(R.id.setting);
        ImageButton message = (ImageButton) findViewById(R.id.message);
        ImageButton addFriend = (ImageButton) findViewById(R.id.addFriend);
        mCall = (ImageButton) findViewById(R.id.call);

        // TODO NOT READY YET !! (FOR OPEN VER.)
        message.setVisibility(View.GONE);
        addFriend.setVisibility(View.GONE);
        mCall.setVisibility(View.GONE);
        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(String.format("tel:%s", mUserDataSimple.getMobileNum())));
                    startActivity(i);
                } else {
                    Utility.showPopupOk(UserProfileActivity.this, getString(R.string.popup_message_phone_call_permission_required));
                }
            }
        });


        mSetting.setVisibility(View.GONE);
        mSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfileActivity.this, UserProfileSettingActivity.class));
            }
        });

        mTabs = new View[Constant.MY_CAR_TAB_COUNT];
        mTabButtons = new ImageButton[Constant.MY_CAR_TAB_COUNT];

        mTabs[Constant.MY_CAR_TAB_SELL_CAR] = findViewById(R.id.tab1);
        mTabs[Constant.MY_CAR_TAB_SHARE_CAR] = findViewById(R.id.tab2);

        mTabButtons[Constant.MY_CAR_TAB_SELL_CAR] = (ImageButton) findViewById(R.id.tab1Button);
        mTabButtons[Constant.MY_CAR_TAB_SHARE_CAR] = (ImageButton) findViewById(R.id.tab2Button);

        mTabButtons[Constant.MY_CAR_TAB_SELL_CAR].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTab(Constant.MY_CAR_TAB_SELL_CAR);
            }
        });
        mTabButtons[Constant.MY_CAR_TAB_SHARE_CAR].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTab(Constant.MY_CAR_TAB_SHARE_CAR);
            }
        });


        mGridView = (TwoWayView) findViewById(R.id.carList);
        mGridAdapter = new MyCarGridAdapter(this, R.layout.item_user_profile_car_list);
        mGridView.setAdapter(mGridAdapter);

        mNoCar = findViewById(R.id.noCar);
        mCarGradation = findViewById(R.id.carGradation);

        mNoCar.setVisibility(View.GONE);
        mCarGradation.setVisibility(View.GONE);

        mCurrentTabIndex = Constant.MY_CAR_TAB_SELL_CAR;

        init();
    }

    @Override
    protected void onResume() {
        super.onResume();

        updateProfile();
    }

    private void init() {
        Glide.with(this)
                .load(mUserDataSimple.getProfilePic())
                .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                .into(mUserPic);

        mUserName.setText(mUserDataSimple.getNickName());
        mUserSaying.setText(mUserDataSimple.getProfileSay());
        mUserCreatedAt.setText(String.format(getString(R.string.text_user_created_at), mUserDataSimple.getDateWithUnit(this)));

        if(Application.get().isMe(mUserDataSimple)) {
            mUserData = Application.get().getUserData();
        } else {
            ServerRequest.get().requestGetUserData(mUserDataSimple.getID(), new Response.Listener<GetUserDataResult>() {
                @Override
                public void onResponse(GetUserDataResult response) {
                    mUserData = response.getUserDataFriend();
                    updateProfile();
                }
            });
        }
    }

    private void updateProfile() {
        if(mUserData == null) {
            return;
        }

        if(Application.get().isMe(mUserData)) {
            mSetting.setVisibility(View.VISIBLE);
        } else {
            mSetting.setVisibility(View.GONE);
            if(mUserData.getMobileNumPolicy() == UserDataSimple.MOBILE_NUM_POLICY_NOBODY) {
                mCall.setVisibility(View.GONE);
            } else {
                mCall.setVisibility(View.VISIBLE);
            }
        }

        Glide.with(this)
                .load(mUserData.getProfilePic())
                .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                .bitmapTransform(new GlideCircleTransform(this))
                .into(mUserPic);

        mUserName.setText(mUserData.getNickName());
        mUserSaying.setText(mUserData.getProfileSay());
        mUserCreatedAt.setText(String.format(getString(R.string.text_user_created_at), mUserData.getDateWithUnit(this)));

        setTab(mCurrentTabIndex);

        ServerRequest.get().requestGetMyCarList(new Response.Listener<ServerResult>() {
            @Override
            public void onResponse(ServerResult response) {
                setTab(mCurrentTabIndex);
            }
        });
    }

    private void setTab(int tabIndex) {
        if(mUserData == null) {
            return;
        }

        mTabs[mCurrentTabIndex].setBackgroundResource(R.drawable.btn_bottom);

        mCurrentTabIndex = tabIndex;
        mTabs[mCurrentTabIndex].setBackgroundResource(R.drawable.btn_bottom_click);

        if(mCurrentTabIndex == Constant.MY_CAR_TAB_SHARE_CAR) {
            mGridAdapter.setList(mUserData.getCarShareList());
            mGridAdapter.setCarDataType(MyCarGridAdapter.Type.SHARE_CAR);
            // TODO Start Activity For Share Car
        } else {
            mGridAdapter.setList(mUserData.getCarList());
            mGridAdapter.setCarDataType(MyCarGridAdapter.Type.SELL_CAR);
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(UserProfileActivity.this, CarDetailActivity.class);
                    intent.putExtra(Constant.DATA_CAR_DATA, (Parcelable) mGridAdapter.getItem(position));
                    startActivity(intent);
                    overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
                }
            });
        }

        mMyCarFrame.setVisibility(View.GONE);
        if(mGridAdapter.getCount() <= 0) {
            mNoCar.setVisibility(View.VISIBLE);
            mGridView.setVisibility(View.GONE);
            mCarGradation.setVisibility(View.GONE);
            if(Application.get().isMe(mUserData)) {
                mMyCarFrame.setVisibility(View.VISIBLE);
            }
        } else {
            mNoCar.setVisibility(View.GONE);
            mGridView.setVisibility(View.VISIBLE);
            if(mGridAdapter.getCount() > 1) {
                mCarGradation.setVisibility(View.VISIBLE);
            } else {
                mCarGradation.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_bottom);
    }
}
