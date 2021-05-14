package com.helloworld.goodpoint.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LostPerson {
    @SerializedName("id")
    @Expose
    String  id;


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
}
