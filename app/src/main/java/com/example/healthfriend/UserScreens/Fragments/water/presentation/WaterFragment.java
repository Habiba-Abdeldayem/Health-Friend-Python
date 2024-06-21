package com.example.healthfriend.UserScreens.Fragments.water.presentation;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class WaterFragment extends Fragment {
    private int progress = 0;
    private ProgressBar progressBar;
    private TextView textViewProgress;
    private TextView waterGoal;
    private WaterViewModel viewModel;
    private FirebaseFirestore db;
    private Double waterNeeded = User.getInstance().getDaily_water_need();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(WaterViewModel.class);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_water, container, false);


        progressBar = view.findViewById(R.id.water_progress_bar);
        textViewProgress = view.findViewById(R.id.text_view_progress);
        waterGoal = view.findViewById(R.id.water_goal);

        AppCompatImageButton buttonIncrease = view.findViewById(R.id.button_incr);
        buttonIncrease.setOnClickListener(v -> {
            if (progress == waterNeeded){
                Toast.makeText(requireContext(), "Invalid action", Toast.LENGTH_SHORT).show();
            } else if(progress > (waterNeeded - 250)) {
                increaseProgress(waterNeeded- progress);
            } else {
                increaseProgress(250);
                updateProgressBar();
            }
        });

        AppCompatImageButton buttonDecrease = view.findViewById(R.id.button_decr);
        buttonDecrease.setOnClickListener(v -> {
            if (progress == 0) {
                Toast.makeText(requireContext(), "Invalid action", Toast.LENGTH_SHORT).show();
            } else {
                decreaseProgress(Math.min(progress, 250));
                updateProgressBar();

            }

        });


        viewModel.initializeManager(db);
        viewModel.getWeight();
        viewModel.getProgress();

//        viewModel.weight.observe(getViewLifecycleOwner(), w -> {
//            waterNeeded = viewModel.calculateWaterInTake(Double.parseDouble(w));
//            waterGoal.setText("/ " + waterNeeded);
//        });

        viewModel.progress.observe(getViewLifecycleOwner(), p -> {
            progress = p;
            updateProgressBar();
            waterNeeded = User.getInstance().getDaily_water_need();
            waterGoal.setText("/ " + waterNeeded);
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void updateProgressBar() {
        // Calculate the scaled progress value
        int scaledProgress = (int) ((progress * 100) / waterNeeded); // Scale the progress to a percentage
        progressBar.setProgress(scaledProgress);
        textViewProgress.setText(progress + "");
    }

    private void increaseProgress(double amount) {
        progress += amount;
        viewModel.setProgress(progress);
    }

    private void decreaseProgress(double amount) {
        progress -= amount;
        viewModel.setProgress(progress);
    }
}
