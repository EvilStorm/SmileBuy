package com.autofactory.smilebuy.ui.main.car.register;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.FixedSpeedScroller;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.data.model.PictureData;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class RegisterCarPictureFragment extends Fragment implements RegisterCarPictureInterface {
    //private final int REQUEST_TAKE_PICTURE = 10;
    private final int REQUEST_PICK_PICTURE = 20;
    //private File mTakePictureFile = null;

    private RegisterCarActivity mRegisterCarActivity = null;

    private ViewPager mSellGuide = null;
    private RegisterCarGuideSlideAdapter mSellGuideAdapter = null;

    private RegisterCarPictureAdapter mCarPictureAdapter = null;
    private GridView mGridView;


    // Animate Sell Guide
    private final int SELL_GUIDE_ANIMATION_DELAY = 4000;
    private final int SELL_GUIDE_ANIMATION_DURATION = 2000;
    private Handler mSellGuideHandler = new Handler();
    private Runnable mSellGuideRunnable = new Runnable() {
        @Override
        public void run() {
            if (mSellGuide != null && mSellGuideAdapter != null) {
                mSellGuide.setCurrentItem((mSellGuide.getCurrentItem() + 1) % mSellGuideAdapter.getCount(), true);
//                if(mSellGuide.getCurrentItem() == mSellGuideAdapter.getCount() - 1) {
//                    mSellGuide.setCurrentItem(0);
//                } else {
//
//                }
                mSellGuideHandler.postDelayed(mSellGuideRunnable, SELL_GUIDE_ANIMATION_DELAY);
            }
        }
    };

    public RegisterCarPictureFragment() {
        // Required empty public constructor
    }


    public static RegisterCarPictureFragment newInstance(RegisterCarActivity fragmentActivity, int id, String name) {
        RegisterCarPictureFragment fragment = new RegisterCarPictureFragment();
        fragment.init(fragmentActivity, id, name);
        fragment.mRegisterCarActivity = fragmentActivity;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fra_register_car_1_picture, container, false);

        mSellGuide = (ViewPager) findViewById(R.id.sellGuide);
        mSellGuideAdapter = new RegisterCarGuideSlideAdapter(getActivity(), mSellGuide);
        mSellGuide.setAdapter(mSellGuideAdapter);
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            FixedSpeedScroller scroller = new FixedSpeedScroller(mSellGuide.getContext());
            scroller.setDuration(SELL_GUIDE_ANIMATION_DURATION);
            mScroller.set(mSellGuide, scroller);
        } catch (NoSuchFieldException e) {

        } catch (IllegalArgumentException e) {

        } catch (IllegalAccessException e) {

        } catch (Exception e) {

        }


        mCarPictureAdapter = new RegisterCarPictureAdapter(getActivity(), this, mRegisterCarActivity.getCarDataEdit().getPictures(), Constant.ADD_PICTURE_LIMIT_USER);
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setAdapter(mCarPictureAdapter);

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkCanGoNext();
        mSellGuideHandler.postDelayed(mSellGuideRunnable, SELL_GUIDE_ANIMATION_DELAY);
    }

    @Override
    public void onPause() {
        super.onPause();
        mSellGuideHandler.removeCallbacks(mSellGuideRunnable);
    }

    @Override
    public void checkCanGoNext() {
        Bundle data = new Bundle();
        data.putInt(RegisterCarActivity.KEY_SAY_ID, RegisterCarActivity.SAY_ID_CAN_GO_NEXT);
        data.putBoolean(RegisterCarActivity.KEY_SAY_DATA, mCarPictureAdapter.getRemainPictureCount() != mCarPictureAdapter.getCount());
        mFragmentActivity.onFragmentSay(data);
    }

//    private void takePicture() {
//        if (mCarPictureAdapter.getRemainPictureCount() <= 0) return;
//
//        if (Utility.hasCamera(mFragmentActivity) && Utility.hasDefualtCameraApp(mFragmentActivity)) {
//            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            if (i.resolveActivity(mFragmentActivity.getPackageManager()) != null) {
//                mTakePictureFile = null;
//                try {
//                    mTakePictureFile = Utility.createTemporaryImageFile(mFragmentActivity);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                if (mTakePictureFile != null) {
//                    i.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mTakePictureFile));
//                    startActivityForResult(i, REQUEST_TAKE_PICTURE);
//
//                }
//            }
//        }
//    }

    @Override
    public void pickPictures() {
        int remainPictureCount = mCarPictureAdapter.getRemainPictureCount();
        if (remainPictureCount <= 0) return;
//        Intent i = new Intent(Intent.ACTION_PICK);
//        i.setType("image/*");
//        i.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
//        startActivityForResult(i, REQUEST_PICK_PICTURE);

        PhotoPickerIntent intent = new PhotoPickerIntent(getContext());
        intent.setPhotoCount(remainPictureCount);
        intent.setShowCamera(true);
        //intent.setShowGif(true);
        startActivityForResult(intent, REQUEST_PICK_PICTURE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == REQUEST_TAKE_PICTURE) {
//            if (resultCode == Activity.RESULT_OK) {
////                Bundle extras = data.getExtras();
////                if(extras != null) {
////                    Bitmap thumbNail = (Bitmap) extras.get("data");
////                    if(thumbNail != null) {
////                        int pictureIndex = RegisterCarActivity.carDataSmileBuy.addPictureURL("file:" + mTakePictureFile.getAbsolutePath());
////                        if(pictureIndex >= 0 && pictureIndex < mThumbNails.length) {
////                            mThumbNails[pictureIndex].setImageBitmap(thumbNail);
////                        }
////                    }
////                }
//                mCarPictureAdapter.addItem("file://" + mTakePictureFile.getAbsolutePath());
//                checkCanGoNext();
//            }
//        } else
        if (requestCode == REQUEST_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                // When Using EXTRA_ALLOW_MULTIPLE
//                ClipData photos = data.getClipData();
//                if(photos != null) {
//                    for (int i = 0; i < photos.getItemCount(); i++) {
//                        Uri uri = photos.getItemAt(i).getUri();
//                    }
//                }
                List<String> pictureList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                if (pictureList != null) {
                    for (int i = 0; i < pictureList.size(); i++) {
                        mCarPictureAdapter.addItem("file:///" + pictureList.get(i));
                    }
                    checkCanGoNext();
                }
            }
        }
    }

    @Override
    public void onActivitySay(Bundle data) {
        if (data != null) {
            int id = data.getInt(FragmentActivity.KEY_SAY_ID);
            switch (id) {
                case FragmentActivity.SAY_ID_ON_NEXT:
                    mRegisterCarActivity.getCarDataEdit().setToRemoveOnServerPictures(mCarPictureAdapter.getToRemoveIDs());
                    List<PictureData> pictureDatas = mRegisterCarActivity.getCarDataEdit().getPictures();
                    pictureDatas.clear();

                    List<PictureData> adapterList = mCarPictureAdapter.getList();
                    for(int i=0; i<adapterList.size(); i++) {
                        PictureData pictureData = adapterList.get(i);
                        if(!pictureData.isEmpty()) {
                            pictureDatas.add(pictureData);
                        } else {
                            break;
                        }
                    }
            }
        }
    }
}

