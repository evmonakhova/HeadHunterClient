package ru.hh.headhunterclient.model;

/**
 * Created by alena on 06.05.2017.
 */

public class Vacancy {

    private String jobTitle;
    private String salary;
    private String companyName;
    private String city;
    private String subwayStation;

    public Vacancy(String jobTitle, String salary, String companyName, String city, String subwayStation) {
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.companyName = companyName;
        this.city = city;
        this.subwayStation = subwayStation;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public String getSalary() {
        return salary;
    }

    public String getCompanyName() {
        return companyName;
    }

    public String getCity() {
        return city;
    }

    public String getSubwayStation() {
        return subwayStation;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setSubwayStation(String subwayStation) {
        this.subwayStation = subwayStation;
    }

}
