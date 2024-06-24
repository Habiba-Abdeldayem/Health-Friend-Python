package com.example.healthfriend.UserScreens;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.healthfriend.DoctorScreens.Doctor;
import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.UserScreens.Adapters.IngredientModel;
import com.example.healthfriend.UserScreens.Adapters.MealModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FireStoreManager {
    private FirebaseFirestore db;
    CollectionReference breakfastCollectionRef;
    CollectionReference lunchCollectionRef;
    CollectionReference dinnerCollectionRef;
    CollectionReference ingredientsCollectionRef;
    DocumentReference userDocumentRef;
    DocumentReference personalInfoDocumentRef;
    private FirestoreCallback callback;

    // Add a method to set the callback
    public void setFirestoreCallback(FirestoreCallback callback) {
        this.callback = callback;
    }

    public FireStoreManager() {
        db = FirebaseFirestore.getInstance();
        ingredientsCollectionRef = db.collection("/Ingredients");
        breakfastCollectionRef = db.collection("/BreakfastMeals");
        lunchCollectionRef = db.collection("/BreakfastMeals");
        dinnerCollectionRef = db.collection("/BreakfastMeals");
    }

    public interface FirestoreCallback {
        void onSuccess(List<MealModel> meals);

        void onSuccessIngredients(List<IngredientModel> ingredients);

        void onFailure(Exception e);
    }

    public void getIngredients(List<Integer> ingredientsId) {
        List<IngredientModel> mealIngredient = new ArrayList<>();

        for (Integer ingredientId : ingredientsId) {
            String ingredientIdString = String.valueOf(ingredientId); // Convert integer ID to string

            // Construct a query to retrieve the document for the current ingredient ID
            ingredientsCollectionRef.document(ingredientIdString)
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot.exists()) {
                                IngredientModel ingredientModel = documentSnapshot.toObject(IngredientModel.class);
                                mealIngredient.add(ingredientModel);
                                callback.onSuccessIngredients(mealIngredient);

                            } else {
                                // Document doesn't exist for the given ingredient ID
                                Log.d("Firestore", "Document does not exist for ingredient ID: " + ingredientIdString);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle any errors that occur during the query
                            Log.e("Firestore", "Error fetching ingredient document for ID: " + ingredientIdString, e);
                        }
                    });
        }


    }

    public void getBreakfasts() {

        breakfastCollectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    List<MealModel> fireStoreBreakfastMeals = new ArrayList<>();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        // Convert each document to MealModel
                        MealModel breakfastMeal = document.toObject(MealModel.class);
                        fireStoreBreakfastMeals.add(breakfastMeal);
                    }
                    // Callback with the list of meals
                    callback.onSuccess(fireStoreBreakfastMeals);
                }
            } else {
                // Callback with the failure
                callback.onFailure(task.getException());
            }
        });
    }

    public void getLunches() {

        lunchCollectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    List<MealModel> fireStoreLunchMeals = new ArrayList<>();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        // Convert each document to MealModel
                        MealModel lunchMeal = document.toObject(MealModel.class);
                        fireStoreLunchMeals.add(lunchMeal);
                    }
                    // Callback with the list of meals
                    callback.onSuccess(fireStoreLunchMeals);
                }
            } else {
                // Callback with the failure
                callback.onFailure(task.getException());
            }
        });
    }

    public void getDinners() {

        dinnerCollectionRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                if (querySnapshot != null) {
                    List<MealModel> fireStoreDinnerMeals = new ArrayList<>();
                    for (QueryDocumentSnapshot document : querySnapshot) {
                        // Convert each document to MealModel
                        MealModel dinnerMeal = document.toObject(MealModel.class);
                        fireStoreDinnerMeals.add(dinnerMeal);
                    }
                    // Callback with the list of meals
                    callback.onSuccess(fireStoreDinnerMeals);
                }
            } else {
                // Callback with the failure
                callback.onFailure(task.getException());
            }
        });
    }

    public void setUserPersonalInfo(IndividualUser u) {
        // Create a Map to represent your data
        Map<String, Object> user_personal_data = new HashMap<>();
        user_personal_data.put("age", u.getAge());
        user_personal_data.put("daily_calories_need", u.getDaily_calories_need());
        user_personal_data.put("daily_water_need", u.getDaily_water_need());
        user_personal_data.put("daily_carbs_need", u.getDaily_carbs_need());
        user_personal_data.put("daily_proteins_need", u.getDaily_proteins_need());
        user_personal_data.put("daily_fats_need", u.getDaily_fats_need());
        user_personal_data.put("gender", u.getGender());
        user_personal_data.put("height", u.getHeight());
        user_personal_data.put("weight", u.getWeight());
        user_personal_data.put("plan", u.getPlan());
        user_personal_data.put("water_progress", u.getWater_progress());

        personalInfoDocumentRef = db.collection("/Users/").document(u.getEmail()).collection("/personal_info").document("/data");

        personalInfoDocumentRef.set(user_personal_data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d("sucessss", "sucessss");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d("falll", e.getMessage());

            }
        });

    }

    public void getUserPersonalInfo(IndividualUser u) {
        userDocumentRef = db.document("/Users/" + u.getEmail() + "/personal_info/data");
        Task<DocumentSnapshot> documentSnapshotTask = userDocumentRef.get();

        documentSnapshotTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        u.fromDocumentSnapshot(document);

                        // Access the document data here
                    } else {
                        // Document doesn't exist
                    }
                } else {
                    // Handle task failure
                    Exception e = task.getException();
                    if (e != null) {
                        Log.e("aaao", "Error fetching document: " + e.getMessage());
                    }
                }
            }
        });

    }

    public void getUserPersonalInfo(String userEmail) {
        userDocumentRef = db.document("/Users/" + userEmail + "/personal_info/data");
        Task<DocumentSnapshot> documentSnapshotTask = userDocumentRef.get();

        documentSnapshotTask.addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        IndividualUser.getInstance().fromDocumentSnapshot(document);

                        // Access the document data here
                    } else {
                        retrieveDoctorFromFirestore(userEmail);
                        // Document doesn't exist
                    }
                } else {
                    // Handle task failure
                    Exception e = task.getException();
                    if (e != null) {
                        Log.e("aaao", "Error fetching document: " + e.getMessage());
                    }
                }
            }
        });

    }


    // Doctor methods
//    public void getPatientEmails(String doctorEmail, final PatientFirestoreCallback callback) {
//        DocumentReference doctorRef = db.collection("DoctorsPlan").document(doctorEmail);
//        doctorRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful() && task.getResult() != null && task.getResult().exists()) {
//                    DocumentSnapshot document = task.getResult();
//                    ArrayList<String> patientEmails = (ArrayList<String>) document.get("patients_email");
//                    if (patientEmails != null) {
//                        callback.onCallback(patientEmails);
//                    } else {
//                        Log.w("FirestoreHelper", "No patient emails found.");
//                    }
//                } else {
//                    Log.w("FirestoreHelper", "Error getting doctor document.", task.getException());
//                }
//            }
//        });
//    }

    public void saveDoctorToFirestore(Doctor doctor) {
        db.collection("Doctors")
                .document(doctor.getEmail())
                .set(doctor)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("TAG", "Doctor data saved successfully");
                        } else {
                            Log.e("TAG", "Error saving doctor data", task.getException());
                        }
                    }
                });
    }
    private void retrieveDoctorFromFirestore(String email) {
        db.collection("Doctors")
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Doctor doctor = document.toObject(Doctor.class);
                                if (doctor != null) {
                                    Doctor.getInstance().setName(doctor.getName());
                                    Log.d("TAG", "Doctor data retrieved successfully: " + doctor.getName());
                                    // Do something with the retrieved doctor data
                                }
                            } else {
                                Log.d("TAG", "No such document");
                            }
                        } else {
                            Log.e("TAG", "Error retrieving doctor data", task.getException());
                        }
                    }
                });
    }


    // Method to retrieve patient emails for a specific doctor
    public void getPatientEmails(String doctorEmail, final PatientFirestoreCallback callback) {
        db.collection("DoctorsPlan").document(doctorEmail).collection("patients_email")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<String> patientEmails = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                patientEmails.add(document.getId());
                            }
                            callback.onCallback(patientEmails);
                        } else {
                            Log.w("FirestoreHelper", "Error getting patient emails.", task.getException());
                        }
                    }
                });
    }    // Save the weekly plan for a patient

    public void saveWeeklyPlan(String patient_email, WeeklyPlan weeklyPlan, Context context) {
//        db.collection("DoctorsPlan")
//                .document("doctor1@gmail.com")
//                .collection("patient1@gmail.com")
//                .document("weekly_plan")
//                .set(weeklyPlan)
        db.collection("DoctorsPlan")
                .document("doctor1@gmail.com")
                .collection(patient_email)
                .document("weekly_plan")
                .set(weeklyPlan)
                .addOnSuccessListener(aVoid -> {
                    // Successfully saved
                    Toast myToast = Toast.makeText(context, "saveeed", Toast.LENGTH_LONG);
                    myToast.show();
                    System.out.println("Weekly plan successfully saved!" + patient_email);
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                    Toast myToast = Toast.makeText(context, "Something went wrong", Toast.LENGTH_LONG);
                    myToast.show();
                    System.err.println("Error saving weekly plan: " + e.getMessage());
                });
    }

    // Retrieve the weekly plan for a patient
    public void getWeeklyPlan(String patient_email, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        db.collection("DoctorsPlan")
                .document("doctor1@gmail.com")
                .collection(patient_email)
                .document("weekly_plan")
                .get()
                .addOnCompleteListener(onCompleteListener);

    }

    public interface PatientFirestoreCallback {
        void onCallback(ArrayList<String> patientEmails);
    }
//    public interface FirestoreCallback {
//        void onCallback(List<String> patientEmails);
//    }
//
//    public interface WeeklyPlanCallback {
//        void onCallback(WeeklyPlan weeklyPlan);
//    }

}
