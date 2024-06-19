package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.Arrays;
import java.util.List;

public class DaysActivity extends AppCompatActivity {
DaysAdapter adapter;
    private static final List<String> DAY_LIST = Arrays.asList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_days);
        RecyclerView recyclerView = findViewById(R.id.days_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DaysAdapter (this,DAY_LIST);
        recyclerView.setAdapter(adapter);
    }
}