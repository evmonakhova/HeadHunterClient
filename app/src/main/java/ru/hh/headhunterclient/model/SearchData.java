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

    public List<Vacancy> getItems() {
        return items;
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
