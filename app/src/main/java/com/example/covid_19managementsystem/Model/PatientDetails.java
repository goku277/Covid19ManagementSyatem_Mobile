package com.example.covid_19managementsystem.Model;

public class PatientDetails {
    private String name, age, status, id;

    public PatientDetails(String name, String age, String status, String id) {
        this.name = name;
        this.age = age;
        this.status = status;
        this.id= id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public PatientDetails() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}