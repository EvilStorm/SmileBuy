package com.autofactory.smilebuy.ui.main.car.register;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.data.model.CarDataEdit;
import com.autofactory.smilebuy.data.server.RegisterCarResult;
import com.autofactory.smilebuy.data.server.ServerResult;
import com.autofactory.smilebuy.data.server.ServerRequest;
import com.autofactory.smilebuy.ui.main.MainActivity;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.data.model.CarData;
import com.autofactory.smilebuy.ui.main.car.detail.CarDetailFragment;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;

public class RegisterCarCompleteActivity extends FragmentActivity {
    private CarDataEdit mCarDataEdit;

    private int mRequestSmileManServiceType = -1;
    private int mRequestSmileManPaymentAmount = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_register_car_complete);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        findViewById(R.id.edit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSellActivity();
            }
        });
        findViewById(R.id.confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ServerRequest.get().requestRegisterCar(mCarDataEdit, new Response.Listener<RegisterCarResult>() {
                    @Override
                    public void onResponse(RegisterCarResult response) {
                        mCarDataEdit.update(response.getCarData());
                        if(mRequestSmileManServiceType != -1) {
                            requestSmileMan();
                        } else {
                            startMainActivity();
                        }
                    }
                });
            }
        });

        mCarDataEdit = getIntent().getParcelableExtra(Constant.DATA_CAR_DATA_EDIT);
        mRequestSmileManServiceType = mCarDataEdit.getRequestSmileManServiceType();
        mRequestSmileManPaymentAmount = mCarDataEdit.getRequestSmileManPaymentAmount();

        FragmentTransaction transaction = mManager.beginTransaction();
        Fragment fragment = CarDetailFragment.newInstance(this, mCarDataEdit, true);
        transaction.replace(R.id.fragmentContainer, fragment);
        transaction.commit();

        setCurrent(fragment);
    }

    private void requestSmileMan() {
        ServerRequest.get().requestSmileMan(mCarDataEdit.getID(), mCarDataEdit.getRequestSmileManServiceType(), mCarDataEdit.getRequestSmileManPaymentAmount(), new Response.Listener<ServerResult>() {
            @Override
            public void onResponse(ServerResult response) {
                if (response.isSuccess()) {
                    Utility.showPopupOk(RegisterCarCompleteActivity.this, getString(R.string.popup_message_confirm_smileman_request), new PopupBase.OnClickListener() {
                        @Override
                        public void onClick() {
                            startMainActivity();
                        }
                    });
//                    if (mCarDataEdit.getRequestSmileManServiceType() == Constant.SMILEMAN_SERVICE_TYPE_CHECK_SELLER) {
//                        Utility.showPopupOk(RegisterCarCompleteActivity.this, getString(R.string.popup_message_confirm_smileman_request_1), new PopupBase.OnClickListener() {
//                            @Override
//                            public void onClick() {
//                                startMainActivity();
//                            }
//                        });
//                    } else {
//                        Utility.showPopupOk(RegisterCarCompleteActivity.this, getString(R.string.popup_message_confirm_smileman_request_2), new PopupBase.OnClickListener() {
//                            @Override
//                            public void onClick() {
//                                startMainActivity();
//                            }
//                        });
//                    }
                } else {
                    Utility.showPopupYesOrNo(RegisterCarCompleteActivity.this, getString(R.string.popup_message_check_smileman_re_request), new PopupBase.OnClickListener() {
                        @Override
                        public void onClick() {
                            requestSmileMan();
                        }
                    });
                }
            }
        });
    }

    private void startSellActivity() {
        Intent i = new Intent(RegisterCarCompleteActivity.this, RegisterCarActivity.class);
        i.putExtra(Constant.DATA_CAR_DATA, (CarData) mCarDataEdit);
        startActivity(i);
        overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
    }

    private void startMainActivity() {
        Intent i = new Intent(RegisterCarCompleteActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        overridePendingTransition(R.anim.anim_in_from_left, R.anim.anim_stay);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startSellActivity();
    }
    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_in_from_left, R.anim.anim_out_to_right);
    }

    @Override
    public void onFragmentSay(Bundle data) {

    }
}
