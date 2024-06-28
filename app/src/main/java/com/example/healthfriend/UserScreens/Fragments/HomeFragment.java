package com.example.healthfriend.UserScreens.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthfriend.DoctorScreens.Doctor;
import com.example.healthfriend.DoctorScreens.Userlist_Fragment;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.Fragments.water.presentation.WaterFragment;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.example.healthfriend.UserScreens.TodaysBreakfastSingleton;
import com.example.healthfriend.UserScreens.TodaysDinnerSingleton;
import com.example.healthfriend.UserScreens.TodaysLunchSingleton;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;
import com.example.healthfriend.UserScreens.UserStatisticsFragment;

public class HomeFragment extends Fragment implements FireStoreManager.coolBack {
    private FireStoreManager fireStoreManager;

    private TextView caloriesLeft;
    private TodaysNutrientsEaten todaysNutrientsEaten;
    private double progress;
    private IndividualUser individualUser;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        individualUser = IndividualUser.getInstance();
        fireStoreManager = new FireStoreManager();
        fireStoreManager.setCoolback(this);  // Set the callback here

        todaysNutrientsEaten = TodaysNutrientsEaten.getInstance();
        progress = (TodaysNutrientsEaten.getEatenCalories() / IndividualUser.getInstance().getDaily_calories_need()) * 100;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ProgressBar progressBar = view.findViewById(R.id.home_progress_bar);
        fireStoreManager.getUserPersonalInfo(IndividualUser.getInstance().getEmail());
        Log.d("HomeFragment", "nulll " + Boolean.toString(individualUser.getName().equals("")));

        caloriesLeft = view.findViewById(R.id.tv_homeFragment_calories_left);
        progressBar.setProgress((int) progress);
        Double calories_left_value = IndividualUser.getInstance().getDaily_calories_need() - TodaysNutrientsEaten.getEatenCalories();
        String calories_left_string = getString(R.string.home_calories_left, calories_left_value);
        caloriesLeft.setText(calories_left_string);

        CardView caloriesCV = view.findViewById(R.id.calories_cv);
        CardView waterCV = view.findViewById(R.id.water_cv);
        CardView sleepCV = view.findViewById(R.id.sleep_cv);
        CardView connectWithDoctorCV = view.findViewById(R.id.doctor_cv);
        CardView stats = view.findViewById(R.id.stats_cv);

        caloriesCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                CaloriesFragment caloriesFragment = new CaloriesFragment();
                ft.replace(R.id.home_frame_layout, caloriesFragment);
                ft.addToBackStack(null); // Add this line to enable back navigation
                ft.commit();
            }
        });

        waterCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                WaterFragment waterFragment = new WaterFragment();
                ft.replace(R.id.home_frame_layout, waterFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        sleepCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                SleepFragment sleepFragment = new SleepFragment();
                ft.replace(R.id.home_frame_layout, sleepFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        connectWithDoctorCV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (individualUser.getDoctorEmailConnectedWith() == null) {
                    Log.d("HomeFragment", "nulll" + individualUser.getDoctorEmailConnectedWith());

                    FragmentManager fm = requireActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.replace(R.id.home_frame_layout, new SearchADoctorFragment());
                    ft.addToBackStack(null); // Add this line to enable back navigation
                    ft.commit();
                } else {
                    Log.d("HomeFragment", "Doctor email connected with: " + individualUser.getDoctorEmailConnectedWith());
                    fireStoreManager.retrieveDoctorFromFirestore(individualUser.getDoctorEmailConnectedWith());
                }
            }
        });
        stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fm = requireActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                UserStatisticsFragment userStatisticsFragment = new UserStatisticsFragment();
                ft.replace(R.id.home_frame_layout, userStatisticsFragment);
                ft.addToBackStack(null);
                ft.commit();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ProgressBar progressBar = requireView().findViewById(R.id.home_progress_bar);
        double progress = (TodaysNutrientsEaten.getEatenCalories() / IndividualUser.getInstance().getDaily_calories_need()) * 100;
        progressBar.setProgress((int) progress);
        Double calories_left_value = IndividualUser.getInstance().getDaily_calories_need() - TodaysNutrientsEaten.getEatenCalories();
        String calories_left_string = getString(R.string.home_calories_left, calories_left_value);
        caloriesLeft.setText(calories_left_string);
    }

    @Override
    public void onCallback() {
        if (individualUser.getDoctorEmailConnectedWith() != null && Doctor.getInstance() != null) {
            individualUser.setCurrentDoctor(Doctor.getInstance());
            Log.d("HomeFragment", "Doctor set in IndividualUser: " + Doctor.getInstance().getName());
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.home_frame_layout, new CurrentDoctorFragment());
            ft.addToBackStack(null);
            ft.commit();
        } else {
            Log.e("HomeFragment", "Doctor instance is null or doctor email is null");
        }
    }
}
