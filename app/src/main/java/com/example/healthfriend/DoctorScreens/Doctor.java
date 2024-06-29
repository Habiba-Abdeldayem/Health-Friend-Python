package com.example.healthfriend.DoctorScreens;

import java.util.List;

public class Doctor {
    private static volatile Doctor instance;

    private String name;
    private String email;
    private int age;
    private List<String> patientList;

    private Doctor() {
        // Private constructor to prevent instantiation
    }

    public static Doctor getInstance() {
        if (instance == null) {
            synchronized (Doctor.class) {
                if (instance == null) {
                    instance = new Doctor();
                }
            }
        }
        return instance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void setInstance(Doctor instance) {
        Doctor.instance = instance;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<String> getPatientList() {
        return patientList;
    }

    public void setPatientList(List<String> patientList) {
        this.patientList = patientList;
    }

    public void logout() {
        this.email = null;
        this.name = null;
        this.age=0;
        instance = null;
    }
}
