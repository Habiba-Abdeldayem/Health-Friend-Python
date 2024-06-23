package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.example.healthfriend.R;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.ArrayList;
import java.util.List;

public class FollowingActivity extends AppCompatActivity {

    private RecyclerView recyclerViewDoctors;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;
    private List<Doctor> selectedDoctors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_following);

        EditText searchEditText = findViewById(R.id.searchEditText);
        recyclerViewDoctors = findViewById(R.id.doctorRecyclerView);

        doctorList = getDoctors();
        selectedDoctors = new ArrayList<>();

        doctorAdapter = new DoctorAdapter(doctorList, new DoctorAdapter.OnDoctorListener() {
            //fakis
            @Override
            public void onDoctorClick(int position) {
                Doctor selectedDoctor = doctorList.get(position);
                if (selectedDoctors.contains(selectedDoctor)) {
                    selectedDoctors.remove(selectedDoctor);
                    Toast.makeText(FollowingActivity.this, "Unfollowed: " + selectedDoctor.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    selectedDoctors.add(selectedDoctor);
                    Toast.makeText(FollowingActivity.this, "Followed: " + selectedDoctor.getName(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public boolean isFollowing(Doctor doctor) {
                return selectedDoctors.contains(doctor);
            }
        });

        recyclerViewDoctors.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewDoctors.setAdapter(doctorAdapter);

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Do nothing
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                doctorAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Do nothing
            }
        });
    }

    private List<Doctor> getDoctors() {
        // This should be replaced with actual data retrieval logic
        List<Doctor> doctors = new ArrayList<>();
        doctors.add(new Doctor("Dr. Smith", "Cardiologist"));
        doctors.add(new Doctor("Dr. Johnson", "Dermatologist"));
        doctors.add(new Doctor("Dr. Williams", "Neurologist"));
        return doctors;
    }
}