package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alena on 07.05.2017.
 */

public class SearchData {

    @SerializedName("clusters")
    private String clusters;
    @SerializedName("items")
    private List<Vacancy> items;
    @SerializedName("page")
    private int page;
    @SerializedName("per_page")
    private int perPage;
    @SerializedName("pages")
    private int pages;
    @SerializedName("found")
    private int found;

    public List<Vacancy> getItems() {
        return items;
    }

    public int getPage() {
        return page;
    }

    @Override
    public String toString() {
        String itemsStr = items == null ? "" : items.toString();
        return "SearchData{" +
                "clusters='" + clusters + '\'' +
                ", items=" + itemsStr +
                '}';
    }
}
