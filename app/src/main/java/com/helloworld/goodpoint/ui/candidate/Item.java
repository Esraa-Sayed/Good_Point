package com.helloworld.goodpoint.ui.candidate;


import android.graphics.Bitmap;

import java.util.List;

public class Item {

    private String itemTitle;
    private Bitmap ItemImage;
    private List<SubItem> subItemList;

    public Item(String itemTitle, Bitmap ItemImage, List<SubItem> subItemList) {
        this.itemTitle = itemTitle;
        this.subItemList = subItemList;
        this.ItemImage = ItemImage;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public List<SubItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<SubItem> subItemList) {
        this.subItemList = subItemList;
    }

    public Bitmap getItemImage() {
        return ItemImage;
    }

    public void setItemImage(Bitmap itemImage) {
        ItemImage = itemImage;
    }

}
