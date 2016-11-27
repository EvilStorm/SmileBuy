package com.autofactory.smilebuy.util.popup;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.autofactory.smilebuy.R;

/**
 * Created by Phebe on 16. 1. 2..
 */
public class PopupMakeInterview extends PopupTwoButton {
    protected Button mTwoButton = null;

    public PopupMakeInterview(Context context) {
        super(context, R.layout.popup_make_interview);

    }

    public String getContent() {
        return ((EditText) findViewById(R.id.content)).getText().toString();
    }


}
