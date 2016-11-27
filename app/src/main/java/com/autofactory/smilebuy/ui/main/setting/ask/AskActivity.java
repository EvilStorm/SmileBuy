package com.autofactory.smilebuy.ui.main.setting.ask;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;

/**
 * Created by Phebe on 16. 1. 25..
 */
public class AskActivity extends NormalActivity {
    private WebView mWebView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ask);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportMultipleWindows(false);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setDomStorageEnabled(true);
//        webSettings.setSupportZoom(true);
//        webSettings.setBuiltInZoomControls(true);
        //mWebView.loadUrl("http://me2.do/54ceWxQL");
        mWebView.loadUrl("https://form.office.naver.com/form/responseView.cmd?formkey=M2EyYjUyNDQtMzc5Yy00NDdkLTllOGQtYWU1OTIwMjBkYzll&sourceId=urlshare");
        //mWebView.loadUrl("http://www.naver.com");

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
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_right);
    }
}
