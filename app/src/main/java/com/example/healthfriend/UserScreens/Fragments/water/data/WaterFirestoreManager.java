package com.example.healthfriend.UserScreens.Fragments.water.data;

import com.example.healthfriend.UserScreens.Fragments.water.domain.GetCallback;
import com.example.healthfriend.UserScreens.Fragments.water.domain.SetCallback;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.Objects;

public class WaterFirestoreManager {
    private FirebaseFirestore db;
    public WaterFirestoreManager(FirebaseFirestore db) {
        this.db = db;
    }

    public void getWeight(final GetCallback callback) {
        db.collection("profile")
                .document("pY7jkNH0LxVkqH2BzeHn")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc != null) {
                            String weight = doc.getString("weight");
                            callback.onSuccess(weight);  // Notify success
                        } else {
                            callback.onError("Error 404");  // Handle missing document
                        }
                    } else {
                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());  // Handle task failure
                    }
                });

    }

    public void setProgress(int progress, SetCallback callback) {
        db.collection("profile")
                .document("pY7jkNH0LxVkqH2BzeHn")
                .update("progress", progress)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        callback.onSuccess();
                    } else {
                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());  // Handle task failure
                    }
                });
    }

    public void getProgress(GetCallback callback) {
        db.collection("profile")
                .document("pY7jkNH0LxVkqH2BzeHn")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot doc = task.getResult();
                        if (doc != null) {
                            Double progress = doc.getDouble("progress");
                            callback.onSuccess(String.valueOf(Math.round(progress)));  // Notify success
                        } else {
                            callback.onError("Error 404");  // Handle missing document
                        }
                    } else {
                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());  // Handle task failure
                    }
                });
    }
}
