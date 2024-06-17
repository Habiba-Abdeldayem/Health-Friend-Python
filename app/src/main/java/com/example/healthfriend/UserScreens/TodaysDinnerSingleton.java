package com.example.healthfriend.UserScreens;

import com.example.healthfriend.UserScreens.Adapters.IngredientModel;
import com.example.healthfriend.UserScreens.Adapters.MealModel;

import java.util.List;
import java.util.Random;

public class TodaysDinnerSingleton implements FireStoreManager.FirestoreCallback{
    private static TodaysDinnerSingleton instance;
    private TodaysNutrientsEaten dinnerTodaysNutrientsEaten;

    private FireStoreManager fireStoreManager;
    private List<MealModel> availableDinners;
    private List<IngredientModel> dinnerIngredients;
    private MealModel todaysDinner;

    private TodaysDinnerSingleton() {
        dinnerTodaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
        fireStoreManager = new FireStoreManager();
        fireStoreManager.setFirestoreCallback(this);
        fireStoreManager.getDinners();
    }

    public static synchronized TodaysDinnerSingleton getInstance() {
        if (instance == null) {
            instance = new TodaysDinnerSingleton();
        }
        return instance;
    }
    public synchronized MealModel getTodayDinner() {
        return todaysDinner;
    }

    @Override
    public void onSuccess(List<MealModel> meals) {
        availableDinners = meals;
        if (availableDinners != null && !availableDinners.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(availableDinners.size());
            todaysDinner = availableDinners.get(randomIndex);
            fireStoreManager.getIngredients(todaysDinner.getMeal_ingredients_id());
        }
    }

    @Override
    public void onSuccessIngredients(List<IngredientModel> ingredients) {
        dinnerIngredients = ingredients;
    }

    @Override
    public void onFailure(Exception e) {
        System.out.println("something went wrong");
    }

    public List<MealModel> getAvailableDinners() {
        return availableDinners;
    }

    public List<IngredientModel> getDinnerIngredients() {
        return dinnerIngredients;
    }
}
