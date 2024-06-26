package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class FollowingActivity extends AppCompatActivity {
    private FireStoreManager fireStoreManager = new FireStoreManager();
    private IndividualUser individualUser = IndividualUser.getInstance();
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

        doctorList = new ArrayList<>();
        selectedDoctors = new ArrayList<>();

        doctorAdapter = new DoctorAdapter(doctorList, new DoctorAdapter.OnDoctorListener() {
            @Override
            public void onDoctorClick(int position) {
                Doctor selectedDoctor = doctorList.get(position);
                if (selectedDoctors.contains(selectedDoctor)) {
                    selectedDoctors.remove(selectedDoctor);
                    fireStoreManager.removePatientEmail(selectedDoctor.getEmail(),individualUser.getEmail());
                    Toast.makeText(FollowingActivity.this, "Unfollowed: " + selectedDoctor.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    selectedDoctors.add(selectedDoctor);
                    fireStoreManager.addPatientEmail(selectedDoctor.getEmail(), individualUser.getEmail(),individualUser.getName());
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

        // Retrieve doctors when the activity is created
        retrieveDoctors();
    }

    private void retrieveDoctors() {
        FireStoreManager fireStoreManager = new FireStoreManager();
        fireStoreManager.retrieveAllDoctors(new OnCompleteListener<List<Doctor>>() {
            @Override
            public void onComplete(@NonNull Task<List<Doctor>> task) {
                if (task.isSuccessful()) {
                    List<Doctor> doctors = task.getResult();
                    doctorList.clear();
                    doctorList.addAll(doctors);
                    doctorAdapter.updateFullDoctorList(doctors);
                    doctorAdapter.notifyDataSetChanged();
                    for (Doctor doctor : doctors) {
                        Log.d("doctororor", "Doctor: " + doctor.getName());
                    }
                } else {
                    Log.e("doctororor", "Error retrieving doctor list", task.getException());
                }
            }
        });
    }
}
