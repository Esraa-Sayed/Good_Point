package com.helloworld.goodpoint.ui.lostFoundObject;

import android.app.Fragment;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.helloworld.goodpoint.R;

import java.util.ArrayList;
import java.util.List;

public class ObjectFragment extends Fragment implements AdapterView.OnItemSelectedListener {
   private Spinner spinner;
   private List<String> list;
   private AutoCompleteTextView autoCom;
   private TextInputLayout other;
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
        other = v.findViewById(R.id.other);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                getActivity().getApplicationContext(),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.Types));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          int posit = parent.getSelectedItemPosition();
          if(posit == 9)
          {
              other.setVisibility(View.VISIBLE);
          }
          else
          {
              other.setVisibility(View.GONE);
          }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}