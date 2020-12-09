package com.helloworld.goodpoint.ui;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.helloworld.goodpoint.R;

public class ObjectFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private String mParam1;

    public ObjectFragment() {
        // Required empty public constructor
    }
    public static ObjectFragment newInstance(String param1, String param2) {
        ObjectFragment fragment = new ObjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_object, container, false);
    }
}