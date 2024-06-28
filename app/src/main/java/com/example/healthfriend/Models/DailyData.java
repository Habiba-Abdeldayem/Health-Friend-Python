package com.example.healthfriend.Models;

import java.util.List;

public class DailyData {
    private List<PythonIngredient> breakfast;
    private List<PythonIngredient> lunch;
    private List<PythonIngredient> dinner;
    private Double eatenCalories;
    private Double eatenCarbs;
    private Double eatenFats;
    private Double eatenProteins;
    private Double water_progress;
    private Double weight;

    // Default constructor for Firestore
    public DailyData() {}

    // Getters and setters
    public List<PythonIngredient> getBreakfast() { return breakfast; }
    public void setBreakfast(List<PythonIngredient> breakfast) { this.breakfast = breakfast; }

    public List<PythonIngredient> getLunch() { return lunch; }
    public void setLunch(List<PythonIngredient> lunch) { this.lunch = lunch; }

    public List<PythonIngredient> getDinner() { return dinner; }
    public void setDinner(List<PythonIngredient> dinner) { this.dinner = dinner; }

    public Double getEatenCalories() { return eatenCalories; }
    public void setEatenCalories(Double eatenCalories) { this.eatenCalories = eatenCalories; }

    public Double getEatenCarbs() { return eatenCarbs; }
    public void setEatenCarbs(Double eatenCarbs) { this.eatenCarbs = eatenCarbs; }

    public Double getEatenFats() { return eatenFats; }
    public void setEatenFats(Double eatenFats) { this.eatenFats = eatenFats; }

    public Double getEatenProteins() { return eatenProteins; }
    public void setEatenProteins(Double eatenProteins) { this.eatenProteins = eatenProteins; }

    public Double getWater_progress() { return water_progress; }
    public void setWater_progress(Double water_progress) { this.water_progress = water_progress; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}
