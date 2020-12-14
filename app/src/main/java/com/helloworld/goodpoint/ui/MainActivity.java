package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
<<<<<<< HEAD
=======
import android.view.View;
<<<<<<< HEAD
import android.widget.Toast;
>>>>>>> 4e5d1530d687f1a10a4e41fdf57a7de7bf73e2a8
=======
import android.text.Layout;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
>>>>>>> abdelrhman
=======
>>>>>>> parent of da76247... Coding in lost object
=======
>>>>>>> parent of 95103ed... Date of missing finsh
=======
>>>>>>> parent of afb0570... test

import com.helloworld.goodpoint.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import fragments.PageFragment1;
import fragments.PageFragment2;
import fragments.PageFragment3;

public class MainActivity extends AppCompatActivity {
    private ViewPager pager;
    private SlideAdapter slideAdapter;
    private LinearLayout Dots_layout;
    private ImageView[] dots;
    private int[] layouts = {R.layout.activity_home, R.layout.home1, R.layout.home2};
    private TextView Next,Skip,Start;
    private ImageView arrow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD
        List<Fragment> list = new ArrayList<>();
        list.add(new PageFragment1());
        list.add(new PageFragment2());
        list.add(new PageFragment3());

        pager = findViewById(R.id.viewpager);
        slideAdapter = new SlideAdapter(getSupportFragmentManager(), list);
        pager.setAdapter(slideAdapter);
        Dots_layout = (LinearLayout) findViewById(R.id.dotsLayout);
        createDots(0);
        arrow=(ImageView) findViewById(R.id.arrow);


        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                createDots(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
=======

>>>>>>> parent of afb0570... test
    }
<<<<<<< HEAD
<<<<<<< HEAD

    private void createDots(int current_position) {
        if (Dots_layout != null)
            Dots_layout.removeAllViews();
        dots = new ImageView[layouts.length];
        for (int i = 0; i < layouts.length; i++) {
            dots[i] = new ImageView(this);
            if (i == current_position) {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.active_dot));
            } else {
                dots[i].setImageDrawable(ContextCompat.getDrawable(this, R.drawable.default_dot));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT
                    , ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 0, 4, 0);
            Dots_layout.addView(dots[i], params);

        }

=======
    protected void Intent(View v)
    {
        Intent I  = new Intent(this , LostObjectDetailsActivity.class);
        startActivity(I);
>>>>>>> parent of 95103ed... Date of missing finsh
    }
=======
>>>>>>> parent of da76247... Coding in lost object
}