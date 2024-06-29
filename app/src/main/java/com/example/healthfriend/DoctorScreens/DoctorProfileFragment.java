package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Activities.LoginActivity;
import com.example.healthfriend.UserScreens.IndividualUser;

public class DoctorProfileFragment extends Fragment {
    private TextView doctor_name, doctor_email, doctor_age, logout_tv;
    private Doctor doctor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        doctor = Doctor.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_doctor_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);

        logout_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPref = getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.clear();
                editor.apply();

                // Logout the doctor
                Doctor.getInstance().logout();

                // Check if the Doctor instance is truly null
                if (Doctor.getInstance().getEmail() == null) {
                    Log.d("logoutotot", "Doctor instance is null after logout");
                } else {
                    Log.d("logoutotot", "Doctor instance is NOT null after logout");
                }

                // Navigate to LoginActivity
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initUI(View view) {
        doctor_name = view.findViewById(R.id.doctor_name);
        doctor_email = view.findViewById(R.id.doctor_email);
        doctor_age = view.findViewById(R.id.doctor_age);
        logout_tv = view.findViewById(R.id.logout_tv);

        if (doctor != null) {
            doctor_name.setText(getString(R.string.current_doctor_name, doctor.getName()));
            doctor_email.setText(getString(R.string.current_doctor_email, doctor.getEmail()));
            doctor_age.setText(getString(R.string.current_doctor_age, doctor.getAge()));


        }
    }

}