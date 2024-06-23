package com.example.healthfriend.Models;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Calendar;

public class DailyDataManager {
    private SharedPreferences sharedPreferences;
    private Context context;
    private static final String PREFERENCES_NAME = "DailyData";
    private static final String LAST_UPDATE_KEY = "lastUpdate";
    private static final String DAILY_DATA_KEY = "dailyData";

    public DailyDataManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public void saveDailyData(String data) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(DAILY_DATA_KEY, data);
        editor.putLong(LAST_UPDATE_KEY, System.currentTimeMillis());
        editor.apply();
    }

    public String getDailyData() {
        return sharedPreferences.getString(DAILY_DATA_KEY, "");
    }

    public boolean isNewDay() {
        long lastUpdate = sharedPreferences.getLong(LAST_UPDATE_KEY, 0);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastUpdate);
        int lastDay = calendar.get(Calendar.DAY_OF_YEAR);
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        return lastDay != currentDay;
    }
}

