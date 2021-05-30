package com.helloworld.goodpoint.ui.myService;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.gson.JsonObject;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.pojo.NotificationItem;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.retrofit.ApiClient;
import com.helloworld.goodpoint.retrofit.ApiInterface;
import com.helloworld.goodpoint.ui.NotificationActivity;
import com.helloworld.goodpoint.ui.PrefManager;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationService extends IntentService {

    public static boolean isSurviceRun = false;
    public static String user_id = "0";

    public NotificationService() {
        super("NotificationService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        user_id = User.getUser().getId();
        int c=0;
        while (isSurviceRun){
            Log.d("Good Point Service", "onHandleIntent: "+c);
            ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getApplicationContext()).getNGROKLink()).create(ApiInterface.class);
            Call<List<NotificationItem>> call = apiInterface.getNewNotification(user_id);
            call.enqueue(new Callback<List<NotificationItem>>() {
                @Override
                public void onResponse(Call<List<NotificationItem>> call, Response<List<NotificationItem>> response) {
                    if(response.body() == null || response.body().isEmpty())
                        return;
                    createNotification(response.body());
                }

                @Override
                public void onFailure(Call<List<NotificationItem>> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                Log.e("TAG", "onHandleIntent: "+e.getMessage());
            }
        }
    }

    private void createNotification(List<NotificationItem> list) {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        for(NotificationItem item: list){
            Intent notifyIntent = new Intent(this, NotificationActivity.class);
            PendingIntent pintent = PendingIntent.getActivity(this,item.getType(),notifyIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel nc = new NotificationChannel("Good Point",item.getTitle(), NotificationManager.IMPORTANCE_HIGH);
                nc.setDescription(item.getDescription());
                nm.createNotificationChannel(nc);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"Good Point");
                builder.setContentTitle(item.getTitle()).setContentText(item.getDescription());
                builder.setSmallIcon(R.drawable.application_icon2).setStyle(new NotificationCompat.BigTextStyle().bigText(item.getDescription()));
                builder.setContentIntent(pintent).setAutoCancel(true);
                NotificationManagerCompat nmc = NotificationManagerCompat.from(this);
                nmc.notify(Integer.parseInt(item.getId()),builder.build());
            }else{
                Notification.Builder builder = new Notification.Builder(this);
                builder.setContentTitle(item.getTitle()).setContentText(item.getDescription());
                builder.setSmallIcon(R.drawable.application_icon2).setStyle(new Notification.BigTextStyle().bigText(item.getDescription()));
                builder.setContentIntent(pintent);
                nm.notify(Integer.parseInt(item.getId()),builder.build());
            }
            ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(this).getNGROKLink()).create(ApiInterface.class);
            Call<JsonObject> call = apiInterface.updateSent(item.getId(),true);
            call.enqueue(new Callback<JsonObject>() {
                @Override
                public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                    Log.e("TAG", "onResponse: "+response.body());
                }

                @Override
                public void onFailure(Call<JsonObject> call, Throwable t) {
                    Log.e("TAG", "onFailure: "+t.getMessage());
                }
            });
        }
    }

}