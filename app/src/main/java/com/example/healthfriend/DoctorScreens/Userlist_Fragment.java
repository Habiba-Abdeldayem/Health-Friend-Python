package com.example.healthfriend.DoctorScreens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.DailyPlan;
import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.Meal;
import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;

import java.util.ArrayList;
import java.util.Arrays;

public class Userlist_Fragment extends Fragment implements UserList.OnItemClickListener,UserList.OnItemLongClickListener{

    private ArrayList<String> usersEmail;
    private RecyclerView recyclerView;
    private UserList adapter;
    IndividualUser individualUser = IndividualUser.getInstance();

    public Userlist_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_userlist_, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        usersEmail = new ArrayList<>();

        FireStoreManager firestoreHelper = new FireStoreManager();

        recyclerView = view.findViewById(R.id.rv_userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        firestoreHelper.getPatientEmails("doctor1@gmail.com", new FireStoreManager.PatientFirestoreCallback() {
            @Override
            public void onCallback(ArrayList<String> patientEmails) {
                if (patientEmails != null) {
                    usersEmail.addAll(patientEmails);
                    Log.d("alaaa", "size after callback: " + usersEmail.size());
                    adapter = new UserList(usersEmail,Userlist_Fragment.this::onItemClick);
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        Log.e("lkl", "onViewCreated end");
    }

    @Override
    public void onItemLongClick(User user) {

    }

    @Override
    public void onItemClick(IndividualUser user) {
        Intent intent = new Intent(getContext(), DaysActivity.class);
        startActivity(intent);

    }
}
