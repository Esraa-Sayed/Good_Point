package com.helloworld.goodpoint.ui;

import android.graphics.Bitmap;

import com.helloworld.goodpoint.ui.select_multiple_faces.SubItemList;

import java.util.ArrayList;
import java.util.List;

public class GlobalVar {
    public static  List<List<Bitmap>> allFaces = new ArrayList<>();
    public static  List<Bitmap> ImgThatHaveMoreThanOneFace = new ArrayList<>();
    public static  List<Bitmap> FinialFacesThatWillGoToDataBase = new ArrayList<>();
    public static  List<SubItemList> slist;
    public static  int position;

}
