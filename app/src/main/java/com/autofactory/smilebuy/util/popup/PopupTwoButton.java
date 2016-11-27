package com.autofactory.smilebuy.util.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.autofactory.smilebuy.R;

/**
 * Created by Phebe on 16. 1. 2..
 */
public class PopupTwoButton extends PopupOneButton {
    protected Button mTwoButton = null;

    public PopupTwoButton(Context context) { this(context, R.layout.popup_two_button); }

    protected PopupTwoButton(Context context, int layoutID) {
        super(context, layoutID);
        mTwoButton = (Button) findViewById(R.id.twoButton);

        setTwoButtonListener(null);
    }

    public void setTwoButtonListener(final OnClickListener onClickListener) {
        if(mTwoButton != null) {
            mTwoButton.setOnClickListener(new View.OnClickListener() {
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
