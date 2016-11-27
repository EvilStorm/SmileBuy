package com.autofactory.smilebuy.ui.main.car.smilebuy;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;

/**
 * Created by Phebe on 16. 1. 27..
 */
public class RequestSmileManActivity extends NormalActivity {
    public static final String CAR_ID = "CAR_ID";
    public static final String FROM_SELLER = "FROM_SELLER";
    public static final String FROM_REGISTER = "FROM_REGISTER";
    public static final String SERVICE_TYPE = "SERVICE_TYPE";
    public static final String PAYMENT_AMOUNT = "PAYMENT_AMOUNT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_request_smile_man);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ImageView imageView = (ImageView) findViewById(R.id.image);
        final boolean fromSeller = getIntent().getBooleanExtra(FROM_SELLER, false);
        if (fromSeller) {
            imageView.setImageResource(R.drawable.icon_smileman_01);
        } else {
            imageView.setImageResource(R.drawable.icon_smileman_02);
        }
        TextView textView1 = (TextView) findViewById(R.id.text1);
        TextView textView2 = (TextView) findViewById(R.id.text2);
        if (fromSeller) {
            textView1.setText(getString(R.string.subtitle_request_smileman_seller));
            textView2.setText(getString(R.string.text_request_smileman_seller));
        } else {
            textView1.setText(getString(R.string.subtitle_request_smileman_buyer));
            textView2.setText(getString(R.string.text_request_smileman_buyer));
        }

        final boolean fromRegister = getIntent().getBooleanExtra(FROM_REGISTER, true);

        final long carID = getIntent().getLongExtra(CAR_ID, -1);
        findViewById(R.id.requestSmileMan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fromSeller) {
                    Utility.showPopupYesOrNo(RequestSmileManActivity.this, getString(R.string.popup_message_check_smileman_request), new PopupBase.OnClickListener() {
                        @Override
                        public void onClick() {
                            if (fromRegister) {
                                Utility.showPopupOk(RequestSmileManActivity.this, getString(R.string.popup_message_smileman_requested_on_register), new PopupBase.OnClickListener() {
                                    @Override
                                    public void onClick() {
                                        requestSmileManAfterRegister(Constant.SMILEMAN_SERVICE_TYPE_CHECK_SELLER, 0);
                                    }
                                });
                            } else {
                                ServerRequest.get().requestSmileMan(carID, Constant.SMILEMAN_SERVICE_TYPE_CHECK_SELLER, 0, new Response.Listener<ServerResult>() {
                                    @Override
                                    public void onResponse(ServerResult response) {
                                        if (response.isSuccess()) {
                                            Utility.showPopupOk(RequestSmileManActivity.this, getString(R.string.popup_message_confirm_smileman_request), new PopupBase.OnClickListener() {
                                                @Override
                                                public void onClick() {
                                                    onBackPressed();
                                                }
                                            });
                                        }
                                    }
                                });
                            }
                        }
                    });
                } else {
                    Utility.showPopupYesOrNo(RequestSmileManActivity.this, getString(R.string.popup_message_check_smileman_request), new PopupBase.OnClickListener() {
                        @Override
                        public void onClick() {
                            ServerRequest.get().requestSmileMan(carID, Constant.SMILEMAN_SERVICE_TYPE_CHECK_BUYER, 0, new Response.Listener<ServerResult>() {
                                @Override
                                public void onResponse(ServerResult response) {
                                    if (response.isSuccess()) {
                                        Utility.showPopupOk(RequestSmileManActivity.this, getString(R.string.popup_message_confirm_smileman_request), new PopupBase.OnClickListener() {
                                            @Override
                                            public void onClick() {
                                                onBackPressed();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });
//        findViewById(R.id.request2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(fromSeller) {
//                    Utility.showPopupYesOrNo(RequestSmileManActivity.this, getString(R.string.popup_message_check_smileman_request_2), new PopupBase.OnClickListener() {
//                        @Override
//                        public void onClick() {
//                            Utility.showPopupOk(RequestSmileManActivity.this, getString(R.string.popup_message_smileman_requested_on_register), new PopupBase.OnClickListener() {
//                                @Override
//                                public void onClick() {
//                                    requestSmileManAfterRegister(Constant.SMILEMAN_SERVICE_TYPE_PACK_SELLER, 0);
//                                }
//                            });
//                        }
//                    });
//                } else {
//                    Utility.showPopupYesOrNo(RequestSmileManActivity.this, getString(R.string.popup_message_check_smileman_request_2), new PopupBase.OnClickListener() {
//                        @Override
//                        public void onClick() {
//                            ServerRequest.get().requestSmileMan(carID, Constant.SMILEMAN_SERVICE_TYPE_PACK_BUYER, 0, new Response.Listener<ServerResult>() {
//                                @Override
//                                public void onResponse(ServerResult response) {
//                                    if(response.isSuccess()) {
//                                        Utility.showPopupOk(RequestSmileManActivity.this, getString(R.string.popup_message_confirm_smileman_request_2), new PopupBase.OnClickListener() {
//                                            @Override
//                                            public void onClick() {
//                                                onBackPressed();
//                                            }
//                                        });
//                                    }
//                                }
//                            });
//                        }
//                    });
//                }
//            }
//        });
    }

    private void requestSmileManAfterRegister(int serviceType, int paymentAmount) {
        Intent data = new Intent();
        data.putExtra(SERVICE_TYPE, serviceType);
        data.putExtra(PAYMENT_AMOUNT, paymentAmount);
        setResult(RESULT_OK, data);

        finish();
    }


}
