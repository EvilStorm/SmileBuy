package com.autofactory.smilebuy.ui.main.car.comment;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.autofactory.smilebuy.component.GlideCircleTransform;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.model.CommentData;
import com.autofactory.smilebuy.data.model.InterviewData;
import com.autofactory.smilebuy.data.model.UserDataSimple;
import com.autofactory.smilebuy.data.server.CarCommentResult;
import com.autofactory.smilebuy.data.server.CarFavoriteResult;
import com.autofactory.smilebuy.data.server.CarListResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.main.car.smilebuy.CarSmileBuyActivity;
import com.autofactory.smilebuy.ui.main.user.UserProfileActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;
import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 11..
 */
public class CommentAdapter extends BaseAdapter {

    private CommentActivity mActivity = null;
    private Context mContext = null;
    private LayoutInflater mInflater = null;
    private List<CommentData> mList = null;

    public CommentAdapter(CommentActivity activity, List<CommentData> list) {
        mActivity = activity;
        mContext = activity;
        mInflater = LayoutInflater.from(mContext);
        mList = list;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public CommentData getItem(int position) {
        return mList == null ? null : mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(mList == null) {
            return null;
        }

        final CommentData item = mList.get(position);
        final UserDataSimple user = item.getUser();
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_comment, null);
        }


        if (user != null) {
            ImageView imageView = Utility.getViewHolder(convertView, R.id.userPic);
            if (user.getProfilePic() != null && user.getProfilePic().length() > 0) {
                Glide.with(mActivity)
                        .load(user.getProfilePic())
                        .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                        .bitmapTransform(new GlideCircleTransform(mActivity))
                        .into(imageView);
            } else {
                imageView.setImageDrawable(null);
            }
            ((TextView) Utility.getViewHolder(convertView, R.id.userName)).setText(user.getNickName());
            Utility.getViewHolder(convertView, R.id.userSelect).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(mActivity, UserProfileActivity.class);
                    i.putExtra(UserProfileActivity.USER_DATA_SIMPLE, user);
                    mActivity.startActivity(i);
                }
            });
        }

        ((TextView) Utility.getViewHolder(convertView, R.id.date)).setText(item.getDateAsShort());
        ((TextView) Utility.getViewHolder(convertView, R.id.content)).setText(item.getMessage());

        ImageView imageView = Utility.getViewHolder(convertView, R.id.image);
        if(item.getPicture() != null && item.getPicture().length() > 0) {
            imageView.setVisibility(View.VISIBLE);
            Glide.with(mActivity)
                    .load(item.getPicture())
                    .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                    .into(imageView);
        } else {
            imageView.setVisibility(View.GONE);
        }

        ImageButton remove = Utility.getViewHolder(convertView, R.id.remove);
        if(Application.get().isMe(user)) {
            remove.setVisibility(View.VISIBLE);
            remove.setFocusable(false);
            remove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Utility.showPopupYesOrNo(mActivity, mActivity.getString(R.string.popup_message_remove_check), new PopupBase.OnClickListener() {
                        @Override
                        public void onClick() {
                            ServerRequest.get().requestDeleteComment(item.getID(), new Response.Listener<CarCommentResult>() {
                                @Override
                                public void onResponse(CarCommentResult response) {
                                    mActivity.onCommentDeleted(response.getCarData());
                                }
                            });
                        }
                    });
                }
            });
        } else {
            remove.setVisibility(View.GONE);
        }



        return convertView;
    }

    public void setList(List<CommentData> list) {
        mList = list;
        notifyDataSetChanged();
    }
}
