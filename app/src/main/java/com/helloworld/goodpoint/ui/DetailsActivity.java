package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.helloworld.goodpoint.R;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        int orientation = getResources().getConfiguration().orientation;
        if(orientation == Configuration.ORIENTATION_LANDSCAPE){
            LinearLayout layout = findViewById(R.id.parent_table_details);
            LinearLayout.MarginLayoutParams layoutParams = (LinearLayout.MarginLayoutParams) layout.getLayoutParams();
            float ver = getResources().getDimension(R.dimen._5sdp);
            float hor = getResources().getDimension(R.dimen._15sdp);
            int marginVer = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,ver,getResources().getDisplayMetrics());
            int marginHor = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,hor,getResources().getDisplayMetrics());
            layoutParams.setMargins(marginHor,marginVer,marginHor,marginVer);
            layout.requestLayout();
        }
    }
}