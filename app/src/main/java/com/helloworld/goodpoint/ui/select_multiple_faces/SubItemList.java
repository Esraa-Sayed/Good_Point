package com.helloworld.goodpoint.ui.select_multiple_faces;

import android.graphics.Bitmap;
import android.widget.RadioButton;

public class SubItemList {
    private Bitmap subItemImage;
    private RadioButton btn;

    public RadioButton getBtn() {
        return btn;
    }

    public void setBtn(RadioButton btn) {
        this.btn = btn;
    }


    public SubItemList(Bitmap subItemImage) {
        this.subItemImage = subItemImage;
    }


    public Bitmap getSubItemImage() {
        return subItemImage;
    }

    public void setSubItemImage(Bitmap subItemImage) {
        this.subItemImage = subItemImage;
    }

}
