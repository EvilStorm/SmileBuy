package com.autofactory.smilebuy.ui.main;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.ui.main.car.list.CarListAdapter;
import com.autofactory.smilebuy.util.Constant;

public class MainActivity extends FragmentActivity {
    private MainActivityPageAdapter mPageAdapter;
    private ViewPager mViewPager;

    private View []mTabs;
    private ImageButton []mTabButtons;
    private int mCurrentTabIndex = Constant.MAIN_TAB_CAR_LIST;

    private CarListAdapter mCarListAdapter;

    public CarListAdapter getCarListAdapter() {
        return mCarListAdapter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mCurrentTabIndex = Constant.MAIN_TAB_CAR_LIST;

        setContentView(R.layout.act_main);


        mPageAdapter = new MainActivityPageAdapter(this, getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mPageAdapter);

        mTabs = new View[Constant.MAIN_TAB_COUNT];
        mTabButtons = new ImageButton[Constant.MAIN_TAB_COUNT];

        mTabs[Constant.MAIN_TAB_CAR_LIST] = findViewById(R.id.tab1);
        mTabs[Constant.MAIN_TAB_FRIEND_LIST] = findViewById(R.id.tab2);
        mTabs[Constant.MAIN_TAB_SETTING] = findViewById(R.id.tab3);

        mTabButtons[Constant.MAIN_TAB_CAR_LIST] = (ImageButton) findViewById(R.id.tab1Button);
        mTabButtons[Constant.MAIN_TAB_FRIEND_LIST] = (ImageButton) findViewById(R.id.tab2Button);
        mTabButtons[Constant.MAIN_TAB_SETTING] = (ImageButton) findViewById(R.id.tab3Button);

        mTabButtons[Constant.MAIN_TAB_CAR_LIST].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(Constant.MAIN_TAB_CAR_LIST, true);
            }
        });
        mTabButtons[Constant.MAIN_TAB_FRIEND_LIST].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(Constant.MAIN_TAB_FRIEND_LIST, true);
//                Intent i = new Intent(MainActivity.this, UserProfileActivity.class);
//                i.putExtra(UserProfileActivity.USER_DATA, Application.get().getUserData());
//                startActivity(i);
            }
        });
        mTabButtons[Constant.MAIN_TAB_SETTING].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewPager.setCurrentItem(Constant.MAIN_TAB_SETTING, true);
            }
        });

//        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
//        tabLayout.setSelectedTabIndicatorHeight(Math.round(this.getResources().getDisplayMetrics().density * (float)4));
//        for(int i=0; i<mPageAdapter.getCount(); i++) {
//            tabLayout.addTab(tabLayout.newTab().
//                    setCustomView(R.layout.tab_main));
////                    setIcon(mPageAdapter.getPageIcon(TAB_CAR_LIST+i)).
////                    setText(mPageAdapter.getPageTitle(TAB_CAR_LIST+i)));
//            View tab = tabLayout.getTabAt(i).getCustomView();
//            ((ImageView)tab.findViewById(R.id.icon)).setImageResource(mPageAdapter.getPageIcon(Constant.MAIN_TAB_CAR_LIST+i));
//            ((TextView)tab.findViewById(R.id.text1)).setText(mPageAdapter.getPageTitle(Constant.MAIN_TAB_CAR_LIST+i));
//        }
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                mTabs[mCurrentTabIndex].setBackgroundResource(R.drawable.btn_bottom);

                mCurrentTabIndex = position;
                mTabs[mCurrentTabIndex].setBackgroundResource(R.drawable.btn_bottom_tab);
                setCurrent(mPageAdapter.getItem(mCurrentTabIndex));

//                for(int i=0; i<Constant.MAIN_TAB_COUNT; i++) {
//                    if(i == position) {
//                        mTabs[i].setBackgroundResource(R.drawable.btn_bottom_click);
//                    } else {
//                        mTabs[i].setBackgroundResource(R.drawable.btn_bottom);
//                    }
//                }
//                setCurrent(mPageAdapter.getItem(position));
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });


        mCarListAdapter = new CarListAdapter(this);
        Application.get().setMainCarListAdapter(mCarListAdapter);

        //tabLayout.setupWithViewPager(mViewPager);

//        for(int i=0; i<tabLayout.getTabCount(); i++) {
//            tabLayout.getTabAt(i).setCustomView(R.layout.tab_main);
//            View tab = tabLayout.getTabAt(i).getCustomView();
//            ((ImageView)tab.findViewById(R.id.icon_tab)).setImageResource(R.drawable.com_facebook_close);
//            ((TextView)tab.findViewById(R.id.text_tab)).setText(mPageAdapter.getPageTitle(i));
//        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }


    @Override
    public void onFragmentSay(Bundle data) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Application.get().setMainCarListAdapter(null);
    }
}
