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
import com.google.firebase.firestore.FirebaseFirestore;

public class WaterFragment extends Fragment {
    private int progress = 0;
    private ProgressBar progressBar;
    private TextView textViewProgress;
    private TextView waterGoal;
    private WaterViewModel viewModel;
    private FirebaseFirestore db;
    private Double waterNeeded = 160.0;

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


        progressBar = view.findViewById(R.id.progress_bar);
        textViewProgress = view.findViewById(R.id.text_view_progress);
        waterGoal = view.findViewById(R.id.water_goal);

        AppCompatImageButton buttonIncrease = view.findViewById(R.id.button_incr);
        buttonIncrease.setOnClickListener(v -> {
            if (progress == 2500){
                Toast.makeText(requireContext(), "Invalid action", Toast.LENGTH_SHORT).show();
            } else if(progress > (2500 - 250)) {
                increaseProgress(2500- progress);
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

        viewModel.weight.observe(getViewLifecycleOwner(), w -> {
            waterNeeded = viewModel.calculateWaterInTake(Double.parseDouble(w));
            waterGoal.setText("/ " + 2500);
        });

        viewModel.progress.observe(getViewLifecycleOwner(), p -> {
            progress = p;
            updateProgressBar();
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void updateProgressBar() {
        // Calculate the scaled progress value
        int scaledProgress = (int) ((progress * 100) / 2500); // Scale the progress to a percentage
        progressBar.setProgress(scaledProgress);
        textViewProgress.setText(2500 + "");
    }

    private void increaseProgress(int amount) {
        progress += amount;
        viewModel.setProgress(progress);
    }

    private void decreaseProgress(int amount) {
        progress -= amount;
        viewModel.setProgress(progress);
    }
}

/*
public class MainActivity extends AppCompatActivity {
    private int progr = 0;
    private ProgressBar progressBar;
    private TextView textViewProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progress_bar);
        textViewProgress = findViewById(R.id.text_view_progress);
        updateProgressBar();

        Button buttonIncr = findViewById(R.id.button_incr);
        buttonIncr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increaseProgress(250);
            }
        });

        Button buttonDecr = findViewById(R.id.button_decr);
        buttonDecr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decreaseProgress(250);
            }
        });
    }

    private void updateProgressBar() {
        // Calculate the scaled progress value
        int scaledProgress = (progr * 100) / 2500; // Scale the progress to a percentage
        progressBar.setProgress(scaledProgress);
        textViewProgress.setText(progr + "/2500 ml");
    }

    private void increaseProgress(int amount) {
        progr += amount;
        if (progr > 2500)
            progr = 2500;
        updateProgressBar();
    }

    private void decreaseProgress(int amount) {
        progr -= amount;
        if (progr < 0)
            progr = 0;
        updateProgressBar();
    }
}

 */