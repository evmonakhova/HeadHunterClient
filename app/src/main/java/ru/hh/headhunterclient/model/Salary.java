package ru.hh.headhunterclient.model;

/**
 * Created by alena on 07.05.2017.
 */

public class Salary {

    private Integer from;
    private Integer to;
    private String currency;

    public Integer getFrom() {
        return from;
    }

    public Integer getTo() {
        return to;
    }

    public String getCurrency() {
        return currency;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public void setTo(Integer to) {
        this.to = to;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
