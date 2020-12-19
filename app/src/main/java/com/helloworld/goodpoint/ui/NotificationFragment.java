package com.helloworld.goodpoint.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.adapter.NotificationListAdapter;
import com.helloworld.goodpoint.pojo.NotificationItem;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {
    List<NotificationItem> list;
    ListView listView;
    TextView noNotification;
    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        ///*
        NotificationItem item = new NotificationItem("First Title", "12/10/2019 22:20", "this is a description 1",null);
        item.setRead(true);
        list.add(item);
        item = new NotificationItem("Second Title", "20/12/2019", "this is a description 2",null);
        list.add(item);
        item = new NotificationItem("Third Title", "02/02/2020", "this is a description 3",null);
        item.setRead(true);
        list.add(item);
        item = new NotificationItem("Forth Title", "13/06/2020", "this is a description 4",null);
        list.add(item);
        item = new NotificationItem("Fifth Title", "10/10/2020", "this is a description 5",null);
        list.add(item);
        item = new NotificationItem("Sixth Title", "13/12/2020", "this is a description 6",null);
        list.add(item);//*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        init(v);
        updateFragmentView();
        return v;
    }

    public void updateFragmentView() {
        if(list.isEmpty()){
            noNotification.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else {
            listView.setAdapter(new NotificationListAdapter(getContext(), 0, list, this));
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

    private void init(View v) {
        listView = v.findViewById(R.id.notification_listview);
        noNotification = v.findViewById(R.id.no_notification);
    }
}