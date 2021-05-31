package com.helloworld.goodpoint.ui.candidate;

public class SubItem {
    private String subItemTitle;
    private String subItemDes;
    private int pos;

    public SubItem(String subItemTitle, String subItemDes, int pos) {
        this.subItemTitle = subItemTitle;
        this.subItemDes = subItemDes;
        this.pos = pos;
    }

    public String getSubItemTitle() {
        return subItemTitle;
    }

    public void setSubItemTitle(String subItemTitle) {
        this.subItemTitle = subItemTitle;
    }

    public String getSubItemDes() {
        return subItemDes;
    }

    public void setSubItemDes(String subItemDes) {
        this.subItemDes = subItemDes;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
