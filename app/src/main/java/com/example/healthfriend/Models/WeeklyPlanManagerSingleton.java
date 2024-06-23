package com.example.healthfriend.Models;

public class WeeklyPlanManagerSingleton {
    private static WeeklyPlanManagerSingleton instance;
    private WeeklyPlan weeklyPlan;
    private int currentDayIdx;
    private int currentMealIdx; // 1 breakfast , 2 lunch , 3 dinner

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

    public int getCurrentMealIdx() {
        return currentMealIdx;
    }

    public void setCurrentMeal(int currentMeal) {
        this.currentMealIdx = currentMeal;
    }
}
