package com.helloworld.goodpoint.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LostItem {

    @SerializedName("user_id")
    @Expose
    int  user_id;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("is_matched")
    @Expose
    private String is_matched;
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("serial_number")
    @Expose
    private String serial_number;
    @Expose
    @SerializedName("brand")
    private String brand;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("description")
    @Expose
    private String description;


    public LostItem(int user_id, String date, String city, String is_matched, String type, String serial_number, String brand, String color, String description) {
        this.user_id = user_id;
        this.date = date;
        this.city = city;
        this.is_matched = is_matched;
        this.type = type;
        this.serial_number = serial_number;
        this.brand = brand;
        this.color = color;
        this.description = description;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }


    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() { return date; }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getIs_matched() {
        return is_matched;
    }

    public void setIs_matched(String is_matched) {
        this.is_matched = is_matched;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSerial_number() {
        return serial_number;
    }

    public void setSerial_number(String serial_number) {
        this.serial_number = serial_number;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
