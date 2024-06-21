package com.example.healthfriend.Models;

public class DoctorIngredient extends Ingredient{
    private boolean isIngredientSelectedByDoctor; // New boolean field to track the image state

    public DoctorIngredient() {
    }

    public DoctorIngredient(String name, double carbs, double calories, double fats, double protein) {
        super(name,carbs,calories,fats,protein);
        isIngredientSelectedByDoctor = false;
    }


    public boolean isIngredientSelectedByDoctor() {
        return isIngredientSelectedByDoctor;
    }

    public void setIngredientSelectedByDoctor(boolean ingredientSelectedByDoctor) {
        isIngredientSelectedByDoctor = ingredientSelectedByDoctor;
    }
}
