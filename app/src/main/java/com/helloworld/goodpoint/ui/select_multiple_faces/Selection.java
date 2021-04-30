package com.helloworld.goodpoint.ui.select_multiple_faces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.ui.GlobalVar;
import com.helloworld.goodpoint.ui.HomeActivity;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.ArrayList;
import java.util.List;

public class Selection extends AppCompatActivity {

    private static final String TAG = "Selection";
    //vars
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    RadioButton rb;
    Button Done_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.multiple_faces_selection);

        getImages();
        RecyclerView rvItem = findViewById(R.id.recycler_view_items);
        LinearLayoutManager layoutManager = new LinearLayoutManager(com.helloworld.goodpoint.ui.select_multiple_faces.Selection.this);
        ItemListAdapter itemAdapter = new ItemListAdapter(buildItemList(), this);
        rvItem.setAdapter(itemAdapter);
        rvItem.setLayoutManager(layoutManager);
        Done_btn = (Button) findViewById(R.id.Done_btn);
        Done_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FancyToast.makeText(Selection.this, "Thank you", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                startActivity(new Intent(Selection.this, HomeActivity.class));
                finish();
            }
        });


    }

    //Toast.makeText(Selection.this,
//"size="+faces.get(i).size(),
//Toast.LENGTH_LONG).show();
    private List<com.helloworld.goodpoint.ui.select_multiple_faces.ItemList> buildItemList() {
        List<com.helloworld.goodpoint.ui.select_multiple_faces.ItemList> itemList = new ArrayList<>();
        List<List<Bitmap>> faces = GlobalVar.allFaces;
        List<Bitmap> mfaces = GlobalVar.ImgThatHaveMoreThanOneFace;
        for (int i = 0; i < mfaces.size(); i++) {
            com.helloworld.goodpoint.ui.select_multiple_faces.ItemList item = new com.helloworld.goodpoint.ui.select_multiple_faces.ItemList(mfaces.get(i), buildSubItemList(faces.get(i)));
            itemList.add(item);
        }
        return itemList;
    }

    private List<com.helloworld.goodpoint.ui.select_multiple_faces.SubItemList> buildSubItemList(List<Bitmap> faces) {
        List<com.helloworld.goodpoint.ui.select_multiple_faces.SubItemList> subItemList = new ArrayList<>();
        for (int i = 0; i < faces.size(); i++) {
            com.helloworld.goodpoint.ui.select_multiple_faces.SubItemList subItem = new com.helloworld.goodpoint.ui.select_multiple_faces.SubItemList(faces.get(i));
            subItemList.add(subItem);
        }
        // addRadioButtons(10);

        return subItemList;
    }

    private void getImages() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");

        mImageUrls.add("https://c1.staticflickr.com/5/4636/25316407448_de5fbf183d_o.jpg");
        mNames.add("Havasu Falls");

        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");


    }


}
