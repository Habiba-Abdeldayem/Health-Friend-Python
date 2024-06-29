package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.PythonIngredient;
import com.example.healthfriend.Models.UserMeal;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.DayMealManager;
import com.example.healthfriend.UserScreens.Fragments.CaloriesFragment;
import com.example.healthfriend.UserScreens.Fragments.LunchFragment;
import com.example.healthfriend.UserScreens.PythonBreakfast;
import com.example.healthfriend.UserScreens.PythonDinner;
import com.example.healthfriend.UserScreens.PythonLunch;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Change_meal_Fragment extends Fragment implements Change_MealAdapter.MealPosition{

    private String mealType;


    public Change_meal_Fragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mealType = getArguments().getString("mealType");
        }
        if (mealType == null) {
            // Set a default value or handle the error as appropriate
            mealType = "dinner";
        }

    }

    Change_MealAdapter adapter;
    DayMealManager dayMealManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_change_meal_, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.change_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
         dayMealManager = DayMealManager.getInstance(getContext());
        List<String> meal_ingredients_names = new ArrayList<>();


        switch (mealType){
            case "breakfast":
                if (dayMealManager.breakfast_alternatives != null) {
                    for (UserMeal meal : dayMealManager.breakfast_alternatives) {
                        meal_ingredients_names.add(meal.getIngredientsName());
                    }
                }
                break;
            case "lunch":
                if (dayMealManager.lunch_alternatives != null) {
                    for (UserMeal meal : dayMealManager.lunch_alternatives) {
                        meal_ingredients_names.add(meal.getIngredientsName());
                    }
                }
                break;
            case "dinner":
                if (dayMealManager.dinner_alternatives != null) {
                    for (UserMeal meal : dayMealManager.dinner_alternatives) {
                        meal_ingredients_names.add(meal.getIngredientsName());
                    }
                }
                break;
        }
        adapter = new Change_MealAdapter(getContext(), meal_ingredients_names);
        adapter.setMealPositionListener(this);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatButton switchMealButton = view.findViewById(R.id.switchMealButton);
        switchMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TodaysNutrientsEaten.setEatenCalories(0.0);
                TodaysNutrientsEaten.setEatenProteins(0.0);
                TodaysNutrientsEaten.setEatenCarbs(0.0);
                TodaysNutrientsEaten.setEatenFats(0.0);
                dayMealManager.updateMealsInFirestore();
                Toast.makeText(getContext(),"Meal changed successfully",Toast.LENGTH_SHORT);
                CaloriesFragment fragment= new CaloriesFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_frame_layout, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    @Override
    public void onClick(int arrayIdx) {
        switch (mealType){
            case "breakfast":
                PythonBreakfast.getInstance().setBreakfastPythonIngredients(dayMealManager.breakfast_alternatives.get(arrayIdx).getIngredients());
                break;
            case "lunch":
                PythonLunch.getInstance().setLunchPythonIngredients(dayMealManager.lunch_alternatives.get(arrayIdx).getIngredients());
                break;
            case "dinner":
                PythonDinner.getInstance().setDinnerPythonIngredients(dayMealManager.dinner_alternatives.get(arrayIdx).getIngredients());
                break;
        }

    }
}