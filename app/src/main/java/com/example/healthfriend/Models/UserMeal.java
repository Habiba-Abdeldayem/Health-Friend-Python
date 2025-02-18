package com.example.healthfriend.Models;

import java.util.ArrayList;
import java.util.List;

public class UserMeal {
    double similarity_score;
    List<PythonIngredient> ingredients;

    public double getSimilarity_score() {
        return similarity_score;
    }

    public void setSimilarity_score(double similarity_score) {
        this.similarity_score = similarity_score;
    }

    public List<PythonIngredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<PythonIngredient> ingredients) {
        this.ingredients = ingredients;
    }
    public String getIngredientsName(){
        StringBuilder names = new StringBuilder();
        for(PythonIngredient pythonIngredient:this.ingredients){
            names.append(pythonIngredient.getName()).append(", ");
        }
        return names.toString();
    }
}
