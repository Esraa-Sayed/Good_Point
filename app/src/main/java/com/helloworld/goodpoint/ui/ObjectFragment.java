package com.helloworld.goodpoint.ui;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.helloworld.goodpoint.R;

public class ObjectFragment extends Fragment {
    Spinner spinner;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_object, container, false);
        spinner = v.findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Types));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // Inflate the layout for this fragment
        return v;
    }
}