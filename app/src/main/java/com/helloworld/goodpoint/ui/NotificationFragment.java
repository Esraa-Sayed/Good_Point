package com.helloworld.goodpoint.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.adapter.NotificationListAdapter;
import com.helloworld.goodpoint.pojo.NotificationItem;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class NotificationFragment extends Fragment {

    List<NotificationItem> list;
    ListView listView;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        list.add(new NotificationItem("First Title", calendar, "this is a description 1",null));
        calendar.set(2020,12,10);
        list.add(new NotificationItem("Second Title", calendar, "this is a description 2",null));
        calendar.set(2020,12,1);
        NotificationItem item = new NotificationItem("Third Title", calendar, "this is a description 3",null);
        item.setRead(true);
        list.add(item);
        item = new NotificationItem("Forth Title", calendar, "this is a description 4",null);
        list.add(item);
        item = new NotificationItem("Fifth Title", calendar, "this is a description 5",null);
        list.add(item);
        item = new NotificationItem("Sixth Title", calendar, "this is a description 6",null);
        list.add(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        listView = v.findViewById(R.id.notification_listview);
        listView.setAdapter(new NotificationListAdapter(getContext(),0,list));
        return v;
    }
}