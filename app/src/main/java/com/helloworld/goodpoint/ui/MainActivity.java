package com.helloworld.goodpoint.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.pojo.FoundItem;
import com.helloworld.goodpoint.pojo.FoundPerson;
import com.helloworld.goodpoint.pojo.LostItem;
import com.helloworld.goodpoint.pojo.LostObject;
import com.helloworld.goodpoint.pojo.LostPerson;
import com.helloworld.goodpoint.pojo.Token;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.retrofit.ApiClient;
import com.helloworld.goodpoint.retrofit.ApiInterface;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    PrefManager prefManager;
    ImageView splash;
    Thread t;
    List<LostObject> listObj;

    List<LostItem> list1;
    List<FoundPerson> list3;
    List<LostPerson> list2;
    List<FoundItem> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        if (prefManager.isFirstTimeLaunch()) {
            prefManager.setFirstTimeLaunch(true);
            startActivity(new Intent(MainActivity.this, WelcomeActivity.class));
            finish();
        }else if(!prefManager.isLoginned().isEmpty()) {
            rotateSplash();
            t = startApp();
        }else {

            startActivity(new Intent(this,SigninActivity.class));
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        t.start();
    }

    @Override
    protected void onPause() {
        t.interrupt();
        super.onPause();
    }

    private void init() {
        prefManager = new PrefManager(getApplicationContext());
        splash = findViewById(R.id.splash_icon);
    }

    private void rotateSplash() {
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.rotate_splash);
        splash.startAnimation(animation);
    }

    private Thread startApp() {
        return new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                    ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getApplicationContext()).getNGROKLink()).create(ApiInterface.class);
                    Call<Token> call = apiInterface.refresh(new PrefManager(getApplicationContext()).isLoginned());
                    call.enqueue(new Callback<Token>() {
                        @Override
                        public void onResponse(Call<Token> call, Response<Token> response) {
                            if(response.isSuccessful()) {
                                String token = response.body().getAccess();

                                Call<JsonObject> call2 = apiInterface.getData("Bearer " + token);
                                call2.enqueue(new Callback<JsonObject>() {
                                    @Override
                                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {

                                    try {
                                        Log.d("e","res="+response.body().toString());
                                        JSONObject jsonObject = new JSONObject(response.body().toString()).getJSONObject("user");
                                        String id = jsonObject.getString("id");
                                        String name = jsonObject.getString("username");
                                        String email = jsonObject.getString("email");
                                        String phone = jsonObject.getString("phone");
                                        String city = jsonObject.getString("city");
                                        String birthdate = jsonObject.getString("birthdate");
                                        String Userimage = jsonObject.getString("profile_pic");
                                        String idcardimage = jsonObject.getString("id_card_pic");
                                        JSONArray jsonArray = jsonObject.getJSONArray("losts");
                                        Log.e("blabla", jsonArray.length() + "");
                                        for(int i=0;i<jsonArray.length();i++)
                                            User.getUser().getLosts().add(jsonArray.getJSONObject(i).getInt("id"));
                                        jsonArray = jsonObject.getJSONArray("founds");
                                        for(int i=0;i<jsonArray.length();i++)
                                            User.getUser().getFounds().add(jsonArray.getJSONObject(i).getInt("id"));

                                            User.getUser().setId(id);
                                            User.getUser().setUsername(name);
                                            User.getUser().setEmail(email);
                                            User.getUser().setPhone(phone);
                                            User.getUser().setCity(city);
                                            User.getUser().setBirthdate(birthdate);
                                            User.getUser().setProfile_pic(Userimage);
                                            User.getUser().setId_card_pic(idcardimage);


                                        if(User.getUser().getProfile_pic() != null &&!User.getUser().getProfile_pic().isEmpty() && User.getUser().getProfile_bitmap() == null) {
                                                String dnsLink = new PrefManager(MainActivity.this).getNGROKLink();
                                                DownloadProfilePic download = new DownloadProfilePic();
                                                download.execute(dnsLink+User.getUser().getProfile_pic()+"/");
                                            }else{
                                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);

                                                startActivity(intent);
                                                finish();
                                            }


                                        } catch (Exception e) {
                                            Log.e("Error: ", e.getMessage());
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<JsonObject> call, Throwable t) {
                                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(MainActivity.this, SigninActivity.class));
                                        finish();
                                    }
                                });
                            }else{
                                startActivity(new Intent(MainActivity.this, SigninActivity.class));
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(Call<Token> call, Throwable t) {
                            Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            startActivity(new Intent(MainActivity.this, SigninActivity.class));
                            finish();
                        }
                    });

                } catch (InterruptedException e) {
                    Log.e("InterruptedException: ", e.getMessage());
                }
            }
        });
    }

    class DownloadProfilePic extends AsyncTask<String,Void, Bitmap> {

        private Bitmap download(String urlLink) throws IOException {
            Bitmap bitmap = null;
            URL url = null;
            HttpURLConnection httpConn;
            InputStream is = null;
            Log.e("ProfilePic", urlLink);
            try {
                url = new URL(urlLink);
                httpConn = (HttpURLConnection) url.openConnection();
                httpConn.connect();
                is = httpConn.getInputStream();
                bitmap = BitmapFactory.decodeStream(is);
            }catch (MalformedURLException e){
                Log.e("DownloadProfilePic", "download: "+e.getMessage());
            }
            return  bitmap;
        }

        @Override
        protected Bitmap doInBackground(String... urls) {
            try {

                return download(urls[0]);
            } catch (IOException e) {
                Log.e("TAG", "doInBackground: "+e.getMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap!=null)
                User.getUser().setProfile_bitmap(bitmap);
            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }

    public void getHomeLosts() {

        List<Integer> losts = User.getUser().getLosts();
        ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getApplicationContext()).getNGROKLink()).create(ApiInterface.class);
        Log.d("test", "id=" + losts.get(0));
        Log.d("test", "id=" + losts);
        GlobalVar.losts = new ArrayList<>(losts.size());
            for (int i = 0; i < losts.size(); i++) {
                Log.d("test", "id=" + losts.get(i));
                if (true) {
                    Call<List<LostItem>> call2 = apiInterface.getLostItem(losts.get(i));
                    call2.enqueue(new Callback<List<LostItem>>() {
                        @Override
                        public void onResponse(Call<List<LostItem>> call, Response<List<LostItem>> response) {
                            list1 = response.body();
                            if (list1 != null) {
                                String t = list1.get(0).getType() + " " + list1.get(0).getBrand() + "";
                                GlobalVar.losts.add(t);
                            } else
                                Toast.makeText(getApplicationContext(), "There is no items of lost object !", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<LostItem>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Call<List<LostPerson>> call3 = apiInterface.getLostPerson(losts.get(i));
                    call3.enqueue(new Callback<List<LostPerson>>() {
                        @Override
                        public void onResponse(Call<List<LostPerson>> call, Response<List<LostPerson>> response) {
                            list2 = response.body();
                            if (list2 != null) {
                                String t = list2.get(0).getName() + "missing";
                                GlobalVar.losts.add(t);
                            } else
                                Toast.makeText(getApplicationContext(), "There is no persons of lost object !", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<LostPerson>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

    }

    public void getHomeFounds() {
        List<Integer> founds = new ArrayList<>();
        founds = User.getUser().getFounds();
        GlobalVar.founds = new ArrayList<String>();
        if (!founds.isEmpty()) {
            for (int i = 0; i < founds.size(); i++) {
                Log.d("test", "id=" + founds.get(i));
                ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getApplicationContext()).getNGROKLink()).create(ApiInterface.class);
                if (true) {
                    Call<List<FoundItem>> call2 = apiInterface.getFoundItem(founds.get(i));
                    call2.enqueue(new Callback<List<FoundItem>>() {
                        @Override
                        public void onResponse(Call<List<FoundItem>> call, Response<List<FoundItem>> response) {
                            list = response.body();
                            if (list != null) {
                                String t = list.get(0).getType() + " " + list.get(0).getBrand() + "";
                                GlobalVar.founds.add(t);
                            } else
                                Toast.makeText(getApplicationContext(), "There is no items of found object !", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<FoundItem>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    Call<List<FoundPerson>> call3 = apiInterface.getFoundPerson(founds.get(i));
                    call3.enqueue(new Callback<List<FoundPerson>>() {
                        @Override
                        public void onResponse(Call<List<FoundPerson>> call, Response<List<FoundPerson>> response) {
                            list3 = response.body();
                            if (list3 != null) {
                                String t = list3.get(0).getName() + "missing";
                                GlobalVar.founds.add(t);
                            } else
                                Toast.makeText(getApplicationContext(), "There is no persons of found object !", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<List<FoundPerson>> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }
        } else
            Toast.makeText(getApplicationContext(), "There is no object", Toast.LENGTH_LONG).show();
    }

}