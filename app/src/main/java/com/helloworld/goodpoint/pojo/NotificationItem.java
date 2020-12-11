package com.helloworld.goodpoint.pojo;

import android.graphics.Bitmap;

import java.util.Date;

public class NotificationItem {
    String title;
    Date date;
    String description;
    Bitmap image;

    public NotificationItem(String title, Date date, String description, Bitmap image) {
        this.title = title;
        this.date = date;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public Date getDate() {
        return date;
    }

    public String getDescription() {
        return description;
    }

    public Bitmap getImage() {
        return image;
    }
}
