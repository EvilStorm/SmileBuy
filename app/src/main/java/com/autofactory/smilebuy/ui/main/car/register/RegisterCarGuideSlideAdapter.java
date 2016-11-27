package com.autofactory.smilebuy.ui.main.car.register;

import android.app.Activity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.PagerAdapterWithListeners;
import com.bumptech.glide.Glide;


/**
 * Created by AirPhebe on 2015. 10. 30..
 */
public class RegisterCarGuideSlideAdapter extends PagerAdapterWithListeners {
    private class SellGuide {
        private int mDrawableID;
        private int mStringID;
        public SellGuide(int drawableID, int stringID) {
            mDrawableID = drawableID;
            mStringID = stringID;
        }

        public int getDrawableID() { return mDrawableID; }
        public int getStringID() { return mStringID; }
    }
    private final int SELL_GUIDE_COUNT = 3;
    private SellGuide []sellGuides = null;


    public RegisterCarGuideSlideAdapter(Activity activity, ViewPager viewPager) {
        super(activity, viewPager);

        sellGuides = new SellGuide[SELL_GUIDE_COUNT];
        sellGuides[0] = new SellGuide(R.drawable.sell_picture_guide_1, R.string.content_sell_picture_guide_1);
        sellGuides[1] = new SellGuide(R.drawable.sell_picture_guide_2, R.string.content_sell_picture_guide_2);
        sellGuides[2] = new SellGuide(R.drawable.sell_picture_guide_3, R.string.content_sell_picture_guide_3);
    }

    @Override
    public int getCount() {
        return SELL_GUIDE_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mLayoutInflater.inflate(R.layout.item_register_car_1_picture_guide, null);
        ImageView image = (ImageView) view.findViewById(R.id.image);
        TextView text = (TextView) view.findViewById(R.id.text);


//        Glide.with(mActivity)
//                .load(sellGuides[position].getDrawableID())
//                .into(image);
        image.setImageResource(sellGuides[position].getDrawableID());
        text.setText(sellGuides[position].getStringID());

        container.addView(view);
        return view;
    }

}
