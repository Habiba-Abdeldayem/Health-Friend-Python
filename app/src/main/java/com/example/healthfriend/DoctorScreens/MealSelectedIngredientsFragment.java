package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.Ingredient;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.MealAdapterInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MealSelectedIngredientsFragment extends Fragment implements MealAdapterInterface {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private List<DoctorIngredient> mealIngredients;

    public MealSelectedIngredientsFragment() {
        // Required empty public constructor
    }

    public static MealSelectedIngredientsFragment newInstance(String param1, String param2) {
        MealSelectedIngredientsFragment fragment = new MealSelectedIngredientsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WeeklyPlanManagerSingleton weeklyPlanManagerSingleton = WeeklyPlanManagerSingleton.getInstance();
        mealIngredients = weeklyPlanManagerSingleton.getWeeklyPlan().getDailyPlans().get(weeklyPlanManagerSingleton.getCurrentDayIdx()).getBreakfast().getIngredients();


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal_selected_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);




    }

    @Override
    public void addItem(int position) {

    }

    @Override
    public void removeItem(int position) {

    }
}