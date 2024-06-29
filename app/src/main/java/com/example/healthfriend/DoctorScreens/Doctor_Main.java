package com.example.healthfriend.DoctorScreens;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Fragments.HomeFragment;
import com.example.healthfriend.UserScreens.Fragments.SettingsFragment;
import com.example.healthfriend.UserScreens.Fragments.profile.presentation.ProfileFragment;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Doctor_Main extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    FrameLayout frameLayout;
    Userlist_Fragment userlistFragment= new Userlist_Fragment();
     DoctorProfileFragment doctorProfileFragment = new DoctorProfileFragment();
    SettingsFragment settingsFragment = new SettingsFragment();

    Button b1,feed;
    //private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_main);

        frameLayout = findViewById(R.id.doctor_home_frame_layout);
        getSupportFragmentManager().beginTransaction().replace(R.id.doctor_home_frame_layout, userlistFragment).commit();
        bottomNavigationView = findViewById(R.id.doctor_home_nav_bar);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                int itemId = item.getItemId();
                if (itemId == R.id.home_nav_item) {
                    transaction.replace(R.id.doctor_home_frame_layout, userlistFragment).addToBackStack(null).commit();
                    return true;
                } else if (itemId == R.id.profile_nav_item) {
                    transaction.replace(R.id.doctor_home_frame_layout, doctorProfileFragment).addToBackStack(null).commit();
                    return true;
                }  else {
                    transaction.replace(R.id.doctor_home_frame_layout, userlistFragment).addToBackStack(null).commit();
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
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.doctor_home_frame_layout);
        if (currentFragment instanceof HomeFragment) {
            selectedItem = bottomNavigationView.getMenu().findItem(R.id.home_nav_item);
        } else if (currentFragment instanceof DoctorProfileFragment) {
            selectedItem = bottomNavigationView.getMenu().findItem(R.id.profile_nav_item);
        }

        // Set the selected item on the BottomNavigationView
        if (selectedItem != null) {
            selectedItem.setChecked(true);
        }
    }





    private void showAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert Title")
                .setMessage("This is a message for the user.")
                .setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action to be performed when the "Accept" button is clicked
                        // You can put your code here
                        dialog.dismiss(); // Dismiss the dialog
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Action to be performed when the "Cancel" button is clicked
                        // You can put your code here
                        dialog.dismiss(); // Dismiss the dialog
                    }
                });

        // Create and show the AlertDialog
//        AlertDialog alertDialog = builder.create();
        builder.show();
    }

//    @Override
//    public void onItemClick(IndividualUser user) {
//        Intent intent = new Intent(getApplicationContext(), DaysActivity.class);
//        startActivity(intent);
////        intent.putExtra("id",category.getId());
//    }

//    @Override
//    public void onItemLongClick(User category) {
//
//    }
}