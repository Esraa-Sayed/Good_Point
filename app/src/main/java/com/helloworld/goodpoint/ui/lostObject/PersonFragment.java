package com.helloworld.goodpoint.ui.lostObject;

import android.Manifest;
import android.app.Fragment;
import android.content.ClipData;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;

import com.helloworld.goodpoint.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class PersonFragment extends Fragment implements View.OnClickListener {
    private ImageButton imageView;
    private ImageView imageView2;
    private List<Bitmap> bitmap  = new ArrayList<>();
    private Uri photoFromGallery;
    private LinearLayout linearLayout;
    LayoutInflater inflater2;
    View rootView;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.MANAGE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},100);
       return;
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_person, container, false);
        imageView = rootView.findViewById(R.id.imageView);
        linearLayout = (LinearLayout) rootView.findViewById(R.id.Gallery2);
        inflater2 = LayoutInflater.from(getActivity());
        imageView.setOnClickListener(this);
        return rootView;

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
                        Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
                        pickPhoto.setType("image/*");//accept any type of images
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
                    bitmap.add((Bitmap) data.getExtras().get("data"));
                    imageView.setImageBitmap(bitmap.get(bitmap.size()-1));
                    break;
                case 1:
                    try {
                        ClipData clipData = data.getClipData();
                        if(clipData != null)
                        {
                            for(int i = 0; i<clipData.getItemCount();i++)
                            {
                                photoFromGallery = clipData.getItemAt(i).getUri();
                                bitmap.add(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoFromGallery));
                                imageView.setImageBitmap(bitmap.get(bitmap.size()-1));
                            }
                        }
                        else{
                            photoFromGallery = data.getData();
                            bitmap.add(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), photoFromGallery));
                            imageView.setImageBitmap(bitmap.get(bitmap.size()-1));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
            }
            if(linearLayout.getChildCount() == 0) {
                for (int i = 0; i < bitmap.size(); i++) {
                    View view = inflater2.inflate(R.layout.images, linearLayout, false);
                    imageView2 = (ImageView) view.findViewById(R.id.imageView2);
                    imageView2.setImageBitmap(bitmap.get(i));
                    linearLayout.addView(view);

                }
            }
            else
            {
                for (int i = linearLayout.getChildCount(); i < bitmap.size(); i++) {
                    View view = inflater2.inflate(R.layout.images, linearLayout, false);
                    imageView2 = view.findViewById(R.id.imageView2);
                    imageView2.setImageBitmap(bitmap.get(i));
                    linearLayout.addView(view);

                }
            }
        }

    }
}