package com.autofactory.smilebuy.util.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.autofactory.smilebuy.R;

/**
 * Created by Phebe on 16. 1. 2..
 */
public class PopupCamera extends PopupBase {
    public PopupCamera(Context context, final OnClickListener onUploadListener, final OnClickListener onDeleteListener, boolean noDelete) {
        super(context, R.layout.popup_profile_camera);

        findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onUploadListener != null) {
                    onUploadListener.onClick();
                }
                dismiss();
            }
        });

        findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onDeleteListener != null) {
                    onDeleteListener.onClick();
                }
                dismiss();
            }
        });

        if(noDelete) {
            findViewById(R.id.delete).setVisibility(View.GONE);
        }

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

    }


}
