package com.helloworld.goodpoint.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.helloworld.goodpoint.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
    BottomNavigationView bottomNavigationView;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        setToolBarAndDrawer();

        ///*
       // FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
      //  ft.replace(R.id.home_frag,new HomeFragment());
      //  ft.commitNow();
        //*/

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        //To Disable item under Fab
        Menu menuNav=bottomNavigationView.getMenu();
        MenuItem nav_item2 = menuNav.findItem(R.id.placeholder);
        nav_item2.setEnabled(false);

        bottomNavigationView.setBackgroundColor(0); //To hide the color of nav view
        bottomNavigationView.setOnNavigationItemSelectedListener(navListner);


        //To make first fragment is home when opening the app
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();

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

    public void showPopup(View v){ //Fab Action
        FabFragment fabFragment = new FabFragment();
        fabFragment.show(getSupportFragmentManager(), fabFragment.getTag());
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navListner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;

                    switch (item.getItemId()) {
                        case R.id.miHome:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.miMatch:
                            selectedFragment = new MatchFragment();
                            break;
                        case R.id.miProfile:
                            //selectedFragment = new ProfileFragment();
                            selectedFragment = new NotificationFragment();
                            break;
                        case R.id.miLocation:
                            selectedFragment = new MapFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };
}