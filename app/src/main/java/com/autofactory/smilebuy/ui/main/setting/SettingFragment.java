package com.autofactory.smilebuy.ui.main.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.ui.main.car.smilebuy.register.RegisterCarSmileBuyActivity;
import com.autofactory.smilebuy.ui.main.setting.mycar.MyCarActivity;
import com.autofactory.smilebuy.ui.main.setting.myfavoritecar.MyFavoriteCarActivity;
import com.autofactory.smilebuy.ui.main.setting.myrequest.MyRequestActivity;
import com.autofactory.smilebuy.ui.main.setting.notice.NoticeActivity;
import com.autofactory.smilebuy.ui.main.user.UserProfileActivity;
import com.autofactory.smilebuy.util.Utility;
import com.bumptech.glide.Glide;


public class SettingFragment extends Fragment {
    public SettingFragment() {
        // Required empty public constructor
    }

    public static SettingFragment newInstance(FragmentActivity fragmentActivity) {
        SettingFragment fragment = new SettingFragment();
        fragment.init(fragmentActivity, 2, fragmentActivity.getString(R.string.title_setting));
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fra_setting, container, false);

//        Glide.with(getActivity())
//                .load(R.drawable.bg_setting)
//                .into((ImageView) findViewById(R.id.settingBG));
        ((ImageView) findViewById(R.id.settingBG)).setImageResource(R.drawable.bg_setting);

        findViewById(R.id.setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SettingSettingActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
            }
        });

        findViewById(R.id.myProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), UserProfileActivity.class);
                intent.putExtra(UserProfileActivity.USER_DATA_SIMPLE, Application.get().getUserData());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
            }
        });

        findViewById(R.id.lay_buy_obd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://goo.gl/forms/puVg4qVzD9ZJQNhv2";
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });

        findViewById(R.id.lay_download_caroo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + "com.pokevian.optimus")));

            }
        });




//        findViewById(R.id.myMessage).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Utility.showPopupOk(getActivity(), getString(R.string.popup_message_not_ready_yet));
//            }
//        });

        findViewById(R.id.myFavorite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyFavoriteCarActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
            }
        });

        findViewById(R.id.notice).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NoticeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
            }
        });

        findViewById(R.id.myCar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyCarActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
            }
        });

        findViewById(R.id.myRequest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MyRequestActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
            }
        });

        View managerFrame = findViewById(R.id.managerFrame);
        if(Application.get().isManager()) {
            managerFrame.setVisibility(View.VISIBLE);
            findViewById(R.id.managerUpload).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), RegisterCarSmileBuyActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
                }
            });
        } else {
            managerFrame.setVisibility(View.INVISIBLE);
        }

        findViewById(R.id.kakaoAsk).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://plus.kakao.com/home/mmxkcpwu"));
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
            }
        });


        return mView;
    }

    @Override
    public void onActivitySay(Bundle data) {

    }
}
