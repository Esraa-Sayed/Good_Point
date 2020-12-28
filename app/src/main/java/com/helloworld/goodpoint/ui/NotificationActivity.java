package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.adapter.NotificationListAdapter;
import com.helloworld.goodpoint.pojo.NotificationItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationActivity extends AppCompatActivity {

    List<NotificationItem> list;
    ListView listView;
    TextView noNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        init();
        createList();
        showView();
    }

    private void createList() {
        ///*
        NotificationItem item = new NotificationItem("First Title", new Date(2019-1900,11,25,10,55), "this is a description 1",null);
        item.setRead(true);
        list.add(item);
        item = new NotificationItem("Second Title", new Date(2020-1900,1,1,0,0), "this is a description 2",null);
        list.add(item);
        item = new NotificationItem("Third Title", new Date(2020-1900,2,25,10,55), "this is a description 3",null);
        item.setRead(true);
        list.add(item);
        item = new NotificationItem("Forth Title", new Date(2020-1900,3,17,15,0), "this is a description 4",null);
        list.add(item);
        item = new NotificationItem("Fifth Title", new Date(2020-1900,11,25,10,55), "this is a description 5",null);
        list.add(item);
        item = new NotificationItem("Sixth Title", Calendar.getInstance().getTime(), "this is a description 6",null);
        list.add(item);//*/
    }

    private void showView() {
        if(list.isEmpty()){
            noNotification.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else {
            listView.setAdapter(new NotificationListAdapter(this, 0, list));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    NotificationListAdapter adapter = (NotificationListAdapter)listView.getAdapter();
                    adapter.getItem(list.size()-i-1).setRead(true);
                    adapter.notifyDataSetChanged();
                }
            });

        }
    }

    private void init() {
        list = new ArrayList<>();
        listView = findViewById(R.id.notification_listview);
        noNotification = findViewById(R.id.no_notification);
    }
}