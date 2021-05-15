package com.helloworld.goodpoint.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class FoundPerson {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;


    private static FoundPerson FoundPerson;
    public static FoundPerson getFoundPerson()
    {
        if (FoundPerson == null) {
            FoundPerson = new FoundPerson();

        }
        return FoundPerson;
    }
    private FoundPerson()
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
