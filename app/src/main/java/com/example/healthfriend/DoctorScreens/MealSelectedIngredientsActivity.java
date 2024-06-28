package com.example.healthfriend.DoctorScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.healthfriend.Models.DailyPlan;
import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.Meal;
import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MealSelectedIngredientsActivity extends AppCompatActivity {

    private List<Map<String, String>> adapterData = new ArrayList<>();
    private WeeklyPlanManagerSingleton weeklyPlanManagerSingleton = WeeklyPlanManagerSingleton.getInstance();
    AppCompatButton edit_meal;
    int dayIdx = WeeklyPlanManagerSingleton.getInstance().getCurrentDayIdx();
    int mealIdx = WeeklyPlanManagerSingleton.getInstance().getCurrentMealIdx();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_selected_ingredients);

        ListView listView = findViewById(R.id.lv_meal_selected_ingredients);
        TextView no_meals_selected = findViewById(R.id.tv_no_meals_selected);
        edit_meal = findViewById(R.id.edit_meal);
        initializeMealAndIngredients();

        List<DoctorIngredient> ingredientList = null;
        if (weeklyPlanManagerSingleton.getWeeklyPlan().getDailyPlans() != null) {
            ingredientList = weeklyPlanManagerSingleton.getWeeklyPlan().getDailyPlans().get(dayIdx)
                    .isItBreakfastLunchDinner(mealIdx).getIngredients();
        }

        if (ingredientList == null || ingredientList.isEmpty()) {
            no_meals_selected.setVisibility(View.VISIBLE);
            listView.setVisibility(View.INVISIBLE);
        } else {
            no_meals_selected.setVisibility(View.INVISIBLE);
            listView.setVisibility(View.VISIBLE);
            adapterData = prepareSimpleAdapterArray(ingredientList);

            String[] from = {"textViewIngredientName", "textViewIngredientInfo"};
            int[] to = {R.id.details_textViewIngredientName, R.id.details_textViewIngredientInfo};

            SimpleAdapter adapter = new SimpleAdapter(this, adapterData, R.layout.item_meal_selected_ingredient, from, to);
            listView.setAdapter(adapter);
        }

        edit_meal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MealSelectedIngredientsActivity.this, CategoryActivity.class);
                List<String> categoriesList;
                if (getMealCategories() != null) {
                    categoriesList = new ArrayList<>(getMealCategories());
                    intent.putStringArrayListExtra("categories", (ArrayList<String>) categoriesList);
                }
                startActivity(intent);
            }
        });
    }

    private List<Map<String, String>> prepareSimpleAdapterArray(List<DoctorIngredient> ingredientList) {
        for (DoctorIngredient ingredient : ingredientList) {
            Map<String, String> item = new HashMap<>();
            item.put("textViewIngredientName", ingredient.getName());
            String ingredientInfo = getString(R.string.ingredient_info, ingredient.getCalories(), ingredient.getProtein(), ingredient.getCarbs(), ingredient.getFats());
            item.put("textViewIngredientInfo", ingredientInfo);
            adapterData.add(item);
        }
        return adapterData;
    }

    private Set<String> getMealCategories() {
        Set<String> categoriesSet = null;
        int mealType = weeklyPlanManagerSingleton.getCurrentMealIdx(); // 1 breakfast, 2 lunch, 3 dinner
        if (mealType == 1) {
            categoriesSet = CSVParser.getCategories(getResources().openRawResource(R.raw.breakfast));
        } else if (mealType == 2) {
            categoriesSet = CSVParser.getCategories(getResources().openRawResource(R.raw.lunch));
        } else {
            categoriesSet = CSVParser.getCategories(getResources().openRawResource(R.raw.breakfast));
        }
        return categoriesSet;
    }

    private void initializeMealAndIngredients() {
        WeeklyPlan weeklyPlan = WeeklyPlanManagerSingleton.getInstance().getWeeklyPlan();

        if (weeklyPlan == null) {
            weeklyPlan = new WeeklyPlan();
            WeeklyPlanManagerSingleton.getInstance().setWeeklyPlan(weeklyPlan);
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

        Meal currentMeal = dailyPlan.isItBreakfastLunchDinner(WeeklyPlanManagerSingleton.getInstance().getCurrentMealIdx());
        if (currentMeal == null) {
            currentMeal = new Meal();
            dailyPlan.updateMeal(WeeklyPlanManagerSingleton.getInstance().getCurrentMealIdx(), currentMeal);
        }

        List<DoctorIngredient> selectedIngredients = currentMeal.getIngredients();
        if (selectedIngredients == null) {
            selectedIngredients = new ArrayList<>();
            currentMeal.setIngredients(selectedIngredients);
        }
    }
}
