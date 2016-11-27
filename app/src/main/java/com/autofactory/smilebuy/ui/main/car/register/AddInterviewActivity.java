package com.autofactory.smilebuy.ui.main.car.register;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.util.Utility;
import com.autofactory.smilebuy.util.popup.PopupBase;

public class AddInterviewActivity extends NormalActivity {
    public static final int REQUEST_CODE_QUESTION = 0;
    public static final String RESULT_KEY_QUESTION = "RESULT_KEY_QUESTION";

    private AddInterviewListAdapter mListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_add_interview);

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.selfQuestion).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final StringBuilder returnString = new StringBuilder();
                Utility.showPopupMakeInterview(AddInterviewActivity.this, returnString, new PopupBase.OnClickListener() {
                    @Override
                    public void onClick() {
                        if (returnString.toString() != null && returnString.toString().length() > 0) {
                            addInterview(returnString.toString());
                        }
                    }
                });
            }
        });

        mListAdapter = new AddInterviewListAdapter(this, getResources().getStringArray(R.array.interview_qustion));

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(mListAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                addInterview((String)mListAdapter.getItem(position));
            }
        });
    }


    private void addInterview(String question) {
        Intent result = new Intent();
        result.putExtra(RESULT_KEY_QUESTION, question);
        setResult(RESULT_OK, result);
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_bottom);
    }


}
