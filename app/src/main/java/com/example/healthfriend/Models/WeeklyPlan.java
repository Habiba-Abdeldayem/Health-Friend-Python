package com.example.healthfriend.Models;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class WeeklyPlan {
    private List<DailyPlan> dailyPlans; // 7 DailyPlan objects

    // Default constructor
    public WeeklyPlan() {}

    public WeeklyPlan(List<DailyPlan> dailyPlans) {
        this.dailyPlans = dailyPlans;
    }

    // Getters and Setters
    public List<DailyPlan> getDailyPlans() {
        return dailyPlans;
    }

    public void setDailyPlans(List<DailyPlan> dailyPlans) {
        this.dailyPlans = dailyPlans;
    }

}
