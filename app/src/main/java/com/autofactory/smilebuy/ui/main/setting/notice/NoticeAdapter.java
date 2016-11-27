package com.autofactory.smilebuy.ui.main.setting.notice;


import android.app.Activity;
import android.support.annotation.NonNull;
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
import com.autofactory.smilebuy.data.model.NoticeData;
import com.autofactory.smilebuy.data.server.CarFavoriteResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 11..
 */
public class NoticeAdapter extends BaseAdapter {
    private Activity mActivity = null;
    private LayoutInflater mInflater = null;
    private List<NoticeData> mList = null;

    public NoticeAdapter(Activity activity, @NonNull List<NoticeData> list) {
        mActivity = activity;
        mInflater = LayoutInflater.from(mActivity);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public NoticeData getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NoticeData item = mList.get(position);

        if(convertView == null) {
            convertView = mInflater.inflate(R.layout.item_notice, null);
        }

        ImageView imageView = Utility.getViewHolder(convertView, R.id.image);
        if(item.getImageResID() >= 0) {
            imageView.setVisibility(View.VISIBLE);
            imageView.setImageResource(item.getImageResID());
        } else {
            imageView.setImageDrawable(null);
            imageView.setVisibility(View.GONE);
        }

        TextView textView = Utility.getViewHolder(convertView, R.id.subject);
        textView.setText(item.getSubject());
        textView = Utility.getViewHolder(convertView, R.id.date);
        textView.setText(item.getDate());

        TextView content = Utility.getViewHolder(convertView, R.id.content);
        content.setText(item.getContent());


        ImageView arrow = Utility.getViewHolder(convertView, R.id.arrow);
        if(item.isOpened()) {
            arrow.setImageResource(R.drawable.icon_list_open);
            content.setVisibility(View.VISIBLE);
        } else {
            arrow.setImageResource(R.drawable.icon_list_close);
            imageView.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
        }

        return convertView;
    }
}
