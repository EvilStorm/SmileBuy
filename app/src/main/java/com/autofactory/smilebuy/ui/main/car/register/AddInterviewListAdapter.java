package com.autofactory.smilebuy.ui.main.car.register;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.model.InterviewData;
import com.autofactory.smilebuy.data.model.UserDataSimple;
import com.autofactory.smilebuy.data.server.CarListResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.main.car.smilebuy.CarSmileBuyActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 11..
 */
public class AddInterviewListAdapter extends BaseAdapter {

    private Activity mActivity = null;
    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private String []mQuestions = null;

    public AddInterviewListAdapter(Activity activity, String []questions) {
        mActivity = activity;
        mContext = activity;
        mInflater = LayoutInflater.from(mContext);
        mQuestions = questions;
    }

    @Override
    public int getCount() {
        return mQuestions == null ? 0 : mQuestions.length;
    }

    @Override
    public Object getItem(int position) {
        if(mQuestions == null)
            return null;
        if(position < 0 || position >= mQuestions.length)
            return null;
        return mQuestions[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String question = (String) getItem(position);
        if(question == null) {
            return null;
        }

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_add_interview_list, null);
        }

        ((TextView) Utility.getViewHolder(convertView, R.id.num)).setText(String.format("Q%d", position+1));
        ((TextView) Utility.getViewHolder(convertView, R.id.question)).setText(question);

        return convertView;
    }
}
