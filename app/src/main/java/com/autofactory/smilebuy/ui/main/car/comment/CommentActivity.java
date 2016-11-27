package com.autofactory.smilebuy.ui.main.car.comment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.data.server.CarCommentResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;
import com.autofactory.smilebuy.util.popup.PopupCamera;
import com.bumptech.glide.Glide;

import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by Phebe on 16. 1. 24..
 */
public class CommentActivity extends NormalActivity {
    private final int REQUEST_PICK_PICTURE = 20;

    private CarData mCarData = null;
    private TextView mTitle;

    private CommentAdapter mAdapter;
    private View mNoComment;
    private EditText mComment;
    private ImageView mThumb;
    private Button mSend;

    private String mImageFilePath = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_comment);

        mCarData = getIntent().getParcelableExtra(Constant.DATA_CAR_DATA);
        if (mCarData == null) {
            finish();
            return;
        }

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        mAdapter = new CommentAdapter(this, mCarData.getComments());
        listView.setAdapter(mAdapter);

        mTitle = (TextView) findViewById(R.id.title);
        mNoComment = findViewById(R.id.noComment);

        final ImageButton camera = (ImageButton) findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupCamera popupProfileCamera = new PopupCamera(CommentActivity.this, new PopupBase.OnClickListener() {
                    @Override
                    public void onClick() {
                        PhotoPickerIntent intent = new PhotoPickerIntent(CommentActivity.this);
                        intent.setPhotoCount(1);
                        intent.setShowCamera(true);
                        //intent.setShowGif(true);
                        startActivityForResult(intent, REQUEST_PICK_PICTURE);
                    }
                }, new PopupBase.OnClickListener() {
                    @Override
                    public void onClick() {
                        updateImageFilePath(null);
                    }
                }, mImageFilePath == null || mImageFilePath.length() <= 0);
                popupProfileCamera.show();
            }
        });
        mComment = (EditText) findViewById(R.id.comment);
        mComment.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateSend();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        mThumb = (ImageView) findViewById(R.id.thumb);
        mSend = (Button) findViewById(R.id.send);

        mSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequest.get().requestAddComment(mCarData.getID(), mComment.getText().toString(), mImageFilePath, new Response.Listener<CarCommentResult>() {
                    @Override
                    public void onResponse(CarCommentResult response) {
                        mCarData.update(response.getCarData());

                        mComment.setText(null);

                        updateImageFilePath(null);
                        updateSend();
                        updateComments();
                    }
                });
            }
        });

        updateImageFilePath(null);
        updateSend();
        updateComments();
    }

    private void updateImageFilePath(String filePath) {
        mImageFilePath = filePath;

        if (mImageFilePath == null || mImageFilePath.length() <= 0) {
            mThumb.setVisibility(View.GONE);
            mComment.setMinHeight(Utility.dpToPx(this, 32));
        } else {
            mThumb.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(mImageFilePath)
                    .thumbnail(Constant.GLIDE_THUMBNAIL_SIZE)
                    .into(mThumb);
            mComment.setMinHeight(Utility.dpToPx(this, 90));
        }
    }

    private void updateSend() {
        if ((mComment.getText().toString() == null || mComment.getText().toString().length() <= 0)
                && (mImageFilePath == null || mImageFilePath.length() <= 0)) {
            mSend.setEnabled(false);
        } else {
            mSend.setEnabled(true);
        }
    }

    private void updateComments() {
        mAdapter.setList(mCarData.getComments());
        mTitle.setText(String.format(getString(R.string.title_activity_comment), mAdapter.getCount()));
        if (mAdapter.getCount() > 0) {
            mNoComment.setVisibility(View.GONE);
        } else {
            mNoComment.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_PICK_PICTURE) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                List<String> pictureList = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                if (pictureList != null && pictureList.size() > 0) {
                    updateImageFilePath("file:///" + pictureList.get(0));
                }
            }
        }
    }

    public void onCommentDeleted(CarData carData) {
        mCarData.update(carData);
        updateComments();
    }



    @Override
    public void finish() {
        Intent data = new Intent();
        data.putExtra(Constant.DATA_CAR_DATA, mCarData);
        setResult(RESULT_OK, data);

        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_right);
    }
}
