package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;
import com.google.gson.JsonObject;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.adapter.NotificationListAdapter;
import com.helloworld.goodpoint.pojo.NotificationItem;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.retrofit.ApiClient;
import com.helloworld.goodpoint.retrofit.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationActivity extends AppCompatActivity {

    List<NotificationItem> list;
    ListView listView;
    TextView noNotification;
    Bitmap img;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        init();
        createList();
    }

    private void createList() {
        ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getApplicationContext()).getNGROKLink()).create(ApiInterface.class);
        Call<List<NotificationItem>>call = apiInterface.getNotification(User.getUser().getId());
        call.enqueue(new Callback<List<NotificationItem>>() {
            @Override
            public void onResponse(Call<List<NotificationItem>> call, Response<List<NotificationItem>> response) {
                list = response.body();
                if(list == null)
                    list = new ArrayList<>();
                showView();
            }

            @Override
            public void onFailure(Call<List<NotificationItem>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    private void showView() {
        if(list.isEmpty()){
            noNotification.setVisibility(View.VISIBLE);
            listView.setVisibility(View.GONE);
        }else {
            listView.setAdapter(new NotificationListAdapter(this, 0, list));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    int pos = list.size()-i-1;
                    if(list.get(pos).getType()==3){
                        //intent to candidate
                        return;
                    }
                    NotificationListAdapter adapter = (NotificationListAdapter)listView.getAdapter();
                    adapter.getItem(pos).setRead(true);
                    adapter.notifyDataSetChanged();
                    ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getApplicationContext()).getNGROKLink()).create(ApiInterface.class);
                    Call<JsonObject>call = apiInterface.updateRead(list.get(pos).getId(), true);
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            Log.e("TAG", "onResponse: "+response.body().toString());
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            Log.e("TAG", "onFailure: "+t.getMessage());
                        }
                    });
                    if(list.get(pos).getType() == 1 || list.get(pos).getType() == 4){
                        //lost person and lost item
                        if(User.getUser().getId_card_pic().isEmpty()){
                            Button Choose = view.findViewById(R.id.Id_card);
                            Choose.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (ActivityCompat.checkSelfPermission(getApplicationContext(),  Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                                            && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(NotificationActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
                                    }
                                    else{
                                        PopupMenu popupMenu = new PopupMenu(NotificationActivity.this, view);
                                        popupMenu.getMenuInflater().inflate(R.menu.choose_photo, popupMenu.getMenu());
                                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

                                            @Override
                                            public boolean onMenuItemClick(MenuItem item) {
                                                switch (item.getItemId()) {
                                                    case R.id.TakePhoto:
                                                        Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                                                        if (i.resolveActivity(NotificationActivity.this.getPackageManager()) != null) {
                                                            startActivityForResult(i, 10);
                                                        } else
                                                            Toast.makeText(NotificationActivity.this, "Error", Toast.LENGTH_SHORT).show();
                                                        //FancyToast.makeText(getActivity().getApplicationContext(),"Error",FancyToast.LENGTH_LONG, FancyToast.ERROR,false).show();
                                                        break;
                                                    case R.id.Gallery:
                                                        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                                                        startActivityForResult(gallery, 11);
                                                        break;
                                                }
                                                return true;
                                            }
                                        });
                                        popupMenu.show();
                                    }
                                }
                            });

                            builder = new AlertDialog.Builder(NotificationActivity.this);
                            builder.setCancelable(false);
                            builder.setMessage("Please , Upload an image of your id_card");
                            builder.setView(view);
                            dialog = builder.create();
                            dialog.show();
                            return;
                        }
                    }
                    Intent intent = new Intent(NotificationActivity.this,DetailsActivity.class);
                    intent.putExtra("id",list.get(pos).getId());
                    intent.putExtra("type",list.get(pos).getType());
                    startActivity(intent);
                }
            });

        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&data!=null)
        {
            switch(requestCode) {
                case 10: {
                    img =(Bitmap) data.getExtras().get("data");
                }
                break;
                case 11:
                    Uri photoFromGallery = data.getData();
                    try {
                        img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), photoFromGallery);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }
        checkVaildIdcard();
    }
    private void checkVaildIdcard() {
        TextView messageforuser = view.findViewById(R.id.messageforuser);
        boolean flag_not_idCard = false;
        FaceDetector faceDetector = new FaceDetector.Builder(this)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .setMode(FaceDetector.FAST_MODE).build();
        if (!faceDetector.isOperational()) {
            Toast.makeText(this, "Face Detection can't be setup", Toast.LENGTH_SHORT).show();
        }
        else {
            Frame frame = new Frame.Builder().setBitmap(img).build();
            SparseArray<Face> sparseArray = faceDetector.detect(frame);
            if(sparseArray.size() != 1)
            {
                flag_not_idCard = true;

            }
            TextRecognizer textRecognizer  = new TextRecognizer.Builder(this).build();
            if(!textRecognizer.isOperational())
                Toast.makeText(this, "textRecognizer can't be setup", Toast.LENGTH_SHORT).show();
            else
            {
                Frame frametext = new Frame.Builder().setBitmap(img).build();
                SparseArray<TextBlock> sparseArraytext = textRecognizer.detect(frame);
                TextBlock item;
                if(sparseArraytext.size()>4 || sparseArraytext.size()<1)
                {
                    flag_not_idCard = true;
                }
                else if(sparseArraytext.size()>=1) {
                    item = sparseArraytext.valueAt(sparseArraytext.size() - 1);
                    if(item.getValue().length()>9)
                        flag_not_idCard = true;
                }

                Log.e("img", "num of img : "+sparseArray.size()+"  num of text " +sparseArraytext.size());
            }
            if(flag_not_idCard)
            {
                messageforuser.setTextColor(0xFFB80D0D);
                messageforuser.setText("Error, The card picture cannot be recognized\nplease upload your id card");
            }
            else{
                dialog.dismiss();
                builder = new AlertDialog.Builder(NotificationActivity.this);
                builder.setMessage("Your id_card has been successfully taken").setNegativeButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {}});
                dialog = builder.create();
                dialog.show();
            }

        }
    }
    private void init() {
        list = new ArrayList<>();
        listView = findViewById(R.id.notification_listview);
        noNotification = findViewById(R.id.no_notification);
        view = getLayoutInflater().inflate(R.layout.alert_id_card, null);
    }
}