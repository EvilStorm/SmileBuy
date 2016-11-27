package com.autofactory.smilebuy.util.popup;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.autofactory.smilebuy.R;

/**
 * Created by Phebe on 16. 1. 2..
 */
public class PopupBase extends Dialog {
    public PopupBase(Context context, int layoutID) {
        super(context , android.R.style.Theme_Translucent_NoTitleBar);
        //super(context);

        //requestWindowFeature(Window.FEATURE_NO_TITLE);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.4f;
        getWindow().setAttributes(lpWindow);

        //getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        setContentView(layoutID);

        setCancelable(false);
    }


    public interface OnClickListener {
        void onClick();
    }

}
