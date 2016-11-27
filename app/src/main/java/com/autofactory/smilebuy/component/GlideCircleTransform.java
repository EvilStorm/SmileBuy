package com.autofactory.smilebuy.component;

import android.content.Context;
import android.graphics.Bitmap;

import com.autofactory.smilebuy.util.Utility;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
/**
 * Created by Phebe on 16. 3. 23..
 */
public class GlideCircleTransform extends BitmapTransformation {
    public GlideCircleTransform(Context context) {
        super(context);
    }

    public GlideCircleTransform(BitmapPool bitmapPool) {
        super(bitmapPool);
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return Utility.getCircularBitmapImage(toTransform);
    }

    @Override
    public String getId() {
        return "glide_circle";
    }
}
