package com.example.healthfriend.UserScreens;

import java.util.List;

public class PythonLaunch {
    private List<PythonIngredient> lunchPythonIngredients;
    private TodaysNutrientsEaten launchTodaysNutrientsEaten;
    private static PythonLaunch instance;

    private PythonLaunch() {
        launchTodaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
    }

    public static  PythonLaunch getInstance() {
        if (instance == null) {
            instance = new PythonLaunch();
        }
        return instance;
    }

    public List<PythonIngredient> getLunchPythonIngredients() {
        return lunchPythonIngredients;
    }

    public void setLunchPythonIngredients(List<PythonIngredient> lunchPythonIngredients) {
        this.lunchPythonIngredients = lunchPythonIngredients;
    }
}
