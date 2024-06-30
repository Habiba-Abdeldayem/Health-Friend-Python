package com.example.healthfriend.UserScreens;

import com.example.healthfriend.Models.PythonIngredient;

import java.util.List;

public class PythonBreakfast {
    private List<PythonIngredient> breakfastPythonIngredients;
    private TodaysNutrientsEaten breakfastTodaysNutrientsEaten;
    private static PythonBreakfast instance;

    private PythonBreakfast() {
        breakfastTodaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
    }

    public static  PythonBreakfast getInstance() {
        if (instance == null) {
            instance = new PythonBreakfast();
        }
        return instance;
    }

    public List<PythonIngredient> getBreakfastPythonIngredients() {
        return breakfastPythonIngredients;
    }

    public void setBreakfastPythonIngredients(List<PythonIngredient> breakfastPythonIngredients) {
        this.breakfastPythonIngredients = breakfastPythonIngredients;
    }
    public double getMealCalories(){
        int mealCalories = 0;
        for(PythonIngredient ingredient : breakfastPythonIngredients){
            mealCalories+= ingredient.getCalories();
        }
        return mealCalories;
    }
}
