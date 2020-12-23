package com.helloworld.goodpoint.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.ui.lostFoundObject.FoundObjectActivity;
import com.helloworld.goodpoint.ui.lostFoundObject.LostObjectDetailsActivity;

public class FabFragment extends BottomSheetDialogFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_fab, container, false);

        NavigationView navigationView = v.findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id)
                {
                    case R.id.ilost:
                        startActivity(new Intent(getContext(), LostObjectDetailsActivity.class));
                        break;
                    case R.id.ifound:
                        startActivity(new Intent(getContext(), FoundObjectActivity.class));
                        break;
                }
                return false;
            }
        });

        return v;
    }
}