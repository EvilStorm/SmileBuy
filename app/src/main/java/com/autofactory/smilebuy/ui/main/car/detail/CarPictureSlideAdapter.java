package com.autofactory.smilebuy.ui.main.car.detail;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.PagerAdapterWithListeners;
import com.autofactory.smilebuy.data.model.PictureData;
import com.autofactory.smilebuy.util.Constant;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by AirPhebe on 2015. 10. 20..
 */
public class CarPictureSlideAdapter extends PagerAdapterWithListeners {

    private List<PictureData> mPictures = null;

    private boolean mFromPictureDetail = false;

    public CarPictureSlideAdapter(Activity activity, ViewPager viewPager, boolean fromPictureDetail) {
        super(activity, viewPager);

        mFromPictureDetail = fromPictureDetail;
    }

    public void setPictures(List<PictureData> pictures) {
        mPictures = pictures;
        notifyDataSetChanged();
    }

    public void addPicture(String pictureURL) {
        if (mPictures == null) {
            mPictures = new ArrayList<>();
        }
        mPictures.add(new PictureData(pictureURL));
        notifyDataSetChanged();
    }

    public void removePicture(int index) {
        if (mPictures == null) {
            mPictures = new ArrayList<>();
        }
        mPictures.remove(index);
        notifyDataSetChanged();
    }
//    public void removePicture(String pictureURL) {
//        if(mPictures == null) {
//            mPictures = new ArrayList<>();
//        }
//        mPictures.remove(pictureURL);
//        notifyDataSetChanged();
//    }


    @Override
    public int getCount() {
        if (mPictures == null) {
            mPictures = new ArrayList<>();
        }
        return mPictures.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        //return super.instantiateItem(container, position);
        View view = mLayoutInflater.inflate(R.layout.item_car_picture_slide, null);
        final ImageView imageView = (ImageView) view.findViewById(R.id.imageView);

        if (mFromPictureDetail) {
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
            photoViewAttacher.update();
        } else {
//            ViewGroup.LayoutParams lp = imageView.getLayoutParams();
//            if (lp != null) {
//                lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                imageView.setLayoutParams(lp);
//            }
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, CarPictureDetailActivity.class);
                    intent.putParcelableArrayListExtra(Constant.DATA_PICTURE_LIST, (ArrayList<? extends Parcelable>) mPictures);
                    //intent.putStringArrayListExtra(Constant.DATA_PICTURE_URL_LIST, (ArrayList<String>) mPictures);
                    intent.putExtra(Constant.DATA_PICTURE_CURRENT_INDEX, position);
                    mActivity.startActivity(intent);
                    mActivity.overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
                }
            });
        }

        Glide.with(mActivity)
                .load(mPictures.get(position).getURL())
                .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (mFromPictureDetail) {
//                            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

                            PhotoViewAttacher photoViewAttacher = new PhotoViewAttacher(imageView);
                            photoViewAttacher.update();
                        }
                        return false;
                    }
                })
                .into(imageView);
//                .into(imageView, new Callback() {
//                    @Override
//                    public void onSuccess() {
//                        ViewGroup.LayoutParams lp = imageView.getLayoutParams();
//                        if (lp != null) {
//                            lp.height = ViewGroup.LayoutParams.MATCH_PARENT;
//                            imageView.setLayoutParams(lp);
//                        }
//                    }
//
//                    @Override
//                    public void onError() {
//
//                    }
//                });

        container.addView(view);
        return view;
    }

}
