package com.example.healthfriend;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import com.example.healthfriend.UserScreens.Activities.LoginActivity;
import com.example.healthfriend.UserScreens.Adapters.IngredientModel;
import com.example.healthfriend.UserScreens.PythonLaunch;
import com.example.healthfriend.UserScreens.TodaysLunchSingleton;

import java.util.List;

public class splachScreenn extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //put here date to load
         //TodaysLunchSingleton lunchSingleton;
        //lunchSingleton = TodaysLunchSingleton.getInstance();
        //List<IngredientModel> todaysIngredient = lunchSingleton.getLunchIngredients();
        //pythonLaunch = PythonLaunch.getInstance();
         new Handler().postDelayed(new Runnable() {
        @Override
        public void run() {
startActivity(new Intent(splachScreenn.this, LoginActivity.class));
finish();
        }
    },8000);
    }
}
