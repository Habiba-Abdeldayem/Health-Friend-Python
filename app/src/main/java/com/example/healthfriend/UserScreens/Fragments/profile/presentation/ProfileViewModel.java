package com.example.healthfriend.UserScreens.Fragments.profile.presentation;


import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthfriend.UserScreens.Fragments.profile.data.ProfileFireStoreManager;
import com.example.healthfriend.UserScreens.Fragments.profile.domain.EditUserCallback;
import com.example.healthfriend.UserScreens.Fragments.profile.domain.GetUserCallback;
import com.example.healthfriend.UserScreens.User;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileViewModel extends ViewModel {
    private final MutableLiveData<String> _weight = new MutableLiveData<>();
    public LiveData<String> weight = _weight;

    private final MutableLiveData<String> _height = new MutableLiveData<>();
    public LiveData<String> height = _height;

    private final MutableLiveData<String> _age = new MutableLiveData<>();
    public LiveData<String> age = _age;

    private final MutableLiveData<String> _gender = new MutableLiveData<>();
    public LiveData<String> gender = _gender;

    private final MutableLiveData<String> _plan = new MutableLiveData<>();
    public LiveData<String> plan = _plan;

    private final MutableLiveData<String> _calories = new MutableLiveData<>();
    public LiveData<String> calories = _calories;

    private ProfileFireStoreManager manager;
    private User user = User.getInstance();





    public void initializeManager(FirebaseFirestore db) {
        this.manager = new ProfileFireStoreManager(db);
    }

    public void getProfileData() {
        manager.getUser(new GetUserCallback() {
            @Override
            public void onSuccess(User user) {
                if (user != null) {
                    _height.postValue(Double.toString(user.getHeight()));
                    _weight.postValue(Double.toString(user.getWeight()));
                    _age.postValue(Integer.toString(user.getAge()));
                    _gender.postValue(user.getGender());
                    _plan.postValue(user.getPlan());
                    _calories.postValue(Double.toString(user.getDaily_calories_need()));
                    Log.d("prooo",Double.toString(user.getDaily_calories_need()));

                }
            }


            @Override
            public void onError(String error) {
                Log.e("prooo",error);
                // Implement proper error handling (e.g., logging, notifying UI)
            }
        });
    }

    public void editWeight(String value, EditUserCallback callback) {
        user.setWeight(Double.valueOf(value));
        _weight.postValue(value);
        user.updateCalculations();
        _calories.postValue(Double.toString(user.getDaily_calories_need()));
//        manager.editWeight(value, callback);
    }

    public void editHeight(String value, EditUserCallback callback) {
        user.setHeight(Double.valueOf(value));
        _height.postValue(value);
        user.updateCalculations();
        _calories.postValue(Double.toString(user.getDaily_calories_need()));
//        manager.editHeight(value, callback);
    }
    public void editAge(String value, EditUserCallback callback) {
        user.setAge(Integer.valueOf(value));
        _age.postValue(value);
        user.updateCalculations();
        _calories.postValue(Double.toString(user.getDaily_calories_need()));
//        manager.editAge(value, callback);
    }
    public void editGender(String value, EditUserCallback callback) {
        user.setGender(value);
        _gender.postValue(value);
        user.updateCalculations();
        _calories.postValue(Double.toString(user.getDaily_calories_need()));
//        manager.editGender(value, callback);
    }
    public void editTarget(String value, EditUserCallback callback) {
        user.setPlan(value);
        _plan.postValue(value);
        user.updateCalculations();
        _calories.postValue(Double.toString(user.getDaily_calories_need()));
//        manager.editTarget(value, callback);
    }
    public void editCalories(String value, EditUserCallback callback) {
//        manager.editCalories(value, callback);
    }
}
