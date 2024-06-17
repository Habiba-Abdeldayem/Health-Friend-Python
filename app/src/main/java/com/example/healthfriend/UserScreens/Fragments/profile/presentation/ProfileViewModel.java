package com.example.healthfriend.UserScreens.Fragments.profile.presentation;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.healthfriend.UserScreens.Fragments.profile.data.FireStoreManager;
import com.example.healthfriend.UserScreens.Fragments.profile.data.ProfileUser;
import com.example.healthfriend.UserScreens.Fragments.profile.domain.EditUserCallback;
import com.example.healthfriend.UserScreens.Fragments.profile.domain.GetUserCallback;
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

    private final MutableLiveData<String> _target = new MutableLiveData<>();
    public LiveData<String> target = _target;

    private final MutableLiveData<String> _calories = new MutableLiveData<>();
    public LiveData<String> calories = _calories;

    private FireStoreManager manager;




    public void initializeManager(FirebaseFirestore db) {
        this.manager = new FireStoreManager(db);
    }

    public void getProfileData() {
        manager.getUser(new GetUserCallback() {
            @Override
            public void onSuccess(ProfileUser user) {
                if (user != null) {  // Check for null user object
                    _height.postValue(user.getHeight());
                    _weight.postValue(user.getWeight());  // Use appropriate getters
                    _age.postValue(user.getAge());
                    _gender.postValue(user.getGender());
                    _target.postValue(user.getTarget());
                    _calories.postValue(user.getCalories());
                }
            }

            @Override
            public void onError(String error) {
                // Implement proper error handling (e.g., logging, notifying UI)
            }
        });
    }

    public void editWeight(String value, EditUserCallback callback) {
        manager.editWeight(value, callback);
    }

    public void editHeight(String value, EditUserCallback callback) {
        manager.editHeight(value, callback);
    }
    public void editAge(String value, EditUserCallback callback) {
        manager.editAge(value, callback);
    }
    public void editGender(String value, EditUserCallback callback) {
        manager.editGender(value, callback);
    }
    public void editTarget(String value, EditUserCallback callback) {
        manager.editTarget(value, callback);
    }
    public void editCalories(String value, EditUserCallback callback) {
        manager.editCalories(value, callback);
    }
}
