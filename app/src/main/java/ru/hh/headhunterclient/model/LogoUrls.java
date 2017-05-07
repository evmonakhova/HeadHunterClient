package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alena on 07.05.2017.
 */

public class LogoUrls {

    @SerializedName("90")
    private String logo90;
    @SerializedName("240")
    private String logo240;
    @SerializedName("original")
    private String original;

    @Override
    public String toString() {
        return "LogoUrls{" +
                "logo90='" + logo90 + '\'' +
                ", logo240='" + logo240 + '\'' +
                ", original='" + original + '\'' +
                '}';
    }
}
