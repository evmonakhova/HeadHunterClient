package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alena on 07.05.2017.
 */

public class SearchData {

    private String clusters;
    private List<Vacancy> items;
    private int page;
    @SerializedName("per_page")
    private int perPage;
    private int pages;
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
