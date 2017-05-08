package ru.hh.headhunterclient.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by alena on 06.05.2017.
 */

public class Vacancy {

    private Integer id;
    private String name;
    private Salary salary;
    private Area area;
    private Address address;
    private Employer employer;
    private Department department;
    @SerializedName("published_at")
    private String publishedAt;
    private boolean premium;
    private String url;
    @SerializedName("alternate_url")
    private String alternateUrl;
    @SerializedName("apply_alternate_url")
    private String applyAlternateUrl;
    @SerializedName("response_letter_required")
    private boolean responseLetterRequired;
    private Type type;
    private boolean archived;
    private Snippet snippet;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Salary getSalary() {
        return salary;
    }

    public Area getArea() {
        return area;
    }

    public Address getAddress() {
        return address;
    }

    public Employer getEmployer() {
        return employer;
    }

    public Department getDepartment() {
        return department;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public boolean isPremium() {
        return premium;
    }

    public String getUrl() {
        return url;
    }

    public String getAlternateUrl() {
        return alternateUrl;
    }

    public String getApplyAlternateUrl() {
        return applyAlternateUrl;
    }

    public boolean isResponseLetterRequired() {
        return responseLetterRequired;
    }

    public Type getType() {
        return type;
    }

    public boolean isArchived() {
        return archived;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSalary(Salary salary) {
        this.salary = salary;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAlternateUrl(String alternateUrl) {
        this.alternateUrl = alternateUrl;
    }

    public void setApplyAlternateUrl(String applyAlternateUrl) {
        this.applyAlternateUrl = applyAlternateUrl;
    }

    public void setResponseLetterRequired(boolean responseLetterRequired) {
        this.responseLetterRequired = responseLetterRequired;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
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
