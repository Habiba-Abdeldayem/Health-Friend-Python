package com.example.healthfriend.Models;

public class IngredientAppearedRefused {
    private String ingredientName;
    private int appearance_count, refuse_count;

    public IngredientAppearedRefused() {
    }

    public IngredientAppearedRefused(String ingredientName, int appearance_count, int refuse_count) {
        this.ingredientName = ingredientName;
        this.appearance_count = appearance_count;
        this.refuse_count = refuse_count;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public int getAppearance_count() {
        return appearance_count;
    }

    public void setAppearance_count(int appearance_count) {
        this.appearance_count = appearance_count;
    }

    public int getRefuse_count() {
        return refuse_count;
    }

    public void setRefuse_count(int refuse_count) {
        this.refuse_count = refuse_count;
    }
}
