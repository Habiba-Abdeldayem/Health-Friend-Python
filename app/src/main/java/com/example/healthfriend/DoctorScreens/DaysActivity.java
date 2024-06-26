package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Arrays;
import java.util.List;

public class DaysActivity extends AppCompatActivity {
    DaysAdapter adapter;
    IndividualUser user = IndividualUser.getInstance();

    private static final List<String> DAY_LIST = Arrays.asList(
            "Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7"
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_days);
        RecyclerView recyclerView = findViewById(R.id.days_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new DaysAdapter(this, DAY_LIST);
        recyclerView.setAdapter(adapter);
        TextView personal_info = findViewById(R.id.tv_patient_info);
        String personalData = getString(R.string.patient_personal_data, user.getEmail(), user.getEmail(), user.getAge(), user.getGender(), user.getHeight(), user.getWeight(), user.getPlan());
        personal_info.setText(personalData);

        FireStoreManager fireStoreManager = new FireStoreManager();
        fireStoreManager.getWeeklyPlan(user.getEmail(), Doctor.getInstance().getEmail(), task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && document!=null) {
                    WeeklyPlan weeklyPlan = document.toObject(WeeklyPlan.class);
                    WeeklyPlanManagerSingleton.getInstance().setWeeklyPlan(weeklyPlan);
//                    Log.d("taaaag", Double.toString(weeklyPlan.getDailyPlans().get(0).getBreakfast().getTotalCalories()));
                    // Use the weeklyPlan object
                } else {
                    System.out.println("No such document!");
                }
            } else {
                System.err.println("Task failed: " + task.getException());
            }
        });
//        fireStoreManager.getWeeklyPlan(user.getEmail());
    }


}