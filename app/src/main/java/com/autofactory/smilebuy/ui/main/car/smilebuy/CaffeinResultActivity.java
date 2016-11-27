package com.autofactory.smilebuy.ui.main.car.smilebuy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;

public class CaffeinResultActivity extends NormalActivity {
    public static final String CAFFEIN_URL = "CAFFEIN_URL";

    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_caffein_result);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebView = (WebView) findViewById(R.id.webView);


        String url = getIntent().getStringExtra(CAFFEIN_URL);
        if(url == null || url.length() <= 0) {
            finish();
            return;
        }
        mWebView.loadUrl(url);
    }

    @Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_bottom);
    }


}
