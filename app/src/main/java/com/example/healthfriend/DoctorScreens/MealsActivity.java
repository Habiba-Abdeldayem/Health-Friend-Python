package com.example.healthfriend.DoctorScreens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MealsActivity extends AppCompatActivity {
    private Button breakfastButton;
    private Button lunchButton;
    private Button dinnerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meals);
        breakfastButton = findViewById(R.id.breakfastButton);
        lunchButton = findViewById(R.id.lunchButton);
        dinnerButton = findViewById(R.id.dinnerButton);
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
}