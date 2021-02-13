package com.helloworld.goodpoint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class Match extends AppCompatActivity {
ListView listView;
//String ObjStat[] ={},ObjDetails;
String status[]={"Has found" ,"Its owner has been found","Has found" ,"Its owner has been found",
        "Has found" ,"Its owner has been found","Has found" ,"Its owner has been found" }  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
        listView =findViewById(R.id.lisView);
        MyAdapter myAdapter  = new MyAdapter(this,status);
        listView.setAdapter(myAdapter);
    }
    class MyAdapter extends ArrayAdapter<String>{
        private Activity context;
        private String Rtitle[];
        private String Rstatus[];
        private int Rimg[];

        public MyAdapter(@NonNull Activity context, String Rstatus[]) {
            super(context, R.layout.row, Rstatus);
            this.context = context;
            //this.Rtitle = Rtitle;
            this.Rstatus = Rstatus;
           // this.Rimg = Rimg;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


            View r = convertView;
            viewHolder viewholder =null;
            if(r==null){
                LayoutInflater layoutInflater= context.getLayoutInflater();
                r = layoutInflater.inflate(R.layout.row,null,true);
                viewholder = new viewHolder(r);
                r.setTag(viewholder);
            }
            else{

                viewholder = (viewHolder) r.getTag();
            }
            viewholder.imageView.setImageResource(R.drawable.ic_baseline_gallery_24);
            viewholder.stat.setText(status[position]);

            return r;
        }
        class viewHolder{
            private ImageView imageView;
            private TextView stat;
            viewHolder(View v){
                 imageView =  v.findViewById(R.id.obj);
                 stat =  v.findViewById(R.id.status);
            }
        }
    }

}