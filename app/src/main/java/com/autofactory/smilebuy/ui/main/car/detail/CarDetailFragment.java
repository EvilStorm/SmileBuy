package com.autofactory.smilebuy.ui.main.car.detail;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.component.PageIndicateTextView;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.model.InterviewData;
import com.autofactory.smilebuy.data.model.UserDataSimple;
import com.autofactory.smilebuy.ui.main.car.comment.CommentActivity;
import com.autofactory.smilebuy.ui.main.car.smilebuy.CarSmileBuyActivity;
import com.autofactory.smilebuy.ui.main.car.smilebuy.RequestSmileManActivity;
import com.autofactory.smilebuy.ui.main.car.smilebuy.WhatIsSmileManActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;


public class CarDetailFragment extends Fragment {
    public static final String CAR_DATA_UPDATED = "CAR_DATA_UPDATED";

    private CarData mCarData = null;
    private boolean mIsPreview = false;

    private CarPictureSlideAdapter mPictureSlideAdapter;
    private ViewPager mPictureSlider;

    private TextView mName;
    private TextView mPrice;
    private ImageButton mSmileBuy;
    private TextView mArea;
    private TextView mCarNum;
    private TextView mMileage;
    private TextView mAge;
    private TextView mFuel;
    private TextView mTransmission;
    private TextView mEngine;

    private View mAdditionalLine;
    private TextView mAdditional;
    private View mSaleTag;

    private TextView mSmileManText;
    //private View mWhatIsSmileMan;
    private Button mRequestSmileMan;
    private ImageView mCommentImage;
    private TextView mCommentText;

    private String[] mAreaArray;
    private String[] mFuelArray;
    private String[] mTransmissionArray;

    private View mContact;

    private boolean mAttatched = false;

    public CarDetailFragment() {
        // Required empty public constructor
    }

    public static CarDetailFragment newInstance(FragmentActivity fragmentActivity, CarData carData, boolean isPreview) {
        CarDetailFragment fragment = new CarDetailFragment();
        fragment.init(fragmentActivity, 2, fragmentActivity.getString(R.string.title_activity_car_detail));
        fragment.mCarData = carData;
        fragment.mIsPreview = isPreview;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fra_car_detail, container, false);

        mPictureSlider = (ViewPager) findViewById(R.id.pictureSlider);

        mPictureSlideAdapter = new CarPictureSlideAdapter(mFragmentActivity, mPictureSlider, false);
        mPictureSlider.setAdapter(mPictureSlideAdapter);

        PageIndicateTextView indicateTextView = (PageIndicateTextView) findViewById(R.id.pictureIndicator);
        indicateTextView.Init(mPictureSlider);

        mPictureSlideAdapter.addOnPageChangeListener(indicateTextView);
        mPictureSlideAdapter.setPictures(mCarData.getPictures());

        mName = (TextView) findViewById(R.id.name);
        mPrice = (TextView) findViewById(R.id.price);
        mSmileBuy = (ImageButton) findViewById(R.id.smileBuy);
        mArea = (TextView) findViewById(R.id.area);
        mCarNum = (TextView) findViewById(R.id.carNum);
        mMileage = (TextView) findViewById(R.id.mileage);
        mAge = (TextView) findViewById(R.id.age);
        mFuel = (TextView) findViewById(R.id.fuelType);
        mTransmission = (TextView) findViewById(R.id.transmissionType);
        mEngine = (TextView) findViewById(R.id.engine);
        mAdditionalLine = findViewById(R.id.additionalLine);
        mAdditional = (TextView) findViewById(R.id.additional);
        mSaleTag = findViewById(R.id.saleTag);

        mSmileBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CarSmileBuyActivity.class);
                i.putExtra(Constant.DATA_CAR_SMILEBUY_ID, mCarData.getSmileBuyID());
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
            }
        });


        View interviewTitle = findViewById(R.id.interviewTitle);
        final ImageButton interviewButton = (ImageButton) findViewById(R.id.interviewButton);
        final LinearLayout interviewContent = (LinearLayout) findViewById(R.id.interviewContent);
        final ImageView interviewArrow = (ImageView) findViewById(R.id.interviewArrow);
        if (mCarData.getInterviews().size() > 0) {
            interviewTitle.setVisibility(View.VISIBLE);
            interviewContent.setVisibility(View.VISIBLE);
            interviewArrow.setImageResource(R.drawable.btn_open_white);

            for (int i = 0; i < mCarData.getInterviews().size(); i++) {
                View interview = getActivity().getLayoutInflater().inflate(R.layout.item_interview_car_detail, null);
                InterviewData interviewData = mCarData.getInterviews().get(i);

                ((TextView) interview.findViewById(R.id.num)).setText(String.format("Q%d", i + 1));
                ((TextView) interview.findViewById(R.id.question)).setText(interviewData.getQuestion());
                ((TextView) interview.findViewById(R.id.answer)).setText(interviewData.getAnswer());
                if (i == mCarData.getInterviews().size() - 1) {
                    (interview.findViewById(R.id.divider)).setVisibility(View.GONE);
                } else {
                    (interview.findViewById(R.id.divider)).setVisibility(View.VISIBLE);
                }
                interviewContent.addView(interview);
            }

            interviewButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (interviewContent.getVisibility() == View.GONE) {
                        interviewContent.setVisibility(View.VISIBLE);
                        interviewArrow.setImageResource(R.drawable.btn_open_white);
                    } else {
                        interviewContent.setVisibility(View.GONE);
                        interviewArrow.setImageResource(R.drawable.btn_close_white);
                    }
                }
            });
        } else {
            interviewTitle.setVisibility(View.GONE);
            interviewContent.setVisibility(View.GONE);
        }

        mSmileManText = (TextView) findViewById(R.id.smileManText);
        if (Application.get().isMe(mCarData.getUser())) {
            mSmileManText.setText(getString(R.string.text_sell_request_smilebuy_free));
        } else {
            mSmileManText.setText(getString(R.string.content_request_car_check));
        }
//        mWhatIsSmileMan = findViewById(R.id.whatIsSmileMan);
//        mWhatIsSmileMan.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(), WhatIsSmileManActivity.class));
//                getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
//            }
//        });
        mRequestSmileMan = (Button) findViewById(R.id.requestSmileMan);
        mRequestSmileMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), RequestSmileManActivity.class);
                i.putExtra(RequestSmileManActivity.FROM_SELLER, Application.get().isMe(mCarData.getUser()) ? true : false);
                i.putExtra(RequestSmileManActivity.FROM_REGISTER, false);
                i.putExtra(RequestSmileManActivity.CAR_ID, mCarData.getID());
                startActivity(i);
                getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
            }
        });

        mCommentImage = (ImageView) findViewById(R.id.commentImage);
        mCommentText = (TextView) findViewById(R.id.commentText);
        ImageButton comment = (ImageButton) findViewById(R.id.comment);
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), CommentActivity.class);
                i.putExtra(Constant.DATA_CAR_DATA, mCarData);
                getActivity().startActivityForResult(i, CarDetailActivity.ACTIVITY_REQUEST_CODE_COMMENT);
                getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
            }
        });

        ImageButton share = (ImageButton) findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showPopupOk(getActivity(), getString(R.string.popup_message_not_ready_yet));
            }
        });

        mAreaArray = getResources().getStringArray(R.array.area);
        mFuelArray = getResources().getStringArray(R.array.fuel);
        mTransmissionArray = getResources().getStringArray(R.array.transmission);

        ImageButton call = (ImageButton) findViewById(R.id.call);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(getActivity().checkCallingOrSelfPermission(Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent i = new Intent(Intent.ACTION_CALL);
                    i.setData(Uri.parse(String.format("tel:%s", mCarData.getUser().getMobileNum())));
                    getActivity().startActivity(i);

                    Application.get().setGAEvent(Constant.GoogleAnalytic.EVENT_CATEGORY_CONTACT,
                            Constant.GoogleAnalytic.EVENT_ACTION_CALL,
                            getUserName() + " -> " + mCarData.getUser().getMobileNum());
                } else {
                    Utility.showPopupOk(getActivity(), getString(R.string.popup_message_phone_call_permission_required));
                }
            }
        });

        ImageButton sms = (ImageButton) findViewById(R.id.sendSMS);
        sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + mCarData.getUser().getMobileNum())));
                Application.get().setGAEvent(Constant.GoogleAnalytic.EVENT_CATEGORY_CONTACT,
                        Constant.GoogleAnalytic.EVENT_ACTION_SMS,
                        getUserName() + " -> " + mCarData.getUser().getMobileNum());
            }
        });

        mContact = findViewById(R.id.contact);
        mContact.setVisibility(mCarData.getUser().getMobileNumPolicy() == UserDataSimple.MOBILE_NUM_POLICY_NOBODY ? View.GONE : View.VISIBLE);

        if (mIsPreview) {
            mSmileBuy.setEnabled(false);
//            mWhatIsSmileMan.setEnabled(false);
            comment.setEnabled(false);
            share.setEnabled(false);
            mRequestSmileMan.setEnabled(false);
            call.setEnabled(false);
            sms.setEnabled(false);
        }

        return mView;
    }

    private String getUserName(){
        if(Application.get().getUserData() == null){
            return "unknown";
        }else{
            return Application.get().getUserData().getNickName();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onActivitySay(Bundle data) {
        if (data != null && data.getBoolean(CAR_DATA_UPDATED, false)) {
            mCarData = data.getParcelable(Constant.DATA_CAR_DATA);
            refresh();
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mAttatched = true;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mAttatched = false;
    }

    private void refresh() {
        // REFRESH EXCEPT INTERVIEWS
        if (mCarData != null && mAttatched) {
            mPictureSlideAdapter.setPictures(mCarData.getPictures());
            mPictureSlideAdapter.notifyDataSetChanged();
            mName.setText(mCarData.getName());
            mPrice.setText(mCarData.getPriceAsString(getResources()));
            mCarNum.setText(mCarData.getCarNum());

            boolean showIcon = false;
            if (mCarData.getSmileBuyID() >= 0) {
                showIcon = true;
                switch (mCarData.getCarType()) {
                    case Constant.CAR_TYPE_ALLIANCE:
                        mSmileBuy.setImageResource(R.drawable.icon_cartype_02);
                        break;
                    case Constant.CAR_TYPE_COMMISSION:
                        mSmileBuy.setImageResource(R.drawable.icon_cartype_03);
                        break;
                    case Constant.CAR_TYPE_NORMAL:
                    default:
                        mSmileBuy.setImageResource(R.drawable.icon_cartype_01);
                        break;
                }
            }

            if (showIcon) {
                mSmileBuy.setVisibility(View.VISIBLE);
            } else {
                mSmileBuy.setVisibility(View.GONE);
            }

            if (mCarData.getAreaType() > 0 && mCarData.getAreaType() < mAreaArray.length) {
                mArea.setVisibility(View.VISIBLE);
                mArea.setText(mAreaArray[mCarData.getAreaType()]);
            } else {
                mArea.setVisibility(View.GONE);
            }
            if (mCarData.getFuelType() > 0 && mCarData.getFuelType() < mFuelArray.length) {
                mFuel.setVisibility(View.VISIBLE);
                mFuel.setText(mFuelArray[mCarData.getFuelType()]);
            } else {
                mFuel.setVisibility(View.GONE);
            }
            if (mCarData.getTransmissionType() > 0 && mCarData.getTransmissionType() < mTransmissionArray.length) {
                mTransmission.setVisibility(View.VISIBLE);
                mTransmission.setText(mTransmissionArray[mCarData.getTransmissionType()]);
            } else {
                mTransmission.setVisibility(View.GONE);
            }

            String str = mCarData.getMileageAsString(getResources());
            if (str != null) {
                mMileage.setVisibility(View.VISIBLE);
                mMileage.setText(str);
            } else {
                mMileage.setVisibility(View.GONE);
            }

            str = mCarData.getAgeAsString(getResources());
            if (str != null) {
                mAge.setVisibility(View.VISIBLE);
                mAge.setText(str);
            } else {
                mAge.setVisibility(View.GONE);
            }

            str = mCarData.getEngineAsString(getResources());
            if (str != null) {
                mEngine.setVisibility(View.VISIBLE);
                mEngine.setText(str);
            } else {
                mEngine.setVisibility(View.GONE);
            }

            str = mCarData.getAdditional();
            if (str != null && str.length() > 0) {
                mAdditional.setText(str);
                mAdditional.setVisibility(View.VISIBLE);
                mAdditionalLine.setVisibility(View.VISIBLE);
            } else {
                mAdditional.setVisibility(View.GONE);
                mAdditionalLine.setVisibility(View.GONE);
            }

            if ((mCarData.isCanSmileBuy() || Application.get().isMe(mCarData.getUser())) && Utility.checkSmileManAvailalbe(mFragmentActivity, mCarData.getAreaType()) == Constant.CheckSmileMan.AVAILABLE) {
                mSmileManText.setVisibility(View.VISIBLE);
//                mWhatIsSmileMan.setVisibility(View.VISIBLE);
                mRequestSmileMan.setVisibility(View.VISIBLE);
            } else {
                mSmileManText.setVisibility(View.GONE);
//                mWhatIsSmileMan.setVisibility(View.GONE);
                mRequestSmileMan.setVisibility(View.GONE);
            }

            if (mCarData.getCommentCount() > 0) {
                mCommentImage.setImageResource(R.drawable.icon_comment_n);
            } else {
                mCommentImage.setImageResource(R.drawable.icon_comment);
            }
            mCommentText.setText(String.format(getString(R.string.title_activity_comment), mCarData.getCommentCount()));

            if (mCarData.isSold()) {
                mSaleTag.setVisibility(View.VISIBLE);
            } else {
                mSaleTag.setVisibility(View.GONE);
            }

            if (Application.get().isMyRequestedCar(mCarData)) {
                mRequestSmileMan.setEnabled(false);
            }

            mContact.setVisibility(mCarData.getUser().getMobileNumPolicy() == UserDataSimple.MOBILE_NUM_POLICY_NOBODY ? View.GONE : View.VISIBLE);
        }
    }


}
