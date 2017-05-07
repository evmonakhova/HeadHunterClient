package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alena on 06.05.2017.
 */

public class Vacancy {

    @SerializedName("id")
    private String id;
    @SerializedName("premium")
    private boolean premium;
    @SerializedName("address")
    private Address address;
    @SerializedName("alternate_url")
    private String alternateUrl;
    @SerializedName("apply_alternate_url")
    private String applyAlternateUrl;
    @SerializedName("department")
    private Department department;
    @SerializedName("salary")
    private Salary salary;
    @SerializedName("name")
    private String name;
    @SerializedName("area")
    private Area area;
    @SerializedName("url")
    private String url;
    @SerializedName("published_at")
    private String publishedAt;
    @SerializedName("employer")
    private Employer employer;
    @SerializedName("response_letter_required")
    private boolean responseLetterRequired;
    @SerializedName("type")
    private Type type;
    @SerializedName("archived")
    private boolean archived;
    @SerializedName("snippet")
    private Snippet snippet;

    public Address getAddress() {
        return address;
    }

    public Department getDepartment() {
        return department;
    }

    public Salary getSalary() {
        return salary;
    }

    public String getName() {
        return name;
    }

    public Area getArea() {
        return area;
    }

    public Employer getEmployer() {
        return employer;
    }

    @Override
    public String toString() {
        String addressStr = address == null ? "" : address.toString();
        String departmentStr = department == null ? "" : department.toString();
        String salaryStr = salary == null ? "" : salary.toString();
        String areaStr = area == null ? "" : area.toString();
        String employerStr = employer == null ? "" : employer.toString();
        String typeStr = type == null ? "" : type.toString();

        return "Vacancy{" +
                "id='" + id + '\'' +
                ", premium=" + premium +
                ", address=" + addressStr +
                ", alternateUrl='" + alternateUrl + '\'' +
                ", applyAlternateUrl='" + applyAlternateUrl + '\'' +
                ", department=" + departmentStr +
                ", salary=" + salaryStr +
                ", name='" + name + '\'' +
                ", area=" + areaStr +
                ", url='" + url + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                ", employer=" + employerStr +
                ", responseLetterRequired=" + responseLetterRequired +
                ", type=" + typeStr +
                ", archived=" + archived +
                '}';
    }
}
