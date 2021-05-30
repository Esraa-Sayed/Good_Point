package com.helloworld.goodpoint.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.adapter.NotificationListAdapter;
import com.helloworld.goodpoint.pojo.NotificationItem;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.retrofit.ApiClient;
import com.helloworld.goodpoint.retrofit.ApiInterface;

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

    private void init() {
        list = new ArrayList<>();
        listView = findViewById(R.id.notification_listview);
        noNotification = findViewById(R.id.no_notification);
    }
}