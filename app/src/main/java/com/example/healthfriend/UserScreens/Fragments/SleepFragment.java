package com.example.healthfriend.UserScreens.Fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.healthfriend.R;
import java.util.Calendar;


public class SleepFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sleep, container, false);

        // Inflate the layout for this fragment
        final ImageView timeInput = view.findViewById(R.id.timeBtn);
        final TextView textView4 = view.findViewById(R.id.textView4);
        final TextView timeTextView = view.findViewById(R.id.textTime);
        Button button = view.findViewById(R.id.button);

        timeInput.setOnClickListener(v -> {
            // Get current time
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new TimePickerDialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                    (view1, hourOfDay, minute1) -> {
                        // Display selected time in EditText
                        timeTextView.setText(hourOfDay + ":" + minute1);
                    }, hour, minute, false);

            // Show the TimePickerDialog
            timePickerDialog.show();
        });

        button.setOnClickListener(v -> {
            // Get the time from EditText
            String time = timeTextView.getText().toString();
            if (!time.isEmpty()) {
                // Split the time into hour and minute
                String[] parts = time.split(":");
                int hour = Integer.parseInt(parts[0]);
                int minute = Integer.parseInt(parts[1]);

                // Add 6 hours to the time
                hour = (hour + 6) % 24;

                // Display the modified time in textView4
                textView4.setText(hour + ":" + minute);
            } else {
                // Notify the user to enter a time first
                Toast.makeText(requireContext(), "Please enter a time first", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}

/*
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText timeInput = findViewById(R.id.timeInput);
        final TextView textView4 = findViewById(R.id.textView4);
        Button button = findViewById(R.id.button);

        timeInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get current time
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                // Create a new TimePickerDialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(MainActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Display selected time in EditText
                                timeInput.setText(hourOfDay + ":" + minute);
                            }
                        }, hour, minute, false);

                // Show the TimePickerDialog
                timePickerDialog.show();
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the time from EditText
                String time = timeInput.getText().toString();
                if (!time.isEmpty()) {
                    // Split the time into hour and minute
                    String[] parts = time.split(":");
                    int hour = Integer.parseInt(parts[0]);
                    int minute = Integer.parseInt(parts[1]);

                    // Add 6 hours to the time
                    hour = (hour + 6) % 24;

                    // Display the modified time in textView4
                    textView4.setText(hour + ":" + minute);
                } else {
                    // Notify the user to enter a time first
                    Toast.makeText(MainActivity.this, "Please enter a time first", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
 */