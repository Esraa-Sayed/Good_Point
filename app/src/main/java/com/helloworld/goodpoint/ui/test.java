package com.helloworld.goodpoint.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.helloworld.goodpoint.R;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class test extends AppCompatActivity  {


    subAdapter recyclerViewAdapter;
    RecyclerView rvItem;
    List<Bitmap> mfaces;
    Button Save_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r);
        rvItem = findViewById(R.id.q);
        mfaces = GlobalVar.FinialFacesThatWillGoToDataBase;
        createCard();
        Save_btn = (Button) findViewById(R.id.Save_btn);
        Save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyToast.makeText(test.this, "Done", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                startActivity(new Intent(test.this, HomeActivity.class));
                finish();
            }
        });
    }
    private void createCard() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(
                rvItem.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        recyclerViewAdapter = new subAdapter(buildSubItemList(mfaces), this);
        rvItem.setAdapter(recyclerViewAdapter);
        rvItem.setLayoutManager(layoutManager);

    }


    private List<sub> buildSubItemList(List<Bitmap> faces) {
        List<sub> subItemList = new ArrayList<>();
        for (int i = 0; i < faces.size(); i++) {
            sub subItem = new sub(faces.get(i), i);
            subItemList.add(subItem);
        }

        return subItemList;
    }


}
