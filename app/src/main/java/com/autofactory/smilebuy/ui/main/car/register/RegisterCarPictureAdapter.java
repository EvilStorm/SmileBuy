package com.autofactory.smilebuy.ui.main.car.register;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.data.model.CarDataSimple;
import com.autofactory.smilebuy.data.model.PictureData;
import com.autofactory.smilebuy.data.server.CarFavoriteResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 11..
 */
public class RegisterCarPictureAdapter extends BaseAdapter {
    private Activity mActivity;
    private LayoutInflater mInflater = null;
    private RegisterCarPictureInterface mRegisterCarPicture;
    private List<PictureData> mList = null;
    private List<Long> mToRemoveIDs = new ArrayList<>();
    private int mLimitCount = Constant.ADD_PICTURE_LIMIT_USER;
    private int mAddPosition = 0;

    public RegisterCarPictureAdapter(Activity activity, RegisterCarPictureInterface registerCarPicture, List<PictureData> list, int limitCount) {
        mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
        mRegisterCarPicture = registerCarPicture;

        mLimitCount = limitCount;
        mList = new ArrayList<>(mLimitCount);
        for(int i=0; i<mLimitCount; i++) {
            if(i < list.size()) {
                mList.add(list.get(i));
            } else {
                mList.add(new PictureData());
            }
        }
        mToRemoveIDs.clear();
        calcAddPosition();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final PictureData item = mList.get(position);
        if(item == null) {
            return null;
        }

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_register_car_picture, null);
        }

        ImageView imageView = Utility.getViewHolder(convertView, R.id.thumb);
        Glide.with(mActivity)
                .load(item.getURL())
                .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                .into(imageView);

        ImageButton remove = Utility.getViewHolder(convertView, R.id.remove);
        if(remove != null) {
            if (!item.isEmpty()) {
                remove.setVisibility(View.VISIBLE);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        removeItem(position);
                    }
                });
            } else {
                remove.setVisibility(View.GONE);
            }
        }

        ImageButton add = Utility.getViewHolder(convertView, R.id.add);
        if(add != null) {
            if (position == mAddPosition) {
                add.setVisibility(View.VISIBLE);
                add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mRegisterCarPicture.pickPictures();
                    }
                });
            } else {
                add.setVisibility(View.GONE);
            }
        }


        return convertView;
    }


    public void addItem(String url) {
        if(mAddPosition != -1) {
            mList.get(mAddPosition).setURL(url);

            calcAddPosition();
            notifyDataSetChanged();
            mRegisterCarPicture.checkCanGoNext();
        }
    }
    public void removeItem(int position) {
        if(position >= 0 && position < mList.size()) {
            if(mList.get(position).getID() != PictureData.ID_NOT_ON_SERVER) {
                mToRemoveIDs.add(mList.get(position).getID());
            }
            mList.remove(position);
            mList.add(new PictureData());

            calcAddPosition();
            notifyDataSetChanged();
            mRegisterCarPicture.checkCanGoNext();
        }
    }

    private void calcAddPosition() {
        mAddPosition = -1;
        for(int i=mList.size()-1; i>= 0; i--) {
            if(mList.get(i).isEmpty()) {
                mAddPosition = i;
            } else {
                break;
            }
        }
    }

    public int getRemainPictureCount() {
        return mAddPosition == -1 ? 0 : mLimitCount - mAddPosition;
    }

    public List<Long> getToRemoveIDs() {
        return mToRemoveIDs;
    }

    public List<PictureData> getList() {
        return mList;
    }
}
