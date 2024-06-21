package com.example.healthfriend.Models;

public class WeeklyPlanManagerSingleton {
    private static WeeklyPlanManagerSingleton instance;
    private WeeklyPlan weeklyPlan;
    private int currentDayIdx;

    private WeeklyPlanManagerSingleton() {}

    public static WeeklyPlanManagerSingleton getInstance() {
        if (instance == null) {
            instance = new WeeklyPlanManagerSingleton();
        }
        return instance;
    }

    public WeeklyPlan getWeeklyPlan() {
        return weeklyPlan;
    }

    public void setWeeklyPlan(WeeklyPlan weeklyPlan) {
        this.weeklyPlan = weeklyPlan;
    }

    public int getCurrentDayIdx() {
        return currentDayIdx;
    }

    public void setCurrentDayIdx(int currentDayIdx) {
        this.currentDayIdx = currentDayIdx;
    }
}
