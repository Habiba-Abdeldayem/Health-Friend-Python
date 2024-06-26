package com.example.healthfriend.UserScreens.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.firebase.firestore.DocumentSnapshot;

public class CurrentDoctorFragment extends Fragment {
    private IndividualUser individualUser;
    private FireStoreManager fireStoreManager;
    private TextView doctor_name,doctor_email,doctor_age,doctor_available_plan;
    private AppCompatButton apply_plan_btn,unfollow_doc_btn;
    public CurrentDoctorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_current_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        individualUser = IndividualUser.getInstance();
        fireStoreManager = new FireStoreManager();
        initUI(view);
        unfollow_doc_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                individualUser.unFollowDoctor();
                Toast.makeText(getContext(),"Doctor Unfollowed",Toast.LENGTH_SHORT).show();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, new SearchADoctorFragment()).addToBackStack(null).commit();
            }
        });
    }

    private void initUI(View view){
        doctor_name = view.findViewById(R.id.doctor_name);
        doctor_email = view.findViewById(R.id.doctor_email);
        doctor_available_plan = view.findViewById(R.id.doctor_available_plan);
        doctor_age = view.findViewById(R.id.doctor_age);
        apply_plan_btn = view.findViewById(R.id.apply_plan_btn);
        unfollow_doc_btn = view.findViewById(R.id.unfollow_doc_btn);

        if(individualUser.getCurrentDoctor()!=null){
        doctor_name.setText(getString(R.string.current_doctor_name,individualUser.getCurrentDoctor().getName()));
        doctor_email.setText(getString(R.string.current_doctor_email,individualUser.getCurrentDoctor().getEmail()));
        doctor_age.setText(getString(R.string.current_doctor_age,individualUser.getCurrentDoctor().getAge()));
        getWeeklyPlan();

        boolean hasPlan = (individualUser.getWeeklyPlan() != null && individualUser.getWeeklyPlan().getDailyPlans().size() != 0);
        if(hasPlan){
            doctor_available_plan.setText("There is 1 weekly plan available");
        }else{
            doctor_available_plan.setText("There's no weekly plans available yet");
            apply_plan_btn.setEnabled(false);
            apply_plan_btn.setAlpha(0.3f);
        }

        }
    }

    private void getWeeklyPlan(){
        fireStoreManager.getWeeklyPlan(individualUser.getEmail(), individualUser.getDoctorEmailConnectedWith(), task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists() && document!=null) {
                    WeeklyPlan weeklyPlan = document.toObject(WeeklyPlan.class);
                    individualUser.setWeeklyPlan(weeklyPlan);
//                    WeeklyPlanManagerSingleton.getInstance().setWeeklyPlan(weeklyPlan);
                } else {
                    System.out.println("No such document!");
                }
            } else {
                System.err.println("Task failed: " + task.getException());
            }
        });
    }


}