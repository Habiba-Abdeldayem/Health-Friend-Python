package com.example.healthfriend.Models;

import com.example.healthfriend.Models.Ingredient;

public class PythonIngredient extends Ingredient {
    private boolean isIngredientSelectedByUser; // New boolean field to track the image state

    public PythonIngredient() {
    }

    public PythonIngredient(String name, double carbs, double calories, double fats, double protein) {
        super(name,carbs,calories,fats,protein);
        isIngredientSelectedByUser = false;
    }


    public boolean isIngredientSelectedByUser() {
        return isIngredientSelectedByUser;
    }

    public void setIngredientSelectedByUser(boolean ingredientSelectedByUser) {
        isIngredientSelectedByUser = ingredientSelectedByUser;
    }
}
