package com.example.healthfriend.UserScreens;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.PythonIngredient;
import com.example.healthfriend.Models.UserMeal;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DayMealManager {
    private static DayMealManager instance;
    IndividualUser individualUser;
    private TodaysNutrientsEaten todaysNutrientsEaten;
    private PythonLaunch pythonLaunch;
    private PythonBreakfast pythonBreakfast;
    private PythonDinner pythonDinner;
    private Context context;

    private DayMealManager(Context context) {
        this.context = context;
        individualUser = IndividualUser.getInstance();
        pythonBreakfast= PythonBreakfast.getInstance();
        pythonLaunch= PythonLaunch.getInstance();
        pythonDinner= PythonDinner.getInstance();
        todaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
//        isDoctorPlanApplied()
        if (isDoctorPlanApplied()) {
            setDoctorLunch();
        }
        else{

        }
    }

    public static DayMealManager getInstance(Context context) {
        if (instance == null) {
            instance = new DayMealManager(context);
        }
        return instance;
    }

    public boolean isDoctorPlanApplied() {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.healthfriend.PREFERENCES", Context.MODE_PRIVATE);
        Log.d("jjgjgj",""+sharedPref.getBoolean("is_doctor_plan_applied", false));
        return sharedPref.getBoolean("is_doctor_plan_applied", false); // Default is false if not found
    }

    public PythonLaunch getPythonLaunch() {
        return pythonLaunch;
    }

    public void setPythonLaunch() {
        if(pythonLaunch.getLunchPythonIngredients()==null) {

            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(context));
            }
            Python python=Python.getInstance();
            PyObject myModule=python.getModule("chocoo");
            PyObject myfncall=myModule.get("calll");
            PyObject result= myfncall.call(individualUser.getWeight(), individualUser.getHeight(),"lunch.csv");
            String f=result.toString();
            Type type = new TypeToken<List<UserMeal>>() {}.getType();

            // Deserialize JSON to List<Meal>
            List<UserMeal> meals = new Gson().fromJson(f, type);
            List<PythonIngredient> pythonIngredients = new ArrayList<>();

            for(UserMeal meal : meals){
                for (PythonIngredient ingredient :meal.getIngredients()){
                    pythonIngredients.add(new PythonIngredient(ingredient.getName(),
                            ingredient.getCarbs(),
                            ingredient.getCalories(),
                            ingredient.getFats(),
                            ingredient.getProtein(),
                            ingredient.getCount(),
                            ingredient.getCategory()));
                }
                break;
            }

            pythonLaunch.setLunchPythonIngredients(pythonIngredients);
        }

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

    private void setDoctorBreakfast() {
        if (IndividualUser.getInstance().getWeeklyPlan() != null && IndividualUser.getInstance().getWeeklyPlan().getDailyPlans() != null) {
            List<DoctorIngredient> tmpBreakfastIngredients = IndividualUser.getInstance().getWeeklyPlan().getDailyPlans().get(0).getBreakfast().getIngredients();
            List<PythonIngredient> breakfastIngredients = DoctorIngredient.convertToPythonIngredientList(tmpBreakfastIngredients);
            PythonBreakfast.getInstance().setBreakfastPythonIngredients(breakfastIngredients);
        }
    }
    private void setDoctorLunch() {
        if (IndividualUser.getInstance().getWeeklyPlan() != null && IndividualUser.getInstance().getWeeklyPlan().getDailyPlans() != null) {
            List<DoctorIngredient> tmpLunchIngredients = IndividualUser.getInstance().getWeeklyPlan().getDailyPlans().get(0).getLunch().getIngredients();
            List<PythonIngredient> lunchIngredients = DoctorIngredient.convertToPythonIngredientList(tmpLunchIngredients);
            PythonLaunch.getInstance().setLunchPythonIngredients(lunchIngredients);
        }
    }
    private void setDoctorDinner() {
        if (IndividualUser.getInstance().getWeeklyPlan() != null && IndividualUser.getInstance().getWeeklyPlan().getDailyPlans() != null) {
            List<DoctorIngredient> tmpDinnerIngredients = IndividualUser.getInstance().getWeeklyPlan().getDailyPlans().get(0).getDinner().getIngredients();
            List<PythonIngredient> dinnerIngredients = DoctorIngredient.convertToPythonIngredientList(tmpDinnerIngredients);
            PythonDinner.getInstance().setDinnerPythonIngredients(dinnerIngredients);
        }
    }
}
