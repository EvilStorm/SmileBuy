package com.autofactory.smilebuy.ui.main.setting.notice;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.autofactory.smilebuy.R;
import com.autofactory.smilebuy.component.NormalActivity;
import com.autofactory.smilebuy.data.model.NoticeData;

import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends NormalActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notice);

        List<NoticeData> noticeList = makeNoticeList();

        ((TextView) findViewById(R.id.title)).setText(getString(R.string.title_faq));

        ListView listView = (ListView) findViewById(R.id.listView);
        final NoticeAdapter noticeAdapter = new NoticeAdapter(this, noticeList);
        listView.setAdapter(noticeAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NoticeData noticeData = noticeAdapter.getItem(position);
                noticeData.setOpened(!noticeData.isOpened());
                noticeAdapter.notifyDataSetChanged();
            }
        });

        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private List<NoticeData> makeNoticeList() {
        List<NoticeData> list = new ArrayList<>();

        String [] titles = getResources().getStringArray(R.array.faq_title);
        String [] contents = getResources().getStringArray(R.array.faq_content);

        if(titles != null && contents != null && titles.length == contents.length) {
            for(int i=0; i<titles.length; i++) {
                NoticeData noticeData = new NoticeData();
                noticeData.setSubject(titles[i]);
                noticeData.setContent(contents[i]);
                noticeData.setDate("");

                list.add(noticeData);
            }
        }
        return list;
    }

}
