package com.helloworld.goodpoint.ui.lostObject;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.widget.PopupMenu;

import com.helloworld.goodpoint.R;

import java.io.IOException;

import static android.app.Activity.RESULT_OK;

public class PersonFragment extends Fragment implements View.OnClickListener {
 ImageButton imageView;
    Bitmap bitmap;
    Uri photoFromGallery;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_person, container, false);
        imageView = v.findViewById(R.id.imageView);
        imageView.setOnClickListener(this);
        return v;

    }
    @Override
    public void onClick(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(),view);
        popupMenu.getMenuInflater().inflate(R.menu.choose_photo, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.TakePhoto:
                        Intent i=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(i.resolveActivity(getActivity().getPackageManager())!=null)
                        {
                            startActivityForResult(i,10);
                        }
                        else Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.Gallery:
                        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        if(pickPhoto.resolveActivity(getActivity().getPackageManager())!=null) {
                            startActivityForResult(pickPhoto, 1);
                        }
                        else Toast.makeText(getActivity().getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                        break;

                }
                return true;
            }
        });
        popupMenu.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK&&data!=null)
        {
            switch(requestCode) {
                case 10:
                    bitmap = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bitmap);

                    break;
                case 1:
                    photoFromGallery=data.getData();
                    try {
                        bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoFromGallery);
                        imageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
        }

    }
}