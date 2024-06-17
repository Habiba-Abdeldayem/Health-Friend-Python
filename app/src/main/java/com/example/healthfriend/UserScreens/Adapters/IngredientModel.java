package com.example.healthfriend.UserScreens.Adapters;

public class IngredientModel {
    private String name;
    private double calories;
    private double protein;
    private double fat;
    private double carbohydrates;
    private int id;
    private String servingSize;
    private boolean isIngredientSelected; // New boolean field to track the image state

    public IngredientModel() {
        this.isIngredientSelected = false;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getFat() {
        return fat;
    }

    public void setFat(double fat) {
        this.fat = fat;
    }

    public double getCarbohydrates() {
        return carbohydrates;
    }

    public void setCarbohydrates(double carbohydrates) {
        this.carbohydrates = carbohydrates;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }

    public boolean isIngredientSelected() {
        return isIngredientSelected;
    }

    public void setIngredientSelected(boolean ingredientSelected) {
        isIngredientSelected = ingredientSelected;
    }
}
