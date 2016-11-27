package com.autofactory.smilebuy.ui.main.car.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.ui.main.car.smilebuy.RequestSmileManActivity;
import com.autofactory.smilebuy.ui.main.car.smilebuy.WhatIsSmileManActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.bumptech.glide.util.Util;

public class RegisterCarInfoFragment extends Fragment {
    public static final int REQUEST_SMILEMAN_ACTIVITY = 90;



    private int mCanGo = 0x0;
    private final int CAN_GO_NAME = 0x0001;
    private final int CAN_GO_CARNUM = 0x0010;
    private final int CAN_GO_PRICE = 0x0100;
    private final int CAN_GO_AREA = 0x1000;
    private final int CAN_GO_ALL = CAN_GO_NAME | CAN_GO_CARNUM | CAN_GO_PRICE | CAN_GO_AREA;

    private RegisterCarActivity mRegisterCarActivity = null;

    private View mForUser;
    private View mForManager;

    private Spinner mArea;
    private Spinner mFuel;
    private Spinner mTransmission;
    private Button mRequestSmileMan;
    private CheckBox mCanSmileMan;

    private CheckBox mAlliance;
    private CheckBox mCommission;



    public RegisterCarInfoFragment() {
        // Required empty public constructor
    }


    public static RegisterCarInfoFragment newInstance(RegisterCarActivity fragmentActivity, int id, String name) {
        RegisterCarInfoFragment fragment = new RegisterCarInfoFragment();
        fragment.init(fragmentActivity, id, name);
        fragment.mRegisterCarActivity = fragmentActivity;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fra_register_car_2_info, container, false);

        EditText editText = (EditText) findViewById(R.id.name);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_NAME);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

        });
        editText = (EditText) findViewById(R.id.carNum);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_CARNUM);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
//        editText = (EditText) findViewById(R.id.phoneNum);
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                setCanGo(s != null && s.length() > 0, CAN_GO_PHONENUM);
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//            }
//        });
        editText = (EditText) findViewById(R.id.price);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_PRICE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        mArea = (Spinner) findViewById(R.id.area);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, getResources().getStringArray(R.array.area));
        mArea.setAdapter(spinnerAdapter);
        mArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    setCanGo(true, CAN_GO_AREA);
                } else {
                    setCanGo(false, CAN_GO_AREA);
                }
                if (Utility.checkSmileManAvailalbe(mFragmentActivity, mArea.getSelectedItemPosition()) != Constant.CheckSmileMan.AVAILABLE) {
                    if (mRegisterCarActivity.getCarDataEdit().getRequestSmileManServiceType() != -1) {
                        Utility.showPopupOk(getActivity(), getString(R.string.popup_message_smileman_not_available));
                        mRegisterCarActivity.getCarDataEdit().setRequestSmileManServiceType(-1);
                    }
                    mCanSmileMan.setChecked(false);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //setCanGo(false, CAN_GO_AREA);
            }
        });

        mFuel = (Spinner) findViewById(R.id.fuelType);
        spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, getResources().getStringArray(R.array.fuel));
        mFuel.setAdapter(spinnerAdapter);
        mTransmission = (Spinner) findViewById(R.id.transmissionType);
        spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, getResources().getStringArray(R.array.transmission));
        mTransmission.setAdapter(spinnerAdapter);


        mRegisterCarActivity.getCarDataEdit().setRequestSmileManServiceType(-1);
        mRequestSmileMan = (Button) findViewById(R.id.requestSmileMan);
        mRequestSmileMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.CheckSmileMan checkSmileMan = Utility.checkSmileManAvailalbe(mFragmentActivity, mArea.getSelectedItemPosition());
                if (checkSmileMan == Constant.CheckSmileMan.AVAILABLE) {
                    Intent i = new Intent(getActivity(), RequestSmileManActivity.class);
                    i.putExtra(RequestSmileManActivity.FROM_SELLER, true);
                    i.putExtra(RequestSmileManActivity.FROM_REGISTER, true);
                    startActivityForResult(i, REQUEST_SMILEMAN_ACTIVITY);
                } else if (checkSmileMan == Constant.CheckSmileMan.NOT_YET) {
                    Utility.showPopupOk(getActivity(), getString(R.string.popup_message_smileman_not_available));
                    mRegisterCarActivity.getCarDataEdit().setRequestSmileManServiceType(-1);
                } else if (checkSmileMan == Constant.CheckSmileMan.CHOOSE_AREA) {
                    Utility.showPopupOk(getActivity(), getString(R.string.popup_message_smileman_not_available_choose_area));
                    mRegisterCarActivity.getCarDataEdit().setRequestSmileManServiceType(-1);
                }
            }
        });
        mCanSmileMan = (CheckBox) findViewById(R.id.canSmileMan);
        mCanSmileMan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Constant.CheckSmileMan checkSmileMan = Utility.checkSmileManAvailalbe(mFragmentActivity, mArea.getSelectedItemPosition());
                if (checkSmileMan == Constant.CheckSmileMan.NOT_YET) {
                    Utility.showPopupOk(getActivity(), getString(R.string.popup_message_smileman_not_available));
                    mCanSmileMan.setChecked(false);
                } else if (checkSmileMan == Constant.CheckSmileMan.CHOOSE_AREA) {
                    Utility.showPopupOk(getActivity(), getString(R.string.popup_message_smileman_not_available_choose_area));
                    mCanSmileMan.setChecked(false);
                }
            }
        });
        findViewById(R.id.whatIsSmileMan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), WhatIsSmileManActivity.class));
                getActivity().overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
            }
        });

        mForUser = findViewById(R.id.forUser);
        mForManager = findViewById(R.id.forManager);
        if (Application.get().isManager()) {
            mForUser.setVisibility(View.GONE);
            mForManager.setVisibility(View.VISIBLE);
        } else {
            mRegisterCarActivity.getCarDataEdit().setCarType(Constant.CAR_TYPE_NORMAL);
            mForUser.setVisibility(View.VISIBLE);
            mForManager.setVisibility(View.GONE);
        }

        mAlliance = (CheckBox) findViewById(R.id.alliance);
        mAlliance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectCarType(Constant.CAR_TYPE_ALLIANCE);
                }
            }
        });
        mCommission = (CheckBox) findViewById(R.id.commission);
        mCommission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectCarType(Constant.CAR_TYPE_COMMISSION);
                }
            }
        });


        fillAtFirst();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

        EditText editText = (EditText) findViewById(R.id.name);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_NAME);
        editText = (EditText) findViewById(R.id.carNum);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_CARNUM);
//        editText = (EditText) findViewById(R.id.phoneNum);
//        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_PHONENUM);
        editText = (EditText) findViewById(R.id.price);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_PRICE);
        Spinner spinner = (Spinner) findViewById(R.id.area);
        setCanGo(spinner.getSelectedItemPosition() > 0, CAN_GO_AREA);
    }


    private void selectCarType(int carType) {
        if (carType == Constant.CAR_TYPE_COMMISSION) {
            mAlliance.setChecked(false);
            mCommission.setChecked(true);
        } else {
            mAlliance.setChecked(true);
            mCommission.setChecked(false);
        }
    }

    private void setCanGo(boolean can, int canGoType) {
        if (can) {
            mCanGo = mCanGo | canGoType;
        } else {
            mCanGo = mCanGo & (~canGoType);
        }

        Bundle data = new Bundle();
        data.putInt(RegisterCarActivity.KEY_SAY_ID, RegisterCarActivity.SAY_ID_CAN_GO_NEXT);
        data.putBoolean(RegisterCarActivity.KEY_SAY_DATA, mCanGo == CAN_GO_ALL);
        mFragmentActivity.onFragmentSay(data);
    }

    private void fillAtFirst() {
        EditText editText = (EditText) findViewById(R.id.name);
        editText.setText(mRegisterCarActivity.getCarDataEdit().getName());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_NAME);
        editText = (EditText) findViewById(R.id.carNum);
        editText.setText(mRegisterCarActivity.getCarDataEdit().getCarNum());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_CARNUM);
//        editText = (EditText) findViewById(R.id.phoneNum);
//        editText.setText(mRegisterCarActivity.getCarDataEdit().getPhoneNum());
//        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_PHONENUM);
        editText = (EditText) findViewById(R.id.price);
        if (mRegisterCarActivity.getCarDataEdit().getPrice() >= 0) {
            editText.setText("" + mRegisterCarActivity.getCarDataEdit().getPrice());
        }
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_PRICE);
        mArea.setSelection(mRegisterCarActivity.getCarDataEdit().getAreaType());
        setCanGo(mArea.getSelectedItemPosition() > 0, CAN_GO_AREA);
        mCanSmileMan.setChecked(mRegisterCarActivity.getCarDataEdit().isCanSmileBuy());

        if (Application.get().isManager()) {
            selectCarType(mRegisterCarActivity.getCarDataEdit().getCarType());
        }


        editText = (EditText) findViewById(R.id.mileage);
        if (mRegisterCarActivity.getCarDataEdit().getMileage() >= 0) {
            editText.setText("" + mRegisterCarActivity.getCarDataEdit().getMileage());
        }
        editText = (EditText) findViewById(R.id.ageYear);
        if (mRegisterCarActivity.getCarDataEdit().getAgeYear() >= 0) {
            editText.setText("" + mRegisterCarActivity.getCarDataEdit().getAgeYear());
        }
        editText = (EditText) findViewById(R.id.ageMonth);
        if (mRegisterCarActivity.getCarDataEdit().getAgeMonth() >= 0) {
            editText.setText("" + mRegisterCarActivity.getCarDataEdit().getAgeMonth());
        }
        mFuel.setSelection(mRegisterCarActivity.getCarDataEdit().getFuelType());
        mTransmission.setSelection(mRegisterCarActivity.getCarDataEdit().getTransmissionType());
        editText = (EditText) findViewById(R.id.engine);
        if (mRegisterCarActivity.getCarDataEdit().getEngine() >= 0) {
            editText.setText("" + mRegisterCarActivity.getCarDataEdit().getEngine());
        }

        editText = (EditText) findViewById(R.id.additional);
        editText.setText(mRegisterCarActivity.getCarDataEdit().getAdditional());
    }

    private void saveToCarData() {
        EditText editText = (EditText) findViewById(R.id.name);
        mRegisterCarActivity.getCarDataEdit().setName(editText.getText().toString());
        editText = (EditText) findViewById(R.id.carNum);
        mRegisterCarActivity.getCarDataEdit().setCarNum(editText.getText().toString());
//        editText = (EditText) findViewById(R.id.phoneNum);
//        mRegisterCarActivity.getCarDataEdit().setPhoneNum(editText.getText().toString());
        editText = (EditText) findViewById(R.id.price);
        mRegisterCarActivity.getCarDataEdit().setPrice((int) Utility.parseFloat(editText.getText().toString()));
        mRegisterCarActivity.getCarDataEdit().setAreaType(mArea.getSelectedItemPosition());
        mRegisterCarActivity.getCarDataEdit().setCanSmileBuy(mCanSmileMan.isChecked());

        if (Application.get().isManager()) {
            if (mAlliance.isChecked()) {
                mRegisterCarActivity.getCarDataEdit().setCarType(Constant.CAR_TYPE_ALLIANCE);
            } else {
                mRegisterCarActivity.getCarDataEdit().setCarType(Constant.CAR_TYPE_COMMISSION);
            }
        } else {
            mRegisterCarActivity.getCarDataEdit().setCarType(Constant.CAR_TYPE_NORMAL);
        }


        editText = (EditText) findViewById(R.id.mileage);
        mRegisterCarActivity.getCarDataEdit().setMileage((int) Utility.parseFloat(editText.getText().toString()));
        editText = (EditText) findViewById(R.id.ageYear);
        mRegisterCarActivity.getCarDataEdit().setAgeYear((int) Utility.parseFloat(editText.getText().toString()));
        editText = (EditText) findViewById(R.id.ageMonth);
        mRegisterCarActivity.getCarDataEdit().setAgeMonth((int) Utility.parseFloat(editText.getText().toString()));
        mRegisterCarActivity.getCarDataEdit().setFuelType(mFuel.getSelectedItemPosition());
        mRegisterCarActivity.getCarDataEdit().setTransmissionType(mTransmission.getSelectedItemPosition());
        editText = (EditText) findViewById(R.id.engine);
        mRegisterCarActivity.getCarDataEdit().setEngine((int) Utility.parseFloat(editText.getText().toString()));

        editText = (EditText) findViewById(R.id.additional);
        mRegisterCarActivity.getCarDataEdit().setAdditional(editText.getText().toString());
    }

    @Override
    public void onActivitySay(Bundle data) {
        if (data != null) {
            int id = data.getInt(FragmentActivity.KEY_SAY_ID);
            switch (id) {
                case FragmentActivity.SAY_ID_ON_NEXT:
                    saveToCarData();
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SMILEMAN_ACTIVITY) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    mRegisterCarActivity.getCarDataEdit().setRequestSmileManServiceType(data.getIntExtra(RequestSmileManActivity.SERVICE_TYPE, -1));
                    mRegisterCarActivity.getCarDataEdit().setRequestSmileManPaymentAmount(data.getIntExtra(RequestSmileManActivity.PAYMENT_AMOUNT, -1));

                    if(mRegisterCarActivity.getCarDataEdit().getRequestSmileManServiceType() != -1) {
                        mRequestSmileMan.setEnabled(false);
                    }
                }
            }
        }
    }
}

