package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by alena on 07.05.2017.
 */

public class Address {

    @SerializedName("id")
    private Long id;
    @SerializedName("city")
    private String city;
    @SerializedName("street")
    private String street;
    @SerializedName("building")
    private String building;
    @SerializedName("description")
    private String description;
    @SerializedName("lat")
    private Double lat;
    @SerializedName("lng")
    private Double lng;
    @SerializedName("metro")
    private Metro metro;
    @SerializedName("metro_stations")
    private List<Metro> metroStations;

    public Long getId() {
        return id;
    }

    public Metro getMetro() {
        return metro;
    }

    @Override
    public String toString() {
        String metroStationsStr = metroStations == null ? "" : metroStations.toString();
        return "Address{" +
                "city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", building='" + building + '\'' +
                ", description='" + description + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", metro=" + metro +
                ", metroStations=" + metroStationsStr +
                '}';
    }
}
