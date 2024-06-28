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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
        checkAndResetMealsIfNeeded();
        retrieveMealsFromFirestore(getCurrentDate());
        if (isDoctorPlanApplied()) {
            setDoctorLunch();
            setDoctorBreakfast();
            setDoctorDinner();
        }
        else{

//        setPythonLaunch();

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
    private String getPreviousDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date previousDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(previousDate);
    }
    private String getpPreviousDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -2);
        Date previousDate = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(previousDate);
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
        dailyData.setDate(getPreviousDate());
        dailyData.setBreakfast(pythonBreakfast.getBreakfastPythonIngredients());
        dailyData.setLunch(pythonLunch.getLunchPythonIngredients());
        dailyData.setDinner(pythonDinner.getDinnerPythonIngredients());
        dailyData.setEatenCalories(TodaysNutrientsEaten.getEatenCalories());
        dailyData.setEatenCarbs(TodaysNutrientsEaten.getEatenCarbs());
        dailyData.setEatenFats(TodaysNutrientsEaten.getEatenFats());
        dailyData.setEatenProteins(TodaysNutrientsEaten.getEatenProteins());
        dailyData.setWater_progress(IndividualUser.getInstance().getWater_progress());
        dailyData.setWeight(IndividualUser.getInstance().getWeight());

        db.collection("Users").document(userId).collection("history").document(getPreviousDate())
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
    public void setPythonBreakfast() {
        if(pythonBreakfast.getBreakfastPythonIngredients()==null) {

            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(context));
            }
            Python python=Python.getInstance();
            PyObject myModule=python.getModule("chocoo");
            PyObject myfncall=myModule.get("calll");
            PyObject result= myfncall.call(individualUser.getWeight(), individualUser.getHeight(),"breakfast.csv");
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

            pythonBreakfast.setBreakfastPythonIngredients(pythonIngredients);
        }
//        this.pythonBreakfast = pythonBreakfast;
//        updateMealsInFirestore();
    }
    public void setPythonDinner() {
//        Log.d("ddssis","dd "+ pythonDinner.getDinnerPythonIngredients().size());
        if(pythonDinner.getDinnerPythonIngredients()==null) {
            Log.d("ddssis","started  "+ pythonDinner.getDinnerPythonIngredients().size());

            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(context));
            }
            Python python=Python.getInstance();
            PyObject myModule=python.getModule("chocoo");
            PyObject myfncall=myModule.get("calll");
            PyObject result= myfncall.call(individualUser.getWeight(), individualUser.getHeight(),"breakfast.csv");
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

            pythonDinner.setDinnerPythonIngredients(pythonIngredients);
        }
//        this.pythonDinner = pythonDinner;
//        updateMealsInFirestore();
    }
    private void setDoctorBreakfast() {
        if (IndividualUser.getInstance().getWeeklyPlan() != null && IndividualUser.getInstance().getWeeklyPlan().getDailyPlans() != null) {
            List<DoctorIngredient> tmpBreakfastIngredients = IndividualUser.getInstance().getWeeklyPlan().getDailyPlans().get(0).getBreakfast().getIngredients();
            List<PythonIngredient> breakfastIngredients = DoctorIngredient.convertToPythonIngredientList(tmpBreakfastIngredients);
            pythonBreakfast.setBreakfastPythonIngredients(breakfastIngredients);
            updateMealsInFirestore();
        }
    }
    private void setDoctorLunch() {
        if (IndividualUser.getInstance().getWeeklyPlan() != null && IndividualUser.getInstance().getWeeklyPlan().getDailyPlans() != null) {
            List<DoctorIngredient> tmpLunchIngredients = IndividualUser.getInstance().getWeeklyPlan().getDailyPlans().get(0).getLunch().getIngredients();
            List<PythonIngredient> lunchIngredients = DoctorIngredient.convertToPythonIngredientList(tmpLunchIngredients);
            PythonLunch.getInstance().setLunchPythonIngredients(lunchIngredients);
            updateMealsInFirestore();
        }
    }
    private void setDoctorDinner() {
        if (IndividualUser.getInstance().getWeeklyPlan() != null && IndividualUser.getInstance().getWeeklyPlan().getDailyPlans() != null) {
            List<DoctorIngredient> tmpDinnerIngredients = IndividualUser.getInstance().getWeeklyPlan().getDailyPlans().get(0).getDinner().getIngredients();
            List<PythonIngredient> dinnerIngredients = DoctorIngredient.convertToPythonIngredientList(tmpDinnerIngredients);
            PythonDinner.getInstance().setDinnerPythonIngredients(dinnerIngredients);
            updateMealsInFirestore();
        }
    }


    public void updateMealsInFirestore() {
        String currentDate = getCurrentDate();
        storeMealsInFirestore(currentDate);
//        storeMealsInFirestore(getPreviousDate());
    }
    public boolean isDoctorPlanApplied() {
        SharedPreferences sharedPref = context.getSharedPreferences("com.example.healthfriend.PREFERENCES", Context.MODE_PRIVATE);
        Log.d("jjgjgj",""+sharedPref.getBoolean("is_doctor_plan_applied", false));
        return sharedPref.getBoolean("is_doctor_plan_applied", false); // Default is false if not found
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
    public void retrieveHistoryFirestore(String startDate, String endDate, OnDataRetrievedListener listener) {
        String userId = IndividualUser.getInstance().getEmail();
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Prepare to collect data
        List<DailyData> dailyDataList = new ArrayList<>();

        // Convert startDate and endDate to "yyyyMMdd" format for Firestore query
        SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("yyyyMMdd");

        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        try {
            // Parse input dates to Date objects
            Date startDateObj = sdfInput.parse(startDate);
            Date endDateObj = sdfInput.parse(endDate);

            // Format parsed dates to "yyyyMMdd"
            String formattedStartDate = sdfOutput.format(startDateObj);
            String formattedEndDate = sdfOutput.format(endDateObj);

            // Set Calendar instances to parsed and formatted dates
            startCal.setTime(sdfOutput.parse(formattedStartDate));
            endCal.setTime(sdfOutput.parse(formattedEndDate));

        } catch (ParseException e) {
            e.printStackTrace();
            Log.d("UserStatisticsFragment", "Error parsing dates: " + e.getMessage());
            return;
        }

        // Iterate through dates and retrieve data from Firestore
        while (!startCal.after(endCal)) {
            String date = sdfOutput.format(startCal.getTime());

            db.collection("Users").document(userId).collection("history").document(date)
                    .get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            DailyData dailyData = documentSnapshot.toObject(DailyData.class);
                            if (dailyData != null) {
                                dailyDataList.add(dailyData);
                            }
                        }

                        // Check if it's the last date to notify listener
                        if (date.equals(sdfOutput.format(endCal.getTime()))) {
                            Log.d("UserStatisticsFragment", "Data retrieved, dailydata? " + dailyDataList.isEmpty());
                            listener.onDataRetrieved(dailyDataList);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.w("DayMealManager", "Error retrieving document for date " + date, e);
                        // Notify listener even on failure
                        if (date.equals(sdfOutput.format(endCal.getTime()))) {
                            listener.onDataRetrieved(dailyDataList);
                        }
                    });

            startCal.add(Calendar.DAY_OF_YEAR, 1); // Move to next day
        }
    }


    public interface OnDataRetrievedListener {
        void onDataRetrieved(List<DailyData> dailyDataList);
    }

}
