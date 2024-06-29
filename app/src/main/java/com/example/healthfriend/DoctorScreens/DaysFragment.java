package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DaysFragment extends Fragment {
    DaysAdapter adapter;
    IndividualUser user = IndividualUser.getInstance();

    private static final List<String> DAY_LIST = Arrays.asList(
            "Day 1", "Day 2", "Day 3", "Day 4", "Day 5", "Day 6", "Day 7"
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_days, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView recyclerView = view.findViewById(R.id.days_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize adapter and set it to RecyclerView
        adapter = new DaysAdapter(getContext(), DAY_LIST);
        recyclerView.setAdapter(adapter);

        // Get reference to TextView for personal info
        TextView personalInfoTextView = view.findViewById(R.id.tv_patient_info);

        // Fetch user data asynchronously from Firestore
        FireStoreManager fireStoreManager = new FireStoreManager();
        fireStoreManager.getWeeklyPlan(user.getEmail(), Doctor.getInstance().getEmail(), task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    WeeklyPlan weeklyPlan = document.toObject(WeeklyPlan.class);
                    WeeklyPlanManagerSingleton.getInstance().setWeeklyPlan(weeklyPlan);

                    // Update UI with user information
                    String personalData = getString(R.string.patient_personal_data, user.getEmail(), user.getEmail(), user.getAge(), user.getGender(), user.getHeight(), user.getWeight(), user.getPlan());
                    personalInfoTextView.setText(personalData);
                } else {
                    // Handle case where document does not exist
                    Log.d("DaysFragment", "No such document");
                }
            } else {
                // Handle errors during data fetching
                Log.d("DaysFragment", "Failed with: ", task.getException());
            }
        });
    }
}
