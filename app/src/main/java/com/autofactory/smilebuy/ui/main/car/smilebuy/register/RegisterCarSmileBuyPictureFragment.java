package com.autofactory.smilebuy.ui.main.car.smilebuy.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.data.model.PictureData;
import com.autofactory.smilebuy.data.server.CarResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.main.car.register.RegisterCarActivity;
import com.autofactory.smilebuy.ui.main.car.register.RegisterCarPictureAdapter;
import com.autofactory.smilebuy.ui.main.car.register.RegisterCarPictureInterface;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.bumptech.glide.Glide;

import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

public class RegisterCarSmileBuyPictureFragment extends Fragment implements RegisterCarPictureInterface {
    private final int REQUEST_PICK_PICTURE = 20;

    private RegisterCarSmileBuyActivity mRegisterCarSmileBuyActivity = null;
    private TextView mTargetCarResult;

    private RegisterCarPictureAdapter mCarPictureAdapter = null;
    private GridView mGridView;

    public RegisterCarSmileBuyPictureFragment() {
        // Required empty public constructor
    }


    public static RegisterCarSmileBuyPictureFragment newInstance(RegisterCarSmileBuyActivity fragmentActivity, int id, String name) {
        RegisterCarSmileBuyPictureFragment fragment = new RegisterCarSmileBuyPictureFragment();
        fragment.init(fragmentActivity, id, name);
        fragment.mRegisterCarSmileBuyActivity = fragmentActivity;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fra_register_car_smile_buy_1_picture, container, false);

        mTargetCarResult = (TextView) findViewById(R.id.targetCarResult);
        mTargetCarResult.setVisibility(View.GONE);

        if (mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getCarID() >= 0) {
            updateTargetCar(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getCarID());
        }


        final EditText targetCarID = (EditText) findViewById(R.id.targetCarID);
        findViewById(R.id.getTargetCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(targetCarID.getText())) {
                    Utility.showPopupOk(getActivity(), getString(R.string.popup_message_no_target_car_id));
                } else {
                    long id = Utility.parseLong(targetCarID.getText().toString());
                    updateTargetCar(id);
                }
            }
        });


        mCarPictureAdapter = new RegisterCarPictureAdapter(getActivity(), this, mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getPictures(), Constant.ADD_PICTURE_LIMIT_MANAGER);
        mGridView = (GridView) findViewById(R.id.gridView);
        mGridView.setAdapter(mCarPictureAdapter);

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        checkCanGoNext();
    }

    private void updateTargetCar(long id) {
        ServerRequest.get().requestGetCar(id, new Response.Listener<CarResult>() {
            @Override
            public void onResponse(CarResult response) {
                if (response.isSuccess()) {
                    mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setCarID(response.getCarData().getID());
                    mTargetCarResult.setText(String.format(getString(R.string.text_target_car_info), response.getCarData().getID(), response.getCarData().getName(), response.getCarData().getUser().getNickName()));
                    mTargetCarResult.setVisibility(View.VISIBLE);
                } else {
                    mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setCarID(-1);
                    mTargetCarResult.setText(getString(R.string.text_target_car_no_exist));
                    mTargetCarResult.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void checkCanGoNext() {
        Bundle data = new Bundle();
        data.putInt(RegisterCarActivity.KEY_SAY_ID, RegisterCarActivity.SAY_ID_CAN_GO_NEXT);
        data.putBoolean(RegisterCarActivity.KEY_SAY_DATA, mCarPictureAdapter.getRemainPictureCount() != mCarPictureAdapter.getCount());
        mFragmentActivity.onFragmentSay(data);
    }

    @Override
    public void pickPictures() {
        int remainPictureCount = mCarPictureAdapter.getRemainPictureCount();
        if (remainPictureCount <= 0) return;

        PhotoPickerIntent intent = new PhotoPickerIntent(getContext());
        intent.setPhotoCount(remainPictureCount);
        intent.setShowCamera(true);
        startActivityForResult(intent, REQUEST_PICK_PICTURE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
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
                    List<PictureData> pictureDatas = mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getPictures();
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

