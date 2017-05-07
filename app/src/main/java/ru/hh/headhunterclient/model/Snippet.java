package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alena on 08.05.2017.
 */

public class Snippet {

    @SerializedName("requirement")
    private String requirement;
    @SerializedName("responsibility")
    private String responsibility;

    @Override
    public String toString() {
        return "Snippet{" +
                "requirement='" + requirement + '\'' +
                ", responsibility='" + responsibility + '\'' +
                '}';
    }
}
