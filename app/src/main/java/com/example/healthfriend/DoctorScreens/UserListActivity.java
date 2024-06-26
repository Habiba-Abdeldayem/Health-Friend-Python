package com.example.healthfriend.DoctorScreens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;

import java.util.ArrayList;

public class UserListActivity extends AppCompatActivity implements UserList.OnItemClickListener, UserList.OnItemLongClickListener {
    private ArrayList<String> usersEmail;
    private RecyclerView recyclerView;
    private UserList adapter;
    IndividualUser individualUser = IndividualUser.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        usersEmail = new ArrayList<>();

        FireStoreManager firestoreHelper = new FireStoreManager();

        recyclerView = findViewById(R.id.rv_userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        firestoreHelper.getPatientEmails(Doctor.getInstance().getEmail(), new FireStoreManager.PatientFirestoreCallback() {
            @Override
            public void onCallback(ArrayList<String> patientEmails) {
                if (patientEmails != null) {
                    usersEmail.addAll(patientEmails);
                    adapter = new UserList(usersEmail, UserListActivity.this::onItemClick);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }


    @Override
    public void onItemLongClick(User user) {

    }

    @Override
    public void onItemClick(IndividualUser user) {
        Intent intent = new Intent(getApplicationContext(), DaysActivity.class);
        startActivity(intent);

    }
}
