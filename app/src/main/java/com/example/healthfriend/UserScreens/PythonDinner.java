package com.example.healthfriend.UserScreens;

import com.example.healthfriend.Models.PythonIngredient;

import java.util.List;

public class PythonDinner {
    private List<PythonIngredient> dinnerPythonIngredients;
    private TodaysNutrientsEaten dinnerTodaysNutrientsEaten;
    private static PythonDinner instance;

    private PythonDinner() {
        dinnerTodaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
    }

    public static  PythonDinner getInstance() {
        if (instance == null) {
            instance = new PythonDinner();
        }
        return instance;
    }

    public List<PythonIngredient> getDinnerPythonIngredients() {
        return dinnerPythonIngredients;
    }

    public void setDinnerPythonIngredients(List<PythonIngredient> DinnerPythonIngredients) {
        this.dinnerPythonIngredients = DinnerPythonIngredients;
    }
    public double getMealCalories(){
        int mealCalories = 0;
        for(PythonIngredient ingredient : dinnerPythonIngredients){
            mealCalories+= ingredient.getCalories();
        }
        return mealCalories;
    }
}
