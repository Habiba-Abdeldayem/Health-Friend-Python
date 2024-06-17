package com.example.healthfriend.UserScreens;

import com.example.healthfriend.UserScreens.Adapters.IngredientModel;
import com.example.healthfriend.UserScreens.Adapters.MealModel;

import java.util.List;
import java.util.Random;

public class TodaysLunchSingleton implements FireStoreManager.FirestoreCallback {
    private static TodaysLunchSingleton instance;
    TodaysNutrientsEaten lunchTodaysNutrientsEaten;

    private FireStoreManager fireStoreManager;
    private List<MealModel> availableLunches;
    private List<IngredientModel> lunchIngredients;
    private MealModel todaysLunch;

    private TodaysLunchSingleton() {
        lunchTodaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
        fireStoreManager = new FireStoreManager();
        fireStoreManager.setFirestoreCallback(this);
        fireStoreManager.getLunches();
    }

    public static synchronized TodaysLunchSingleton getInstance() {
        if (instance == null) {
            instance = new TodaysLunchSingleton();
        }
        return instance;
    }
    public synchronized MealModel getTodaysLunch() {
        return todaysLunch;
    }

    @Override
    public void onSuccess(List<MealModel> meals) {
        availableLunches = meals;
        if (availableLunches != null && !availableLunches.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(availableLunches.size());
            todaysLunch = availableLunches.get(randomIndex);
            fireStoreManager.getIngredients(todaysLunch.getMeal_ingredients_id());
        }
    }

    @Override
    public void onSuccessIngredients(List<IngredientModel> ingredients) {
        lunchIngredients = ingredients;

    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("something went wrong");

    }

    public List<MealModel> getAvailableLunches() {
        return availableLunches;
    }

    public List<IngredientModel> getLunchIngredients() {
        return lunchIngredients;
    }
}
