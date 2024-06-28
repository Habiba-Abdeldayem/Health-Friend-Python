package com.example.healthfriend.UserScreens.Fragments;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.healthfriend.R;

import java.util.Calendar;
import java.util.Locale;

public class SleepFragment extends Fragment {

    private TextView textTime;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;
    private Button button;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sleep, container, false);

        textTime = view.findViewById(R.id.textTime);
        textView4 = view.findViewById(R.id.textView4);
        textView5 = view.findViewById(R.id.textView5);
        textView6 = view.findViewById(R.id.textView6);
        button = view.findViewById(R.id.button);
        TextView timeBtn = view.findViewById(R.id.textTime);

        timeBtn.setOnClickListener(v -> showTimePicker());

        button.setOnClickListener(v -> calculateWakingUpTimes());

        return view;
    }

    private void showTimePicker() {
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                (view, hourOfDay, minute1) -> {
                    String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                    textTime.setText(selectedTime);
                }, hour, minute, false);

        timePickerDialog.show();
    }

    private void calculateWakingUpTimes() {
        String time = textTime.getText().toString().trim();
        if (time.isEmpty()) {
            Toast.makeText(requireContext(), "Please enter a sleeping time first", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            String[] parts = time.split(":");
            int hour = Integer.parseInt(parts[0]);
            int minute = Integer.parseInt(parts[1]);

            // Calculate waking up times with durations in hours
            double[] durations = {4.5, 6, 7.5}; // durations in hours
            int[] wakingUpHours = new int[durations.length];
            int[] wakingUpMinutes = new int[durations.length];

            for (int i = 0; i < durations.length; i++) {
                double totalHours = hour + durations[i];
                int wakeHour = (int) totalHours;
                double minuteDecimalPart = totalHours - wakeHour; // Get the decimal part for minutes calculation
                int wakeMinute = (int) (minuteDecimalPart * 60) + minute;


                if(wakeMinute>60){
                  wakeHour++;
                }
                wakeHour %= 24;
                wakeMinute %= 60;

                wakingUpHours[i] = wakeHour;
                wakingUpMinutes[i] = wakeMinute;

                // Display the calculated waking up time
                switch (i) {
                    case 0:
                        textView4.setText(String.format(Locale.getDefault(), "%02d:%02d", wakeHour, wakeMinute));
                        break;
                    case 1:
                        textView5.setText(String.format(Locale.getDefault(), "%02d:%02d", wakeHour, wakeMinute));
                        break;
                    case 2:
                        textView6.setText(String.format(Locale.getDefault(), "%02d:%02d", wakeHour, wakeMinute));
                        break;
                }
            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Invalid time format", Toast.LENGTH_SHORT).show();
        }
    }
}
