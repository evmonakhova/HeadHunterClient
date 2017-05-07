package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alena on 07.05.2017.
 */

public class Employer {

    @SerializedName("id")
    private int id;
    @SerializedName("name")
    private String name;
    @SerializedName("trusted")
    private boolean trusted;
    @SerializedName("url")
    private String url;
    @SerializedName("alternate_url")
    private String alternateUrl;
    @SerializedName("vacancies_url")
    private String vacanciesUrl;
    @SerializedName("logo_urls")
    private LogoUrls logoUrls;

    public String getName() {
        return name;
    }

    public boolean isTrusted() {
        return trusted;
    }

    @Override
    public String toString() {
        String logoUrlsStr = logoUrls == null ? "" : logoUrls.toString();
        return "Employer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", trusted=" + trusted +
                ", url='" + url + '\'' +
                ", alternateUrl='" + alternateUrl + '\'' +
                ", vacanciesUrl='" + vacanciesUrl + '\'' +
                ", logoUrls=" + logoUrlsStr +
                '}';
    }
}
