package com.autofactory.smilebuy.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.ui.main.car.list.CarListFragment;
import com.autofactory.smilebuy.ui.main.setting.SettingFragment;
import com.autofactory.smilebuy.util.Constant;

/**
 * Created by AirPhebe on 2015. 10. 20..
 */
public class MainActivityPageAdapter extends FragmentPagerAdapter {
    private MainActivity mMainActivity;
    private Context mContext;

    public MainActivityPageAdapter(MainActivity mainActivity, FragmentManager fm) {
        super(fm);
        mMainActivity = mainActivity;
        mContext = mainActivity;
    }

    @Override
    public com.autofactory.smilebuy.component.Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        switch(position) {
            case Constant.MAIN_TAB_CAR_LIST:
                return CarListFragment.newInstance(mMainActivity, mMainActivity.getCarListAdapter());
            case Constant.MAIN_TAB_SETTING:
                return SettingFragment.newInstance(mMainActivity);
            case Constant.MAIN_TAB_FRIEND_LIST:
            default:
                return PlaceholderFragment.newInstance(position + 1);
        }
    }

    @Override
    public int getCount() {
        return Constant.MAIN_TAB_COUNT-1;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case Constant.MAIN_TAB_CAR_LIST:
                return mContext.getString(R.string.title_car_list);
            case Constant.MAIN_TAB_FRIEND_LIST:
                return mContext.getString(R.string.title_friends);
            case Constant.MAIN_TAB_SETTING:
                return mContext.getString(R.string.title_setting);
            default:
                return null;
        }
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends com.autofactory.smilebuy.component.Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fra_friends, container, false);
            return rootView;
        }

        @Override
        public void onActivitySay(Bundle data) {

        }
    }

}

