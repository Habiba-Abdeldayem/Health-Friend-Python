package com.example.healthfriend.DoctorScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthfriend.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MealsActivity extends AppCompatActivity {
    private Button breakfastButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meals);
        breakfastButton = findViewById(R.id.breakfastButton);
        //categoryListView = findViewById(R.id.categoryListView); // Ensure you have this ListView in your layout

        breakfastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Set<String> categoriesSet = CSVParser.getCategories(getResources().openRawResource(R.raw.breakfast));
                List<String> categoriesList = new ArrayList<>(categoriesSet);
                Toast.makeText(MealsActivity.this, "This is my Toast message!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MealsActivity.this, CategoryActivity.class);
                intent.putStringArrayListExtra("categories", (ArrayList<String>) categoriesList);

                startActivity(intent);
                // Assuming you have a custom adapter for ListView, set it here
                //categoryAdapter = new CategoryAdapter(MealsActivity.this, categoriesList);
               // categoryListView.setAdapter(categoryAdapter);
            }
        });

    }
}