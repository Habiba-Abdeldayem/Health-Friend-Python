package com.example.healthfriend.Models;

public class Ingredient {
    private String name;
    private double carbs, calories, fats, protein;
    private boolean isIngredientSelectedByUser; // New boolean field to track the image state
    private boolean isIngredientSelectedByDoctor; // New boolean field to track the image state

    public Ingredient() {
    }

    public Ingredient(String name, double carbs, double calories, double fats, double protein) {
        this.name = name;
        this.carbs = carbs;
        this.calories = calories;
        this.fats = fats;
        this.protein = protein;
        isIngredientSelectedByUser = false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getCarbs() {
        return carbs;
    }

    public void setCarbs(double carbs) {
        this.carbs = carbs;
    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getFats() {
        return fats;
    }

    public void setFats(double fats) {
        this.fats = fats;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

}
