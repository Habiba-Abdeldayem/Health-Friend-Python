package com.example.healthfriend.Models;

public class DoctorIngredient extends Ingredient{
    private boolean isIngredientSelectedByDoctor; // New boolean field to track the image state

    public DoctorIngredient() {
    }

    public DoctorIngredient(String name, double carbs, double calories, double fats, double protein,int count,String category) {
        super(name,carbs,calories,fats,protein,count,category);
        isIngredientSelectedByDoctor = false;
    }


    public boolean isIngredientSelectedByDoctor() {
        return isIngredientSelectedByDoctor;
    }

    public void setIngredientSelectedByDoctor(boolean ingredientSelectedByDoctor) {
        isIngredientSelectedByDoctor = ingredientSelectedByDoctor;
    }
}
