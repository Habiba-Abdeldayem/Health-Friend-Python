package com.example.healthfriend.UserScreens;

import com.example.healthfriend.Models.PythonIngredient;

import java.util.List;

public class PythonLunch {
    private List<PythonIngredient> lunchPythonIngredients;
    private TodaysNutrientsEaten launchTodaysNutrientsEaten;
    private static PythonLunch instance;

    private PythonLunch() {
        launchTodaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
    }

    public static PythonLunch getInstance() {
        if (instance == null) {
            instance = new PythonLunch();
        }
        return instance;
    }

    public List<PythonIngredient> getLunchPythonIngredients() {
        return lunchPythonIngredients;
    }

    public void setLunchPythonIngredients(List<PythonIngredient> lunchPythonIngredients) {
        this.lunchPythonIngredients = lunchPythonIngredients;
    }
    public double getMealCalories(){
        int mealCalories = 0;
        for(PythonIngredient ingredient : lunchPythonIngredients){
            mealCalories+= ingredient.getCalories();
        }
        return mealCalories;
    }


}
