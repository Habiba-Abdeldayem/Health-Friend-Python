package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
CategryAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);
        ArrayList<String> categories = getIntent().getStringArrayListExtra("categories");
        RecyclerView recyclerView =findViewById(R.id.categry_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if(categories!=null) {
            adapter = new CategryAdapter(this, categories);
            recyclerView.setAdapter(adapter);
        }

    }
}