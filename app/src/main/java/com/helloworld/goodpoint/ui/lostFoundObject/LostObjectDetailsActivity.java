package com.helloworld.goodpoint.ui.lostFoundObject;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.content.CursorLoader;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import com.google.gson.JsonObject;
import com.helloworld.goodpoint.R;
import com.helloworld.goodpoint.pojo.LostItem;
import com.helloworld.goodpoint.pojo.LostPerson;
import com.helloworld.goodpoint.pojo.User;
import com.helloworld.goodpoint.retrofit.ApiClient;
import com.helloworld.goodpoint.retrofit.ApiInterface;
import com.helloworld.goodpoint.ui.GlobalVar;
import com.helloworld.goodpoint.ui.PrefManager;
import com.helloworld.goodpoint.ui.prepareList;
import com.helloworld.goodpoint.ui.select_multiple_faces.Selection;
import com.shashank.sony.fancytoastlib.FancyToast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LostObjectDetailsActivity extends AppCompatActivity implements View.OnClickListener, objectDataType {
    private TextView DateT;
    private Button Person, Object, Match;
    private Fragment PersonF, ObjectF;
    private DatePickerDialog.OnDateSetListener DateSet;
    private AutoCompleteTextView autoCom;
    private int year, month, Day;
    private prepareList List;
    private List<String> list, listColor;
    private String City, ObjectColor, Serial, brand, textArea_information, Type;
    private String PName;
    private Bitmap Bitmap_Image;
    Uri imageUri;
    private FaceDetector faceDetector;
    private List<Bitmap> Person_Images;
    private boolean flagPerson, flagObject, CheckImageObeject;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lost_object_details);
        inti();
        Calendar cal = Calendar.getInstance();//To get today's date
        inti();
        if (savedInstanceState != null) {
            year = savedInstanceState.getInt("year");
            month = savedInstanceState.getInt("month");
            Day = savedInstanceState.getInt("Day");
            flagPerson = savedInstanceState.getBoolean("flagPerson");
            flagObject = savedInstanceState.getBoolean("flagObject");
            if (flagPerson) {
                Person.setTextColor(0xFFF38E3A);
                Object.setTextColor(Color.BLACK);
            } else if (flagObject) {
                Object.setTextColor(0xFFF38E3A);
                Person.setTextColor(Color.BLACK);
            }
        } else {
            year = cal.get(Calendar.YEAR);
            month = cal.get(Calendar.MONTH);
            Day = cal.get(Calendar.DAY_OF_MONTH);
        }
        //String todayDate = year + "/" + (month + 1) + "/" + Day;
        String todayDate = year + "-" + (month + 1) + "-" + Day;
        DateT.setText(todayDate);
        DateSet = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int y, int m, int d) {
                m++;
                if (y > year || (m - 1 > month && y >= year) || (d > Day && m - 1 >= month && y >= year)) {
                    FancyToast.makeText(LostObjectDetailsActivity.this, "Invalid date", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                    //String todayDate = year + "/" + (month + 1) + "/" + Day;
                    String todayDate = year + "-" + (month + 1) + "-" + Day;
                    DateT.setText(todayDate);
                } else {
                    year = y;
                    month = m - 1;
                    Day = d;
                    //String Date = y + "/" + m + "/" + d;
                    String Date = y + "-" + m + "-" + d;
                    DateT.setText(Date);
                }
            }
        };

    }

    @Override
    public void onClick(View view) {
        FragmentManager FM = getFragmentManager();
        FragmentTransaction FT = FM.beginTransaction();
        switch (view.getId()) {
            case R.id.Person:
                FT.replace(R.id.FragmentID, PersonF, null);
                Person.setTextColor(0xFFF38E3A);
                Object.setTextColor(Color.BLACK);

                FT.commit();
                flagPerson = true;
                flagObject = false;
                break;
            case R.id.Object:
                FT.replace(R.id.FragmentID, ObjectF, "object");
                Object.setTextColor(0xFFF38E3A);
                Person.setTextColor(Color.BLACK);

                FT.commit();
                flagObject = true;
                flagPerson = false;
                break;
            case R.id.Date:
                DatePickerDialog dialog = new DatePickerDialog(
                        LostObjectDetailsActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        DateSet,
                        year, month, Day
                );
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
                break;
            case R.id.Match:
                if (!flagObject && !flagPerson) {
                    FancyToast.makeText(this, "Specify the type of the missing object", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
                } else if (flagObject && CheckMatchObject()) {
                    LostItems();
                    FancyToast.makeText(this, "The data has been saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    finish();
                } else if (flagPerson && CheckMatchPerson()) {
                    faceDetector = new FaceDetector.Builder(this)
                            .setTrackingEnabled(false)
                            .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                            .setMode(FaceDetector.FAST_MODE).build();
                    if (!faceDetector.isOperational()) {
                        Toast.makeText(this, "Face Detection can't be setup", Toast.LENGTH_SHORT).show();
                    }
                    checkFaces N = new checkFaces(this);
                    N.execute();
                }
                break;
        }
    }

    private boolean CheckMatchPerson() {
        EditText PersonName = PersonF.getView().findViewById(R.id.PersonName);
        PName = PersonName.getText().toString();
        City = autoCom.getText().toString();
        if (City.isEmpty()) {
            autoCom.setError("Field can't be empty");
            return false;
        } else if (!list.contains(City.trim())) {
            autoCom.setError("Please Enter a valid city!");
            return false;
        }
        if (PName.isEmpty()) {
            PersonName.setError("Field can't be empty");
            return false;
        } else if (Person_Images.size() == 0) {
            FancyToast.makeText(this, "You must put at least one picture!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        }
        return true;
    }

    private boolean CheckMatchObject() {
        AutoCompleteTextView V = ObjectF.getView().findViewById(R.id.ColorOfObject);
        EditText serialObject = ObjectF.getView().findViewById(R.id.Serial);
        EditText brandObject = ObjectF.getView().findViewById(R.id.brand);
        EditText textArea_informationObject = ObjectF.getView().findViewById(R.id.textArea_information);
        EditText TypeObject;
        City = autoCom.getText().toString();
        ObjectColor = V.getText().toString();
        Serial = serialObject.getText().toString();
        brand = brandObject.getText().toString();
        textArea_information = textArea_informationObject.getText().toString();
        if (City.isEmpty()) {
            autoCom.setError("Field can't be empty");
            return false;
        } else if (!list.contains(City.trim())) {
            autoCom.setError("Please Enter a valid city!");
            return false;
        } else if (Type.equals("Type")) {
            FancyToast.makeText(this, "You must Choose the Type!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        } else if (Type.equals("Others")) {
            TypeObject = ObjectF.getView().findViewById(R.id.Other);

             if (TypeObject.getText().toString().isEmpty()) {
                TypeObject.setError("Field can't be empty");
                return false;
            }
            else {
                  Type = TypeObject.getText().toString();
             }
        } else if (brand.isEmpty()) {
            brandObject.setError("Field can't be empty");
            return false;
        } else if (ObjectColor.isEmpty()) {
            V.setError("Field can't be empty");
            return false;
        } else if (!listColor.contains(ObjectColor.trim())) {
            V.setError("Color isn't known!");
            return false;
        } else if (CheckImageObeject && Bitmap_Image == null) {
            FancyToast.makeText(this, "You should put the image to the item!", FancyToast.LENGTH_LONG, FancyToast.ERROR, false).show();
            return false;
        } else if (textArea_information.isEmpty()) {
            textArea_informationObject.setError("Field can't be empty");
            return false;
        }
        return true;

    }

    class checkFaces extends AsyncTask<Void, Void, Void> {
        AlertDialog.Builder builder;
        AlertDialog dialog;
        Context context;

        private checkFaces(Context context) {
            this.context = context.getApplicationContext();
            builder = new AlertDialog.Builder(LostObjectDetailsActivity.this);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            builder.setCancelable(false);
            View view = getLayoutInflater().inflate(R.layout.progress_bar_alert, null);
            builder.setView(view);
            dialog = builder.create();
            dialog.show();
        }

        @Override
        protected void onPostExecute(Void a) {
            super.onPostExecute(a);
            if (GlobalVar.allFaces.size() > 0) {
                startActivity(new Intent(LostObjectDetailsActivity.this, Selection.class));
                finish();
            } else {
                FancyToast.makeText(LostObjectDetailsActivity.this, "The data has been saved successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, false).show();
                finish();
            }
            dialog.dismiss();
            LostPerson();

        }

        @Override
        protected Void doInBackground(Void... voids) {
            GlobalVar.ImgThatHaveMoreThanOneFace.clear();
            GlobalVar.FinialFacesThatWillGoToDataBase.clear();
            GlobalVar.allFaces.clear();
            boolean flag = false;
            for (int i = 0; i < Person_Images.size(); i++) {
                Bitmap My = Person_Images.get(i);
                Bitmap faceBitmap;
                List<Bitmap> faces = new ArrayList<>();//In one Img;
                Frame frame = new Frame.Builder().setBitmap(My).build();
                SparseArray<Face> sparseArray = faceDetector.detect(frame);
                for (int j = 0; j < sparseArray.size(); j++) {
                    flag = false;
                    Face face = sparseArray.valueAt(j);
                    if (((int) face.getPosition().y + (int) face.getHeight()) > My.getHeight()) {
                        int H = My.getHeight() - (int) face.getPosition().y;
                        faceBitmap = Bitmap.createBitmap(My, (int) face.getPosition().x, (int) face.getPosition().y, (int) face.getWidth(), H);
                    } else if (((int) face.getPosition().x + (int) face.getWidth()) > My.getWidth()) {
                        int W = My.getWidth() - (int) face.getPosition().x;
                        faceBitmap = Bitmap.createBitmap(My, (int) face.getPosition().x, (int) face.getPosition().y, W, (int) face.getHeight());
                    } else if ((((int) face.getPosition().x + (int) face.getWidth()) > My.getWidth()) && (((int) face.getPosition().y + (int) face.getHeight()) > My.getHeight())) {
                        int H = My.getHeight() - (int) face.getPosition().y;
                        int W = My.getWidth() - (int) face.getPosition().x;
                        faceBitmap = Bitmap.createBitmap(My, (int) face.getPosition().x, (int) face.getPosition().y, W, H);
                    } else {
                        faceBitmap = Bitmap.createBitmap(My, (int) face.getPosition().x, (int) face.getPosition().y, (int) face.getWidth(), (int) face.getHeight());

                    }
                    if (sparseArray.size() == 1) {
                        GlobalVar.FinialFacesThatWillGoToDataBase.add(faceBitmap);//
                        flag = true;
                    } else {
                        faces.add(faceBitmap);
                    }
                }
                if (!flag) {
                    GlobalVar.ImgThatHaveMoreThanOneFace.add(My);
                    GlobalVar.allFaces.add(faces);
                }

            }
            return null;
        }
    }

    @Override
    public void getType(String T) {
        Type = T;
    }

    @Override
    public void getImageCheck(Boolean Check) {
        CheckImageObeject = Check;
    }

    @Override
    public void getBitmap_Image(Bitmap BImage) {
        Bitmap_Image = BImage;
    }

    @Override
    public void getBitmap_ImagePersonImages(List<Bitmap> PImages) {
        Person_Images = PImages;
    }

    protected void inti() {
        DateT = findViewById(R.id.Date);
        autoCom = findViewById(R.id.auto);
        Person = findViewById(R.id.Person);
        Object = findViewById(R.id.Object);
        Match = findViewById(R.id.Match);
        Person.setOnClickListener(this);
        Object.setOnClickListener(this);
        Match.setOnClickListener(this);
        DateT.setOnClickListener(this);
        PersonF = new PersonFragment();
        ObjectF = new ObjectFragment();
        List = new prepareList();
        list = List.prepareList(this);
        listColor = List.prepareListColor(this);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, list);
        autoCom.setThreshold(1);//start working from first char
        autoCom.setAdapter(adapter);
        flagPerson = false;
        flagObject = false;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("year", year);
        outState.putInt("month", month);
        outState.putInt("Day", Day);
        outState.putBoolean("flagPerson", flagPerson);
        outState.putBoolean("flagObject", flagObject);
    }

    /*
    public Uri getImageUri(Context inContext, Bitmap Bitmap_Image) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Bitmap_Image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), Bitmap_Image, "Title", null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri imageUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(this, imageUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    private String imageToString()
    {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap_Image.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
     */



    public void LostItems()  {

        String Datee = DateT.getText().toString().trim();
        ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getApplicationContext()).getNGROKLink()).create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.storeLostObj(User.getUser().getId(),Datee,City);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String id = jsonObject.getString("id");
                        //LostItem.getLostItem().setId(id);
                        Toast.makeText(LostObjectDetailsActivity.this, "Object is posted.", Toast.LENGTH_SHORT).show();



                        Call<LostItem> call2 = apiInterface.storeLostItem(id,Type,Serial,brand,ObjectColor,textArea_information);
                        call2.enqueue(new Callback<LostItem>() {
                            @Override
                            public void onResponse(Call<LostItem> call, Response<LostItem> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(LostObjectDetailsActivity.this, "Item is posted.", Toast.LENGTH_SHORT).show();
                                }
                                else
                                    Toast.makeText(LostObjectDetailsActivity.this, "The item is not posted.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<LostItem> call, Throwable t) {
                                Toast.makeText(LostObjectDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                    Toast.makeText(LostObjectDetailsActivity.this, "The object is not posted.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LostObjectDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }










    public void LostPerson()  {

        String Datee = DateT.getText().toString().trim();
        ApiInterface apiInterface = ApiClient.getApiClient(new PrefManager(getApplicationContext()).getNGROKLink()).create(ApiInterface.class);

        Call<JsonObject> call = apiInterface.storeLostObj(User.getUser().getId(),Datee,City);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()) {

                    try {
                        JSONObject jsonObject = new JSONObject(response.body().toString());
                        String id = jsonObject.getString("id");
                        //LostPerson.getLostPerson().setId(id);
                        Toast.makeText(LostObjectDetailsActivity.this, "Object posted.", Toast.LENGTH_SHORT).show();


                        Call<JsonObject> call2 = apiInterface.storeLostPerson(id,PName);
                        call2.enqueue(new Callback<JsonObject>() {
                            @Override
                            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                                if(response.isSuccessful())
                                {
                                    Toast.makeText(LostObjectDetailsActivity.this, "Person is posted.", Toast.LENGTH_SHORT).show();
                                    //Call<LostPerson> call3 = apiInterface.storeLostPersonImage(id);

                                    try {
                                        JSONObject jsonObject = new JSONObject(response.body().toString());
                                        String id = jsonObject.getString("id");
                                        //LostPerson.getLostPerson().setId(id);


                                        Call<LostPerson> call3 = apiInterface.storeLostPersonImage(id);
                                        call3.enqueue(new Callback<LostPerson>() {
                                            @Override
                                            public void onResponse(Call<LostPerson> call, Response<LostPerson> response) {
                                                if(response.isSuccessful())
                                                {
                                                    Toast.makeText(LostObjectDetailsActivity.this, "PersonImage is posted.", Toast.LENGTH_SHORT).show();
                                                    //Call<LostPerson> call3 = apiInterface.storeLostPersonImage(id);
                                                }
                                                else
                                                    Toast.makeText(LostObjectDetailsActivity.this, "The personImage is not posted.", Toast.LENGTH_SHORT).show();
                                            }

                                            @Override
                                            public void onFailure(Call<LostPerson> call, Throwable t) {
                                                Toast.makeText(LostObjectDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }


                                }
                                else
                                    Toast.makeText(LostObjectDetailsActivity.this, "The person is not posted.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<JsonObject> call, Throwable t) {
                                Toast.makeText(LostObjectDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                else
                    Toast.makeText(LostObjectDetailsActivity.this, "The object is not posted.", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Toast.makeText(LostObjectDetailsActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }








}