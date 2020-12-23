package com.helloworld.goodpoint.ui;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.helloworld.goodpoint.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    AlertDialog.Builder dialog;
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
}