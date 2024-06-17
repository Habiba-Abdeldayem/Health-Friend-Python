package com.example.healthfriend.DoctorScreens;

public class User {
    private int height,weight,image;
    private String name;

    public int getHeight() {
        return height;
    }

    public int getImage() {
        return image;
    }

    public User(int height, int weight, int image, String name) {
        this.height = height;
        this.weight = weight;
        this.image = image;
        this.name = name;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
