package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alena on 07.05.2017.
 */

public class Salary {

    @SerializedName("from")
    private Integer from;
    @SerializedName("to")
    private Integer to;
    @SerializedName("currency")
    private String currency;

    public int getFrom() {
        return from;
    }

    public int getTo() {
        return to;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public String toString() {
        if (from != null) {
            if (to == null) {
                return String.format("от %s %s", from, currency);
            } else {
                return String.format("%s - %s %s", from, to, currency);
            }
        } else {
            return String.format("до %s %s", to, currency);
        }
    }
}
