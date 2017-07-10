package com.autofactory.smilebuy.util;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.ui.login.LoginActivity;
import com.autofactory.smilebuy.util.popup.PopupBase;
import com.autofactory.smilebuy.util.popup.PopupMakeInterview;
import com.autofactory.smilebuy.util.popup.PopupOneButton;
import com.autofactory.smilebuy.util.popup.PopupTwoButton;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Dictionary;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 10. 12..
 */
public class Utility {

    public static SimpleDateFormat DATE_FORMAT_FULL = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    /**
     * Helper for View Holder
     */
    public static <T extends View> T getViewHolder(View convertView, int id) {
        SparseArray<View> viewHolder = addViewHolder(convertView);

        View childView = viewHolder.get(id);

        if (childView == null) {
            childView = convertView.findViewById(id);
            viewHolder.put(id, childView);
        }

        return (T) childView;
    }

    public static SparseArray<View> addViewHolder(View convertView) {
        SparseArray<View> viewHolder = (SparseArray<View>) convertView.getTag();

        if (viewHolder == null) {
            viewHolder = new SparseArray<>();
            convertView.setTag(viewHolder);
        }

        return viewHolder;
    }

    public static boolean isEmailValid(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPhoneNumValid(CharSequence phoneNum) {
        return phoneNum != null && Patterns.PHONE.matcher(phoneNum).matches();
    }


    private static TypedValue sTypedValue = new TypedValue();
    private static int sActionBarHeight;
    private static Dictionary<Integer, Integer> sListViewItemHeights = new Hashtable<Integer, Integer>();
    //private static Dictionary<Integer, Integer> sRecyclerViewItemHeights = new Hashtable<Integer, Integer>();

    public static int dpToPx(Context context, int dp) {
        int px = Math.round(dp * getPixelScaleFactor(context));
        return px;
    }

    public static int pxToDp(Context context, int px) {
        int dp = Math.round(px / getPixelScaleFactor(context));
        return dp;
    }

    private static float getPixelScaleFactor(Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            return ((BitmapDrawable) drawable).getBitmap();
        }

        // We ask for the bounds if they have been set as they would be most
        // correct, then we check we are  > 0
        final int width = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().width() : drawable.getIntrinsicWidth();

        final int height = !drawable.getBounds().isEmpty() ?
                drawable.getBounds().height() : drawable.getIntrinsicHeight();

        // Now we check we are > 0
        final Bitmap bitmap = Bitmap.createBitmap(width <= 0 ? 1 : width, height <= 0 ? 1 : height,
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public static Bitmap getCircularBitmapImage(Bitmap source) {
        int size = Math.min(source.getWidth(), source.getHeight());
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
        Bitmap squaredBitmap = Bitmap.createBitmap(source, x, y, size, size);
        if (squaredBitmap != source) {
            source.recycle();
        }
        Bitmap bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader = new BitmapShader(squaredBitmap, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAntiAlias(true);
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        squaredBitmap.recycle();
        return bitmap;
    }




    public static int getScrollY(ListView lv) {
        View c = lv.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = lv.getFirstVisiblePosition();
        int top = c.getTop();

        int scrollY = -top + firstVisiblePosition * c.getHeight();
        return scrollY;
    }

    public static int getScrollY(AbsListView lv) {
        View c = lv.getChildAt(0);
        if (c == null) {
            return 0;
        }

        int firstVisiblePosition = lv.getFirstVisiblePosition();
        int scrollY = -(c.getTop());
//        int scrollY = 0;


        sListViewItemHeights.put(lv.getFirstVisiblePosition(), c.getHeight());

//        if(scrollY>0)
//            Log.d("QuickReturnUtils", "getScrollY() : -(c.getTop()) - "+ -(c.getTop()));
//        else
//            Log.i("QuickReturnUtils", "getScrollY() : -(c.getTop()) - "+ -(c.getTop()));

        if (scrollY < 0)
            scrollY = 0;

        for (int i = 0; i < firstVisiblePosition; ++i) {
//            Log.d("QuickReturnUtils", "getScrollY() : i - "+i);

//            Log.d("QuickReturnUtils", "getScrollY() : sListViewItemHeights.get(i) - "+sListViewItemHeights.get(i));

            if (sListViewItemHeights.get(i) != null) // (this is a sanity check)
                scrollY += sListViewItemHeights.get(i); //add all heights of the views that are gone

        }

//        Log.d("QuickReturnUtils", "getScrollY() : scrollY - "+scrollY);

        return scrollY;
    }

//    public static int getScrollY(RecyclerView rv, int columnCount) {
//        View c = rv.getChildAt(0);
//        if (c == null) {
//            return 0;
//        }
//
//        LinearLayoutManager layoutManager = (LinearLayoutManager)rv.getLayoutManager();
//        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
//
////        Log.d("", "getScrollY() : firstVisiblePosition - " + firstVisiblePosition);
//
//        int scrollY = -(c.getTop());
//
////        Log.d("", "getScrollY() : scrollY - " + scrollY);
//
//        if(columnCount > 1){
//            sRecyclerViewItemHeights.put(firstVisiblePosition, c.getHeight() + QuickReturnUtils.dp2px(rv.getContext(), 8)/columnCount);
//        } else {
//            sRecyclerViewItemHeights.put(firstVisiblePosition, c.getHeight());
//        }
//
//        if(scrollY<0)
//            scrollY = 0;
//
//        for (int i = 0; i < firstVisiblePosition; ++i) {
//            if (sRecyclerViewItemHeights.get(i) != null) // (this is a sanity check)
//                scrollY += sRecyclerViewItemHeights.get(i); //add all heights of the views that are gone
//        }
//
//        return scrollY;
//    }


    public static int getActionBarHeight(Context context) {
        if (sActionBarHeight != 0) {
            return sActionBarHeight;
        }

        context.getTheme().resolveAttribute(android.R.attr.actionBarSize, sTypedValue, true);
        sActionBarHeight = TypedValue.complexToDimensionPixelSize(sTypedValue.data, context.getResources().getDisplayMetrics());
        return sActionBarHeight;
    }

    public static boolean isConnectionAvailable(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()
                    && netInfo.isConnectedOrConnecting()
                    && netInfo.isAvailable()) {
                return true;
            }
        }
        return false;
    }

    public static boolean hasCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
    }

    // method to check you have Camera Apps
    public static boolean hasDefualtCameraApp(Context context) {
        final PackageManager packageManager = context.getPackageManager();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);

        return list.size() > 0;
    }


//    public static <T extends Object> List<T> cloneList(List<T> list, Class<T> clazz) {
//        List<T> retList = new ArrayList<>(list.size());
//        for(int i=0; i<list.size(); i++) {
//            try {
//                T item = clazz.newInstance();
//
//            } catch (InstantiationException e) {
//                e.printStackTrace();
//                continue;
//            } catch (IllegalAccessException e) {
//                e.printStackTrace();
//                continue;
//            }
//
//            retList.add((list.get(i)));
//        }
//    }

    public static File createTemporaryImageFile(Context context) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = context.getExternalFilesDir("TAKE_PHOTO");
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        //mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }

    public static void setScaledImage(ImageView imageView, String filePath) {
        // Get the dimensions of the View
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW / targetW, photoH / targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;

        Bitmap bitmap = BitmapFactory.decodeFile(filePath, bmOptions);
        imageView.setImageBitmap(bitmap);
    }

    public static String getTimeFromSec(int sec) {
        int h = sec / 3600;
        int m = (sec % 3600) / 60;
        int s = (sec % 3600) % 60;
        return h == 0 ? (m == 0 ? String.format("%02d", s) : String.format("%d:%02d", m, s)) : String.format("%d:%d:%02d", h, m, s);
    }

    public static void setSubTextColor(TextView view, String subtext, int color) {
        Spannable str = (Spannable) view.getText();
        int i = view.getText().toString().indexOf(subtext);
        if (i >= 0) {
            str.setSpan(new ForegroundColorSpan(color), i, i + subtext.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
    }

    public static float parseFloat(String string) {
        float ret = 0;
        try {
            ret = Float.parseFloat(string);
        } catch(Exception e) {
            ret = -1;
        }
        return ret;
    }

    public static int parseInt(String string) {
        int ret = 0;
        try {
            ret = Integer.parseInt(string);
        } catch(Exception e) {
            ret = -1;
        }
        return ret;
    }

    public static long parseLong(String string) {
        long ret = 0;
        try {
            ret = Long.parseLong(string);
        } catch (Exception e) {
            ret = -1;
        }
        return ret;
    }

    public static int getServerIntFromBoolean(boolean b) {
        return b ? 1 : 0;
    }

    public static Constant.CheckSmileMan checkSmileManAvailalbe(Context context, int areaIndex) {
        if (areaIndex == 0) {
            return Constant.CheckSmileMan.CHOOSE_AREA;
        }

        boolean isAvalable = true;
        int[] notYet = context.getResources().getIntArray(R.array.area_not_yet);
        if (notYet != null) {
            for (int i = 0; i < notYet.length; i++) {
                if (notYet[i] == areaIndex) {
                    isAvalable = false;
                    break;
                }
            }
        }

        return isAvalable ? Constant.CheckSmileMan.AVAILABLE : Constant.CheckSmileMan.NOT_YET;
    }

    public static String getPhoneNumber(Context context) {
        try {
            TelephonyManager tMgr = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tMgr == null) {
                return null;
            }
            return tMgr.getLine1Number();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getNationalNumberIncludeLeadingZero(Phonenumber.PhoneNumber phoneNumber) {
        String number = "";
        for (int i = 0; i < phoneNumber.getNumberOfLeadingZeros(); i++) {
            number += "0";
        }
        return number + phoneNumber.getNationalNumber();
    }

    private static ProgressDialog mProgressDialog = null;

    public static void showProgressDialog(Activity activity, String message) {
        View focusView = activity.getCurrentFocus();
        if(focusView != null) {
            ((InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(focusView.getWindowToken(), 0);
        }

        hideProgressDialog();
        mProgressDialog = new ProgressDialog(new ContextThemeWrapper(activity, android.R.style.Theme_Holo_Light));
        if(message == null || message.length() <= 0) {
            message = activity.getString(R.string.popup_progress_waiting);
        }
        mProgressDialog.setMessage(message);
        mProgressDialog.setCancelable(false);
        mProgressDialog.show();
    }

    public static void showProgressDialog(Activity activity) {
        showProgressDialog(activity, "");
    }

    public static void hideProgressDialog() {
        try {
            if (mProgressDialog != null) {
                if (mProgressDialog.isShowing()) {
                    mProgressDialog.dismiss();
                }
                mProgressDialog = null;
            }
        } catch (Exception e) {
            mProgressDialog = null;
        }
    }

    public static void showPopupOk(Activity activity, String message) {
        showPopupOk(activity, "", message);
    }

    public static void showPopupOk(Activity activity, String title, String message) {
        showPopupOk(activity, title, message, null);
    }

    public static void showPopupOk(Activity activity, String message, PopupBase.OnClickListener onClickListener) {
        showPopupOk(activity, "", message, onClickListener);
    }

    public static void showPopupOk(Activity activity, String title, String message, PopupBase.OnClickListener onClickListener) {
        PopupOneButton popup = new PopupOneButton(activity);
        popup.setTitle(title);
        popup.setMessage(message);
        popup.setOneButtonListener(onClickListener);
        popup.show();
    }

    public static void showPopupYesOrNo(Activity activity, String message, PopupBase.OnClickListener yesClickListener) {
        showPopupYesOrNo(activity, "", message, yesClickListener);
    }

    public static void showPopupYesOrNo(Activity activity, String title, String message, PopupBase.OnClickListener yesClickListener) {
        showPopupYesOrNo(activity, title, message, yesClickListener, null);
    }

    public static void showPopupYesOrNo(Activity activity, String message, PopupBase.OnClickListener yesClickListener, PopupBase.OnClickListener noClickListener) {
        showPopupYesOrNo(activity, "", message, yesClickListener, noClickListener);
    }

    public static void showPopupYesOrNo(Activity activity, String title, String message, PopupBase.OnClickListener yesClickListener, PopupBase.OnClickListener noClickListener) {
        PopupTwoButton popup = new PopupTwoButton(activity);
        popup.setTitle(title);
        popup.setMessage(message);
        popup.setOneButtonListener(yesClickListener);
        popup.setTwoButtonListener(noClickListener);
        popup.show();
    }

    public static void showLoginPop() {
        showPopupYesOrNo(Application.get().getActivity(), Application.get().getString(R.string.popup_message_require_lgin), new PopupBase.OnClickListener() {
            @Override
            public void onClick() {
                Application.get().getActivity().startActivity(new Intent(Application.get().getActivity(), LoginActivity.class));
                Application.get().getActivity().finish();
            }
        }, new PopupBase.OnClickListener() {
            @Override
            public void onClick() {

            }
        });
    }


//    public static void showAlertDialog(NormalActivity activity, String message) {
//        AlertDialog dialog = new AlertDialog.Builder(activity)
//                .setMessage(message)
//                .setPositiveButton(activity.getString(R.string.action_confirm), new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                })
//                .setCancelable(false)
//                .create();
//        dialog.show();
//    }
//
//    public static void showAlertDialog(NormalActivity activity, String message, DialogInterface.OnClickListener okListener) {
//        AlertDialog dialog = new AlertDialog.Builder(activity)
//                .setMessage(message)
//                .setPositiveButton(activity.getString(R.string.action_confirm), okListener)
//                .setCancelable(false)
//                .create();
//        dialog.show();
//    }

    public static void showPopupMakeInterview(Activity activity, final StringBuilder returnString, final PopupBase.OnClickListener confirmListener) {
        final PopupMakeInterview popup = new PopupMakeInterview(activity);
        popup.setOneButtonListener(new PopupBase.OnClickListener() {
            @Override
            public void onClick() {
                returnString.append(popup.getContent());
                confirmListener.onClick();
            }
        });
        popup.show();
    }
}
