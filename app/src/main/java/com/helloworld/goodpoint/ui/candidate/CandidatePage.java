package com.helloworld.goodpoint.ui.candidate;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.helloworld.goodpoint.R;

import java.util.ArrayList;
import java.util.List;

public class CandidatePage extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private static final String TAG = "CandidatePage";
    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    RadioButton rb;
    Button Done_btn;
    SubItemAdapter recyclerViewAdapter;
    TextView type;
    lostitem item;
    List<lostitem> items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_candidate_page);
        type=findViewById(R.id.tv_item_title1);
        RecyclerView rvItem = findViewById(R.id.rv_sub_item1);
        items=new ArrayList<>();
        item=new lostitem("latop","details");
        items.add(item);
        item=new lostitem("latop","details");
        items.add(item);
        item=new lostitem("latop","details");
        items.add(item);


        type.setText("Laptop");

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                rvItem.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        recyclerViewAdapter = new SubItemAdapter(buildSubItemList(items), this);
        recyclerViewAdapter.setOnItemClickListener(this);
        rvItem.setAdapter(recyclerViewAdapter);
        rvItem.setLayoutManager(layoutManager);

    }

    private List<com.helloworld.goodpoint.ui.candidate.SubItem> buildSubItemList(List<lostitem> items) {
        List<com.helloworld.goodpoint.ui.candidate.SubItem> subItemList = new ArrayList<>();
        for (int i = 0; i < items.size(); i++) {
            com.helloworld.goodpoint.ui.candidate.SubItem subItem = new com.helloworld.goodpoint.ui.candidate.SubItem(items.get(i).getType(),items.get(i).getDescriotion(), i);
            subItemList.add(subItem);
        }

        return subItemList;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Toast.makeText(this, "selected", Toast.LENGTH_SHORT).show();
    }
    }

