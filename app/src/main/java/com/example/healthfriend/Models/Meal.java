package com.example.healthfriend.Models;

import java.util.List;

public class Meal {
    private String type; // "Breakfast", "Lunch", "Dinner"
    private List<Ingredient> ingredients;

    // Default constructor
    public Meal() {}

    public Meal(String type, List<Ingredient> ingredients) {
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }
    public void removeIngredient(Ingredient ingredient) {
        this.ingredients.remove(ingredient);
    }

    public double getTotalCalories() {
        double totalCalories = 0;
        for (Ingredient ingredient : ingredients) {
            totalCalories += ingredient.getCalories();
        }
        return totalCalories;
    }

    public double getTotalFats() {
        double totalFats = 0;
        for (Ingredient ingredient : ingredients) {
            totalFats += ingredient.getFats();
        }
        return totalFats;
    }

    public double getTotalProteins() {
        double totalProteins = 0;
        for (Ingredient ingredient : ingredients) {
            totalProteins += ingredient.getProtein();
        }
        return totalProteins;
    }

    public double getTotalCarbs() {
        double totalCarbs = 0;
        for (Ingredient ingredient : ingredients) {
            totalCarbs += ingredient.getCarbs();
        }
        return totalCarbs;
    }
}
