package com.autofactory.smilebuy.ui.main.car.share;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.ui.main.car.register.AddInterviewListAdapter;
import com.autofactory.smilebuy.util.Utility;

public class ShareActivity extends NormalActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_share);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.facebook).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO USE FACEBOOK SDK WITH DEEP LINK
            }
        });
        findViewById(R.id.kakao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO USE KAKAO SDK WITH DEEP LINK
            }
        });
        findViewById(R.id.line).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO USE LINE SDK WITH DEEP LINK
            }
        });
        findViewById(R.id.sms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO USE DEEP LINK
//                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
//                sendIntent.putExtra("sms_body", "default content");
//                sendIntent.setType("vnd.android-dir/mms-sms");
//                startActivity(sendIntent);
            }
        });
    }


    @Override
    public void finish() {
        super.finish();
        //overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_bottom);
    }


}
