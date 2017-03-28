package com.autofactory.smilebuy.util;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;

import java.io.File;

/**
 * Created by evilstorm on 2017. 3. 17..
 */
public class SendFileToS3 {

    public interface SendFileToS3Listener {
        void onStateChange(TransferState state);
        void onProgress(int per);
        void onError(Exception e);
    }

    private SendFileToS3Listener listener;

    private String POOL_ID = "ap-northeast-2:1e95cbea-81f8-4453-a545-95972b339ef6";
    private String BUCKET_PATH_CAR = "smilebuy-seoul/cars";
    private String BUCKET_PATH_COMMENT = "smilebuy-seoul/comment";
    private String BUCKET_PATH_USER = "smilebuy-seoul/user";

    // Storage Permissions
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private AmazonS3 s3;
    private TransferUtility transferUtility;
    private Context context;

    public SendFileToS3(Context context) {
        this.context = context;
        credentialsProvider();

        setTransferUtility();
    }

    public void setListener(SendFileToS3Listener listener) {
        this.listener = listener;
    }

    public static void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

    private void credentialsProvider(){
        // Initialize the Amazon Cognito credentials provider
        CognitoCachingCredentialsProvider credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                POOL_ID, // Identity Pool ID
                Regions.AP_NORTHEAST_2 // Region
        );

        setAmazonS3Client(credentialsProvider);
    }

    /**
     *  Create a AmazonS3Client constructor and pass the credentialsProvider.
     * @param credentialsProvider
     */
    private void setAmazonS3Client(CognitoCachingCredentialsProvider credentialsProvider){

        // Create an S3 client
        s3 = new AmazonS3Client(credentialsProvider);

        // Set the region of your S3 bucket
        s3.setRegion(Region.getRegion(Regions.AP_NORTHEAST_2));

    }
    public void setTransferUtility(){
        transferUtility = new TransferUtility(s3, context);
    }

    public static final int TYPE_CAR = 1;
    public static final int TYPE_COMMENT = 2;
    public static final int TYPE_USER = 3;

    private String getBucketPath(int type) {
        switch (type) {
            case TYPE_CAR:
                return BUCKET_PATH_CAR;
            case TYPE_COMMENT:
                return BUCKET_PATH_COMMENT;
            case TYPE_USER:
                return BUCKET_PATH_USER;
            default:
                return BUCKET_PATH_CAR;
        }
    }
    /**
     * This method is used to upload the file to S3 by using TransferUtility class
     * @param
     */
    public void setFileToUpload(String fileName, File uploadFile, int type ){
        Log.i(" Upload File Name :  " + fileName);
        TransferObserver transferObserver = transferUtility.upload(
                getBucketPath(type),     /* The bucket to upload to */
                fileName,       /* The key for the uploaded object */
                uploadFile       /* The file where the data to upload exists */
        );

        transferObserverListener(transferObserver);
    }

    /**
     * This is listener method of the TransferObserver
     * Within this listener method, we get the status of uploading and downloading the file,
     * it displays percentage part of the  file to be uploaded or downloaded to S3
     * It also displays an error, when there is problem to upload and download file to S3.
     * @param transferObserver
     */

    private void transferObserverListener(TransferObserver transferObserver){

        transferObserver.setTransferListener(new TransferListener(){

            @Override
            public void onStateChanged(int id, TransferState state) {
                Log.i(" File Upload State " + state);
                if(listener != null) {
                    listener.onStateChange(state);
                }
            }

            @Override
            public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                int percentage = (int) (bytesCurrent/bytesTotal * 100);
                if(listener != null) {
                    listener.onProgress(percentage);
                }
            }

            @Override
            public void onError(int id, Exception ex) {
                Log.e(" File Upload Erro " + ex.toString());
                if (listener != null) {
                    listener.onError(ex);
                }
            }
        });
    }

}
