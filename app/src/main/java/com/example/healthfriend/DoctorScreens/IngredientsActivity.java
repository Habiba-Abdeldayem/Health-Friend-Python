package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.MealAdapterInterface;

import java.util.ArrayList;
import java.util.Set;

public class IngredientsActivity extends AppCompatActivity implements MealAdapterInterface {
DoctorIngredientAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingredients);
        String categoryName = getIntent().getStringExtra("category_name");
        Set<DoctorIngredient> ingredientsSet = CSVParser.getIngredients(getResources().openRawResource(R.raw.breakfast),categoryName);
        ArrayList<DoctorIngredient> ingredientsList= new ArrayList<>(ingredientsSet);

        RecyclerView recyclerView =findViewById(R.id.ingredient_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DoctorIngredientAdapter((Context) this, ingredientsList,this);
        recyclerView.setAdapter(adapter);

        TextView calories = findViewById(R.id.ingredients_textview_calories_progress);
        double caa = WeeklyPlanManagerSingleton.getInstance().getWeeklyPlan().getDailyPlans().get(0).getBreakfast().getTotalCalories();
        String caaa = Double.toString(caa);
        if(WeeklyPlanManagerSingleton.getInstance().getWeeklyPlan() != null)
            calories.setText(caaa);



//        DoctorIngredient ingredient1 = new DoctorIngredient("cat name",100,11,2,3);
//        DoctorIngredient ingredient2 = new DoctorIngredient("cat name",100,11,2,100);
//
//// Create meals
//        Meal breakfast = new Meal("Breakfast", Arrays.asList(ingredient1, ingredient2));
//
//// Create daily plans
//        DailyPlan day1 = new DailyPlan(breakfast, null, null); // Assuming other meals are null for simplicity
//
//// Create weekly plan
//        WeeklyPlan weeklyPlan = new WeeklyPlan(Arrays.asList(day1, day1, day1, day1, day1, day1, day1)); // Same plan for all 7 days
//
//// Save to Firestore
//        FireStoreManager firestoreManager = new FireStoreManager();
//        firestoreManager.saveWeeklyPlan("patientId123", weeklyPlan);

    }

    @Override
    public void addItem(int position) {

    }

    @Override
    public void removeItem(int position) {

    }
}