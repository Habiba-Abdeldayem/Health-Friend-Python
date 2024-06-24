package com.example.healthfriend.Models;

public class DailyPlan {
    private Meal breakfast;
    private Meal lunch;
    private Meal dinner;

    // Default constructor
    public DailyPlan() {}

    public DailyPlan(Meal breakfast, Meal lunch, Meal dinner) {
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    // Getters and Setters
    public Meal getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(Meal breakfast) {
        this.breakfast = breakfast;
    }

    public Meal getLunch() {
        return lunch;
    }

    public void setLunch(Meal lunch) {
        this.lunch = lunch;
    }

    public Meal getDinner() {
        return dinner;
    }

    public void setDinner(Meal dinner) {
        this.dinner = dinner;
    }

    // Helper method to safely get total from a meal
    private double safeGetTotalCalories(Meal meal) {
        return meal != null ? meal.getTotalCalories() : 0.0;
    }

    private double safeGetTotalFats(Meal meal) {
        return meal != null ? meal.getTotalFats() : 0.0;
    }

    private double safeGetTotalProteins(Meal meal) {
        return meal != null ? meal.getTotalProteins() : 0.0;
    }

    private double safeGetTotalCarbs(Meal meal) {
        return meal != null ? meal.getTotalCarbs() : 0.0;
    }

    public double getTotalCalories() {
        return safeGetTotalCalories(breakfast) + safeGetTotalCalories(lunch) + safeGetTotalCalories(dinner);
    }

    public double getTotalFats() {
        return safeGetTotalFats(breakfast) + safeGetTotalFats(lunch) + safeGetTotalFats(dinner);
    }

    public double getTotalProteins() {
        return safeGetTotalProteins(breakfast) + safeGetTotalProteins(lunch) + safeGetTotalProteins(dinner);
    }

    public double getTotalCarbs() {
        return safeGetTotalCarbs(breakfast) + safeGetTotalCarbs(lunch) + safeGetTotalCarbs(dinner);
    }

    public Meal isItBreakfastLunchDinner(int position) {
        if (position > 0 && position <= 3) {
            switch (position) {
                case 1:
                    return breakfast;
                case 2:
                    return lunch;
                case 3:
                    return dinner;
            }
        }
        return breakfast;
    }

    public void updateMeal(int position, Meal updatedMeal) {
        if (position > 0 && position <= 3) {
            switch (position) {
                case 1:
                    breakfast = updatedMeal;
                    break;
                case 2:
                    lunch = updatedMeal;
                    break;
                case 3:
                    dinner = updatedMeal;
                    break;
            }
        }
    }
}
