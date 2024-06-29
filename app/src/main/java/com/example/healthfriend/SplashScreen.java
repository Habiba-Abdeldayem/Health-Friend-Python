package com.example.healthfriend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfriend.DoctorScreens.Doctor;
import com.example.healthfriend.DoctorScreens.Doctor_Main;
import com.example.healthfriend.UserScreens.Activities.HomeActivity;
import com.example.healthfriend.UserScreens.Activities.LoginActivity;
import com.example.healthfriend.UserScreens.Activities.RegisterActivity;
import com.example.healthfriend.UserScreens.DayMealManager;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;

public class SplashScreen extends AppCompatActivity {
    boolean isLoggedIn;
    boolean isDoctor;
    FireStoreManager fireStoreManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fireStoreManager=new FireStoreManager();
        SharedPreferences sharedPref = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);

        isLoggedIn = sharedPref.getBoolean("is_logged_in", false);
        isDoctor = sharedPref.getBoolean("is_doctor", false);
        if(!isLoggedIn){
            Intent intent = new Intent(this, RegisterActivity.class);
            startActivity(intent);
        }

         new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {

            if (isLoggedIn && !isDoctor) {
                IndividualUser individualUser = IndividualUser.getInstance();
                individualUser.setEmail(sharedPref.getString("user_email", ""));
                fireStoreManager.getUserPersonalInfo(individualUser);
                DayMealManager dayMealManager = DayMealManager.getInstance(getApplicationContext());
                sendUserToAnotherActivity();
            }
            else if(isLoggedIn && isDoctor){
                Doctor doctor = Doctor.getInstance();
                doctor.setEmail(sharedPref.getString("user_email", ""));
                fireStoreManager.getUserPersonalInfo(doctor.getEmail());
                sendDoctorToAnotherActivity();
            }


//startActivity(new Intent(SplashScreen.this, LoginActivity.class));
finish();
        }
    },8000);
    }
    private void sendUserToAnotherActivity() {
        Intent intent = new Intent(SplashScreen.this, HomeActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
    private void sendDoctorToAnotherActivity() {
        Intent intent = new Intent(SplashScreen.this, Doctor_Main.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TASK | intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
