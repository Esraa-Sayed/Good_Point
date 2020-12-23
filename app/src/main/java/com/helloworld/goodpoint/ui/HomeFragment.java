package com.helloworld.goodpoint.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.adapter.MyExpandableListAdapter;


public class HomeFragment extends Fragment {

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> objects; //To link group list with child list
    ExpandableListView expandableListView;
    ExpandableListAdapter expandableListAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home, container, false);

        // Inflate the layout for this fragment
        createGroupList();
        createObjects();

        expandableListView = v.findViewById(R.id.expanded_menu);
        expandableListAdapter = new MyExpandableListAdapter(getActivity(), groupList, objects); //getActivity
        expandableListView.setAdapter(expandableListAdapter);
        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            int lastExpandedPosition = -1;
            @Override
            public void onGroupExpand(int i) {
                if(lastExpandedPosition != -1 && i != lastExpandedPosition){
                    expandableListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = i;
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int i, int i1, long l) {
                String selected = expandableListAdapter.getChild(i,i1).toString();
                return true;
            }
        });

        return  v;
    }


    private void createObjects() {
        String[] LossesObjects = {"Mobile HUAWEI", "Black wallet", "Child", "ID card"};
        String[] FindingsObjects = {"Money 250 L.E", "White Cat", "Laptop Dell", "Gray Wristwatch"};

        objects = new HashMap<String, List<String>>();
        for (String group : groupList){
            if (group.equals(getString(R.string.Losses))){
                loadChild(LossesObjects);
            }
            else if (group.equals(getString(R.string.Founds)))
                loadChild(FindingsObjects);
            objects.put(group, childList);
        }
    }

    private void loadChild(String[] AllObjects) {
        childList = new ArrayList<>();
        for (String obj : AllObjects)
            childList.add(obj);
    }


    private void createGroupList() {
        groupList = new ArrayList<>();
        groupList.add(getString(R.string.Losses));
        groupList.add(getString(R.string.Founds));
    }

}