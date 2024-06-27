package com.example.healthfriend.Models;

import java.util.ArrayList;
import java.util.List;

public class DoctorIngredient extends Ingredient{
    private boolean isIngredientSelectedByDoctor; // New boolean field to track the image state

    public DoctorIngredient() {
    }

    public DoctorIngredient(String name, double carbs, double calories, double fats, double protein,int count,String category) {
        super(name,carbs,calories,fats,protein,count,category);
        isIngredientSelectedByDoctor = false;
    }


    public boolean isIngredientSelectedByDoctor() {
        return isIngredientSelectedByDoctor;
    }

    public void setIngredientSelectedByDoctor(boolean ingredientSelectedByDoctor) {
        isIngredientSelectedByDoctor = ingredientSelectedByDoctor;
    }

    public static PythonIngredient convertToPythonIngredient(DoctorIngredient doctorIngredient) {
        if (doctorIngredient == null) {
            return null;
        }

        PythonIngredient pythonIngredient = new PythonIngredient(
                doctorIngredient.getName(),
                doctorIngredient.getCarbs(),
                doctorIngredient.getCalories(),
                doctorIngredient.getFats(),
                doctorIngredient.getProtein(),
                doctorIngredient.getCount(),
                doctorIngredient.getCategory()
        );
        pythonIngredient.setIngredientSelectedByUser(false);

        return pythonIngredient;
    }

    public static List<PythonIngredient> convertToPythonIngredientList(List<DoctorIngredient> doctorIngredients) {
        if (doctorIngredients == null) {
            return null;
        }

        List<PythonIngredient> pythonIngredients = new ArrayList<>();
        for (DoctorIngredient doctorIngredient : doctorIngredients) {
            pythonIngredients.add(convertToPythonIngredient(doctorIngredient));
        }

        return pythonIngredients;
    }}
