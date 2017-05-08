package ru.hh.headhunterclient.model;

/**
 * Created by alena on 08.05.2017.
 */

public class Snippet {

    private String requirement;
    private String responsibility;

    public String getRequirement() {
        return requirement;
    }

    public void setRequirement(String requirement) {
        this.requirement = requirement;
    }

    public String getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(String responsibility) {
        this.responsibility = responsibility;
    }

    @Override
    public String toString() {
        return "Snippet{" +
                "requirement='" + requirement + '\'' +
                ", responsibility='" + responsibility + '\'' +
                '}';
    }
}
