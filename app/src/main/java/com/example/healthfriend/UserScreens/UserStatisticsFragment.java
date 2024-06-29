package com.example.healthfriend.UserScreens;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.healthfriend.Models.DailyData;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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

        fetchDataButton.setOnClickListener(v -> loadChartData());
    }

    private void showDatePickerDialog(EditText dateEditText) {
        Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, year, month, dayOfMonth) -> {
                    String date = String.format("%02d/%02d/%04d", dayOfMonth, month + 1, year);
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

        YAxis leftAxis = lineChart.getAxisLeft();
        leftAxis.setDrawGridLines(false);

        YAxis rightAxis = lineChart.getAxisRight();
        rightAxis.setEnabled(false);
    }

    private void loadChartData() {
        String startDate = startDateEditText.getText().toString();
        String endDate = endDateEditText.getText().toString();

        DayMealManager dayMealManager = DayMealManager.getInstance(getContext());
        dayMealManager.retrieveHistoryFirestore(startDate, endDate, dailyDataList -> {
            Log.d("newstuserdaymeal ","is empty? "+ dailyDataList.isEmpty());

            if (dailyDataList != null && !dailyDataList.isEmpty()) {
                Log.d("newstuserdaymeal ","is empty? after condition "+ dailyDataList.isEmpty());

                List<Entry> caloriesEntries = new ArrayList<>();
                List<Entry> carbsEntries = new ArrayList<>();
                List<Entry> proteinEntries = new ArrayList<>();
                List<Entry> fatsEntries = new ArrayList<>();
                List<Entry> waterEntries = new ArrayList<>();
                String[] dates = new String[dailyDataList.size()];

                for (int i = 0; i < dailyDataList.size(); i++) {
                    DailyData data = dailyDataList.get(i);
                    String rawDate = data.getDate(); // Assuming getDate() method exists in DailyData
                    String formattedDate = rawDate.substring(4, 6) + "/" + rawDate.substring(6, 8);
                    dates[i] = formattedDate;

                    caloriesEntries.add(new Entry(i, data.getEatenCalories().floatValue()));
                    carbsEntries.add(new Entry(i, data.getEatenCarbs().floatValue()));
                    proteinEntries.add(new Entry(i, data.getEatenProteins().floatValue()));
                    fatsEntries.add(new Entry(i, data.getEatenFats().floatValue()));
                    waterEntries.add(new Entry(i, data.getWater_progress().floatValue()));
                }

                updateLineChart(lineChart1, dates, caloriesEntries, carbsEntries, proteinEntries, fatsEntries);
                updateWaterLineChart(lineChart2, dates, waterEntries); // Modified method for lineChart2
            } else {
                // Handle case where no data is retrieved
                Log.d("UserStatisticsFragment", "No data retrieved for the selected date range.");
            }
        });
    }

    private void updateLineChart(LineChart lineChart, String[] dates, List<Entry>... entriesLists) {
        LineData lineData = new LineData();

        int[] colors = {R.color.dark_green, R.color.orange_carbs, R.color.pink_protein, R.color.purple_fats};
        String[] labels = {"Calories", "Carbs", "Protein", "Fats"};

        for (int i = 0; i < entriesLists.length; i++) {
            LineDataSet dataSet = new LineDataSet(entriesLists[i], labels[i]);
            dataSet.setColor(getResources().getColor(colors[i]));
            dataSet.setValueTextColor(getResources().getColor(colors[i]));
            lineData.addDataSet(dataSet);
        }

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh the chart
    }

    private void updateWaterLineChart(LineChart lineChart, String[] dates, List<Entry> waterEntries) {
        LineData lineData = new LineData();

        LineDataSet waterDataSet = new LineDataSet(waterEntries, "Water");
        waterDataSet.setColor(getResources().getColor(R.color.blue));
        waterDataSet.setValueTextColor(getResources().getColor(R.color.blue));
        lineData.addDataSet(waterDataSet);

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setValueFormatter(new IndexAxisValueFormatter(dates));

        lineChart.setData(lineData);
        lineChart.invalidate(); // Refresh the chart
    }
}
