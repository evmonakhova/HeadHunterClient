package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alena on 07.05.2017.
 */

public class Metro {

    @SerializedName("station_id")
    private Double stationId;
    @SerializedName("station_name")
    private String stationName;
    @SerializedName("line_id")
    private Long lineId;
    @SerializedName("line_name")
    private String lineName;
    @SerializedName("lat")
    private Double lat;
    @SerializedName("lng")
    private Double lng;

    public String getName() {
        return stationName;
    }

    @Override
    public String toString() {
        return "Metro{" +
                "stationId='" + stationId + '\'' +
                ", stationName='" + stationName + '\'' +
                ", lineId=" + lineId +
                ", lineName='" + lineName + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                '}';
    }
}