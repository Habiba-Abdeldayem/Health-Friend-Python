package com.example.healthfriend.UserScreens;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.healthfriend.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class UserStatisticsFragment extends Fragment {
    private LineChart lineChart1;
    private LineChart lineChart2;
    private EditText startDateEditText, endDateEditText;
    private Button fetchDataButton;

    public UserStatisticsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lineChart1 = view.findViewById(R.id.lineChart1);
        lineChart2 = view.findViewById(R.id.lineChart2);
        startDateEditText = view.findViewById(R.id.startDateEditText);
        endDateEditText = view.findViewById(R.id.endDateEditText);
        fetchDataButton = view.findViewById(R.id.fetchDataButton);

        setupLineChart(lineChart1);
        setupLineChart(lineChart2);

        startDateEditText.setOnClickListener(v -> showDatePickerDialog(startDateEditText));
        endDateEditText.setOnClickListener(v -> showDatePickerDialog(endDateEditText));

        fetchDataButton.setOnClickListener(v -> {
            loadChartData();
        });
    }

    private void showDatePickerDialog(EditText dateEditText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    String date = dayOfMonth + "/" + (month + 1) + "/" + year;
                    dateEditText.setText(date);
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void setupLineChart(LineChart lineChart) {
        lineChart.getDescription().setEnabled(false);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);

        String[] dates = {"20/6", "21/6", "22/6", "23/6", "24/6", "25/6", "26/6", "27/6"};
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void loadChartData() {
        List<Entry> caloriesEntries = getCaloriesEntries();
        List<Entry> carbsEntries = getCarbsEntries();
        List<Entry> proteinEntries = getProteinEntries();
        List<Entry> fatsEntries = getFatsEntries();
        List<Entry> waterEntries = getWaterEntries();

        LineDataSet caloriesDataSet = new LineDataSet(caloriesEntries, "Calories");
        caloriesDataSet.setColor(getResources().getColor(R.color.dark_green));
        caloriesDataSet.setValueTextColor(getResources().getColor(R.color.dark_green));

        LineDataSet carbsDataSet = new LineDataSet(carbsEntries, "Carbs");
        carbsDataSet.setColor(getResources().getColor(R.color.orange_carbs));
        carbsDataSet.setValueTextColor(getResources().getColor(R.color.orange_carbs));

        LineDataSet proteinDataSet = new LineDataSet(proteinEntries, "Protein");
        proteinDataSet.setColor(getResources().getColor(R.color.pink_protein));
        proteinDataSet.setValueTextColor(getResources().getColor(R.color.pink_protein));

        LineDataSet fatsDataSet = new LineDataSet(fatsEntries, "Fats");
        fatsDataSet.setColor(getResources().getColor(R.color.purple_fats));
        fatsDataSet.setValueTextColor(getResources().getColor(R.color.purple_fats));

        LineData lineData1 = new LineData(caloriesDataSet, carbsDataSet, proteinDataSet, fatsDataSet);
        lineChart1.setData(lineData1);
        lineChart1.invalidate(); // Refresh the chart

        LineDataSet waterDataSet = new LineDataSet(waterEntries, "Water");
        waterDataSet.setColor(getResources().getColor(R.color.blue));
        waterDataSet.setValueTextColor(getResources().getColor(R.color.blue));

        LineData lineData2 = new LineData(waterDataSet);
        lineChart2.setData(lineData2);
        lineChart2.invalidate(); // Refresh the chart
    }

    private List<Entry> getCaloriesEntries() {
        // Replace with real data retrieval logic
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 2000)); // 20/6: 2000 calories
        entries.add(new Entry(1, 1800)); // 21/6: 1800 calories
        entries.add(new Entry(2, 2200)); // 22/6: 2200 calories
        entries.add(new Entry(3, 2100)); // 23/6: 2100 calories
        entries.add(new Entry(4, 1900)); // 24/6: 1900 calories
        entries.add(new Entry(5, 2300)); // 25/6: 2300 calories
        entries.add(new Entry(6, 2400)); // 26/6: 2400 calories
        entries.add(new Entry(7, 2000)); // 27/6: 2000 calories
        return entries;
    }

    private List<Entry> getCarbsEntries() {
        // Replace with real data retrieval logic
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 300)); // 20/6: 300 grams of carbs
        entries.add(new Entry(1, 250)); // 21/6: 250 grams of carbs
        entries.add(new Entry(2, 350)); // 22/6: 350 grams of carbs
        entries.add(new Entry(3, 330)); // 23/6: 330 grams of carbs
        entries.add(new Entry(4, 290)); // 24/6: 290 grams of carbs
        entries.add(new Entry(5, 370)); // 25/6: 370 grams of carbs
        entries.add(new Entry(6, 400)); // 26/6: 400 grams of carbs
        entries.add(new Entry(7, 300)); // 27/6: 300 grams of carbs
        return entries;
    }

    private List<Entry> getProteinEntries() {
        // Replace with real data retrieval logic
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 150)); // 20/6: 150 grams of protein
        entries.add(new Entry(1, 130)); // 21/6: 130 grams of protein
        entries.add(new Entry(2, 160)); // 22/6: 160 grams of protein
        entries.add(new Entry(3, 155)); // 23/6: 155 grams of protein
        entries.add(new Entry(4, 140)); // 24/6: 140 grams of protein
        entries.add(new Entry(5, 170)); // 25/6: 170 grams of protein
        entries.add(new Entry(6, 180)); // 26/6: 180 grams of protein
        entries.add(new Entry(7, 150)); // 27/6: 150 grams of protein
        return entries;
    }

    private List<Entry> getFatsEntries() {
        // Replace with real data retrieval logic
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 70)); // 20/6: 70 grams of fats
        entries.add(new Entry(1, 60)); // 21/6: 60 grams of fats
        entries.add(new Entry(2, 80)); // 22/6: 80 grams of fats
        entries.add(new Entry(3, 75)); // 23/6: 75 grams of fats
        entries.add(new Entry(4, 65)); // 24/6  65 grams of fats
        entries.add(new Entry(5, 85)); // 25/6: 85 grams of fats
        entries.add(new Entry(6, 90)); // 26/6: 90 grams of fats
        entries.add(new Entry(7, 70)); // 27/6: 70 grams of fats
        return entries;
    }

    private List<Entry> getWaterEntries() {
        // Replace with real data retrieval logic
        List<Entry> entries = new ArrayList<>();
        entries.add(new Entry(0, 2)); // 20/6: 2 liters of water
        entries.add(new Entry(1, 1.8f)); // 21/6: 1.8 liters of water
        entries.add(new Entry(2, 2.2f)); // 22/6: 2.2 liters of water
        entries.add(new Entry(3, 2.0f)); // 23/6: 2.0 liters of water
        entries.add(new Entry(4, 1.9f)); // 24/6: 1.9 liters of water
        entries.add(new Entry(5, 2.3f)); // 25/6: 2.3 liters of water
        entries.add(new Entry(6, 2.4f)); // 26/6: 2.4 liters of water
        entries.add(new Entry(7, 2.0f)); // 27/6: 2.0 liters of water
        return entries;
    }
}