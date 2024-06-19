package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class IngredientsActivity extends AppCompatActivity {
IngredientAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ingredients);
        String categoryName = getIntent().getStringExtra("category_name");
        Set<String> ingredientsSet = CSVParser.getingraidiant(getResources().openRawResource(R.raw.breakfast),categoryName);
        ArrayList<String> ingredientsList= new ArrayList<>(ingredientsSet);
        //Toast.makeText(CategoryActivity.this, categories.size(),Toast.LENGTH_LONG).show();
        RecyclerView recyclerView =findViewById(R.id.ingredient_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new IngredientAdapter ((Context) this, ingredientsList);
        recyclerView.setAdapter(adapter);
    }
}