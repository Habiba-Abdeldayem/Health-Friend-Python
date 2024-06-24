package com.example.healthfriend.DoctorScreens;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Activities.LoginActivity;
import com.example.healthfriend.UserScreens.FireStoreManager;

public class QuestionnaireDoctorActivity extends AppCompatActivity {
    private EditText name,age;
    private Button confirm;
    private Doctor currentDoctor;
    private FireStoreManager fireStoreManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_questionnaire_doctor);
        currentDoctor = Doctor.getInstance();
        name=findViewById(R.id.doc_name);
        age=findViewById(R.id.doc_age);
        confirm =findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameString= name.getText().toString();
                int a= Integer.parseInt(age.getText().toString());

                currentDoctor.setName(nameString);
                currentDoctor.setAge(a);
                FireStoreManager fireStoreManager = new FireStoreManager();
                fireStoreManager.saveDoctorToFirestore(currentDoctor);

                Toast.makeText(getApplicationContext(),"Data saved", Toast.LENGTH_SHORT);
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);



            }
        });




    }
}