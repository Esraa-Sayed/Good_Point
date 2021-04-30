package com.helloworld.goodpoint.ui.candidate;

import android.graphics.Bitmap;

public class SubItem {
    private Bitmap subItemImage;
    private String subItemTitle;

    public SubItem(String subItemTitle) {
        this.subItemTitle = subItemTitle;
    }

    public Bitmap getSubItemImage() {
        return subItemImage;
    }

    public void setSubItemImage(Bitmap subItemImage) {
        this.subItemImage = subItemImage;
    }

    public String getSubItemTitle() {
        return subItemTitle;
    }

    public void setSubItemTitle(String subItemTitle) {
        this.subItemTitle = subItemTitle;
    }

}
