package com.autofactory.smilebuy.ui.main.car.smilebuy.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.ui.main.car.register.RegisterCarActivity;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;

public class RegisterCarSmileBuyInfoFragment extends Fragment {
    private final int CAN_GO_NAME = 0;
    private final int CAN_GO_CARNUM = 1;
    private final int CAN_GO_MILEAGE = 2;
    private final int CAN_GO_AGE_YEAR = 3;
    private final int CAN_GO_AGE_MONTH = 4;
    private final int CAN_GO_FUEL = 5;
    private final int CAN_GO_TRANSMISSION = 6;
    private final int CAN_GO_ENGINE = 7;
    private final int CAN_GO_ACCIDENT = 8;
    private final int CAN_GO_PREDICTED_PRICE = 9;
    private final int CAN_GO_MANAGER_COMMENT = 10;

    private boolean[] mCanGo = new boolean[CAN_GO_MANAGER_COMMENT + 1];

    private Spinner mFuel;
    private Spinner mTransmission;

    private RegisterCarSmileBuyActivity mRegisterCarSmileBuyActivity = null;

    public RegisterCarSmileBuyInfoFragment() {
        // Required empty public constructor
    }


    public static RegisterCarSmileBuyInfoFragment newInstance(RegisterCarSmileBuyActivity fragmentActivity, int id, String name) {
        RegisterCarSmileBuyInfoFragment fragment = new RegisterCarSmileBuyInfoFragment();
        fragment.init(fragmentActivity, id, name);
        fragment.mRegisterCarSmileBuyActivity = fragmentActivity;
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < mCanGo.length; i++) {
            mCanGo[i] = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fra_register_car_smile_buy_2_info, container, false);

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

        editText = (EditText) findViewById(R.id.mileage);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_MILEAGE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        editText = (EditText) findViewById(R.id.ageYear);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_AGE_YEAR);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        editText = (EditText) findViewById(R.id.ageMonth);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_AGE_MONTH);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        editText = (EditText) findViewById(R.id.engine);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_ENGINE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        editText = (EditText) findViewById(R.id.accident);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_ACCIDENT);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        editText = (EditText) findViewById(R.id.predictedPrice);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_PREDICTED_PRICE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });
        editText = (EditText) findViewById(R.id.managerComment);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                setCanGo(s != null && s.length() > 0, CAN_GO_MANAGER_COMMENT);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });



        mFuel = (Spinner) findViewById(R.id.fuelType);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, getResources().getStringArray(R.array.fuel));
        mFuel.setAdapter(spinnerAdapter);
        mFuel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    setCanGo(true, CAN_GO_FUEL);
                } else {
                    setCanGo(false, CAN_GO_FUEL);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //setCanGo(false, CAN_GO_AREA);
            }
        });
        mTransmission = (Spinner) findViewById(R.id.transmissionType);
        spinnerAdapter = new ArrayAdapter<>(getActivity(), R.layout.item_spinner, getResources().getStringArray(R.array.transmission));
        mTransmission.setAdapter(spinnerAdapter);
        mTransmission.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {
                    setCanGo(true, CAN_GO_TRANSMISSION);
                } else {
                    setCanGo(false, CAN_GO_TRANSMISSION);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //setCanGo(false, CAN_GO_AREA);
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
        editText = (EditText) findViewById(R.id.mileage);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_MILEAGE);
        editText = (EditText) findViewById(R.id.ageYear);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_AGE_YEAR);
        editText = (EditText) findViewById(R.id.ageMonth);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_AGE_MONTH);
        editText = (EditText) findViewById(R.id.engine);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_ENGINE);
        editText = (EditText) findViewById(R.id.accident);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_ACCIDENT);
        editText = (EditText) findViewById(R.id.predictedPrice);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_PREDICTED_PRICE);
        editText = (EditText) findViewById(R.id.managerComment);
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_MANAGER_COMMENT);
        setCanGo(mFuel.getSelectedItemPosition() > 0, CAN_GO_FUEL);
        setCanGo(mTransmission.getSelectedItemPosition() > 0, CAN_GO_TRANSMISSION);
    }

    private void setCanGo(boolean can, int canGoType) {
        mCanGo[canGoType] = can;

        Bundle data = new Bundle();
        data.putInt(RegisterCarActivity.KEY_SAY_ID, RegisterCarActivity.SAY_ID_CAN_GO_NEXT);
        data.putBoolean(RegisterCarActivity.KEY_SAY_DATA, isCanGo());
        mFragmentActivity.onFragmentSay(data);
    }

    private boolean isCanGo() {
        for (int i = 0; i < mCanGo.length; i++) {
            if (mCanGo[i] == false) {
                return false;
            }
        }
        return true;
    }

    private void fillAtFirst() {
        EditText editText = (EditText) findViewById(R.id.name);
        editText.setText(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getName());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_NAME);
        editText = (EditText) findViewById(R.id.carNum);
        editText.setText(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getCarNum());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_CARNUM);
        TextView textView = (TextView) findViewById(R.id.managerInfo);
        textView.setText(String.format(getString(R.string.text_sell_info_manager_info), mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getManager().getNickName(), mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getManager().getMobileNum()));
        editText = (EditText) findViewById(R.id.mileage);
        editText.setText(""+mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getMileage());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_MILEAGE);
        editText = (EditText) findViewById(R.id.ageYear);
        editText.setText("" + mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getAgeYear());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_AGE_YEAR);
        editText = (EditText) findViewById(R.id.ageMonth);
        editText.setText("" + mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getAgeMonth());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_AGE_MONTH);
        editText = (EditText) findViewById(R.id.engine);
        editText.setText("" + mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getEngine());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_ENGINE);
        editText = (EditText) findViewById(R.id.accident);
        editText.setText(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getAccident());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_ACCIDENT);
        editText = (EditText) findViewById(R.id.predictedPrice);
        editText.setText(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getPredictedPrice());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_PREDICTED_PRICE);
        editText = (EditText) findViewById(R.id.managerComment);
        editText.setText(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getManagerComment());
        setCanGo(editText.getText() != null && editText.getText().length() > 0, CAN_GO_MANAGER_COMMENT);


        mFuel.setSelection(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getFuelType());
        setCanGo(mFuel.getSelectedItemPosition() > 0, CAN_GO_FUEL);
        mTransmission.setSelection(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().getTransmissionType());
        setCanGo(mTransmission.getSelectedItemPosition() > 0, CAN_GO_TRANSMISSION);

        if(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().isSunroof()) {
            ((RadioButton) findViewById(R.id.sunroofY)).toggle();
        } else {
            ((RadioButton) findViewById(R.id.sunroofN)).toggle();
        }
        if(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().isSmartKey()) {
            ((RadioButton) findViewById(R.id.smartKeyY)).toggle();
        } else {
            ((RadioButton) findViewById(R.id.smartKeyN)).toggle();
        }
        if(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().isBlackBox()) {
            ((RadioButton) findViewById(R.id.blackBoxY)).toggle();
        } else {
            ((RadioButton) findViewById(R.id.blackBoxN)).toggle();
        }
        if(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().isRearCamera()) {
            ((RadioButton) findViewById(R.id.rearCameraY)).toggle();
        } else {
            ((RadioButton) findViewById(R.id.rearCameraN)).toggle();
        }
        if(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().isNavigation()) {
            ((RadioButton) findViewById(R.id.navigationY)).toggle();
        } else {
            ((RadioButton) findViewById(R.id.navigationN)).toggle();
        }
        if(mRegisterCarSmileBuyActivity.getCarDataSmileBuy().isCaffein()) {
            ((RadioButton) findViewById(R.id.caffeinY)).toggle();
        } else {
            ((RadioButton) findViewById(R.id.caffeinN)).toggle();
        }

    }

    private void saveToCarData() {
        EditText editText = (EditText) findViewById(R.id.name);
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setName(editText.getText().toString());
        editText = (EditText) findViewById(R.id.carNum);
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setCarNum(editText.getText().toString());
        editText = (EditText) findViewById(R.id.mileage);
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setMileage((int) Utility.parseFloat(editText.getText().toString()));
        editText = (EditText) findViewById(R.id.ageYear);
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setAgeYear((int) Utility.parseFloat(editText.getText().toString()));
        editText = (EditText) findViewById(R.id.ageMonth);
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setAgeMonth((int) Utility.parseFloat(editText.getText().toString()));
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setFuelType(mFuel.getSelectedItemPosition());
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setTransmissionType(mTransmission.getSelectedItemPosition());
        editText = (EditText) findViewById(R.id.engine);
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setEngine((int) Utility.parseFloat(editText.getText().toString()));

        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setSunroof(((RadioButton) findViewById(R.id.sunroofY)).isChecked());
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setSmartKey(((RadioButton) findViewById(R.id.smartKeyY)).isChecked());
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setBlackBox(((RadioButton) findViewById(R.id.blackBoxY)).isChecked());
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setRearCamera(((RadioButton) findViewById(R.id.rearCameraY)).isChecked());
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setNavigation(((RadioButton) findViewById(R.id.navigationY)).isChecked());
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setCaffein(((RadioButton) findViewById(R.id.caffeinY)).isChecked());

        editText = (EditText) findViewById(R.id.accident);
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setAccident(editText.getText().toString());
        editText = (EditText) findViewById(R.id.predictedPrice);
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setPredictedPrice(editText.getText().toString());
        editText = (EditText) findViewById(R.id.managerComment);
        mRegisterCarSmileBuyActivity.getCarDataSmileBuy().setManagerComment(editText.getText().toString());
    }

    @Override
    public void onActivitySay(Bundle data) {
        if (data != null) {
            int id = data.getInt(FragmentActivity.KEY_SAY_ID);
            switch (id) {
                case FragmentActivity.SAY_ID_ON_NEXT:
                    saveToCarData();
                    Intent intent = new Intent(getActivity(), RegisterCarSmileBuyCompleteActivity.class);
                    intent.putExtra(Constant.DATA_CAR_DATA, mRegisterCarSmileBuyActivity.getCarDataSmileBuy());
                    intent.putExtra(Constant.DATA_CAR_IS_UPDATE, mRegisterCarSmileBuyActivity.isUpdate());
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
                    break;
            }
        }
    }
}

