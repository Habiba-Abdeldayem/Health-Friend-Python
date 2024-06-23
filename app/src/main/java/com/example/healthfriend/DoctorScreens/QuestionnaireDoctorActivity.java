package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.User;

public class QuestionnaireDoctorActivity extends AppCompatActivity {
    EditText name,age;
    Button Confirm;
    User currentUser;
    private FireStoreManager fireStoreManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questionnaire_doctor);
        currentUser = User.getInstance();
        name=findViewById(R.id.doc_name);
        age=findViewById(R.id.doc_age);
        Confirm=findViewById(R.id.confirm);


    }
}