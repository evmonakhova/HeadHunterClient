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
    private Integer lineId;
    @SerializedName("line_name")
    private String lineName;
    private Double lat;
    private Double lng;

    public Double getStationId() {
        return stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public Integer getLineId() {
        return lineId;
    }

    public String getLineName() {
        return lineName;
    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setStationId(Double stationId) {
        this.stationId = stationId;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public void setLineId(Integer lineId) {
        this.lineId = lineId;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public void setLng(Double lng) {
        this.lng = lng;
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