package com.helloworld.goodpoint.ui.lostObject;

import android.app.Fragment;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.helloworld.goodpoint.R;

import java.util.ArrayList;
import java.util.List;

public class ObjectFragment extends Fragment {
   private Spinner spinner;
   private List<String> list;
    private AutoCompleteTextView autoCom;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prepareList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_object, container, false);
        spinner = v.findViewById(R.id.spinner);
        autoCom = v.findViewById(R.id.ColorOfObject);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Types));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), android.R.layout.simple_list_item_1, list);
        autoCom.setThreshold(1);//start working from first char
        autoCom.setAdapter(adapter);
        // Inflate the layout for this fragment
        return v;
    }
    protected void prepareList() {
        list = new ArrayList<>();
        list.add(getString(R.string.Blue));
        list.add(getString(R.string.Red));
        list.add(getString(R.string.Yellow));
        list.add(getString(R.string.Orange));
        list.add(getString(R.string.Green));
        list.add(getString(R.string.Violet));
        list.add(getString(R.string.Brown));
        list.add(getString(R.string.Magenta));
        list.add(getString(R.string.Tan));
        list.add(getString(R.string.Cyan));
        list.add(getString(R.string.Olive));
        list.add(getString(R.string.Pink));
        list.add(getString(R.string.Black));
        list.add(getString(R.string.White));
        list.add(getString(R.string.Gray));
        list.add(getString(R.string.Purple));
    }
}