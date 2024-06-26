package com.example.healthfriend.DoctorScreens;


import com.example.healthfriend.UserScreens.IndividualUser;

import java.util.List;

public class Doctor {
    private static Doctor instance;

    private String name, email;
//    private boolean isDoctor = true;
    private int age;
    private List<String> patientList;

    public Doctor() {
    }
    public static Doctor getInstance() {
        if (instance == null) {
            instance = new Doctor();
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

//    public boolean isDoctor() {
//        return isDoctor;
//    }
//
//    public void setDoctor(boolean doctor) {
//        isDoctor = doctor;
//    }
}
