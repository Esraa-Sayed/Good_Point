package com.helloworld.goodpoint.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LostPerson {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;


    private static LostPerson lostPerson;
    public static LostPerson getLostPerson()
    {
        if (lostPerson == null) {
            lostPerson = new LostPerson();

        }
        return lostPerson;
    }
    private LostPerson()
    {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }
}
