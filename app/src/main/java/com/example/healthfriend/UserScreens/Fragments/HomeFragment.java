package com.example.healthfriend.UserScreens.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthfriend.DoctorScreens.DaysActivity;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Fragments.water.presentation.WaterFragment;
import com.example.healthfriend.UserScreens.TodaysBreakfastSingleton;
import com.example.healthfriend.UserScreens.TodaysDinnerSingleton;
import com.example.healthfriend.UserScreens.TodaysLunchSingleton;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;
import com.example.healthfriend.UserScreens.User;

public class HomeFragment extends Fragment {

    private TextView caloriesLeft;
    TodaysNutrientsEaten todaysNutrientsEaten;
    private double progress;
    public HomeFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TodaysBreakfastSingleton breakfast = TodaysBreakfastSingleton.getInstance();
        TodaysLunchSingleton lunchFragment = TodaysLunchSingleton.getInstance();
        TodaysDinnerSingleton dinnerFragment = TodaysDinnerSingleton.getInstance();
        todaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
        progress = (TodaysNutrientsEaten.getEatenCalories()/User.getInstance().getDaily_calories_need())*100;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        CardView caloriesCV = view.findViewById(R.id.calories_cv);
        CardView waterCV = view.findViewById(R.id.water_cv);
        CardView sleepCV = view.findViewById(R.id.sleep_cv);
        Button deleteMe=view.findViewById(R.id.btn_deleteme);
        deleteMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DaysActivity.class);
                startActivity(intent);
            }
        });

        caloriesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                CaloriesFragment caloriesFragment = new CaloriesFragment();
                ft.replace(R.id.home_frame_layout, caloriesFragment);
                ft.addToBackStack(null); // Add this line to enable back navigation
                ft.commit();
            }
        });

        waterCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                WaterFragment waterFragment = new WaterFragment();
                ft.replace(R.id.home_frame_layout, waterFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        sleepCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SleepFragment sleepFragment = new SleepFragment();
                ft.replace(R.id.home_frame_layout, sleepFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });


        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar progressBar = view.findViewById(R.id.home_progress_bar);
        caloriesLeft = view.findViewById(R.id.tv_homeFragment_calories_left);
        progressBar.setProgress((int)progress);
        Double calories_left_value = User.getInstance().getDaily_calories_need() - TodaysNutrientsEaten.getEatenCalories();
        String calories_left_string = getString(R.string.home_calories_left, calories_left_value);
        caloriesLeft.setText(calories_left_string);
    }
    public void onResume() {
        super.onResume();

        ProgressBar progressBar = requireView().findViewById(R.id.home_progress_bar);
        double progress = (TodaysNutrientsEaten.getEatenCalories() / User.getInstance().getDaily_calories_need()) * 100;
        progressBar.setProgress((int) progress);
        Double calories_left_value = User.getInstance().getDaily_calories_need() - TodaysNutrientsEaten.getEatenCalories();
        String calories_left_string = getString(R.string.home_calories_left, calories_left_value);
        caloriesLeft.setText(calories_left_string);

    }
}