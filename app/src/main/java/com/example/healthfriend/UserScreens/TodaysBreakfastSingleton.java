package com.example.healthfriend.UserScreens;

import com.example.healthfriend.UserScreens.Adapters.IngredientModel;
import com.example.healthfriend.UserScreens.Adapters.MealModel;

import java.util.List;
import java.util.Random;

public class TodaysBreakfastSingleton implements FireStoreManager.FirestoreCallback{


    private static TodaysBreakfastSingleton instance;
    TodaysNutrientsEaten breakfastTodaysNutrientsEaten;

    private FireStoreManager fireStoreManager;
    private List<MealModel> availableBreakfasts;
    private List<IngredientModel> breakfastIngredients;

    public void setBreakfastIngredients(List<IngredientModel> breakfastIngredients) {
        this.breakfastIngredients = breakfastIngredients;
    }

    private MealModel todaysBreakfast;

    private TodaysBreakfastSingleton() {
        breakfastTodaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
        fireStoreManager = new FireStoreManager();
        fireStoreManager.setFirestoreCallback(this);
        fireStoreManager.getBreakfasts();
    }

    public static synchronized TodaysBreakfastSingleton getInstance() {
        if (instance == null) {
            instance = new TodaysBreakfastSingleton();
        }
        return instance;
    }

    // Getter method for todaysBreakfast
    public synchronized MealModel getTodaysBreakfast() {
        return todaysBreakfast;
    }

    @Override
    public void onSuccess(List<MealModel> meals) {
        availableBreakfasts = meals;
        if (availableBreakfasts != null && !availableBreakfasts.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(availableBreakfasts.size());
            // Set todaysBreakfast to a randomly selected breakfast
            todaysBreakfast = availableBreakfasts.get(randomIndex);
            fireStoreManager.getIngredients(todaysBreakfast.getMeal_ingredients_id());
        }
    }

    @Override
    public void onSuccessIngredients(List<IngredientModel> ingredients) {
        breakfastIngredients = ingredients;
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("something went wrong");
    }

    public List<MealModel> getAvailableBreakfasts() {
        return availableBreakfasts;
    }

    public List<IngredientModel> getBreakfastIngredients() {
        return breakfastIngredients;
    }
}
