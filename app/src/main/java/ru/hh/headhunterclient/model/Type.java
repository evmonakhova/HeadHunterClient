package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alena on 07.05.2017.
 */

public class Type {

    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
