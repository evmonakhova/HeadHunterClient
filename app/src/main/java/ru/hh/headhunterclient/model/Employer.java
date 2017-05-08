package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alena on 07.05.2017.
 */

public class Employer {

    private int id;
    private String name;
    private boolean trusted;
    private String url;
    @SerializedName("alternate_url")
    private String alternateUrl;
    @SerializedName("vacancies_url")
    private String vacanciesUrl;
    @SerializedName("logo_urls")
    private LogoUrls logoUrls;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public boolean isTrusted() {
        return trusted;
    }

    public String getUrl() {
        return url;
    }

    public String getAlternateUrl() {
        return alternateUrl;
    }

    public String getVacanciesUrl() {
        return vacanciesUrl;
    }

    public LogoUrls getLogoUrls() {
        return logoUrls;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTrusted(boolean trusted) {
        this.trusted = trusted;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAlternateUrl(String alternateUrl) {
        this.alternateUrl = alternateUrl;
    }

    public void setVacanciesUrl(String vacanciesUrl) {
        this.vacanciesUrl = vacanciesUrl;
    }

    public void setLogoUrls(LogoUrls logoUrls) {
        this.logoUrls = logoUrls;
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
