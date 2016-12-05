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

public class NoticeActivity extends NormalActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_notice);

        List<NoticeData> noticeList = makeNoticeList();

        ((TextView) findViewById(R.id.title)).setText(getString(R.string.title_notice));

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

        NoticeData noticeData = null;

        noticeData = new NoticeData();
        noticeData.setSubject(getString(R.string.notice_title_3));
        noticeData.setContent(getString(R.string.notice_content_3));
        noticeData.setDate("2016.08.12");
        list.add(noticeData);

        noticeData = new NoticeData();
        noticeData.setSubject(getString(R.string.notice_title_2));
        noticeData.setContent(getString(R.string.notice_content_2));
        noticeData.setDate("2016.07.30");
        list.add(noticeData);

        noticeData = new NoticeData();
        noticeData.setSubject(getString(R.string.notice_title_1));
        noticeData.setContent(getString(R.string.notice_content_1));
        noticeData.setDate("2016.03.31");
        list.add(noticeData);



//        noticeData = new NoticeData();
//        noticeData.setSubject("공지사항 제목 123231");
//        noticeData.setContent("공지사항이 나와야 한다. 엄청 많이 써볼까 .. 귀찮다");
//        noticeData.setDate("2016.01.24");
//
//        list.add(noticeData);
//
//        noticeData = new NoticeData();
//        noticeData.setSubject("공지사항 제목22 1");
//        noticeData.setContent("공지사항이 나와야 한다. 엄청 많이 써볼까 .. 귀찮다");
//        noticeData.setDate("2016.01.24");
//
//        list.add(noticeData);
//
//        noticeData = new NoticeData();
//        noticeData.setSubject("공지사항 제목 1411");
//        noticeData.setContent("공지사항이 나와야 한다.234234청 많이 써볼까 .. 귀찮다");
//        noticeData.setDate("2016.01.24");
//
//        list.add(noticeData);
//
//        noticeData = new NoticeData();
//        noticeData.setSubject("공지사항 제목23423 1");
//        noticeData.setContent("공지사항이 나와야 한다. 엄청 많423423이 써볼까 .. 귀찮다");
//        noticeData.setDate("2016.01.24");
//
//        list.add(noticeData);
//
//        noticeData = new NoticeData();
//        noticeData.setSubject("12312공지사항 제목 1");
//        noticeData.setContent("공지사항이 나와야 2342한다. 엄청 많이 234써볼까 .. 귀찮다");
//        noticeData.setDate("2016.01.24");
//
//        list.add(noticeData);

        return list;
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_stay, R.anim.anim_out_to_right);
    }
}