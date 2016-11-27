package com.autofactory.smilebuy.ui.main.car.smilebuy.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.data.model.CarDataSmileBuy;
import com.autofactory.smilebuy.data.model.UserDataSimple;
import com.autofactory.smilebuy.util.Constant;

public class RegisterCarSmileBuyActivity extends FragmentActivity {
    public static final int ID_FRAGMENT_PICTURE = 0;
    public static final int ID_FRAGMENT_INFO = 1;
    public static final String NAME_FRAGMENT_PICTURE = "FRAGMENT_PICTURE";
    public static final String NAME_FRAGMENT_INFO = "FRAGMENT_INFO";





    protected CarDataSmileBuy mCarDataSmileBuy = null;
    protected boolean mIsUpdate = false;

    private Button mNext;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register_car_smile_buy);

        mTitle = (TextView) findViewById(R.id.title);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mNext = (Button) findViewById(R.id.next);
        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCurrent == null) return;
                onNext();
                switch (mCurrent.getFragmentID()) {
                    case ID_FRAGMENT_PICTURE:
                        changeFragment(ID_FRAGMENT_INFO);
                        break;
                }
            }
        });

        mIsUpdate = false;
        mCarDataSmileBuy = null;
        Intent i = getIntent();
        if(i != null) {
            mCarDataSmileBuy = i.getParcelableExtra(Constant.DATA_CAR_DATA);
            if(mCarDataSmileBuy != null) {
                mIsUpdate = true;
                mCarDataSmileBuy.setManager(new UserDataSimple(Application.get().getUserData()));
            }
        }
        if(mCarDataSmileBuy == null) {
            mCarDataSmileBuy = new CarDataSmileBuy();
            mCarDataSmileBuy.setManager(new UserDataSimple(Application.get().getUserData()));
        }

        changeFragment(ID_FRAGMENT_PICTURE);





        updateNext(false);
    }


    @Override
    protected void changeFragment(int fragmentID) {
        Fragment fragment = null;
        switch(fragmentID) {
            case ID_FRAGMENT_PICTURE:
                fragment = RegisterCarSmileBuyPictureFragment.newInstance(this, ID_FRAGMENT_PICTURE, NAME_FRAGMENT_PICTURE);
                mTitle.setText(R.string.title_sell_picture);
                break;
            case ID_FRAGMENT_INFO:
                fragment = RegisterCarSmileBuyInfoFragment.newInstance(this, ID_FRAGMENT_INFO, NAME_FRAGMENT_INFO);
                mTitle.setText(R.string.title_sell_info);
                break;
            default:
                return;
        }

        FragmentTransaction transaction = mManager.beginTransaction();
        transaction = transaction.replace(R.id.fragmentContainer, fragment);
        transaction = transaction.addToBackStack(fragment.getFragmentName());
        transaction = transaction.setCustomAnimations(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
        transaction.commit();

        //super.changeFragment(fragmentID);
        setCurrent(fragment);
    }


    private void updateTitle() {
        if(mCurrent == null) {
            mTitle.setText(R.string.title_sell_picture);
            return;
        }

        switch(mCurrent.getFragmentID()) {
            case ID_FRAGMENT_PICTURE:
                mTitle.setText(R.string.title_sell_picture);
                break;
            case ID_FRAGMENT_INFO:
                mTitle.setText(R.string.title_sell_info);
                break;
            default:
                return;
        }
    }
    private void updateNext(boolean canGo) {
        mNext.setEnabled(canGo);
        if(mCurrent == null) {
            mNext.setText(String.format(getString(R.string.action_sell_next), 1, 2));
            return;
        }
        switch (mCurrent.getFragmentID()) {
            case ID_FRAGMENT_INFO:
                mNext.setText(String.format(getString(R.string.action_sell_complete), 2, 2));
                break;
            case ID_FRAGMENT_PICTURE:
            default:
                mNext.setText(String.format(getString(R.string.action_sell_next), 1, 2));
                break;
        }
    }
    private void onNext() {
        if(mCurrent == null)    return;
        Bundle data = new Bundle();
        data.putInt(KEY_SAY_ID, SAY_ID_ON_NEXT);
        mCurrent.onActivitySay(data);
    }

    @Override
    public void setCurrent(Fragment fragment) {
        super.setCurrent(fragment);
        updateTitle();
        updateNext(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCarDataSmileBuy = null;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_bottom);
    }

    @Override
    public void onFragmentSay(Bundle data) {
        if(data != null) {
            int id = data.getInt(KEY_SAY_ID);
            switch(id) {
                case SAY_ID_CAN_GO_NEXT:
                    updateNext(data.getBoolean(KEY_SAY_DATA, false));
                    break;
            }
        }
    }

    public CarDataSmileBuy getCarDataSmileBuy() {
        return mCarDataSmileBuy;
    }

    public boolean isUpdate() {
        return mIsUpdate;
    }
}
