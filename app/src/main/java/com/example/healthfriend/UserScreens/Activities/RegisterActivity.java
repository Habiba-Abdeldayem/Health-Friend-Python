package com.example.healthfriend.UserScreens.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthfriend.DoctorScreens.QuestionnaireDoctorActivity;
import com.example.healthfriend.UserScreens.User;
import com.example.healthfriend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    TextView loginText;
EditText email,pass,confirmPass;
ProgressDialog progressDialog;
FirebaseAuth mAuth;
FirebaseUser mUser;
String emailPattern="[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    AppCompatButton registerBtn;
    RadioButton doc,user;
    RadioGroup userType;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        FirebaseApp.initializeApp(this);
        doc=findViewById(R.id.doctor_RadioButton);
        user=findViewById(R.id.user_RadioButton);
        userType=findViewById(R.id.userType_radio_group);
        email=findViewById(R.id.reg_email);
        loginText=findViewById(R.id.login_text);
        pass=findViewById(R.id.reg_pass);
        confirmPass=findViewById(R.id.reg_confirmpass);
        registerBtn = findViewById(R.id.reg_btn);
        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();
        loginText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this, LoginActivity.class);
              //  intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
progressDialog=new ProgressDialog(this);
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                startActivity(intent);
                perforAuth();
            }
        });
    }

    private void perforAuth() {
        String EMAIL=email.getText().toString();
        String PASS=pass.getText().toString();
        String CONPASS=confirmPass.getText().toString();
        if(!EMAIL.matches(emailPattern)){
            email.setError("Please enter a valid email");
        } else if (PASS.isEmpty()||PASS.length()<6) {
           pass.setError("Please enter a proper password");
        } else if (!PASS.equals(CONPASS)) {
            confirmPass.setError("passwords don't match");
        }else {
            progressDialog.setMessage("please wait for registration");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            // Attempt to create the user
            mAuth.createUserWithEmailAndPassword(EMAIL, PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        User newUser = User.getInstance();
                        newUser.setEmail(email.getText().toString());
                        Toast.makeText(RegisterActivity.this, "Registration Done", Toast.LENGTH_LONG).show();
                        sendUserToAnotherActivity();
                    } else {
                        if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            // Email already exists
                            Toast.makeText(RegisterActivity.this, "Email is already registered", Toast.LENGTH_LONG).show();
                        } else {
                            // Other errors
                            Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Registration failed", task.getException());
                        }
                    }
                }
            });

//            mAuth.createUserWithEmailAndPassword(EMAIL,PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isComplete()){
//                        progressDialog.dismiss();
//                        User newUser = User.getInstance();
//                        newUser.setEmail(email.getText().toString());
//                        Toast.makeText(RegisterActivity.this,"Registration Done",Toast.LENGTH_LONG).show();
//                        sendUserToAnotherActivity();
//                    }else {
//                        progressDialog.dismiss();
//                        Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
        }
    }
    private void sendUserToAnotherActivity() {
        User newUser = User.getInstance();
        newUser.setEmail(email.getText().toString());
//        QuestionnaireFragment questionnaireFragment = new QuestionnaireFragment();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        transaction.replace(R.id.register_frame, questionnaireFragment).addToBackStack(null).commit();
        userType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // Check which radio button is selected
                if (checkedId == R.id.user_RadioButton) {
                    Intent intent=new Intent(RegisterActivity.this, QuestionnaireAct.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    // Male option selected
                } else if (checkedId == R.id.doctor_RadioButton) {
                    Intent intent=new Intent(RegisterActivity.this, QuestionnaireDoctorActivity.class);
                    intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK|intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });

    }
}