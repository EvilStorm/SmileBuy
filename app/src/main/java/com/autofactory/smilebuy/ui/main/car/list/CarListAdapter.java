package com.autofactory.smilebuy.ui.main.car.list;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.component.GlideCircleTransform;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.model.UserDataSimple;
import com.autofactory.smilebuy.data.server.CarFavoriteResult;
import com.autofactory.smilebuy.data.server.CarListResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.main.car.comment.CommentActivity;
import com.autofactory.smilebuy.ui.main.car.smilebuy.CarSmileBuyActivity;
import com.autofactory.smilebuy.ui.main.user.UserProfileActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Log;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;
import com.bumptech.glide.Glide;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 11..
 */
public class CarListAdapter extends BaseAdapter {

    private FragmentActivity mActivity = null;
    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private List<CarData> mList = new ArrayList<>();
    private int mPage = 1;
    private long mOffsetCarID = -1;
    private PullToRefreshListView mListView = null;

    private FilterInfo mFilterInfo = new FilterInfo();
    private EditText mSearch = null;

    private StringBuffer car_info = new StringBuffer();
    private String[] mAreaArray;
    private String[] mFuelArray;

    public CarListAdapter(FragmentActivity activity) {
        mActivity = activity;
        mContext = activity;
        mInflater = LayoutInflater.from(mContext);
        mList.clear();

        mAreaArray = activity.getResources().getStringArray(R.array.area);
        mFuelArray = activity.getResources().getStringArray(R.array.fuel);

        refreshCarList(true);
    }

    public void setListView(PullToRefreshListView listView) {
        mListView = listView;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public CarData getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final CarData item = mList.get(position);
        final UserDataSimple user = item.getUser();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_car_list, null);
        }


        if (user != null) {
            ImageView imageView = Utility.getViewHolder(convertView, R.id.userPic);
            if (user.getProfilePic() != null && user.getProfilePic().length() > 0) {
                Glide.with(mActivity)
                        .load(user.getProfilePic())
                        .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                        .bitmapTransform(new GlideCircleTransform(mActivity))
                        .into(imageView);
                Log.d(" Progi");
            } else {
                imageView.setImageDrawable(null);
            }
            ((TextView) Utility.getViewHolder(convertView, R.id.userName)).setText(user.getNickName());
            ImageButton userSelect = Utility.getViewHolder(convertView, R.id.userSelect);
            userSelect.setFocusable(false);
            userSelect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mActivity, UserProfileActivity.class);
                    i.putExtra(UserProfileActivity.USER_DATA_SIMPLE, user);
                    mActivity.startActivity(i);
                    mActivity.overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
                }
            });
        }

        ((TextView) Utility.getViewHolder(convertView, R.id.carName)).setText(item.getName());
        ((TextView) Utility.getViewHolder(convertView, R.id.date)).setText(item.getDateAsShort());

        TextView tv_car_info = Utility.getViewHolder(convertView, R.id.tv_car_info);

        tv_car_info.setText(getCarInfo(item));

        ImageView imageView = Utility.getViewHolder(convertView, R.id.mainImage);
        Glide.with(mActivity)
                .load(item.getMainPictureURL())
                .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                .into(imageView);

        boolean showIcon = false;
        ImageButton smileBuy = Utility.getViewHolder(convertView, R.id.smileBuy);
        if (item.getSmileBuyID() >= 0) {
            showIcon = true;
            switch (item.getCarType()) {
                case Constant.CAR_TYPE_ALLIANCE:
                    smileBuy.setImageResource(R.drawable.icon_cartype_02);
                    break;
                case Constant.CAR_TYPE_COMMISSION:
                    smileBuy.setImageResource(R.drawable.icon_cartype_03);
                    break;
                case Constant.CAR_TYPE_NORMAL:
                default:
                    smileBuy.setImageResource(R.drawable.icon_cartype_01);
                    break;
            }
        }

        if (showIcon) {
            smileBuy.setVisibility(View.VISIBLE);
            smileBuy.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mActivity, CarSmileBuyActivity.class);
                    i.putExtra(Constant.DATA_CAR_SMILEBUY_ID, item.getSmileBuyID());
                    mActivity.startActivity(i);
                    mActivity.overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
                }
            });
            smileBuy.setFocusable(false);
        } else {
            smileBuy.setVisibility(View.GONE);
        }

        ((TextView) Utility.getViewHolder(convertView, R.id.price)).setText(item.getPriceAsString(mContext.getResources()));

        ImageButton imageButton = Utility.getViewHolder(convertView, R.id.favorite);
        imageButton.setFocusable(false);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequest.get().requestCarFavorite(item.getID(), new Response.Listener<CarFavoriteResult>() {
                    @Override
                    public void onResponse(CarFavoriteResult response) {
                        item.update(response.getCarData());
                        notifyDataSetChanged();
                    }
                });
            }
        });
        if (Application.get().isMyFavoriteCar(item)) {
            imageButton.setImageResource(R.drawable.icon_heart_n);
        } else {
            imageButton.setImageResource(R.drawable.icon_heart);
        }
        TextView textView = Utility.getViewHolder(convertView, R.id.favoriteCount);
        if (item.getFavoriteCount() > 0) {
            textView.setVisibility(View.VISIBLE);
            textView.setText("" + item.getFavoriteCount());

        } else {
            textView.setVisibility(View.GONE);
        }

        imageButton = Utility.getViewHolder(convertView, R.id.comment);
        imageButton.setFocusable(false);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mActivity, CommentActivity.class);
                i.putExtra(Constant.DATA_CAR_DATA, item);

                mActivity.startActivity(i);
                mActivity.overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_stay);
            }
        });
        textView = Utility.getViewHolder(convertView, R.id.commentCount);
        if (item.getCommentCount() > 0) {
            imageButton.setImageResource(R.drawable.icon_comment_n);
            textView.setVisibility(View.VISIBLE);
            textView.setText("" + item.getCommentCount());
        } else {
            imageButton.setImageResource(R.drawable.icon_comment);
            textView.setVisibility(View.GONE);
        }

        imageButton = Utility.getViewHolder(convertView, R.id.share);
        imageButton.setFocusable(false);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showPopupOk(mActivity, mActivity.getString(R.string.popup_message_not_ready_yet));
            }
        });

//        TextView question = Utility.getViewHolder(convertView, R.id.question);
//        TextView answer = Utilit®y.getViewHolder(convertView, R.id.answer);
//        if (item.getInterviews().size() > 0) {
//            question.setVisibility(View.VISIBLE);
//            answer.setVisibility(View.VISIBLE);
//
//            int randIndex = (int) (Math.random() * item.getInterviews().size());
//            InterviewData interviewData = item.getInterviews().get(randIndex);
//            question.setText(interviewData.getQuestion());
//            answer.setText(interviewData.getAnswer());
//
//            TextView tv_question_num = Utility.getViewHolder(convertView, R.id.tv_question_num);
//            tv_question_num.setText("Q" + (randIndex+1));
//
//        } else {
//            question.setVisibility(View.GONE);
//            answer.setVisibility(View.GONE);
//        }

        imageView = Utility.getViewHolder(convertView, R.id.saleTag);
        if (item.isSold()) {
            imageView.setVisibility(View.VISIBLE);
        } else {
            imageView.setVisibility(View.GONE);
        }

        textView = Utility.getViewHolder(convertView, R.id.carID);
        if (Application.get().isManager()) {
            textView.setText("" + item.getID());
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
        }

        return convertView;
    }

    private String getCarInfo(CarData item) {
        if(car_info.length() > 0 ) {
            car_info.delete(0,  car_info.length());
        }

        if(item.getAgeYear() != -1){
            car_info.append(item.getAgeYear());
            car_info.append("/");
            car_info.append(item.getAgeMonth());
        }

        if(item.getMileage() != -1) {
            if(car_info.length() > 0){
                car_info.append(" | ");
            }
            car_info.append(item.getMileageAsString(mActivity.getResources()));
        }

        if(item.getFuelType() != -1) {
            if(car_info.length() > 0){
                car_info.append(" | ");
            }
            car_info.append(item.getFuelTypeAsString(mActivity.getResources()));
        }

        if(item.getAreaType() != -1) {
            if(car_info.length() > 0){
                car_info.append(" | ");
            }
            car_info.append(item.getAreaTypeAsString(mActivity.getResources()));
        }

        return car_info.toString();
    }

    public void updateCarInList(CarData carData) {
        if(carData != null) {
            for (int i = 0; i < mList.size(); i++) {
                if (mList.get(i).getID() == carData.getID()) {
                    mList.get(i).update(carData);
                    notifyDataSetChanged();
                    break;
                }
            }
        }
    }

    public void deleteCarFromList(long carID) {
        for(int i=0; i<mList.size(); i++) {
            if(mList.get(i).getID() == carID) {
                mList.remove(i);
                notifyDataSetChanged();
                break;
            }
        }
    }

    public void refreshCarList(boolean reset) {
        if (reset) {
            mList.clear();
            mPage = 1;
            mOffsetCarID = -1;
            if (mListView != null) {
                mListView.setMode(PullToRefreshBase.Mode.BOTH);
            }
        }
        requestCarList();
    }

    public void addCarsFromServer(List<CarData> cars) {
        if (cars != null && cars.size() > 0) {
            if (mPage == 1) {
//                mOffsetCarID = cars.get(cars.size()-1).getID();
                mOffsetCarID = cars.get(0).getID();
                mList.clear();
            }
            mPage++;

            mList.addAll(cars);
        }
        notifyDataSetChanged();
    }

    private void requestCarList() {
        if (mFilterInfo.isDefault() && getSearchKeyword() == null) {
            ServerRequest.get().requestCarList(mPage, mOffsetCarID, mRefreshListener);
        } else {
            ServerRequest.get().requestSearchCar(mPage, mOffsetCarID,
                    getSearchKeyword(),
                    mFilterInfo.getPriceL(),
                    mFilterInfo.getPriceH(),
                    mFilterInfo.getMileageL(),
                    mFilterInfo.getMileageH(),
                    mFilterInfo.getArea(),
                    mFilterInfo.isHasSmileBuy(),
                    mFilterInfo.getCarType(),
                    mRefreshListener);
        }
    }

    private String getSearchKeyword() {
        if (mSearch == null) {
            return null;
        }
        String keyword = mSearch.getText().toString();
        if (keyword == null || keyword.length() <= 0) {
            return null;
        }
        return keyword;
    }

    private Response.Listener<CarListResult> mRefreshListener = new Response.Listener<CarListResult>() {
        @Override
        public void onResponse(CarListResult response) {
            if (mListView != null) {
                mListView.onRefreshComplete();
            }

            if (response.isSuccess()) {
                addCarsFromServer(response.getCarList());
            } else {
                if (Constant.SERVER_ERROR_TYPE_NO_RESULT.equals(response.getErrorType())) {
                    if (mOffsetCarID == -1) {    // 리스트가 비었을때
                        Utility.showPopupOk(mActivity, response.getErrorMessage());
                    } else {
                        // 리스트가 있는데 추가로 요청한 경우 더 없으면 alert 안 띄우고 더이상 요청 못하게 막기
                        if (mListView != null) {
                            mListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        }
                    }
                } else if (Constant.SERVER_ERROR_TYPE_NOT_VALID_LOGIN_TOKEN.equalsIgnoreCase(response.getErrorType())) {
                    Utility.showPopupOk(Application.get().getActivity(), response.getErrorMessage(), new PopupBase.OnClickListener() {
                        @Override
                        public void onClick() {
                            Application.get().reLogin();
                        }
                    });
                } else {
                    Utility.showPopupOk(mActivity, response.getErrorMessage());
                }
            }
        }
    };

    public FilterInfo getFilterInfo() {
        return mFilterInfo;
    }

    public void setSearch(EditText search) {
        mSearch = search;
    }

    public class FilterInfo {
        private boolean mChanged = false;

        private int mCarType = -1;
        private boolean mHasSmileBuy = false;
        private int mPriceL = -1;
        private int mPriceH = -1;
        private int mMileageL = -1;
        private int mMileageH = -1;
        private int mArea = -1;

        public int getCarType() {
            return mCarType;
        }

        public void setCarType(int carType) {
            if (carType != mCarType) {
                mChanged = true;
            }
            mCarType = carType;
        }

        public boolean isHasSmileBuy() {
            return mHasSmileBuy;
        }

        public void setHasSmileBuy(boolean hasSmileBuy) {
            if (hasSmileBuy != mHasSmileBuy) {
                mChanged = true;
            }
            mHasSmileBuy = hasSmileBuy;
        }

        public int getPriceL() {
            return mPriceL;
        }

        public void setPriceL(int priceL) {
            if (priceL != mPriceL) {
                mChanged = true;
            }
            mPriceL = priceL;
        }

        public int getPriceH() {
            return mPriceH;
        }

        public void setPriceH(int priceH) {
            if (priceH != mPriceH) {
                mChanged = true;
            }
            mPriceH = priceH;
        }

        public int getMileageL() {
            return mMileageL;
        }

        public void setMileageL(int mileageL) {
            if (mileageL != mMileageL) {
                mChanged = true;
            }
            mMileageL = mileageL;
        }

        public int getMileageH() {
            return mMileageH;
        }

        public void setMileageH(int mileageH) {
            if (mileageH != mMileageH) {
                mChanged = true;
            }
            mMileageH = mileageH;
        }

        public int getArea() {
            return mArea;
        }

        public void setArea(int area) {
            if (area != mArea) {
                mChanged = true;
            }
            mArea = area;
        }

        public boolean isChanged() {
            boolean changed = mChanged;
            mChanged = false;
            return changed;
        }

        public boolean isDefault() {
            return mCarType == Constant.CAR_TYPE_ALL
                    && mHasSmileBuy == false
                    && mPriceL == -1
                    && mPriceH == -1
                    && mMileageL == -1
                    && mMileageH == -1
                    && mArea <= 0;
        }

    }
}
