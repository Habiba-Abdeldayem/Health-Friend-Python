package com.example.healthfriend.DoctorScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.health.connect.datatypes.MealType;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.healthfriend.Models.DoctorIngredient;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_selected_ingredients);


        ListView listView = findViewById(R.id.lv_meal_selected_ingredients);
        TextView no_meals_selected = findViewById(R.id.tv_no_meals_selected);
        edit_meal = findViewById(R.id.edit_meal);


        // Assuming you have a list of DoctorIngredient objects
        List<DoctorIngredient> ingredientList = weeklyPlanManagerSingleton.getWeeklyPlan().getDailyPlans().get(weeklyPlanManagerSingleton.getCurrentDayIdx()).getBreakfast().getIngredients();
        if (ingredientList != null) {
            no_meals_selected.setVisibility(View.INVISIBLE);
        } else {
            no_meals_selected.setVisibility(View.VISIBLE);
        }
        // Create a list of maps where each map represents a list item
        adapterData = prepareSimpleAdapterArray(ingredientList);

        // Keys in the maps
        String[] from = {"textViewIngredientName", "textViewIngredientInfo"};

        // IDs of the views in the layout
        int[] to = {R.id.details_textViewIngredientName, R.id.details_textViewIngredientInfo};

        SimpleAdapter adapter = new SimpleAdapter(this, adapterData, R.layout.item_meal_selected_ingredient, from, to);
        listView.setAdapter(adapter);

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
        // Populate the data list with maps using a loop
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
        int mealType = weeklyPlanManagerSingleton.getCurrentMealIdx(); // 1 breakfast, 2 lunch , 3 dinner
        if (mealType == 1) {
            categoriesSet = CSVParser.getCategories(getResources().openRawResource(R.raw.breakfast));
        } else if (mealType == 2) {
            categoriesSet = CSVParser.getCategories(getResources().openRawResource(R.raw.lunch));
        } else {
            categoriesSet = CSVParser.getCategories(getResources().openRawResource(R.raw.breakfast));
        }

        return categoriesSet;

    }
}