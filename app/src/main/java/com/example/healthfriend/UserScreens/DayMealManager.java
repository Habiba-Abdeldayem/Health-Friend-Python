package com.example.healthfriend.UserScreens;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.healthfriend.Models.Meal;

public class DayMealManager {
    private static DayMealManager instance;
    private TodaysNutrientsEaten todaysNutrientsEaten;
    private PythonLaunch pythonLaunch;
    private PythonBreakfast pythonBreakfast;
    private PythonDinner pythonDinner;
    public DayMealManager(Context context) {
        if(isDoctorPlanApplied(context)){

        }
    }
    public DayMealManager() {

    }

    public static DayMealManager getInstance() {
        if (instance == null) {
            instance = new DayMealManager();
        }
        return instance;
    }

    public boolean isDoctorPlanApplied(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.healthfriend.PREFERENCES", Context.MODE_PRIVATE);
        return sharedPref.getBoolean("is_doctor_plan_applied", false); // Default is false if not found
    }

    public PythonLaunch getPythonLaunch() {
        return pythonLaunch;
    }

    public void setPythonLaunch(PythonLaunch pythonLaunch) {
        this.pythonLaunch = pythonLaunch;
    }

    public PythonBreakfast getPythonBreakfast() {
        return pythonBreakfast;
    }

    public void setPythonBreakfast(PythonBreakfast pythonBreakfast) {
        this.pythonBreakfast = pythonBreakfast;
    }

    public PythonDinner getPythonDinner() {
        return pythonDinner;
    }

    public void setPythonDinner(PythonDinner pythonDinner) {
        this.pythonDinner = pythonDinner;
    }
}
