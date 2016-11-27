package com.autofactory.smilebuy.ui.main.car.register;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.application.Application;
import com.autofactory.smilebuy.component.Fragment;
import com.autofactory.smilebuy.component.FragmentActivity;
import com.autofactory.smilebuy.data.model.InterviewData;
import com.autofactory.smilebuy.util.Constant;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RegisterCarInterviewFragment extends Fragment {
    private RegisterCarActivity mRegisterCarActivity = null;

    private LinearLayout mContainer;
    private List<View> mAddedInterviews = new ArrayList<>();


    public RegisterCarInterviewFragment() {
        // Required empty public constructor
    }


    public static RegisterCarInterviewFragment newInstance(RegisterCarActivity fragmentActivity, int id, String name) {
        RegisterCarInterviewFragment fragment = new RegisterCarInterviewFragment();
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
        mView = inflater.inflate(R.layout.fra_register_car_3_interview, container, false);

        mContainer = (LinearLayout) findViewById(R.id.interviewContainer);
        mAddedInterviews.clear();


        findViewById(R.id.addInterview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(mFragmentActivity, AddInterviewActivity.class);
                startActivityForResult(i, AddInterviewActivity.REQUEST_CODE_QUESTION);
                mFragmentActivity.overridePendingTransition(R.anim.anim_in_from_bottom, R.anim.anim_stay);
            }
        });

        fillAtFirst();

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void addInterview(String question, String answer) {
        final View interview = getActivity().getLayoutInflater().inflate(R.layout.item_interview_sell_interview, null);
        ((TextView) interview.findViewById(R.id.question)).setText(question);
        EditText answerView = (EditText) interview.findViewById(R.id.answer);
        if(answer != null && answer.length() > 0) {
            answerView.setText(answer);
        }
        answerView.requestFocus();
        interview.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utility.showPopupOk(mFragmentActivity, getString(R.string.alert_delete_interview), new PopupBase.OnClickListener() {
                    @Override
                    public void onClick() {
                        mContainer.removeView(interview);
                        mAddedInterviews.remove(interview);
                        refreshInterviews();
                    }
                });
            }
        });

        mContainer.addView(interview, mContainer.getChildCount() - 1);
        mAddedInterviews.add(interview);
        refreshInterviews();
    }

    private void refreshInterviews() {
        for(int i=0; i<mAddedInterviews.size(); i++) {
            ((TextView) mAddedInterviews.get(i).findViewById(R.id.num)).setText(String.format("Q%d", i+1));
        }
    }


    private void fillAtFirst() {
        List<InterviewData> interviews = mRegisterCarActivity.getCarDataEdit().getInterviews();
        if(interviews.size() > 0) {
            for(int i=0; i<interviews.size(); i++) {
                InterviewData interviewData = interviews.get(i);
                addInterview(interviewData.getQuestion(), interviewData.getAnswer());
            }
        } else {
            if(Application.get().isManager()) {
                addInterview(getString(R.string.interview_for_manager), null);
            } else {
                String [] interviewQuestions = getResources().getStringArray(R.array.interview_qustion);
                int randIndex = (int) (Math.random() * interviewQuestions.length);
                addInterview(interviewQuestions[randIndex], null);
            }
        }
    }

    private boolean saveToCarData() {
        List<InterviewData> interviews = mRegisterCarActivity.getCarDataEdit().getInterviews();
        interviews.clear();

        for(int i=0; i<mAddedInterviews.size(); i++) {
            View interviewView = mAddedInterviews.get(i);
            String answer = ((TextView) interviewView.findViewById(R.id.answer)).getText().toString();
            if(answer != null && answer.length() > 0) {
                String question = ((TextView) interviewView.findViewById(R.id.question)).getText().toString();
                interviews.add(new InterviewData(question, answer));
            }
        }

        return interviews.size() > 0;
    }

    
    @Override
    public void onActivitySay(Bundle data) {
        if(data != null) {
            int id = data.getInt(FragmentActivity.KEY_SAY_ID);
            switch(id) {
                case FragmentActivity.SAY_ID_ON_NEXT:
                    if(saveToCarData()) {
                        Intent intent = new Intent(getActivity(), RegisterCarCompleteActivity.class);
                        intent.putExtra(Constant.DATA_CAR_DATA_EDIT, mRegisterCarActivity.getCarDataEdit());
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.anim_in_from_right, R.anim.anim_out_to_left);
                    } else {
                        Utility.showPopupOk(getActivity(), getString(R.string.popup_message_at_least_one_interview));
                    }
                    break;
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == AddInterviewActivity.REQUEST_CODE_QUESTION && resultCode == Activity.RESULT_OK && data != null) {
            String question = data.getStringExtra(AddInterviewActivity.RESULT_KEY_QUESTION);
            if(question != null && question.length() > 0) {
                addInterview(question, "");
            }
        }
    }
}

