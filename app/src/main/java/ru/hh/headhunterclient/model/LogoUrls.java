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
    private String original;

    public String getLogo90() {
        return logo90;
    }

    public String getLogo240() {
        return logo240;
    }

    public String getOriginal() {
        return original;
    }

    public void setLogo90(String logo90) {
        this.logo90 = logo90;
    }

    public void setLogo240(String logo240) {
        this.logo240 = logo240;
    }

    public void setOriginal(String original) {
        this.original = original;
    }

    @Override
    public String toString() {
        return "LogoUrls{" +
                "logo90='" + logo90 + '\'' +
                ", logo240='" + logo240 + '\'' +
                ", original='" + original + '\'' +
                '}';
    }
}
