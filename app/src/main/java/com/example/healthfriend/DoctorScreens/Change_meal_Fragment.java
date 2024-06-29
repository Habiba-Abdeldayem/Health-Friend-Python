package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.PythonIngredient;
import com.example.healthfriend.Models.UserMeal;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.DayMealManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Change_meal_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public Change_meal_Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Change_MealAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

                 View view =inflater.inflate(R.layout.fragment_change_meal_, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.change_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DayMealManager dayMealManager = DayMealManager.getInstance(getContext());
        List<String> meal_ingredients_names = new ArrayList<>();

        if(dayMealManager.breakfast_alternatives != null){
            for(UserMeal meal: dayMealManager.breakfast_alternatives){
                meal_ingredients_names.add(meal.getIngredientsName());
            }
        }
        adapter = new Change_MealAdapter (getContext(),meal_ingredients_names);
        recyclerView.setAdapter(adapter);

        return  view;
    }
}