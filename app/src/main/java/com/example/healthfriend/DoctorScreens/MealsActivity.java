package com.example.healthfriend.DoctorScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfriend.Models.DailyPlan;
import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.Meal;
import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.IndividualUser;

import java.util.ArrayList;
import java.util.List;

public class MealsActivity extends AppCompatActivity {
    private Button breakfastButton;
    private Button lunchButton;
    private Button dinnerButton;
    private IndividualUser individualUser = IndividualUser.getInstance();
    private int dayIdx = WeeklyPlanManagerSingleton.getInstance().getCurrentDayIdx();
    private int mealIdx = WeeklyPlanManagerSingleton.getInstance().getCurrentMealIdx();
    private WeeklyPlanManagerSingleton weeklyPlanManagerSingleton = WeeklyPlanManagerSingleton.getInstance();
    private ProgressBar caloriesProgressBar, carbsProgressBar, proteinsProgressBar, fatsProgressBar;
    private TextView textview_calories_progress, textview_carbs_progress, textview_proteins_progress, textview_fats_progress;
    private double totalDayCarbs = 0;
    private double totalDayProteins = 0;
    private double totalDayFats = 0;
    private double totalDayCalories = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meals);
        breakfastButton = findViewById(R.id.breakfastButton);
        lunchButton = findViewById(R.id.lunchButton);
        dinnerButton = findViewById(R.id.dinnerButton);

        caloriesProgressBar = findViewById(R.id.doctor_calories_progressbar);
        carbsProgressBar = findViewById(R.id.doctor_carbs_progressbar);
        proteinsProgressBar = findViewById(R.id.doctor_proteins_progressbar);
        fatsProgressBar = findViewById(R.id.doctor_fats_progressbar);
        textview_calories_progress = findViewById(R.id.doctor_textview_calories_progress);
        textview_carbs_progress = findViewById(R.id.doctor_textview_carbs_progress);
        textview_proteins_progress = findViewById(R.id.doctor_textview_proteins_progress);
        textview_fats_progress = findViewById(R.id.doctor_textview_fats_progress);

        initializeMealAndIngredients();
        updateTotalNutrients();
        updateProgressBars();

        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeeklyPlanManagerSingleton.getInstance().setCurrentMeal(1);
                Intent intent = new Intent(MealsActivity.this, MealSelectedIngredientsActivity.class);
                startActivity(intent);
            }
        });

        lunchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeeklyPlanManagerSingleton.getInstance().setCurrentMeal(2);
                Intent intent = new Intent(MealsActivity.this, MealSelectedIngredientsActivity.class);
                startActivity(intent);
            }
        });

        dinnerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WeeklyPlanManagerSingleton.getInstance().setCurrentMeal(3);
                Intent intent = new Intent(MealsActivity.this, MealSelectedIngredientsActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTotalNutrients();
        updateProgressBars();
    }

    private void updateTotalNutrients() {
        totalDayCarbs = weeklyPlanManagerSingleton.getWeeklyPlan().getDailyPlans().get(dayIdx).getTotalCarbs();
        totalDayProteins = weeklyPlanManagerSingleton.getWeeklyPlan().getDailyPlans().get(dayIdx).getTotalProteins();
        totalDayFats = weeklyPlanManagerSingleton.getWeeklyPlan().getDailyPlans().get(dayIdx).getTotalFats();
        totalDayCalories = weeklyPlanManagerSingleton.getWeeklyPlan().getDailyPlans().get(dayIdx).getTotalCalories();
    }

    private void updateProgressBars() {
        updateCaloriesProgress();
        updateCarbsProgress();
        updateProteinsProgress();
        updateFatsProgress();
    }

    private void updateCaloriesProgress() {
        double caloriesProgress = (totalDayCalories / individualUser.getDaily_calories_need()) * 100;
        caloriesProgressBar.setProgress((int) caloriesProgress);
        String calories_left_string = getString(R.string.calories_progress, totalDayCalories, individualUser.getDaily_calories_need());
        textview_calories_progress.setText(calories_left_string);
    }

    private void updateCarbsProgress() {
        double carbsProgress = (totalDayCarbs / individualUser.getDaily_carbs_need()) * 100;
        carbsProgressBar.setProgress((int) carbsProgress);
        String carbs_left_string = getString(R.string.carbs_progress, totalDayCarbs, individualUser.getDaily_carbs_need());
        textview_carbs_progress.setText(carbs_left_string);
    }

    private void updateProteinsProgress() {
        double proteinsProgress = (totalDayProteins / individualUser.getDaily_proteins_need()) * 100;
        proteinsProgressBar.setProgress((int) proteinsProgress);
        String proteins_left_string = getString(R.string.proteins_progress, totalDayProteins, individualUser.getDaily_proteins_need());
        textview_proteins_progress.setText(proteins_left_string);
    }

    private void updateFatsProgress() {
        double fatsProgress = (totalDayFats / individualUser.getDaily_fats_need()) * 100;
        fatsProgressBar.setProgress((int) fatsProgress);
        String fats_left_string = getString(R.string.fats_progress, totalDayFats, individualUser.getDaily_fats_need());
        textview_fats_progress.setText(fats_left_string);
    }

    private void initializeMealAndIngredients() {
        WeeklyPlanManagerSingleton weeklyPlanManager = WeeklyPlanManagerSingleton.getInstance();
        WeeklyPlan weeklyPlan = weeklyPlanManager.getWeeklyPlan();

        if (weeklyPlan == null) {
            weeklyPlan = new WeeklyPlan();
            weeklyPlanManager.setWeeklyPlan(weeklyPlan);
        }

        List<DailyPlan> dailyPlans = weeklyPlan.getDailyPlans();
        if (dailyPlans == null || dailyPlans.isEmpty()) {
            dailyPlans = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                dailyPlans.add(new DailyPlan());
            }
            weeklyPlan.setDailyPlans(dailyPlans);
        }

        DailyPlan dailyPlan = dailyPlans.get(dayIdx);
        if (dailyPlan == null) {
            dailyPlan = new DailyPlan();
            dailyPlans.set(dayIdx, dailyPlan);
        }

        Meal currentMeal = dailyPlan.isItBreakfastLunchDinner(mealIdx);
        if (currentMeal == null) {
            currentMeal = new Meal();
            dailyPlan.updateMeal(mealIdx, currentMeal);
        }

        List<DoctorIngredient> selectedIngredients = currentMeal.getIngredients();
        if (selectedIngredients == null) {
            selectedIngredients = new ArrayList<>();
            currentMeal.setIngredients(selectedIngredients);
        }
    }
}
