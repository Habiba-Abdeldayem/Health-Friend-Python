package com.example.healthfriend.UserScreens.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.healthfriend.DoctorScreens.Doctor;
import com.example.healthfriend.DoctorScreens.Doctor_Main;
import com.example.healthfriend.SplashScreen;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements FireStoreManager.userTypeCallBack{
    FireStoreManager fireStoreManager = new FireStoreManager();
    TextView joinText;
    boolean is_doctor = false;

    ImageView google_btn;
    EditText email, pass;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+";
    private AppCompatButton button;
    boolean isLoggedIn;
    boolean isDoctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        fireStoreManager.setUserTypeCallBack(this);  // Set the callback here



        button = findViewById(R.id.login_btn);
        google_btn = findViewById(R.id.login_google);
        email = findViewById(R.id.login_email);
        joinText = findViewById(R.id.join_text);
        pass = findViewById(R.id.login_pass);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        progressDialog = new ProgressDialog(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                startActivity(intent);
                performLogin();
            }
        });

        joinText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
        google_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//        Intent intent=new Intent(LoginActivity.this,GoogleSignActivity.class);
//        startActivity(intent);
            }
        });
    }

    private void performLogin() {
        IndividualUser.getInstance().setEmail(email.getText().toString());
        Log.d("usdoctor", "firestore is doctor before? " + Boolean.toString(Doctor.getInstance().getName() != null));

        fireStoreManager.getUserPersonalInfo(email.getText().toString());
        Log.d("usdoctor", "firestore is doctor after? " + Boolean.toString(Doctor.getInstance().getName() != null));


        if(Doctor.getInstance().getName() != null){
            is_doctor = true;
        }
        else{
            is_doctor = false;
        }

        String EMAIL = email.getText().toString();
        String PASS = pass.getText().toString();

        if (!EMAIL.matches(emailPattern)) {
            email.setError("please Enter a valid email");
        } else if (PASS.isEmpty()) {
            pass.setError("please Enter a Password");
        } else {
            progressDialog.setMessage("logging in..");
            progressDialog.setTitle("Login");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.signInWithEmailAndPassword(EMAIL, PASS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login succeeded", Toast.LENGTH_SHORT).show();
                        SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putBoolean("is_logged_in", true);
                        editor.putBoolean("is_doctor", Doctor.getInstance().getName() != null);
                        editor.putString("user_email", EMAIL); // Save user email or ID if needed
                        Log.d("kkgkg", "vv "+isDoctor);

                        editor.apply();
                        if( Doctor.getInstance().getName() == null){
                            sendUserToAnotherActivity();
                        }else{
                            sendDoctorToAnotherActivity();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void sendUserToAnotherActivity() {
        Intent intent = new Intent(LoginActivity.this, SplashScreen.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void sendDoctorToAnotherActivity() {
        Intent intent = new Intent(LoginActivity.this, Doctor_Main.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onCallback() {
        Log.d("dooTAG","iaaam callfrback impl");
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        boolean isDoctor = Doctor.getInstance().getName() != null;
        editor.putBoolean("is_doctor", isDoctor);
        editor.apply();

        if (isDoctor) {
            sendDoctorToAnotherActivity();
        } else {
            sendUserToAnotherActivity();
        }
    }
}