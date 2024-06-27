package com.example.healthfriend.UserScreens.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.healthfriend.DoctorScreens.Doctor;
import com.example.healthfriend.DoctorScreens.DoctorAdapter;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class SearchADoctorFragment extends Fragment {

    private FireStoreManager fireStoreManager = new FireStoreManager();
    private FireStoreManager.userTypeCallBack userTypeCallBack;
    private IndividualUser individualUser = IndividualUser.getInstance();
    private RecyclerView recyclerViewDoctors;
    private DoctorAdapter doctorAdapter;
    private List<Doctor> doctorList;
    private List<Doctor> selectedDoctors;

    public SearchADoctorFragment() {
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
        return inflater.inflate(R.layout.fragment_search_a_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditText searchEditText = view.findViewById(R.id.searchEditText);
        recyclerViewDoctors = view.findViewById(R.id.doctorRecyclerView);

        doctorList = new ArrayList<>();
        selectedDoctors = new ArrayList<>();
        // Retrieve doctors when the fragment is created
        retrieveDoctors();
        doctorAdapter = new DoctorAdapter(doctorList, new DoctorAdapter.OnDoctorListener() {
            @Override
            public void onDoctorClick(int position) {
                Doctor selectedDoctor = doctorList.get(position);
                if (selectedDoctors.contains(selectedDoctor)) {
                    selectedDoctors.remove(selectedDoctor);
                    individualUser.setCurrentDoctor(null);
                    individualUser.setDoctorEmailConnectedWith(null);
                    fireStoreManager.removePatientEmail(selectedDoctor.getEmail(), individualUser.getEmail());
                    Toast.makeText(getContext(), "Unfollowed: " + selectedDoctor.getName(), Toast.LENGTH_SHORT).show();
                } else {
                    if (selectedDoctors.size() == 0) {
                        selectedDoctors.add(selectedDoctor);
                        fireStoreManager.addPatientEmail(selectedDoctor.getEmail(), individualUser.getEmail(), individualUser.getName());
                        individualUser.setDoctorEmailConnectedWith(selectedDoctor.getEmail());
                        individualUser.setCurrentDoctor(selectedDoctor);
                        fireStoreManager.setUserPersonalInfo(individualUser);
                        Toast.makeText(getContext(), "Followed: " + selectedDoctor.getName(), Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getContext(), "You can only follow one doctor at a time " , Toast.LENGTH_SHORT).show();

                    }
                }
            }

            @Override
            public boolean isFollowing(Doctor doctor) {
                return selectedDoctors.contains(doctor);
            }
        });

        recyclerViewDoctors.setLayoutManager(new LinearLayoutManager(getContext()));
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