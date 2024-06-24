package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;

public class QuestionnaireDoctorActivity extends AppCompatActivity {
    EditText name,age;
    Button Confirm;
    IndividualUser currentIndividualUser;
    private FireStoreManager fireStoreManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questionnaire_doctor);
        currentIndividualUser = IndividualUser.getInstance();
        name=findViewById(R.id.doc_name);
        age=findViewById(R.id.doc_age);
        Confirm=findViewById(R.id.confirm);


    }
}