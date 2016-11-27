package com.autofactory.smilebuy.util.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.autofactory.smilebuy.R;

/**
 * Created by Phebe on 16. 1. 2..
 */
public class PopupOneButton extends PopupBase {
    protected TextView mMessage = null;
    protected TextView mTitle = null;
    protected Button mOneButton = null;

    public PopupOneButton(Context context) {
        this(context, R.layout.popup_one_button);
    }
    protected PopupOneButton(Context context, int layoutID) {
        super(context, layoutID);

        mMessage = (TextView) findViewById(R.id.message);
        mTitle = (TextView) findViewById(R.id.title);
        mOneButton = (Button) findViewById(R.id.oneButton);

        setOneButtonListener(null);
    }

    public void setMessage(String message) {
        if(mMessage != null) {
            mMessage.setText(message);
        }
    }
    public void setTitle(String title) {
        if(mTitle != null) {
            if(title != null && title.length() > 0) {
                mTitle.setVisibility(View.VISIBLE);
                mTitle.setText(title);
            } else {
                mTitle.setVisibility(View.GONE);
            }
        }
    }
    public void setOneButtonListener(final OnClickListener onClickListener) {
        if(mOneButton != null) {
            mOneButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onClickListener != null) {
                        onClickListener.onClick();
                    }
                    dismiss();
                }
            });
        }
    }


}
