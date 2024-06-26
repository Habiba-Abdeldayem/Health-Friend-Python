package com.example.healthfriend.Models;

import com.example.healthfriend.Models.Ingredient;

public class PythonIngredient extends Ingredient {
    private boolean isIngredientSelectedByUserr; // New boolean field to track the image state

    public PythonIngredient() {
    }

    public PythonIngredient(String name, double carbs, double calories, double fats, double protein,int count,String category) {
        super(name,carbs,calories,fats,protein,count,category);
        isIngredientSelectedByUserr = false;
    }


    public boolean isIngredientSelectedByUser() {
        return isIngredientSelectedByUserr;
    }

    public void setIngredientSelectedByUser(boolean ingredientSelectedByUser) {
        isIngredientSelectedByUserr = ingredientSelectedByUser;
    }
}
