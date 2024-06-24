package com.example.healthfriend.UserScreens.Fragments.profile.data;

import com.example.healthfriend.UserScreens.Fragments.profile.domain.EditUserCallback;
import com.example.healthfriend.UserScreens.Fragments.profile.domain.GetUserCallback;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.firebase.firestore.FirebaseFirestore;


public class ProfileFireStoreManager {
    private FirebaseFirestore db;
    private IndividualUser individualUser = IndividualUser.getInstance();
    public ProfileFireStoreManager(FirebaseFirestore db) {
        this.db = db;
    }

    public void getUser(final GetUserCallback callback) {  // Change return type to void
                            callback.onSuccess(individualUser);  // Notify success

//        db.collection("/Users/")
//                .document(User.getInstance().getEmail())
//                .collection("/personal_info").document("/data")
//                .get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        DocumentSnapshot doc = task.getResult();
//                        if (doc != null) {
//                            ProfileUser user = new ProfileUser(
//                                    doc.getString("weight"),
//                                    doc.getString("height"),
//                                    doc.getString("gender"),
//                                    doc.getString("age"),
//                                    doc.getString("target"),
//                                    doc.getString("calories"));
//                            callback.onSuccess(user);  // Notify success
//                        } else {
//                            callback.onError("No document found");  // Handle missing document
//                        }
//                    } else {
//                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());  // Handle task failure
//                    }
//                });
    }

    public void editWeight(String value, final EditUserCallback callback) {
        individualUser.setWeight(Double.valueOf(value));
        individualUser.updateCalculations();

//        db.collection("profile")
//                .document("pY7jkNH0LxVkqH2BzeHn")
//                .update("weight", value)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        callback.onSuccess();
//                    } else {
//                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());
//                    }
//                });
    }

    public void editHeight(String value, final EditUserCallback callback) {
        individualUser.setHeight(Double.valueOf(value));
        individualUser.updateCalculations();

//        db.collection("profile")
//                .document("pY7jkNH0LxVkqH2BzeHn")
//                .update("height", value)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        callback.onSuccess();
//                    } else {
//                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());
//                    }
//                });
    }
    public void editAge(String value, final EditUserCallback callback) {
        individualUser.setAge(Integer.valueOf(value));
        individualUser.updateCalculations();
//        db.collection("profile")
//                .document("pY7jkNH0LxVkqH2BzeHn")
//                .update("age", value)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        callback.onSuccess();
//                    } else {
//                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());
//                    }
//                });
    }
    public void editGender(String value, final EditUserCallback callback) {
        individualUser.setGender(value);
        individualUser.updateCalculations();

//        db.collection("profile")
//                .document("pY7jkNH0LxVkqH2BzeHn")
//                .update("gender", value)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        callback.onSuccess();
//                    } else {
//                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());
//                    }
//                });
    }
    public void editTarget(String value, final EditUserCallback callback) {
//        db.collection("profile")
//                .document("pY7jkNH0LxVkqH2BzeHn")
//                .update("target", value)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        callback.onSuccess();
//                    } else {
//                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());
//                    }
//                });
    }
    public void editCalories(String value, final EditUserCallback callback) {
//        db.collection("profile")
//                .document("pY7jkNH0LxVkqH2BzeHn")
//                .update("calories", value)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        callback.onSuccess();
//                    } else {
//                        callback.onError(Objects.requireNonNull(task.getException()).getMessage());
//                    }
//                });
    }
}
