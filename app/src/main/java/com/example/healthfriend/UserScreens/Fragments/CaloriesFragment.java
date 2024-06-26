package com.example.healthfriend.UserScreens.Fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.TodaysBreakfastSingleton;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;
import com.example.healthfriend.UserScreens.IndividualUser;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class CaloriesFragment extends Fragment {
    private TextView caloriesLeft, carbsLeft,proteinsLeft,fatsLeft;
    private TextView calendarTextView;
    private final Calendar calendar = Calendar.getInstance();
    TodaysBreakfastSingleton breakfast;
    TodaysNutrientsEaten todaysNutrientsEaten;
    private double progress;
    public CaloriesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        breakfast = TodaysBreakfastSingleton.getInstance();
        todaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
        progress = (TodaysNutrientsEaten.getEatenCalories()/1500.0)*100;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calories, container, false);
        calendarTextView = view.findViewById(R.id.calendarTextView);
        calendarTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        return view;
    }
    private void showDatePicker() {
        new DatePickerDialog(getContext(), dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private final DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    private void updateLabel() {
        String format = "EEEE, dd MMM yyyy"; // Date format including day of the week and year
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        calendarTextView.setText(sdf.format(calendar.getTime()));
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CardView breakfastCv = view.findViewById(R.id.breakfast_cv);
        CardView lunchCv = view.findViewById(R.id.lunch_cv);
        CardView dinnerCv = view.findViewById(R.id.dinner_cv);
        caloriesLeft =view.findViewById(R.id.mealsOverview_calories_tv);
        carbsLeft =view.findViewById(R.id.mealsOverview_carbs_tv);
        proteinsLeft =view.findViewById(R.id.mealsOverview_proteins_tv);
        fatsLeft =view.findViewById(R.id.mealsOverview_fats_tv);
        breakfastCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                BreakfastFragment breakfastFragment = new BreakfastFragment();
                ft.replace(R.id.home_frame_layout, breakfastFragment);
                ft.addToBackStack(null); // Add this line to enable back navigation
                ft.commit();
            }
        });

        lunchCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LunchFragment lunchFragment = new LunchFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, lunchFragment).addToBackStack(null).commit();
            }
        });
        dinnerCv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DinnerFragment dinnerFragment = new DinnerFragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, dinnerFragment).addToBackStack(null).commit();
            }
        });

        ProgressBar progressBar = view.findViewById(R.id.fragment_mealsOverview_progress_bar);
        progressBar.setProgress((int)progress);

    }


    public void onResume() {
        super.onResume();
        updateCaloriesProgress();

    }

    private void updateCaloriesProgress(){
        IndividualUser individualUser = IndividualUser.getInstance();
        ProgressBar caloriesProgressBar = requireView().findViewById(R.id.fragment_mealsOverview_progress_bar);
        double caloriesProgress = (TodaysNutrientsEaten.getEatenCalories() / individualUser.getDaily_calories_need()) * 100;
        caloriesProgressBar.setProgress((int) caloriesProgress);

        double caloriesLeftValue = Math.max(IndividualUser.getInstance().getDaily_calories_need() - TodaysNutrientsEaten.getEatenCalories(), 0);
        double carbsLeftValue = Math.max(individualUser.getDaily_carbs_need() - TodaysNutrientsEaten.getEatenCarbs(), 0);
        double proteinsLeftValue = Math.max(individualUser.getDaily_proteins_need() - TodaysNutrientsEaten.getEatenProteins(), 0);
        double fatsLeftValue = Math.max(individualUser.getDaily_fats_need() - TodaysNutrientsEaten.getEatenFats(), 0);


        String calories_left_string = getString(R.string.home_calories_left, caloriesLeftValue);
        String carbs_left_string = getString(R.string.carbs_left, carbsLeftValue);
        String proteins_left_string = getString(R.string.proteins_left, proteinsLeftValue);
        String fats_left_string = getString(R.string.fats_left, fatsLeftValue);

        caloriesLeft.setText(calories_left_string);
        carbsLeft.setText(carbs_left_string);
        proteinsLeft.setText(proteins_left_string);
        fatsLeft.setText(fats_left_string);

    }
}