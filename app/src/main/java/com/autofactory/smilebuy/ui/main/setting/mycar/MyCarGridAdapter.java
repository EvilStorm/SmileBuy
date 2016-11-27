package com.autofactory.smilebuy.ui.main.setting.mycar;


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
public class MyCarGridAdapter<T extends CarDataSimple> extends BaseAdapter {
    public enum Type {
        SELL_CAR,
        SHARE_CAR,
        FAVORITE_CAR,

        COUNT
    }

    private Activity mActivity = null;
    private LayoutInflater mInflater = null;
    private List<T> mList = new ArrayList<>();
    private boolean mRemovable = false;
    private Type mCarDataType = Type.SELL_CAR;

    private int mLayoutID = R.layout.item_my_car_grid;

    public MyCarGridAdapter(Activity activity, int layoutID) {
        mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
        mList.clear();
        mLayoutID = layoutID;
        mRemovable = false;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        final T item = mList.get(position);
        if(item == null) {
            return null;
        }

        if(convertView == null) {
            convertView = mInflater.inflate(mLayoutID, null);
        }

        ImageView imageView = Utility.getViewHolder(convertView, R.id.image);
        Glide.with(mActivity)
                .load(item.getMainPictureURL())
                .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                .into(imageView);


        TextView textView = Utility.getViewHolder(convertView, R.id.price);
        if(item.getCarDataType() == CarDataSimple.CAR_DATA_TYPE_CAR) {
            textView.setVisibility(View.VISIBLE);
            textView.setText(item.getPriceAsString(mActivity.getResources()));
        } else {
            textView.setVisibility(View.GONE);
        }

        imageView = Utility.getViewHolder(convertView, R.id.icon);
        if(mCarDataType == Type.SHARE_CAR) {
            imageView.setVisibility(View.GONE);
        } else {
            if(item.getSmileBuyID() >= 0) {
                imageView.setVisibility(View.VISIBLE);
                switch (item.getCarType()) {
                    case Constant.CAR_TYPE_ALLIANCE:
                        imageView.setImageResource(R.drawable.icon_cartype_02);
                        break;
                    case Constant.CAR_TYPE_COMMISSION:
                        imageView.setImageResource(R.drawable.icon_cartype_03);
                        break;
                    case Constant.CAR_TYPE_NORMAL:
                    default:
                        imageView.setImageResource(R.drawable.icon_cartype_01);
                        break;
                }
            } else {
                imageView.setVisibility(View.GONE);
            }
        }

        ImageButton remove = Utility.getViewHolder(convertView, R.id.remove);
        if(remove != null) {
            if (mRemovable) {
                remove.setVisibility(View.VISIBLE);
                remove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Utility.showPopupYesOrNo(mActivity, mActivity.getString(R.string.popup_message_remove_check), new PopupBase.OnClickListener() {
                            @Override
                            public void onClick() {
                                if (mCarDataType == Type.SELL_CAR) {
                                    ServerRequest.get().requestDeleteCar(item.getID(), new Response.Listener<ServerResult>() {
                                        @Override
                                        public void onResponse(ServerResult response) {
                                            mList.remove(item);
                                            notifyDataSetChanged();
                                        }
                                    });
                                } else if (mCarDataType == Type.FAVORITE_CAR) {
                                    ServerRequest.get().requestCarFavorite(item.getID(), new Response.Listener<CarFavoriteResult>() {
                                        @Override
                                        public void onResponse(CarFavoriteResult response) {
                                            if (!Application.get().isMyFavoriteCar(response.getCarData())) {
                                                mList.remove(item);
                                                notifyDataSetChanged();
                                            }
                                        }
                                    });
                                } else if (mCarDataType == Type.SHARE_CAR) {
                                    // TODO Request Remove Share Car
                                }
                            }
                        });
                    }
                });
            } else {
                remove.setVisibility(View.GONE);
            }
        }


        return convertView;
    }



    public void setList(List<T> list) {
        //mList = new ArrayList<>(list);
        mList = list;
        notifyDataSetChanged();
    }
    public void setRemovable(boolean removable) {
        mRemovable = removable;
        notifyDataSetChanged();
    }

    public boolean isRemovable() {
        return mRemovable;
    }

    public Type getCarDataType() {
        return mCarDataType;
    }

    public void setCarDataType(Type carDataType) {
        mCarDataType = carDataType;
    }
}
