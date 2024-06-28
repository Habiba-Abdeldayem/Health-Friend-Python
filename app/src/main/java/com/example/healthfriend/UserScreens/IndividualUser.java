package com.example.healthfriend.UserScreens;

import android.util.Log;

import com.example.healthfriend.DoctorScreens.Doctor;
import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.google.firebase.firestore.DocumentSnapshot;

public class IndividualUser {
    private FireStoreManager fireStoreManager;
    private double height, weight, daily_calories_need, daily_carbs_need, daily_proteins_need, daily_fats_need, daily_water_need;
    private int age;
    private double water_target, water_progress;
    private String email, gender, plan, name, doctorEmailConnectedWith;

    private static IndividualUser instance;
    private Doctor currentDoctor;
    private int currentDoctorPlanIdx;
    private WeeklyPlan weeklyPlan;

    public static IndividualUser getInstance() {
        if (instance == null) {
            instance = new IndividualUser();
        }
        return instance;
    }

    private IndividualUser() {
        fireStoreManager = new FireStoreManager();
        height = 0;
        weight = 0;
        daily_calories_need = 0;
        age = 0;
        water_target = 0;
        daily_water_need = 0;
        daily_carbs_need = 0;
        daily_fats_need = 0;
        daily_proteins_need = 0;
        email = "";
        gender = "";
        plan = "";
        name="";
        water_progress = 0;
        currentDoctorPlanIdx=-1;
        doctorEmailConnectedWith = null;
        currentDoctor = null;
        weeklyPlan = null;
        setDaily_water_need();
    }

    public double getDaily_carbs_need() {
        return daily_carbs_need;
    }

    public void setDaily_carbs_need() {
        this.daily_carbs_need = Math.round((daily_calories_need * 0.5) / 4 * 100.0) / 100.0;
        ;
    }

    public double getDaily_proteins_need() {
        return daily_proteins_need;
    }

    public void setDaily_proteins_need() {
        this.daily_proteins_need = Math.round((daily_calories_need * 0.3) / 4 * 100.0) / 100.0;
        ;
    }

    public double getDaily_fats_need() {
        return daily_fats_need;
    }

    public void setDaily_fats_need() {
        this.daily_fats_need = Math.round((daily_calories_need * 0.2) / 9 * 100.0) / 100.0;
        ;
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
        if (plan.equals("Health & Wellness") || plan.equals("Easy Monitoring")) {
            daily_calories_need = weight * 30;
        } else if (plan.equals("Weight Control")) {
            daily_calories_need = weight * 35;
        } else if (plan.equals("Weight Gain")) {
            daily_calories_need = weight * 20;
        }
//        else {  daily_calories_need = weight * 20;}

    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getWater_target() {
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
            name = document.getString("name");
            water_progress = document.getLong("water_progress").doubleValue();
            plan = document.getString("plan");
            doctorEmailConnectedWith = document.getString("doctorEmailConnectedWith");
            if (doctorEmailConnectedWith != null)
                setWeeklyPlan();
            setDaily_water_need();
        }
    }

    public double getWater_progress() {
        return water_progress;
    }

    public void setWater_progress(double water_progress) {
        this.water_progress = water_progress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Used to update info when user update his info
    public void updateCalculations() {
        setDaily_carbs_need();
        setDaily_proteins_need();
        setDaily_fats_need();
        setDaily_calories_need();
        setDaily_water_need();
        FireStoreManager fireStoreManager = new FireStoreManager();
        fireStoreManager.setUserPersonalInfo(instance);
    }

    public String getDoctorEmailConnectedWith() {
        return doctorEmailConnectedWith;
    }

    public void setDoctorEmailConnectedWith(String doctorEmailConnectedWith) {
        this.doctorEmailConnectedWith = doctorEmailConnectedWith;
//        fireStoreManager.retrieveDoctorFromFirestore(doctorEmailConnectedWith);
    }

    public Doctor getCurrentDoctor() {
        return currentDoctor;
    }

    public void setCurrentDoctor(Doctor currentDoctor) {
        this.currentDoctor = currentDoctor;
    }


    public WeeklyPlan getWeeklyPlan() {
        return weeklyPlan;
    }

    public void setWeeklyPlan(WeeklyPlan weeklyPlan) {
        this.weeklyPlan = weeklyPlan;
    }

    public void setWeeklyPlan() {
        fireStoreManager.getWeeklyPlan(instance.getEmail(), doctorEmailConnectedWith, task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    this.weeklyPlan = document.toObject(WeeklyPlan.class);
                    WeeklyPlanManagerSingleton.getInstance().setWeeklyPlan(weeklyPlan);
                } else {
                    System.out.println("No such document!");
                }
            } else {
                System.err.println("Task failed: " + task.getException());
            }
        });
//        Log.d("plalala", ""+this.weeklyPlan.getDailyPlans().size());


    }

    public void unFollowDoctor() {
        Log.d("ehellybnull??", "email?? " + this.getEmail());
        fireStoreManager.removePatientEmail(doctorEmailConnectedWith, getEmail());
        this.currentDoctor = null;
        this.doctorEmailConnectedWith = null;
        fireStoreManager.setUserPersonalInfo(instance);
    }

    public int getCurrentDoctorPlanIdx() {
        return currentDoctorPlanIdx;
    }

    public void setCurrentDoctorPlanIdx(int currentDoctorPlanIdx) {
        this.currentDoctorPlanIdx = currentDoctorPlanIdx;
    }

    public void logout() {
        instance = null;
    }

}
