package com.example.healthfriend.UserScreens.Fragments.profile.data;

public final class ProfileUser {

    private final String weight;
    private final String height;
    private final String gender;
    private final String age;
    private final String target;
    private final String calories;

    public ProfileUser(String weight, String height, String gender, String age, String target, String calories) {
        // Add data validation logic here (optional)
        this.weight = weight;
        this.height = height;
        this.gender = gender;
        this.age = age;
        this.target = target;
        this.calories = calories;
    }

    public String getWeight() {
        return weight;
    }

    public String getHeight() {
        return height;
    }

    public String getGender() {
        return gender;
    }

    public String getAge() {
        return age;
    }

    public String getTarget() {
        return target;
    }

    public String getCalories() {
        return calories;
    }
}

