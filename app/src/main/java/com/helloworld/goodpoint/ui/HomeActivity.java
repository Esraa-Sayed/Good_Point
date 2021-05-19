package com.helloworld.goodpoint.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.pojo.RegUser;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.ui.lostFoundObject.FoundObjectActivity;
import com.helloworld.goodpoint.ui.lostFoundObject.LostObjectDetailsActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;
    Fragment fhome, fmatch, fprofile;
    TextView namenavigator;
    TextView mailnavigator;
    CircleImageView imgnavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        setToolBarAndDrawer();
        setBottomNavigator();

        if(savedInstanceState == null) {
            //To make first fragment is home when opening the app
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fhome).commit();
        }

    }

    private void setBottomNavigator() {
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //To Disable item under Fab
        Menu menuNav=bottomNavigationView.getMenu();
        MenuItem nav_item2 = menuNav.findItem(R.id.placeholder);
        nav_item2.setEnabled(false);

        bottomNavigationView.setBackgroundColor(0); //To hide the color of nav view
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);
    }

    private void setToolBarAndDrawer() {
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_drawer_open,R.string.nav_drawer_close);
        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(android.R.color.white));
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void init() {
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar = findViewById(R.id.toolbar);
        navigationView = findViewById(R.id.nv);
        navigationView.bringToFront();
        fhome = new HomeFragment();
        fmatch = new MatchFragment();
        fprofile = new ProfileFragment();
        View view = navigationView.getHeaderView(0);
        namenavigator = (TextView) view.findViewById(R.id.namenav);
        mailnavigator = (TextView) view.findViewById(R.id.mailnav);
        imgnavigator = view.findViewById(R.id.circuler_profile_img);
        namenavigator.setText(User.getUser().getUsername());
        mailnavigator.setText(User.getUser().getEmail());
        if(!User.getUser().getProfile_pic().isEmpty() && User.getUser().getProfile_bitmap() == null) {
            DownloadProfilePic download = new DownloadProfilePic();
            download.execute(User.getUser().getProfile_pic());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.home_menu, menu);
        menu.getItem(0).getIcon().mutate().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.notification)
            startActivity(new Intent(this,NotificationActivity.class));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.drawer_share:

                break;
            case R.id.drawer_rate:

                break;
            case R.id.drawer_feedback:

                break;
            case R.id.drawer_about_us:

                break;
            case R.id.drawer_setting:

                break;
            case R.id.drawer_logout:
                dialog = createDialog("Logout",R.drawable.ic_baseline_exit_to_app_24);
                dialog.create().show();
                break;
            default:
                return false;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return false;
    }

    private AlertDialog.Builder createDialog(String title, int icon) {
        return new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage("Are you sure?")
                .setIcon(icon)
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        PrefManager prefManager = new PrefManager(getApplicationContext());
                        prefManager.setLogout();
                        startActivity(new Intent(HomeActivity.this,SigninActivity.class));
                        finish();
                    }
                }).setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }).setCancelable(false);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    public void showPopup(View v) { //Fab Action

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(HomeActivity.this, R.style.BottomSheetTheme);
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_dialog, (LinearLayout)findViewById(R.id.bottom_sheet));

        bottomSheetView.findViewById(R.id.hide_sheet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetView.findViewById(R.id.ilost).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, LostObjectDetailsActivity.class));
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetView.findViewById(R.id.ifound).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, FoundObjectActivity.class));
                bottomSheetDialog.dismiss();
            }
        });

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();

    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = getSupportFragmentManager().getFragments().get(0);
                    switch (item.getItemId()) {
                        case R.id.miHome:
                            if(!(selectedFragment instanceof HomeFragment))
                                selectedFragment = fhome;
                            break;
                        case R.id.miMatch:
                            if(!(selectedFragment instanceof MatchFragment))
                                selectedFragment = fmatch;
                            break;
                        case R.id.miProfile:
                            if(!(selectedFragment instanceof ProfileFragment))
                                selectedFragment = fprofile;
                            break;
                        case R.id.miLocation:
                            selectedFragment = new FoundMapFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    class DownloadProfilePic extends AsyncTask<String,Void, Bitmap> {

        private Bitmap download(String urlLink) throws IOException {
            Bitmap bitmap = null;
            URL url = null;
            HttpURLConnection httpConn;
            InputStream is = null;
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
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap==null)return;
            User.getUser().setProfile_bitmap(bitmap);
            imgnavigator.setImageBitmap(bitmap);
        }
    }
}