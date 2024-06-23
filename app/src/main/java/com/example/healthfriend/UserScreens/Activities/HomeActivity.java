package com.example.healthfriend.UserScreens.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthfriend.Models.DailyResetReceiver;
import com.example.healthfriend.UserScreens.Fragments.profile.presentation.ProfileFragment;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.healthfriend.UserScreens.Fragments.HomeFragment;
import com.example.healthfriend.R;

import com.example.healthfriend.UserScreens.Fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.Calendar;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    HomeFragment homeFragment = new HomeFragment();
    ;
    ProfileFragment profileFragment = new ProfileFragment();
    SettingsFragment settingsFragment = new SettingsFragment();
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(this, DailyResetReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        calendar.add(Calendar.DAY_OF_MONTH, 1); // ابدأ من منتصف الليل التالي

        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        frameLayout = findViewById(R.id.home_frame_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, homeFragment).commit();
        bottomNavigationView = findViewById(R.id.home_nav_bar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                int itemId = item.getItemId();
                if (itemId == R.id.home_nav_item) {
                    transaction.replace(R.id.home_frame_layout, homeFragment).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.profile_nav_item) {
                    transaction.replace(R.id.home_frame_layout, profileFragment).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.settings_nav_item) {
                    transaction.replace(R.id.home_frame_layout, settingsFragment).addToBackStack(null).commit();
                    return true;
                } else {
                    transaction.replace(R.id.home_frame_layout, homeFragment).addToBackStack(null).commit();
                }
                return false;

            }
        });


    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // Find the selected item based on the fragment shown
        MenuItem selectedItem = null;
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.home_frame_layout);
        if (currentFragment instanceof HomeFragment) {
            selectedItem = bottomNavigationView.getMenu().findItem(R.id.home_nav_item);
        } else if (currentFragment instanceof ProfileFragment) {
            selectedItem = bottomNavigationView.getMenu().findItem(R.id.profile_nav_item);
        } else if (currentFragment instanceof SettingsFragment) {
            selectedItem = bottomNavigationView.getMenu().findItem(R.id.settings_nav_item);
        }

        // Set the selected item on the BottomNavigationView
        if (selectedItem != null) {
            selectedItem.setChecked(true);
        }
    }

}