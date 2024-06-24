package com.example.healthfriend.UserScreens;

import com.google.firebase.firestore.DocumentSnapshot;

public class IndividualUser {
    private double height,weight, daily_calories_need, daily_carbs_need,daily_proteins_need,daily_fats_need ,daily_water_need;
    private int age, water_target,water_progress;
    private String email,gender, plan,name;

    private static IndividualUser instance;


    public double getDaily_carbs_need() {
        return daily_carbs_need;
    }

    public void setDaily_carbs_need() {
        this.daily_carbs_need =  Math.round((daily_calories_need *0.5)/4*100.0)/100.0;;
    }

    public double getDaily_proteins_need() {
        return daily_proteins_need;
    }

    public void setDaily_proteins_need() {
        this.daily_proteins_need =Math.round((daily_calories_need *0.3)/4 *100.0)/100.0;;
    }

    public double getDaily_fats_need() {
        return daily_fats_need;
    }

    public void setDaily_fats_need() {
        this.daily_fats_need = Math.round((daily_calories_need *0.2)/9*100.0)/100.0;;
    }

    private IndividualUser(){
        height =0;
        weight=0;
        daily_calories_need = 0;
        age=0;
        water_target=0;
        daily_water_need = 0;
        daily_carbs_need =0;
        daily_fats_need =0 ;
        daily_proteins_need =0 ;
        email = "";
        gender = "";
        plan = "";
        water_progress = 0;
        setDaily_water_need();
    }
    public static IndividualUser getInstance() {
        if (instance == null) {
            instance = new IndividualUser();
        }
        return instance;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDaily_calories_need() {

        return daily_calories_need;
    }

    public void setDaily_calories_need() {
        if(plan.equals("Health & Wellness") || plan.equals("Easy Monitoring")){daily_calories_need = weight * 30;}
        else if(plan.equals("Weight Control")){ daily_calories_need = weight * 35;}
        else if(plan.equals("Weight Gain")){  daily_calories_need = weight * 20;}
//        else {  daily_calories_need = weight * 20;}

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getWater_target() {
        return water_target;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getDaily_water_need() {
        return daily_water_need;
    }

    public void setDaily_water_need() {
        final double BASE_FACTOR = 0.3;
        double milliPerKg = weight * BASE_FACTOR;
//        double milliPerKg = WaterIntakeCalculator.calculateRecommendedWaterIntake(weight);
        daily_water_need = milliPerKg * weight;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public void fromDocumentSnapshot(DocumentSnapshot document) {
        if (document.exists()) {
            height = document.getDouble("height");
            weight = document.getDouble("weight");
            daily_calories_need = document.getDouble("daily_calories_need");
            age = document.getLong("age").intValue();
//            water_target = document.getLong("water_target").intValue();
            daily_water_need = document.getDouble("daily_water_need");
            daily_carbs_need = document.getDouble("daily_carbs_need");
            daily_fats_need = document.getDouble("daily_fats_need");
            daily_proteins_need = document.getDouble("daily_proteins_need");
            gender = document.getString("gender");
            water_progress = document.getLong("daily_water_need").intValue();
            plan = document.getString("plan");

            setDaily_water_need();
        }
    }

    public int getWater_progress() {
        return water_progress;
    }

    public void setWater_progress(int water_progress) {
        this.water_progress = water_progress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Used to update info when user update his info
    public void updateCalculations(){
        setDaily_carbs_need();
        setDaily_proteins_need();
        setDaily_fats_need();
        setDaily_calories_need();
        setDaily_water_need();
        FireStoreManager fireStoreManager = new FireStoreManager();
        fireStoreManager.setUserPersonalInfo(instance);
    }

}
