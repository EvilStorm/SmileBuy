package com.autofactory.smilebuy.ui.main.setting.account;

import android.os.Bundle;
import android.view.View;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;

/**
 * Created by Phebe on 16. 1. 25..
 */
public class WithdrawActivity extends NormalActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_withdraw);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.withdraw).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showPopupYesOrNo(WithdrawActivity.this, getString(R.string.popup_message_withdraw_confirm), new PopupBase.OnClickListener() {
                    @Override
                    public void onClick() {
                        ServerRequest.get().requestWithdraw(new Response.Listener<ServerResult>() {
                            @Override
                            public void onResponse(ServerResult response) {
                                Utility.showPopupOk(WithdrawActivity.this, getString(R.string.popup_message_withdraw_success), new PopupBase.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        Application.get().logout();
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
}
