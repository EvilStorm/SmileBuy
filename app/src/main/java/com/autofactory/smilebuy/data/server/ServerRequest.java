package com.autofactory.smilebuy.data.server;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.model.CarDataEdit;
import com.autofactory.smilebuy.data.model.CarDataSmileBuy;
import com.autofactory.smilebuy.data.model.PictureData;
import com.autofactory.smilebuy.data.model.UserData;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Log;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;
import com.facebook.AccessToken;
import com.navercorp.volleyextensions.volleyer.Volleyer;
import com.navercorp.volleyextensions.volleyer.builder.PostBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AirPhebe on 2015. 11. 10..
 */
public class ServerRequest {
    private static ServerRequest _singleton = new ServerRequest();

    public static ServerRequest get() {
        return _singleton;
    }

    private ServerResponseParser mResponseParser;

    public ServerResponseParser getResponseParser() {
        return mResponseParser;
    }

    public Response.ErrorListener getErrorListener() {
        return mErrorListener;
    }

    private ServerRequest() {
        mResponseParser = new ServerResponseParser();
    }

    public void requestRegister(AccessToken facebookToken, String nickName, int userType, String countryCode, String phoneNum, final Response.Listener<ServerResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_REGISTER))
                .addStringPart("device_uuid", Application.get().getUUID())
                .addStringPart("facebook_token", facebookToken.getUserId())
                .addStringPart("nickname", nickName)
                .addStringPart("type", "" + userType)
                .addStringPart("country_code", countryCode)
                .addStringPart("mobile_num", phoneNum)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestRegister(String email, String password, String nickName, int userType, String countryCode, String phoneNum, final Response.Listener<ServerResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_REGISTER))
                .addStringPart("device_uuid", Application.get().getUUID())
                .addStringPart("email", email)
                .addStringPart("password", password)
                .addStringPart("nickname", nickName)
                .addStringPart("type", "" + userType)
                .addStringPart("country_code", countryCode)
                .addStringPart("mobile_num", phoneNum)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestVerifyPhone(String countryCode, String phoneNum, final Response.Listener<ServerResult> listener) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_VERIFY_PHONE))
                .addStringPart("country_code", countryCode)
                .addStringPart("mobile_num", phoneNum)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        Utility.hideProgressDialog();
                        if (listener != null) {
                            listener.onResponse(response);
                        }

                        if (!response.isSuccess()) {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestCheckSecret(String countryCode, String phoneNum, int secretNum, final Response.Listener<ServerResult> listener) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_CHECK_SECRET))
                .addStringPart("country_code", countryCode)
                .addStringPart("mobile_num", phoneNum)
                .addStringPart("secret", "" + secretNum)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        Utility.hideProgressDialog();
                        if (listener != null) {
                            listener.onResponse(response);
                        }

                        if (!response.isSuccess()) {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestWithdraw(final Response.Listener<ServerResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_WITHDRAW))
                .addStringPart("login_token", Application.get().getLoginToken())
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(final ServerResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestChangePwdLogin(String password, final Response.Listener<ServerResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_CHANGE_PWD_LOGIN))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("password", password)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(final ServerResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestChangePwdSave(String newPassword, final Response.Listener<ServerResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_CHANGE_PWD_SAVE))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("new_password", newPassword)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(final ServerResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }


    public void requestLogin(AccessToken facebookToken, final Response.Listener<LoginResult> listener, boolean autoLogin) {
        if(!autoLogin) {
            Utility.showProgressDialog(Application.get().getActivity());
        }
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_LOGIN))
                .addStringPart("client_os", Constant.CLIENT_OS_TYPE)
                .addStringPart("client_version", "" + Application.get().getVersion())
                .addStringPart("facebook_token", facebookToken.getUserId())
                .withTargetClass(LoginResult.class)
                .withListener(new Response.Listener<LoginResult>() {
                    @Override
                    public void onResponse(final LoginResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().setPreferenceBoolean(Constant.PREF_KEY_AUTO_LOGIN, true);     // Facebook Login : Auto Login
                            Application.get().onLoginSuccess(response.getLoginToken(), response.getUserData(), response.getClientVersion(), response.getClientUpdateURL());
                            if (Application.get().isThereUpdateExist()) {
                                Utility.showPopupYesOrNo(Application.get().getActivity(), Application.get().getString(R.string.popup_message_there_is_update), new PopupBase.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        Application.get().DoUpdate();
                                    }
                                }, new PopupBase.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        if (listener != null) {
                                            listener.onResponse(response);
                                        }
                                    }
                                });
                            } else {
                                if (listener != null) {
                                    listener.onResponse(response);
                                }
                            }
                        } else {
                            if (Constant.SERVER_ERROR_TYPE_NEED_TO_UPDATE.equalsIgnoreCase(response.getErrorType())) {
                                Utility.showPopupOk(Application.get().getActivity(), response.getErrorMessage(), new PopupBase.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        Application.get().getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response.getClientUpdateURL())));
                                    }
                                });
                            } else if (Constant.SERVER_ERROR_TYPE_NO_USER.equalsIgnoreCase(response.getErrorType())) {
                                if (listener != null) {
                                    listener.onResponse(response);
                                }
                            } else {
                                mServerErrorListener.onResponse(response);
                                if (listener != null) {
                                    listener.onResponse(response);
                                }
                            }
                        }
                    }
                }).execute();
        //rr.cancel();
    }

    public void requestLogin(String email, String password, final Response.Listener<LoginResult> listener, boolean autoLogin) {
        if(!autoLogin) {
            Utility.showProgressDialog(Application.get().getActivity());
        }
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_LOGIN))
                .addStringPart("client_os", Constant.CLIENT_OS_TYPE)
                .addStringPart("client_version", "" + Application.get().getVersion())
                .addStringPart("email", email)
                .addStringPart("password", password)
                .withTargetClass(LoginResult.class)
                .withListener(new Response.Listener<LoginResult>() {
                    @Override
                    public void onResponse(final LoginResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().setFacebookToken(null);
                            Application.get().onLoginSuccess(response.getLoginToken(), response.getUserData(), response.getClientVersion(), response.getClientUpdateURL());
                            if (Application.get().isThereUpdateExist()) {
                                Utility.showPopupYesOrNo(Application.get().getActivity(), Application.get().getString(R.string.popup_message_there_is_update), new PopupBase.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        Application.get().DoUpdate();
                                    }
                                }, new PopupBase.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        if (listener != null) {
                                            listener.onResponse(response);
                                        }
                                    }
                                });
                            } else {
                                if (listener != null) {
                                    listener.onResponse(response);
                                }
                            }
                        } else {
                            if (Constant.SERVER_ERROR_TYPE_NEED_TO_UPDATE.equalsIgnoreCase(response.getErrorType())) {
                                Utility.showPopupOk(Application.get().getActivity(), response.getErrorMessage(), new PopupBase.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        Application.get().getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(response.getClientUpdateURL())));
                                    }
                                });
                            } else {
                                mServerErrorListener.onResponse(response);
                                if (listener != null) {
                                    listener.onResponse(response);
                                }
                            }
                        }
                    }
                })
                .execute();
    }

    public void requestFindPW(String email, String name, final Response.Listener<ServerResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_FIND_PW))
                .addStringPart("email", email)
                .addStringPart("nickname", name)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }


    public void requestUpdateNickname(String nickname, final Response.Listener<UserDataUpdatedResult> onSuccess) {
        if (nickname == null || nickname.length() <= 0) {
            requestResetNickname(onSuccess);
        } else {
            Utility.showProgressDialog(Application.get().getActivity());
            Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_UPDATE_USER_NAME))
                    .addStringPart("login_token", Application.get().getLoginToken())
                    .addStringPart("nickname", nickname)
                    .withTargetClass(UserDataUpdatedResult.class)
                    .withListener(new Response.Listener<UserDataUpdatedResult>() {
                        @Override
                        public void onResponse(UserDataUpdatedResult response) {
                            Utility.hideProgressDialog();
                            if (response.isSuccess()) {
                                Application.get().onUserDataUpdated(response.getUserData());
                                if (onSuccess != null) {
                                    onSuccess.onResponse(response);
                                }
                            } else {
                                mServerErrorListener.onResponse(response);
                            }
                        }
                    })
                    .execute();
        }
    }

    public void requestUpdateSaying(String saying, final Response.Listener<UserDataUpdatedResult> onSuccess) {
        if (saying == null || saying.length() <= 0) {
            requestResetSaying(onSuccess);
        } else {
            Utility.showProgressDialog(Application.get().getActivity());
            Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_UPDATE_USER_SAYING))
                    .addStringPart("login_token", Application.get().getLoginToken())
                    .addStringPart("profile_say", saying)
                    .withTargetClass(UserDataUpdatedResult.class)
                    .withListener(new Response.Listener<UserDataUpdatedResult>() {
                        @Override
                        public void onResponse(UserDataUpdatedResult response) {
                            Utility.hideProgressDialog();
                            if (response.isSuccess()) {
                                Application.get().onUserDataUpdated(response.getUserData());
                                if (onSuccess != null) {
                                    onSuccess.onResponse(response);
                                }
                            } else {
                                mServerErrorListener.onResponse(response);
                            }
                        }
                    })
                    .execute();
        }
    }

    public void requestUpdateProfilePic(String filePath, final Response.Listener<UserDataUpdatedResult> onSuccess) {
        if (filePath == null || filePath.length() <= 0) {
            requestResetProfilePic(onSuccess);
        } else {
            Utility.showProgressDialog(Application.get().getActivity());
            Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_UPDATE_USER_PROFILE_PIC))
                    .addStringPart("login_token", Application.get().getLoginToken())
                    .addFilePart("profile_picture", new File(Uri.parse(filePath).getPath()))
                    .withTargetClass(UserDataUpdatedResult.class)
                    .withListener(new Response.Listener<UserDataUpdatedResult>() {
                        @Override
                        public void onResponse(UserDataUpdatedResult response) {
                            Utility.hideProgressDialog();
                            if (response.isSuccess()) {
                                Application.get().onUserDataUpdated(response.getUserData());
                                if (onSuccess != null) {
                                    onSuccess.onResponse(response);
                                }
                            } else {
                                mServerErrorListener.onResponse(response);
                            }
                        }
                    })
                    .execute();
        }
    }

    public void requestUpdateMobileNumPolicy(int policy, final Response.Listener<UserDataUpdatedResult> listener) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_UPDATE_USER_MOBILE_NUM_POLICY))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("mobile_num_policy", "" + policy)
                .withTargetClass(UserDataUpdatedResult.class)
                .withListener(new Response.Listener<UserDataUpdatedResult>() {
                    @Override
                    public void onResponse(UserDataUpdatedResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().onUserDataUpdated(response.getUserData());
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                        if (listener != null) {
                            listener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    private void requestResetNickname(final Response.Listener<UserDataUpdatedResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_RESET_USER_NAME))
                .addStringPart("login_token", Application.get().getLoginToken())
                .withTargetClass(UserDataUpdatedResult.class)
                .withListener(new Response.Listener<UserDataUpdatedResult>() {
                    @Override
                    public void onResponse(UserDataUpdatedResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().onUserDataUpdated(response.getUserData());
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    private void requestResetProfilePic(final Response.Listener<UserDataUpdatedResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_RESET_USER_PROFILE_PIC))
                .addStringPart("login_token", Application.get().getLoginToken())
                .withTargetClass(UserDataUpdatedResult.class)
                .withListener(new Response.Listener<UserDataUpdatedResult>() {
                    @Override
                    public void onResponse(UserDataUpdatedResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().onUserDataUpdated(response.getUserData());
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    private void requestResetSaying(final Response.Listener<UserDataUpdatedResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_RESET_USER_SAYING))
                .addStringPart("login_token", Application.get().getLoginToken())
                .withTargetClass(UserDataUpdatedResult.class)
                .withListener(new Response.Listener<UserDataUpdatedResult>() {
                    @Override
                    public void onResponse(UserDataUpdatedResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().onUserDataUpdated(response.getUserData());
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestGetUserData(long userID, final Response.Listener<GetUserDataResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_GET_USER_DATA))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("user_id", "" + userID)
                .withTargetClass(GetUserDataResult.class)
                .withListener(new Response.Listener<GetUserDataResult>() {
                    @Override
                    public void onResponse(GetUserDataResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    private Bitmap getBitmap(String filePath){
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;

        bitmap = BitmapFactory.decodeFile(filePath, options);

        return bitmap;
    }

    private File makeThumnail(String filePath){

        Bitmap bitmap = getBitmap(filePath);
        String[] fileName = filePath.split("/");

        File storage = Application.get().getCacheDir();
        String tempFileName = fileName[(fileName.length-1)];
        File tempFile = new File(storage, tempFileName);

        try{
            tempFile.createNewFile();
            FileOutputStream out = new FileOutputStream(tempFile);
            if(filePath.toLowerCase().contains("png")){
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            }else{
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            }
            out.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch(IOException e2){
            e2.printStackTrace();
        }

        return tempFile;
    }

    public void requestRegisterCar(CarDataEdit carDataEdit, final Response.Listener<RegisterCarResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());

        // REGISTER CAR
        final RegisterCarResult carResult = new RegisterCarResult();

        // 3. DELETE PICTURES
        final List<Long> toRemoveIDs = new ArrayList<>(carDataEdit.getToRemoveOnServerPictures());
        final Response.Listener<ServerResult> deletePictureResultListener = new Response.Listener<ServerResult>() {
            @Override
            public void onResponse(ServerResult response) {
                if (response.isSuccess()) {
                    if (toRemoveIDs.size() > 0) {
                        long removeID = toRemoveIDs.remove(toRemoveIDs.size() - 1);
                        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_DELETE_PICTURE))
                                .addStringPart("login_token", Application.get().getLoginToken())
                                .addStringPart("image_id", "" + removeID)
                                .withTargetClass(ServerResult.class)
                                .withListener(this)
                                .execute();
                    } else {
                        Utility.hideProgressDialog();
                        if (onSuccess != null) {
                            onSuccess.onResponse(carResult);
                        }
                    }
                } else {
                    Utility.hideProgressDialog();
                    mServerErrorListener.onResponse(response);
                }
            }
        };

        // 2. UPLOAD PICTURES
        final List<PictureData> pictures = new ArrayList<>(carDataEdit.getPictures());
        for (int i = pictures.size() - 1; i >= 0; i--) {
            if (pictures.get(i).isOnServer()) {
                pictures.remove(i);
            }
        }
        final Response.Listener<UploadPictureResult> uploadPictureResultListener = new Response.Listener<UploadPictureResult>() {
            @Override
            public void onResponse(UploadPictureResult response) {
                if (response.isSuccess()) {
                    if (pictures.size() > 0) {
                        PictureData pictureData = pictures.remove(pictures.size() - 1);
                        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_UPLOAD_PICTURE))
                                .addStringPart("login_token", Application.get().getLoginToken())
                                .addStringPart("car_id", "" + response.getCarID())
//                                .addFilePart("image", new File(Uri.parse(pictureData.getURL()).getPath()))
                                .addFilePart("image", makeThumnail(Uri.parse(pictureData.getURL()).getPath()))

                                .withTargetClass(UploadPictureResult.class)
                                .withListener(this)
                                .execute();
                    } else {
                        if (toRemoveIDs.size() > 0) {
                            long removeID = toRemoveIDs.remove(toRemoveIDs.size() - 1);
                            Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_DELETE_PICTURE))
                                    .addStringPart("login_token", Application.get().getLoginToken())
                                    .addStringPart("image_id", "" + removeID)
                                    .withTargetClass(ServerResult.class)
                                    .withListener(deletePictureResultListener)
                                    .execute();
                        } else {
                            Utility.hideProgressDialog();
                            if (onSuccess != null) {
                                onSuccess.onResponse(carResult);
                            }
                        }
                    }
                } else {
                    Utility.hideProgressDialog();
                    mServerErrorListener.onResponse(response);
                    requestDeleteCar(response.getCarID(), null);
                }
            }
        };

        // 1. UPLOAD CAR DATA
        PostBuilder postBuilder = Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, carDataEdit.getID() >= 0 ? Constant.SERVER_REQ_UPDATE_CAR : Constant.SERVER_REQ_REGISTER_CAR))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("name", carDataEdit.getName())
                .addStringPart("car_num", carDataEdit.getCarNum())
                .addStringPart("phone_num", "TO_BE_REMOVED")
                .addStringPart("price", "" + carDataEdit.getPrice())
                .addStringPart("area_code", "" + carDataEdit.getAreaType())
                .addStringPart("mileage", "" + carDataEdit.getMileage())
                .addStringPart("age_year", "" + carDataEdit.getAgeYear())
                .addStringPart("age_month", "" + carDataEdit.getAgeMonth())
                .addStringPart("fuel_type", "" + carDataEdit.getFuelType())
                .addStringPart("transmission_type", "" + carDataEdit.getTransmissionType())
                .addStringPart("engine", "" + carDataEdit.getEngine())
                .addStringPart("car_type", "" + carDataEdit.getCarType())
                .addStringPart("additional", carDataEdit.getAdditional())
                .addStringPart("interview_string", carDataEdit.getInterviewAsJson())
                .addStringPart("can_smile_buy", "" + Utility.getServerIntFromBoolean(carDataEdit.isCanSmileBuy()));
        if (carDataEdit.getID() >= 0) {
            postBuilder = postBuilder.addStringPart("car_id", "" + carDataEdit.getID());
        }
        postBuilder.withTargetClass(RegisterCarResult.class)
                .withListener(new Response.Listener<RegisterCarResult>() {
                    @Override
                    public void onResponse(RegisterCarResult response) {
                        //Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            carResult.update(response);
                            if (pictures.size() > 0) {
                                PictureData pictureData = pictures.remove(pictures.size() - 1);
                                Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_UPLOAD_PICTURE))
                                        .addStringPart("login_token", Application.get().getLoginToken())
                                        .addStringPart("car_id", "" + response.getCarData().getID())
//                                        .addFilePart("image", new File(Uri.parse(pictureData.getURL()).getPath()))
                                        .addFilePart("image", makeThumnail(Uri.parse(pictureData.getURL()).getPath()))
                                        .withTargetClass(UploadPictureResult.class)
                                        .withListener(uploadPictureResultListener)
                                        .execute();
                            } else {
                                Utility.hideProgressDialog();
                                if (onSuccess != null) {
                                    onSuccess.onResponse(carResult);
                                }
                            }
                        } else {
                            Utility.hideProgressDialog();
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestDeleteCar(final long carID, final Response.Listener<ServerResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_DELETE_CAR))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("car_id", "" + carID)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().deleteCarFromMainList(carID);
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestCarList(int page, long offsetCarID, final Response.Listener<CarListResult> listener) {
        Utility.showProgressDialog(Application.get().getActivity());
        PostBuilder postBuilder = Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_CAR_LIST))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("page", "" + page);
        if (offsetCarID > 0) {
            postBuilder = postBuilder.addStringPart("offset_car_id", "" + offsetCarID);
        }
        postBuilder.withTargetClass(CarListResult.class)
                .withListener(new Response.Listener<CarListResult>() {
                    @Override
                    public void onResponse(CarListResult response) {
                        Utility.hideProgressDialog();
                        if (listener != null) {
                            listener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestGetCar(long carID, final Response.Listener<CarResult> listener) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_CAR))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("id", "" + carID)
                .withTargetClass(CarResult.class)
                .withListener(new Response.Listener<CarResult>() {
                    @Override
                    public void onResponse(CarResult response) {
                        Utility.hideProgressDialog();
                        if (listener != null) {
                            listener.onResponse(response);
                        }

                        if (!response.isSuccess()) {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestGetCarSmileBuy(long carSmileBuyID, final Response.Listener<GetCarSmileBuyResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_GET_SMILEBUY))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("car_smile_buy_id", "" + carSmileBuyID)
                .withTargetClass(GetCarSmileBuyResult.class)
                .withListener(new Response.Listener<GetCarSmileBuyResult>() {
                    @Override
                    public void onResponse(GetCarSmileBuyResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestCarFavorite(long carID, final Response.Listener<CarFavoriteResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_CAR_FAVORITE))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("car_id", "" + carID)
                .withTargetClass(CarFavoriteResult.class)
                .withListener(new Response.Listener<CarFavoriteResult>() {
                    @Override
                    public void onResponse(CarFavoriteResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().updateCarInMainList(response.getCarData());
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestCarIsSold(long carID, final Response.Listener<CarIsSoldResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_CAR_IS_SOLD))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("car_id", "" + carID)
                .withTargetClass(CarIsSoldResult.class)
                .withListener(new Response.Listener<CarIsSoldResult>() {
                    @Override
                    public void onResponse(CarIsSoldResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().updateCarInMainList(response.getCarData());
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestRegisterCarSmileBuy(boolean isUpdate, CarDataSmileBuy carData, final Response.Listener<ServerResult> listener) {
        Utility.showProgressDialog(Application.get().getActivity());

        final List<PictureData> pictures = new ArrayList<>(carData.getPictures());
        final Response.Listener<UploadPictureSmileBuyResult> uploadPictureResultListener = new Response.Listener<UploadPictureSmileBuyResult>() {
            @Override
            public void onResponse(UploadPictureSmileBuyResult response) {
                if (response.isSuccess()) {
                    if (pictures.size() > 0) {
                        PictureData pictureData = pictures.remove(pictures.size() - 1);
                        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_UPLOAD_PICTURE_SMILEBUY))
                                .addStringPart("login_token", Application.get().getLoginToken())
                                .addStringPart("car_smile_buy_id", "" + response.getCarID())
//                                .addFilePart("image", new File(Uri.parse(pictureData.getURL()).getPath()))
                                .addFilePart("image", makeThumnail(Uri.parse(pictureData.getURL()).getPath()))
                                .withTargetClass(UploadPictureSmileBuyResult.class)
                                .withListener(this)
                                .execute();
                    } else {
                        Utility.hideProgressDialog();
                        listener.onResponse(response);
                    }
                } else {
                    Utility.hideProgressDialog();
                    mServerErrorListener.onResponse(response);
                }
            }
        };

        PostBuilder postBuilder = Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, carData.getID() >= 0 ? Constant.SERVER_REQ_UPDATE_CAR_SMILEBUY : Constant.SERVER_REQ_REGISTER_CAR_SMILEBUY))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("name", carData.getName())
                .addStringPart("car_num", carData.getCarNum())
                .addStringPart("phone_num", "TO_BE_REMOVED")
                .addStringPart("mileage", "" + carData.getMileage())
                .addStringPart("age_year", "" + carData.getAgeYear())
                .addStringPart("age_month", "" + carData.getAgeMonth())
                .addStringPart("fuel_type", "" + carData.getFuelType())
                .addStringPart("transmission_type", "" + carData.getTransmissionType())
                .addStringPart("engine", "" + carData.getEngine())
                .addStringPart("sunroof", "" + Utility.getServerIntFromBoolean(carData.isSunroof()))
                .addStringPart("smart_key", "" + Utility.getServerIntFromBoolean(carData.isSmartKey()))
                .addStringPart("black_box", "" + Utility.getServerIntFromBoolean(carData.isBlackBox()))
                .addStringPart("rear_camera", "" + Utility.getServerIntFromBoolean(carData.isRearCamera()))
                .addStringPart("navigation", "" + Utility.getServerIntFromBoolean(carData.isNavigation()))
                .addStringPart("caffein", "" + Utility.getServerIntFromBoolean(carData.isCaffein()))
                .addStringPart("accident", carData.getAccident())
                .addStringPart("predicted_price", carData.getPredictedPrice())
                .addStringPart("manager_comment", carData.getManagerComment());
        if (!isUpdate) {
            postBuilder = postBuilder.addStringPart("car_id", "" + carData.getCarID());
        } else {
            postBuilder = postBuilder.addStringPart("car_smile_buy_id", "" + carData.getID());
        }
        postBuilder.withTargetClass(RegisterCarSmileBuyResult.class)
                .withListener(new Response.Listener<RegisterCarSmileBuyResult>() {
                    @Override
                    public void onResponse(RegisterCarSmileBuyResult response) {
                        //Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            if (pictures.size() > 0) {
                                PictureData pictureData = pictures.remove(pictures.size() - 1);
                                Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_UPLOAD_PICTURE_SMILEBUY))
                                        .addStringPart("login_token", Application.get().getLoginToken())
                                        .addStringPart("car_smile_buy_id", "" + response.getCarData().getID())
//                                        .addFilePart("image", new File(Uri.parse(pictureData.getURL()).getPath()))
                                        .addFilePart("image", makeThumnail(Uri.parse(pictureData.getURL()).getPath()))
                                        .withTargetClass(UploadPictureSmileBuyResult.class)
                                        .withListener(uploadPictureResultListener)
                                        .execute();
                            } else {
                                Utility.hideProgressDialog();
                            }
                        } else {
                            Utility.hideProgressDialog();
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestDeleteCarSmileBuy(long carID, final Response.Listener<ServerResult> listener) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_DELETE_CAR_SMILEBUY))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("car_smile_buy_id", "" + carID)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        Utility.hideProgressDialog();
                        if (listener != null) {
                            listener.onResponse(response);
                        }

                        if (!response.isSuccess()) {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestGetMyCarList(final Response.Listener<ServerResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_GET_MY_CAR_LIST))
                .addStringPart("login_token", Application.get().getLoginToken())
                .withTargetClass(MyCarListResult.class)
                .withListener(new Response.Listener<MyCarListResult>() {
                    @Override
                    public void onResponse(MyCarListResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            UserData myUser = Application.get().getUserData();
                            if (myUser != null) {
                                myUser.setCarList(response.getCarList());
                                myUser.setCarShareList(response.getCarShareList());
                                myUser.setCarSmileBuyList(response.getCarSmileBuyList());
                            }

                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestGetRequestedCarList(final Response.Listener<RequestedCarListResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_GET_REQUESTED_CAR_LIST))
                .addStringPart("login_token", Application.get().getLoginToken())
                .withTargetClass(RequestedCarListResult.class)
                .withListener(new Response.Listener<RequestedCarListResult>() {
                    @Override
                    public void onResponse(RequestedCarListResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            UserData myUser = Application.get().getUserData();
                            if (myUser != null) {
                                myUser.setRequestCarList(response.getCarList());
                            }

                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestMyFavoriteCarList(final Response.Listener<ServerResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_GET_MY_FAVORITE_CAR_LIST))
                .addStringPart("login_token", Application.get().getLoginToken())
                .withTargetClass(MyFavoriteCarListResult.class)
                .withListener(new Response.Listener<MyFavoriteCarListResult>() {
                    @Override
                    public void onResponse(MyFavoriteCarListResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            UserData myUser = Application.get().getUserData();
                            if (myUser != null) {
                                myUser.setFavoriteCarList(response.getCarList());
                            }
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestAddComment(long carID, String comment, String filePath, final Response.Listener<CarCommentResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        PostBuilder postBuilder = Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_ADD_COMMENT))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("car_id", "" + carID)
                .addStringPart("message", comment);
        if (filePath != null && filePath.length() > 0) {
            postBuilder = postBuilder.addFilePart("picture", (filePath == null || filePath.length() <= 0) ? null : new File(Uri.parse(filePath).getPath()));
        }
        postBuilder.withTargetClass(CarCommentResult.class)
                .withListener(new Response.Listener<CarCommentResult>() {
                    @Override
                    public void onResponse(CarCommentResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().updateCarInMainList(response.getCarData());
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestDeleteComment(long commentID, final Response.Listener<CarCommentResult> onSuccess) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_DEL_COMMENT))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("comment_id", "" + commentID)
                .withTargetClass(CarCommentResult.class)
                .withListener(new Response.Listener<CarCommentResult>() {
                    @Override
                    public void onResponse(CarCommentResult response) {
                        Utility.hideProgressDialog();
                        if (response.isSuccess()) {
                            Application.get().updateCarInMainList(response.getCarData());
                            if (onSuccess != null) {
                                onSuccess.onResponse(response);
                            }
                        } else {
                            mServerErrorListener.onResponse(response);
                        }
                    }
                })
                .execute();
    }

    public void requestSearchCar(int page, long offsetCarID, String keyword, int priceL, int priceH, int mileageL, int mileageH, int areaCode, boolean hasSmileBuy, int carType, final Response.Listener<CarListResult> listener) {
        Utility.showProgressDialog(Application.get().getActivity());
        PostBuilder postBuilder = Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_CAR_SEARCH))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("page", "" + page);
        if (offsetCarID > 0) {
            postBuilder = postBuilder.addStringPart("offset_car_id", "" + offsetCarID);
        }
        if (keyword != null && keyword.length() > 0) {
            postBuilder = postBuilder.addStringPart("keyword", keyword);
        }
        if (priceL > 0) {
            postBuilder = postBuilder.addStringPart("price_lower_bound", "" + priceL);
        }
        if (priceH > 0) {
            postBuilder = postBuilder.addStringPart("price_upper_bound", "" + priceH);
        }
        if (mileageL > 0) {
            postBuilder = postBuilder.addStringPart("mileage_lower_bound", "" + mileageL);
        }
        if (mileageH > 0) {
            postBuilder = postBuilder.addStringPart("mileage_upper_bound", "" + mileageH);
        }
        if (areaCode > 0) {
            postBuilder = postBuilder.addStringPart("area_code", "" + areaCode);
        }
        if (hasSmileBuy) {
            postBuilder = postBuilder.addStringPart("has_smile_buy", "1");
        }
        if (carType > 0) {
            postBuilder = postBuilder.addStringPart("car_type", "" + carType);
        }

        postBuilder.withTargetClass(CarListResult.class)
                .withListener(new Response.Listener<CarListResult>() {
                    @Override
                    public void onResponse(CarListResult response) {
                        Utility.hideProgressDialog();
                        if (listener != null) {
                            listener.onResponse(response);
                        }

                    }
                })
                .execute();
    }

    public void requestSmileMan(long carID, int serviceType, int paymentAmount, final Response.Listener<ServerResult> listener) {
        Utility.showProgressDialog(Application.get().getActivity());
        Volleyer.volleyer().post(String.format("%s%s", Constant.SERVER_ADDRESS, Constant.SERVER_REQ_SMILE_MAN))
                .addStringPart("login_token", Application.get().getLoginToken())
                .addStringPart("car_id", "" + carID)
                .addStringPart("service_type", "" + serviceType)
                .addStringPart("payment_amount", "" + paymentAmount)
                .withTargetClass(ServerResult.class)
                .withListener(new Response.Listener<ServerResult>() {
                    @Override
                    public void onResponse(ServerResult response) {
                        Utility.hideProgressDialog();
                        if (listener != null) {
                            listener.onResponse(response);
                        }

                        if (!response.isSuccess()) {
                            mServerErrorListener.onResponse(response);
                        } else {
                            ServerRequest.get().requestGetRequestedCarList(null);
                        }
                    }
                })
                .execute();
    }


    private Response.Listener<ServerResult> mServerErrorListener = new Response.Listener<ServerResult>() {
        @Override
        public void onResponse(ServerResult response) {
            if (Constant.SERVER_ERROR_TYPE_NOT_VALID_LOGIN_TOKEN.equalsIgnoreCase(response.getErrorType())) {
                Utility.showPopupOk(Application.get().getActivity(), response.getErrorMessage(), new PopupBase.OnClickListener() {
                    @Override
                    public void onClick() {
                        Application.get().reLogin();
                    }
                });
            } else {
                Utility.showPopupOk(Application.get().getActivity(), response.getErrorMessage());
            }
        }
    };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.e(error.getClass().getName());
            error.printStackTrace();
            Utility.hideProgressDialog();
            if(error.getClass().getName().contains("NoConnectionError")
                    || error.getMessage() != null && error.getMessage().contains("UnknownHostException")) {
                Utility.showPopupOk(Application.get().getActivity(), Application.get().getString(R.string.popup_message_check_internet_connection));
            } else {
                Utility.showPopupOk(Application.get().getActivity(), String.format(Application.get().getString(R.string.popup_message_network_error), error.getMessage() + "\n" + error.getLocalizedMessage()));
            }
        }
    };


}
