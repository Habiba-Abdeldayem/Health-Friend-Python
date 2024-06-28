package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.example.healthfriend.UserScreens.DoctorMealAdapterInterface;

import java.util.ArrayList;
import java.util.Set;

public class IngredientsActivity extends AppCompatActivity implements DoctorMealAdapterInterface {
DoctorIngredientAdapter adapter;
    private ProgressBar caloriesProgressBar, carbsProgressBar, proteinsProgressBar, fatsProgressBar;
    private TextView textview_calories_progress, textview_carbs_progress, textview_proteins_progress, textview_fats_progress;
    Button save_meal;

    private IndividualUser individualUser = IndividualUser.getInstance();
    private WeeklyPlanManagerSingleton weeklyPlanManagerSingleton = WeeklyPlanManagerSingleton.getInstance();
    private int dayIdx = WeeklyPlanManagerSingleton.getInstance().getCurrentDayIdx();
    private int mealIdx = WeeklyPlanManagerSingleton.getInstance().getCurrentMealIdx();

    private double totalDayCarbs = 0;
    private double totalDayProteins = 0;
    private double totalDayFats = 0;
    private double totalDayCalories = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingredients);
        initUI();
        updateTotalNutrients();
        updateProgressBars();

        String categoryName = getIntent().getStringExtra("category_name");
        Set<DoctorIngredient> ingredientsSet = CSVParser.getIngredients(getResources().openRawResource(R.raw.breakfast),categoryName);
        ArrayList<DoctorIngredient> ingredientsList= new ArrayList<>(ingredientsSet);

        RecyclerView recyclerView =findViewById(R.id.ingredient_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DoctorIngredientAdapter((Context) this, ingredientsList,this);
        recyclerView.setAdapter(adapter);


        save_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FireStoreManager firestoreHelper = new FireStoreManager();
                firestoreHelper.saveWeeklyPlan(IndividualUser.getInstance().getEmail(), WeeklyPlanManagerSingleton.getInstance().getWeeklyPlan(),getApplicationContext());
                Intent intent = new Intent(getApplicationContext(), MealsActivity.class);
                startActivity(intent);

            }
        });

    }
private void initUI(){
    caloriesProgressBar = findViewById(R.id.doctor_calories_progressbar);
    save_meal = findViewById(R.id.doctor_save_ingredients);
    carbsProgressBar = findViewById(R.id.doctor_carbs_progressbar);
    proteinsProgressBar = findViewById(R.id.doctor_proteins_progressbar);
    fatsProgressBar = findViewById(R.id.doctor_fats_progressbar);
    textview_calories_progress = findViewById(R.id.doctor_textview_calories_progress);
    textview_carbs_progress = findViewById(R.id.doctor_textview_carbs_progress);
    textview_proteins_progress = findViewById(R.id.doctor_textview_proteins_progress);
    textview_fats_progress = findViewById(R.id.doctor_textview_fats_progress);
}
    @Override
    public void addItem(int position) {
        updateTotalNutrients();
        updateProgressBars();
    }

    @Override
    public void removeItem(int position) {
        updateTotalNutrients();
        updateProgressBars();
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
}