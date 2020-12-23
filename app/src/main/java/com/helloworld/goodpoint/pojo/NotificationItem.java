package com.helloworld.goodpoint.pojo;

import android.graphics.Bitmap;

public class NotificationItem {
    String title;
    String date;
    String description;
    Bitmap image;
    boolean read;

    public NotificationItem(String title, String date, String description, Bitmap image) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.image = image;
        this.read = false;
    }

    public String getTitle() {
        return title;
    }

    public String getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage() {
        return image;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }
}
