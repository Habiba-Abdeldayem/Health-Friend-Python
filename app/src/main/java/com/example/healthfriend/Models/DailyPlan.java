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
    public double getTotalCalories() {
        return breakfast.getTotalCalories() + lunch.getTotalCalories() + dinner.getTotalCalories();
    }

    public double getTotalFats() {
        return breakfast.getTotalFats() + lunch.getTotalFats() + dinner.getTotalFats();
    }

    public double getTotalProteins() {
        return breakfast.getTotalProteins() + lunch.getTotalProteins() + dinner.getTotalProteins();
    }

    public double getTotalCarbs() {
        return breakfast.getTotalCarbs() + lunch.getTotalCarbs() + dinner.getTotalCarbs();
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
            switch (position){
                case 1:
                    breakfast=updatedMeal;
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
