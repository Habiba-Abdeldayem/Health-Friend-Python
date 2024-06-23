package com.example.healthfriend.Models;

import java.util.List;

public class Meal {
    private String type; // "Breakfast", "Lunch", "Dinner"
    private List<DoctorIngredient> ingredients;

    // Default constructor
    public Meal() {}

    public Meal(String type, List<DoctorIngredient> ingredients) {
        this.type = type;
        this.ingredients = ingredients;
    }

    // Getters and Setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<DoctorIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<DoctorIngredient> ingredients) {
        this.ingredients = ingredients;
    }
    public void addIngredient(DoctorIngredient ingredient) {
        this.ingredients.add(ingredient);
    }
    public void removeIngredient(DoctorIngredient ingredient) {
        this.ingredients.remove(ingredient);
    }
    public void removeIngredientByName(String ingredientName) {
        for (int i = 0; i < ingredients.size(); i++) {
            if (ingredients.get(i).getName().equals(ingredientName)) {
                ingredients.remove(i);
                break;
            }
        }
    }

    public double getTotalCalories() {
        double totalCalories = 0;
        for (DoctorIngredient ingredient : ingredients) {
            totalCalories += ingredient.getCalories();
        }
        return totalCalories;
    }

    public double getTotalFats() {
        double totalFats = 0;
        for (DoctorIngredient ingredient : ingredients) {
            totalFats += ingredient.getFats();
        }
        return totalFats;
    }

    public double getTotalProteins() {
        double totalProteins = 0;
        for (DoctorIngredient ingredient : ingredients) {
            totalProteins += ingredient.getProtein();
        }
        return totalProteins;
    }

    public double getTotalCarbs() {
        double totalCarbs = 0;
        for (DoctorIngredient ingredient : ingredients) {
            totalCarbs += ingredient.getCarbs();
        }
        return totalCarbs;
    }
}
