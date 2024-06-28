package com.example.healthfriend.UserScreens.Fragments.water.presentation;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.healthfriend.UserScreens.Fragments.water.data.WaterFirestoreManager;
import com.example.healthfriend.UserScreens.Fragments.water.domain.GetCallback;
import com.example.healthfriend.UserScreens.Fragments.water.domain.SetCallback;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class WaterViewModel extends ViewModel {
    private final MutableLiveData<String> _weight = new MutableLiveData<>();
    public LiveData<String> weight = _weight;

    private final MutableLiveData<Double> _progress = new MutableLiveData<>(0.0);
    public LiveData<Double> progress = _progress;
    private WaterFirestoreManager manager;

    public void initializeManager(FirebaseFirestore db) {
        this.manager = new WaterFirestoreManager(db);
    }


//    public double calculateWaterInTake(double weightInKg) {
//        double milliPerKg = WaterIntakeCalculator.calculateRecommendedWaterIntake(weightInKg);
//        return milliPerKg * weightInKg;
//    }

    public void getWeight() {
        IndividualUser.getInstance().getWeight();
//        manager.getWeight(new GetCallback() {
//            @Override
//            public void onSuccess(String value) {
//                _weight.postValue(value);
//            }
//
//            @Override
//            public void onError(String error) {
//                // Todo
//            }
//        });
    }

    public void setProgress(double progress) {
        manager.setProgress(progress, new SetCallback() {
            @Override
            public void onSuccess() {
                _progress.postValue(progress);
            }

            @Override
            public void onError(String error) {
                // Todo
            }
        });
    }

    public void getProgress() {
        manager.getProgress(new GetCallback() {
            @Override
            public void onSuccess(String p) {
                _progress.postValue(Double.valueOf(IndividualUser.getInstance().getWater_progress()));
            }

            @Override
            public void onError(String error) {
                // Todo
            }
        });
    }
}
