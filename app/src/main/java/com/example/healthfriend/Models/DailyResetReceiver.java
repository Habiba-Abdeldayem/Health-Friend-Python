package com.example.healthfriend.Models;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class DailyResetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DailyDataManager dataManager = new DailyDataManager(context);
        if (dataManager.isNewDay()) {
            String oldData = dataManager.getDailyData();
            // احفظ البيانات القديمة في مكان آخر إذا لزم الأمر
            dataManager.saveDailyData(""); // أعد تعيين البيانات اليومية
        }
    }
}

