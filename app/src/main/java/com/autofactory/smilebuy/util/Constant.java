package com.autofactory.smilebuy.util;

/**
 * Created by AirPhebe on 2015. 10. 20..
 */
public class Constant {

    private static final boolean IS_TEST = false;
    public static final boolean IS_PLAY_STORE_UPDATE = false;

    public static final String URQA_API_KEY = "CFD9819F";
    public static final String SMS_RECEIVE_SENDER = "0318556890";

    public static final String IMAGE_DOMAIN_S3_TOKYO = "http://smilebuy-image.s3.amazonaws.com/";

    public static final int REQUEST_CODE_FINISH_ACTIVITY = 99999;

    public static final int CLIENT_TYPE_USER = 0;
    public static final int CLIENT_TYPE_MANAGER = 1;
    public static final int CLIENT_TYPE = CLIENT_TYPE_USER;

    //public static final String SERVER_ADDRESS = "http://ec2-52-192-86-68.ap-northeast-1.compute.amazonaws.com";
    //public static final String SERVER_ADDRESIMAGE_DOMAIN_S3_TOKYOS = "http://112.171.55.131:8000";

    //스마일 바이 Python Server
//    private static final String SERVER_ADDRESS = "http://api.autofactory.or.kr";
    //스마일바이 SpringBoot Server
    private static final String SERVER_ADDRESS = "http://smilebuy.ap-northeast-2.elasticbeanstalk.com";
    private static final String TEST_SERVER_ADDRESS = "http://192.168.0.3:8080";

    public static String getServerUrl() {
        if(IS_TEST){
            return TEST_SERVER_ADDRESS;
        }
        return SERVER_ADDRESS;
    }

    private static final String CLIENT_MARKET_TYPE_ANDROID = "android";
    private static final String CLIENT_MARKET_TYPE_ONE_STORE = "android_one_store";
    public static final int USER_TYPE_USER = 0;          // 0 : normal user, other : manager

    public static final int CAR_TYPE_ALL = -1;
    public static final int CAR_TYPE_NORMAL = 0;
    public static final int CAR_TYPE_ALLIANCE = 1;
    public static final int CAR_TYPE_COMMISSION = 2;

    public static final int SMILEMAN_SERVICE_TYPE_CHECK_SELLER = 0;
    public static final int SMILEMAN_SERVICE_TYPE_CHECK_BUYER = 1;
    //public static final int SMILEMAN_SERVICE_TYPE_PACK_SELLER = 2;
    //public static final int SMILEMAN_SERVICE_TYPE_PACK_BUYER = 3;

    public static final String SERVER_REQ_REGISTER = "/user/register/";
    public static final String SERVER_REQ_VERIFY_PHONE = "/user/mobile_auth/request/";
    public static final String SERVER_REQ_CHECK_SECRET = "/user/mobile_auth/check/";
    public static final String SERVER_REQ_LOGIN = "/user/login/";
    public static final String SERVER_REQ_FIND_PW = "/user/find_pw/";
    public static final String SERVER_REQ_WITHDRAW = "/user/unregister/";
    public static final String SERVER_REQ_CHANGE_PWD_LOGIN = "/user/update_profile/change_password/login/";
    public static final String SERVER_REQ_CHANGE_PWD_SAVE = "/user/update_profile/change_password/save/";

    public static final String SERVER_REQ_UPDATE_USER_NAME = "/user/update_profile/nickname/";
    public static final String SERVER_REQ_UPDATE_USER_PROFILE_PIC = "/user/update_profile/profile_picture/";
    public static final String SERVER_REQ_UPDATE_USER_PROFILE_BG = SERVER_REQ_UPDATE_USER_PROFILE_PIC;
    public static final String SERVER_REQ_UPDATE_USER_SAYING = "/user/update_profile/profile_say/";
    public static final String SERVER_REQ_UPDATE_USER_MOBILE_NUM_POLICY = "/user/update_profile/phone_num_policy/";

    public static final String SERVER_REQ_RESET_USER_NAME = "/user/update_profile/nickname/reset/";
    public static final String SERVER_REQ_RESET_USER_PROFILE_PIC = "/user/update_profile/profile_picture/reset/";
    public static final String SERVER_REQ_RESET_USER_PROFILE_BG = "/user/update_profile/profile_bg/reset/";
    public static final String SERVER_REQ_RESET_USER_SAYING = "/user/update_profile/profile_say/reset/";


    public static final String SERVER_REQ_GET_USER_DATA = "/user/get_user_data/";


    public static final String SERVER_REQ_REGISTER_CAR = "/car/register/";
    public static final String SERVER_REQ_UPDATE_CAR = "/car/update/";
    public static final String SERVER_REQ_UPLOAD_PICTURE = "/car/image/upload/";
    public static final String SERVER_REQ_DELETE_PICTURE = "/car/image/delete/";
    public static final String SERVER_REQ_DELETE_CAR = "/car/delete/";

    public static final String SERVER_REQ_CAR_LIST = "/car/list/";
    public static final String SERVER_REQ_CAR_SEARCH = "/car/search/";
    public static final String SERVER_REQ_CAR = "/car/info/";
    public static final String SERVER_REQ_GET_SMILEBUY = "/car/get_smilebuy/";
    public static final String SERVER_REQ_CAR_FAVORITE = "/car/favorite/";
    public static final String SERVER_REQ_CAR_IS_SOLD = "/car/is_sold/";

    public static final String SERVER_REQ_REGISTER_CAR_SMILEBUY = "/car/smilebuy/register/";
    public static final String SERVER_REQ_UPDATE_CAR_SMILEBUY = "/car/smilebuy/update/";
    public static final String SERVER_REQ_UPLOAD_PICTURE_SMILEBUY = "/car/smilebuy/image/upload/";
    public static final String SERVER_REQ_DELETE_CAR_SMILEBUY = "/car/smilebuy/delete/";

    public static final String SERVER_REQ_REGISTER_CAR_SHARE = "/car/share/register/";
    public static final String SERVER_REQ_UPDATE_CAR_SHARE = "/car/share/update/";
    public static final String SERVER_REQ_UPLOAD_PICTURE_SHARE = "/car/share/image/upload/";
    public static final String SERVER_REQ_DELETE_CAR_SHARE = "/car/share/delete/";

    public static final String SERVER_REQ_GET_MY_CAR_LIST = "/car/get_my_car/";
    public static final String SERVER_REQ_GET_REQUESTED_CAR_LIST = "/car/get_requested_smile_buy/";
    public static final String SERVER_REQ_GET_MY_FAVORITE_CAR_LIST = "/car/get_my_favorite/";

    public static final String SERVER_REQ_ADD_COMMENT = "/car/comment/add/";
    public static final String SERVER_REQ_DEL_COMMENT = "/car/comment/delete/";

    public static final String SERVER_REQ_SMILE_MAN = "/car/smileman/request/";



    public static final String SERVER_ERROR_TYPE_NEED_TO_UPDATE = "ClientIsTooOld";
    public static final String SERVER_ERROR_TYPE_NO_USER = "UserDoesNotExists";
    public static final String SERVER_ERROR_TYPE_DUPLICATED_FACEBOOK_TOKEN = "DuplicatedFacebookToken";
    public static final String SERVER_ERROR_TYPE_NO_RESULT = "NoResult";
    public static final String SERVER_ERROR_TYPE_NOT_VALID_LOGIN_TOKEN = "NotValidLoginToken";


    public static final int MAIN_TAB_CAR_LIST = 0;
    public static final int MAIN_TAB_SETTING = MAIN_TAB_CAR_LIST + 1;
    public static final int MAIN_TAB_FRIEND_LIST = MAIN_TAB_SETTING + 1;
    public static final int MAIN_TAB_COUNT = MAIN_TAB_FRIEND_LIST + 1;

    public static final int MY_CAR_TAB_SELL_CAR = 0;
    public static final int MY_CAR_TAB_SHARE_CAR = MY_CAR_TAB_SELL_CAR + 1;
    public static final int MY_CAR_TAB_COUNT = MY_CAR_TAB_SHARE_CAR + 1;

    public static final String DATA_CAR_ID = "DATA_CAR_ID";
    public static final String DATA_CAR_DATA = "DATA_CAR_DATA";
    public static final String DATA_CAR_DATA_EDIT = "DATA_CAR_DATA_EDIT";
    public static final String DATA_PICTURE_LIST = "DATA_PICTURE_LIST";
    public static final String DATA_PICTURE_CURRENT_INDEX = "DATA_PICTURE_CURRENT_INDEX";
    public static final String DATA_CAR_SMILEBUY_ID = "DATA_CAR_SMILEBUY_ID";
    public static final String DATA_CAR_IS_UPDATE = "DATA_CAR_IS_UPDATE";

    public static final int ADD_PICTURE_LIMIT_USER = 10;
    public static final int ADD_PICTURE_LIMIT_MANAGER = 20;

    public static final String PREF_KEY_AUTO_LOGIN = "PREF_KEY_AUTO_LOGIN";
    public static final String PREF_KEY_EMAIL = "PREF_KEY_EMAIL";
    public static final String PREF_KEY_PWD = "PREF_KEY_PWD";

    public static final float GLIDE_THUMBNAIL_SIZE = 0.2f;

    public static String getMarketType(){
        if(IS_PLAY_STORE_UPDATE){
            return CLIENT_MARKET_TYPE_ANDROID;
        }
        return CLIENT_MARKET_TYPE_ONE_STORE;
    }

    public enum CheckSmileMan {
        CHOOSE_AREA,
        NOT_YET,
        AVAILABLE,
    }

    public class GoogleAnalytic{
        public static final String EVENT_CATEGORY_CONTACT = "contact";

        public static final String EVENT_ACTION_CALL = "call";
        public static final String EVENT_ACTION_SMS = "sms";

        public static final String EVENT_LABEL_NONE = "none";
    }

}
