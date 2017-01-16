package com.autofactory.smilebuy.application;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.model.UserData;
import com.autofactory.smilebuy.data.model.UserDataSimple;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.login.LoginActivity;
import com.autofactory.smilebuy.ui.main.car.list.CarListAdapter;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Log;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;
import com.crashlytics.android.Crashlytics;
import com.facebook.AccessToken;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.navercorp.volleyextensions.volleyer.Volleyer;
import com.navercorp.volleyextensions.volleyer.VolleyerConfiguration;
import com.navercorp.volleyextensions.volleyer.factory.DefaultRequestQueueFactory;
import com.navercorp.volleyextensions.volleyer.factory.DefaultVolleyerConfigurationFactory;
import com.navercorp.volleyextensions.volleyer.http.HttpContent;
import com.navercorp.volleyextensions.volleyer.multipart.Multipart;
import com.navercorp.volleyextensions.volleyer.request.creator.RequestCreator;
import com.navercorp.volleyextensions.volleyer.response.parser.NetworkResponseParser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import io.fabric.sdk.android.Fabric;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by AirPhebe on 2015. 11. 11..
 */
public class Application extends android.app.Application {
//    static {
//        com.android.volley.VolleyLog.DEBUG = true;
//    }

    private static Application _singleton = null;

    public static Application get() {
        return _singleton;
    }


    private Activity mActivity = null;

    private Tracker mGATracker = null;

    private RequestQueue mRequestQueue;
    //private ImageLoader mImageLoader;
    private int mVersion;
    private String mUUID;
    private AccessToken mFacebookToken = null;
    private String mLoginToken = null;
    private UserData mUserData = null;
    private int mLatestVersion;
    private String mVersionUpdateURL = "";
    private CarListAdapter mMainCarListAdapter = null;

    private boolean mGotAllPermissions = false;

    public boolean isGotAllPermissions() {
        return mGotAllPermissions;
    }

    synchronized private Tracker getGATracker() {
        if (mGATracker == null) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            // To enable debug logging use: adb shell setprop log.tag.GAv4 DEBUG
            mGATracker = analytics.newTracker(R.xml.global_tracker);
        }
        return mGATracker;
    }

    public void setGAScreen(String screenName) {
        getGATracker().setScreenName(screenName);
        getGATracker().send(new HitBuilders.ScreenViewBuilder().build());
        Log.d("GAScreen : " + screenName);
    }

    public void setGAEvent(String category, String action, String label){
        getGATracker().send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setAction(action)
                .setLabel(label)
                .build());
    }

    public Activity getActivity() {
        return mActivity;
    }

//    public ImageLoader getImageLoader() {
//        return mImageLoader;
//    }

    public int getVersion() {
        return mVersion;
    }

    public String getVersionAsString() {
        return convertVersionToString(mVersion);
    }

    public int getLatestVersion() {
        return mLatestVersion;
    }

    public String getLatestVersionAsString() {
        return convertVersionToString(mLatestVersion);
    }

    private String convertVersionToString(int versionInt) {
        String version = "";
        int v = versionInt;
        while (v / 10 != 0) {
            int r = v % 10;

            version = "." + r + version;

            v = v / 10;
        }
        version = v + version;

        if(version.length() < 4) {
            version = "0." + version;
        }
        return version;
    }

    public String getUUID() {
        return mUUID;
    }

    public AccessToken getFacebookToken() {
        return mFacebookToken;
    }

    public void setFacebookToken(AccessToken facebookToken) {
        mFacebookToken = facebookToken;
        AccessToken.setCurrentAccessToken(mFacebookToken);
    }

    public boolean isLoginWithFacebook() {
        return mFacebookToken != null;
    }

    public String getLoginToken() {
        return mLoginToken;
    }

    public UserData getUserData() {
        return mUserData;
    }

    public boolean isMe(UserDataSimple userData) {
        return userData == null ? false : (mUserData == null ? false : userData.getID() == mUserData.getID());
    }

    public boolean isMyFavoriteCar(CarData carData) {
        return carData == null ? false : (mUserData == null ? false : carData.isFavoriteUser(mUserData.getID()));
    }

    public boolean isMyRequestedCar(CarData carData) {
        return carData == null ? false : (mUserData == null ? false : mUserData.isMyRequestedCar(carData.getID()));
    }

    public boolean isManager() {
        return mUserData == null ? false : mUserData.getType() != Constant.USER_TYPE_USER;
    }

    public boolean isThereUpdateExist() {
        return mVersion < mLatestVersion;
    }

    public void setMainCarListAdapter(CarListAdapter carListAdapter) {
        mMainCarListAdapter = carListAdapter;
    }
    public void updateCarInMainList(CarData carData) {
        if(mMainCarListAdapter != null) {
            mMainCarListAdapter.updateCarInList(carData);
        }
    }
    public void deleteCarFromMainList(long carID) {
        if(mMainCarListAdapter != null) {
            mMainCarListAdapter.deleteCarFromList(carID);
        }
    }

    public void DoUpdate() {
        try{
            mActivity.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(mVersionUpdateURL)));
        } catch (Exception e) {
            Utility.showPopupOk(Application.get().getActivity(), "삭제 후 마켓에서 다시 다운로드 받아주세요.", new PopupBase.OnClickListener() {
                @Override
                public void onClick() {
                    Application.get().getActivity().finish();
                }
            });
        }
    }

    public void onLoginSuccess(String loginToken, UserData userData, int latestVersion, String versionUpdateURL) {
        mLoginToken = loginToken;
        mUserData = userData;
        mLatestVersion = latestVersion;
        mVersionUpdateURL = versionUpdateURL;

        Log.d("LoginToken : " + mLoginToken + " latestVersion : " + latestVersion);
    }

    public void onUserDataUpdated(UserData userData) {
        mUserData.update(userData);
    }

    public void logout() {
        mFacebookToken = null;
        AccessToken.setCurrentAccessToken(null);

        setPreferenceBoolean(Constant.PREF_KEY_AUTO_LOGIN, false);
        setPreferenceString(Constant.PREF_KEY_EMAIL, null);
        setPreferenceString(Constant.PREF_KEY_PWD, null);

        Intent i = new Intent(mActivity, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(i);
        //mActivity.finish();
    }

    public void reLogin() {
        Intent i = new Intent(mActivity, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mActivity.startActivity(i);
    }

    private SharedPreferences getPreferences() {
        return getSharedPreferences("smilebuy", MODE_PRIVATE);
    }

    public String getPreferenceString(String key) {
        return getPreferences().getString(key, null);
    }

    public void setPreferenceString(String key, String data) {
        SharedPreferences.Editor editor = getPreferences().edit();
        if (data == null) {
            editor.remove(key);
        } else {
            editor.putString(key, data);
        }
        editor.commit();
    }

    public boolean getPreferenceBoolean(String key) {
        return getPreferences().getBoolean(key, false);
    }

    public void setPreferenceBoolean(String key, boolean data) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putBoolean(key, data);
        editor.commit();
    }

    public int getPreferenceInt(String key) {
        return getPreferences().getInt(key, 0);
    }

    public void setPreferenceInt(String key, int data) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putInt(key, data);
        editor.commit();
    }


    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        //initTypekit();
        initCalligraphy();
        initVolleyer();
        //initAUIL();
        //initImageLoader();
        //initUrQA();


        mVersion = 0;
        mUUID = "";


        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
                mActivity = activity;
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mActivity = activity;
            }

            @Override
            public void onActivityResumed(Activity activity) {
                mActivity = activity;
            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (mActivity == activity) {
                    mActivity = null;
                }
            }
        });

        _singleton = this;
    }

    public void initAfterGotPermissions() {
        mVersion = 0;
        try {
            String[] versions = getPackageManager().getPackageInfo(getPackageName(), 0).versionName.split("\\.");
            for (int i = 0; i < versions.length; i++) {
                int v = Utility.parseInt(versions[i]);
                for (int j = versions.length - 1; j > i; j--) {
                    v *= 10;
                }
                mVersion += v;
            }
        } catch (Exception e) {
            mVersion = 1;
            e.printStackTrace();
        }
        mUUID = makeUUID();

        Log.init(this);
    }




    private void initCalligraphy() {
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/NotoReg.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

//    private void initTypekit() {
//        Typekit.getInstance().add
//    }

    private void initVolleyer() {
        mRequestQueue = DefaultRequestQueueFactory.create(this);
        mRequestQueue.start();


        RequestCreator requestCreator = new RequestCreator() {
            @Override
            public <T> Request<T> createRequest(final HttpContent httpContent, final Class<T> clazz, final NetworkResponseParser responseParser, final Response.Listener<T> listener, Response.ErrorListener errorListener) {
                // TYPE 1
                Multipart multipart = httpContent.getMultipart();
                final String contentType = multipart.getContentType();
                final ByteArrayOutputStream os = new ByteArrayOutputStream();
                try {
                    multipart.write(os);
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        os.flush();
                        os.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                httpContent.addHeader("Content-Length", "" + os.toByteArray().length);

                Request<T> request = new Request<T>(httpContent.getMethod().getMethodCode(), httpContent.getUrl(), errorListener) {
                    @Override
                    protected Response<T> parseNetworkResponse(NetworkResponse response) {
                        return responseParser.parseNetworkResponse(response, clazz);
                    }

                    @Override
                    protected void deliverResponse(T response) {
                        listener.onResponse(response);
                    }

                    @Override
                    public byte[] getBody() throws AuthFailureError {
                        return os.toByteArray();
                    }

                    @Override
                    public String getBodyContentType() {
                        return contentType;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        return httpContent.getHeaders();
                    }
                };

                httpContent.getMultipart().clear();


                // TYPE 2
//                Request<T> request = new VolleyerRequest<T>(httpContent, clazz, responseParser, listener, errorListener);
                request.setRetryPolicy(new RetryPolicy() {
                    @Override
                    public int getCurrentTimeout() {
                        return 10000;
                    }

                    @Override
                    public int getCurrentRetryCount() {
                        return 0;
                    }

                    @Override
                    public void retry(VolleyError error) throws VolleyError {

                    }
                });
                return request;
            }
        };

        VolleyerConfiguration configuration = new VolleyerConfiguration(requestCreator,
                DefaultVolleyerConfigurationFactory.createRequestExecutor(),
                ServerRequest.get().getResponseParser(),
                ServerRequest.get().getErrorListener());

//        VolleyerConfiguration configuration = new VolleyerConfiguration(DefaultVolleyerConfigurationFactory.createRequestCreator(),
//                DefaultVolleyerConfigurationFactory.createRequestExecutor(),
//                ServerRequest.get().getResponseParser(),
//                ServerRequest.get().getErrorListener());


        Volleyer.volleyer(mRequestQueue).settings().setAsDefault().setConfiguration(configuration).done();
    }

//    private void initImageLoader() {
//        mImageLoader = new ImageLoader(mRequestQueue, new UniversalLruMemoryCache(getCacheSize()));
//    }

//    private void initAUIL() {
//        // Init AUIL
//        DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
////                .showImageOnLoading(R.drawable.ic_stub) // resource or drawable
////                .showImageForEmptyUri(R.drawable.ic_empty) // resource or drawable
////                .showImageOnFail(R.drawable.ic_error) // resource or drawable
////                .resetViewBeforeLoading(true)  // default : false
////                .delayBeforeLoading(1000)
//                .cacheInMemory(true) // default : false
//                .cacheOnDisc(true)
//                        //.cacheOnDisk(true) // default : false
////                .preProcessor(...)
////                .postProcessor(...)
////                .extraForDownloader(...)
////                .considerExifParams(false) // default
////                .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2) // default
////                .bitmapConfig(Bitmap.Config.ARGB_8888) // default
////                .decodingOptions(...)
////                .displayer(new SimpleBitmapDisplayer()) // default
////                .handler(new Handler()) // default
//
//                .build();
//
//        ImageLoaderConfiguration imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(getApplicationContext())
////                .memoryCacheExtraOptions(480, 800) // default = device screen dimensions
////                .diskCacheExtraOptions(480, 800, null)
////                .taskExecutor(...)
////                .taskExecutorForCachedImages(...)
////                .threadPoolSize(3) // default
////                .threadPriority(Thread.NORM_PRIORITY - 2) // default
////                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
////                .denyCacheImageMultipleSizesInMemory()
////                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
////                .memoryCacheSize(2 * 1024 * 1024)
////                .memoryCacheSizePercentage(13) // default
////                .diskCache(new UnlimitedDiskCache(cacheDir)) // default
////                .diskCacheSize(50 * 1024 * 1024)
////                .diskCacheFileCount(100)
////                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
////                .imageDownloader(new BaseImageDownloader(getApplicationContext())) // default
////                .imageDecoder(new BaseImageDecoder()) // default
//                .defaultDisplayImageOptions(displayImageOptions) // default
////                .writeDebugLogs()
//                .build();
//        com.nostra13.universalimageloader.core.ImageLoader.getInstance().init(imageLoaderConfiguration);
//    }

//    private void initUrQA() {
//        URQAController.InitializeAndStartSession(this, Constant.URQA_API_KEY);
//    }

    private int getCacheSize() {
        final DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        final int screenWidth = displayMetrics.widthPixels;
        final int screenHeight = displayMetrics.heightPixels;
        // 4 bytes per pixel
        final int screenBytes = screenWidth * screenHeight * 4;

        return screenBytes * 3;
    }

//    private int getBitmapScale() {
//
//    }

    private String makeUUID() {
        final TelephonyManager tm = (TelephonyManager) getBaseContext().getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }
}
