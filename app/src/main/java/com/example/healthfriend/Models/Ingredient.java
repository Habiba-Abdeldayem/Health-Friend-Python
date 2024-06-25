package com.example.healthfriend.Models;

public class Ingredient {
    private String ingredient,category;
    private int count;
    private double carbohydrates, energy, fat, protein;
    private boolean isIngredientSelectedByUser; // New boolean field to track the image state
    private boolean isIngredientSelectedByDoctor; // New boolean field to track the image state

    public Ingredient() {
    }

    public Ingredient(String name, double carbs, double calories, double fats, double protein,int count,String category) {
        this.ingredient = name;
        this.carbohydrates = carbs;
        this.energy = calories;
        this.fat = fats;
        this.protein = protein;
        this.count=count;
        this.category=category;

        isIngredientSelectedByUser = false;
    }

    public String getName() {
        return ingredient;
    }

    public void setName(String name) {
        this.ingredient = name;
    }
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }


    public double getCarbs() {
        return carbohydrates;
    }

    public void setCarbs(double carbs) {
        this.carbohydrates = carbs;
    }

    public double getCalories() {
        return energy;
    }

    public void setCalories(double calories) {
        this.energy = calories;
    }

    public double getFats() {
        return fat;
    }

    public void setFats(double fats) {
        this.fat = fats;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

}
