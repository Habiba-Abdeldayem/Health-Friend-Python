package com.example.healthfriend.UserScreens;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.healthfriend.Models.DailyData;
import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.PythonIngredient;
import com.example.healthfriend.Models.UserMeal;
import com.google.common.reflect.TypeToken;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DayMealManager {
    private static DayMealManager instance;
    IndividualUser individualUser=IndividualUser.getInstance();
    private Context context;
    private PythonBreakfast pythonBreakfast;
    private PythonLunch pythonLunch;
    private PythonDinner pythonDinner;

    private DayMealManager(Context context) {
        this.context = context;
        pythonBreakfast = PythonBreakfast.getInstance();
        pythonLunch = PythonLunch.getInstance();
        pythonDinner = PythonDinner.getInstance();
//        checkAndResetMealsIfNeeded();
//        retrieveMealsFromFirestore(getCurrentDate());
        if (isDoctorPlanApplied()) {
            setDoctorLunch();
        }
        else{
        setPythonLaunch();

        }
    }

    public static DayMealManager getInstance(Context context) {
        if (instance == null) {
            instance = new DayMealManager(context);
        }
        return instance;
    }

    private void checkAndResetMealsIfNeeded() {
        String currentDate = getCurrentDate();
        String lastDate = getLastDate();
        if (!currentDate.equals(lastDate)) {
            storeMealsInFirestore(lastDate);
            resetMeals();
            saveCurrentDate(currentDate);
        }
    }

    private String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }

    private void saveCurrentDate(String date) {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.healthfriend.PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("last_date", date);
        editor.apply();
    }

    private String getLastDate() {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.healthfriend.PREFERENCES", Context.MODE_PRIVATE);
        return sharedPref.getString("last_date", "");
    }

    private void storeMealsInFirestore(String date) {
        if (date.isEmpty()) return;

        String userId = IndividualUser.getInstance().getEmail();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        DailyData dailyData = new DailyData();
        dailyData.setBreakfast(pythonBreakfast.getBreakfastPythonIngredients());
        dailyData.setLunch(pythonLunch.getLunchPythonIngredients());
        dailyData.setDinner(pythonDinner.getDinnerPythonIngredients());
        dailyData.setEatenCalories(TodaysNutrientsEaten.getEatenCalories());
        dailyData.setEatenCarbs(TodaysNutrientsEaten.getEatenCarbs());
        dailyData.setEatenFats(TodaysNutrientsEaten.getEatenFats());
        dailyData.setEatenProteins(TodaysNutrientsEaten.getEatenProteins());
        dailyData.setWaterProgress(IndividualUser.getInstance().getWater_progress());  // Assuming waterProgress is a Double
        dailyData.setWeight(IndividualUser.getInstance().getWeight());  // Assuming weight is a Double

        db.collection("Users").document(userId).collection("history").document(date)
                .set(dailyData)
                .addOnSuccessListener(aVoid -> Log.d("DayMealManager", "Meals successfully written!"))
                .addOnFailureListener(e -> Log.w("DayMealManager", "Error writing document", e));
    }


    private Map<String, Object> getMealsData() {
        Map<String, Object> mealsData = new HashMap<>();
        mealsData.put("breakfast", pythonBreakfast.getBreakfastPythonIngredients());
        mealsData.put("lunch", pythonLunch.getLunchPythonIngredients());
        mealsData.put("dinner", pythonDinner.getDinnerPythonIngredients());
        return mealsData;
    }

    private void resetMeals() {
        pythonBreakfast.setBreakfastPythonIngredients(null);
        pythonLunch.setLunchPythonIngredients(null);
        pythonDinner.setDinnerPythonIngredients(null);
    }

    public void setPythonLunch(PythonLunch pythonLunch) {
        this.pythonLunch = pythonLunch;
        updateMealsInFirestore();
    }
    public void setPythonLaunch() {
        if(pythonLunch.getLunchPythonIngredients()==null) {

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

            pythonLunch.setLunchPythonIngredients(pythonIngredients);
        }

    }
    public void setPythonBreakfast(PythonBreakfast pythonBreakfast) {
        this.pythonBreakfast = pythonBreakfast;
        updateMealsInFirestore();
    }

    public void setPythonDinner(PythonDinner pythonDinner) {
        this.pythonDinner = pythonDinner;
        updateMealsInFirestore();
    }

    private void updateMealsInFirestore() {
        String currentDate = getCurrentDate();
        storeMealsInFirestore(currentDate);
    }
    public boolean isDoctorPlanApplied() {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.healthfriend.PREFERENCES", Context.MODE_PRIVATE);
        Log.d("jjgjgj",""+sharedPref.getBoolean("is_doctor_plan_applied", false));
        return sharedPref.getBoolean("is_doctor_plan_applied", false); // Default is false if not found
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
            PythonLunch.getInstance().setLunchPythonIngredients(lunchIngredients);
        }
    }
    private void setDoctorDinner() {
        if (IndividualUser.getInstance().getWeeklyPlan() != null && IndividualUser.getInstance().getWeeklyPlan().getDailyPlans() != null) {
            List<DoctorIngredient> tmpDinnerIngredients = IndividualUser.getInstance().getWeeklyPlan().getDailyPlans().get(0).getDinner().getIngredients();
            List<PythonIngredient> dinnerIngredients = DoctorIngredient.convertToPythonIngredientList(tmpDinnerIngredients);
            PythonDinner.getInstance().setDinnerPythonIngredients(dinnerIngredients);
        }
    }
    private void retrieveMealsFromFirestore(String date) {
        if (date.isEmpty()) return;

        String userId = IndividualUser.getInstance().getEmail();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Users").document(userId).collection("history").document(date)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        DailyData dailyData = documentSnapshot.toObject(DailyData.class);

                        if (dailyData != null) {
                            pythonBreakfast.setBreakfastPythonIngredients(dailyData.getBreakfast());
                            pythonLunch.setLunchPythonIngredients(dailyData.getLunch());
                            pythonDinner.setDinnerPythonIngredients(dailyData.getDinner());

                            TodaysNutrientsEaten.setEatenCalories(dailyData.getEatenCalories());
                            TodaysNutrientsEaten.setEatenCarbs(dailyData.getEatenCarbs());
                            TodaysNutrientsEaten.setEatenFats(dailyData.getEatenFats());
                            TodaysNutrientsEaten.setEatenProteins(dailyData.getEatenProteins());

                            // Set water progress and weight in your respective classes
                            // Example: WaterProgress.setProgress(dailyMealData.getWaterProgress());
                            // Example: UserWeight.setWeight(dailyMealData.getWeight());

                            Log.d("DayMealManager", "Meals successfully retrieved!");
                        }
                    } else {
                        Log.d("DayMealManager", "No document found for the specified date.");
                    }
                })
                .addOnFailureListener(e -> Log.w("DayMealManager", "Error retrieving document", e));
    }

}
