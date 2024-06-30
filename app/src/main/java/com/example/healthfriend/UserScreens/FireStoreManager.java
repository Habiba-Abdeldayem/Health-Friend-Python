package com.example.healthfriend.UserScreens;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.healthfriend.DoctorScreens.Doctor;
import com.example.healthfriend.Models.IngredientAppearedRefused;
import com.example.healthfriend.Models.WeeklyPlan;
import com.example.healthfriend.UserScreens.Adapters.IngredientModel;
import com.example.healthfriend.UserScreens.Adapters.MealModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Transaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

public class FireStoreManager {
    private FirebaseFirestore db;
    CollectionReference breakfastCollectionRef;
    CollectionReference lunchCollectionRef;
    CollectionReference dinnerCollectionRef;
    CollectionReference ingredientsCollectionRef;
    DocumentReference userDocumentRef;
    DocumentReference personalInfoDocumentRef;
    private FirestoreCallback callback;
    private userTypeCallBack userTypeCallBack;
    private coolBack coolback;

    // Add a method to set the callback
    public void setFirestoreCallback(FirestoreCallback callback) {
        this.callback = callback;
    }

    public void setUserTypeCallBack(FireStoreManager.userTypeCallBack userTypeCallBack) {
        this.userTypeCallBack = userTypeCallBack;
    }

    public void setCoolback(FireStoreManager.coolBack coolback) {
        this.coolback = coolback;
    }

    public FireStoreManager() {
        db = FirebaseFirestore.getInstance();
        ingredientsCollectionRef = db.collection("/Ingredients");
        breakfastCollectionRef = db.collection("/BreakfastMeals");
        lunchCollectionRef = db.collection("/BreakfastMeals");
        dinnerCollectionRef = db.collection("/BreakfastMeals");
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
        // Create a Map to represent data
        Map<String, Object> user_personal_data = new HashMap<>();
//        user_personal_data.put("name", u.getName());
        user_personal_data.put("age", u.getAge());
        user_personal_data.put("daily_calories_need", u.getDaily_calories_need());
        user_personal_data.put("daily_water_need", u.getDaily_water_need());
        user_personal_data.put("daily_carbs_need", u.getDaily_carbs_need());
        user_personal_data.put("daily_proteins_need", u.getDaily_proteins_need());
        user_personal_data.put("daily_fats_need", u.getDaily_fats_need());
        user_personal_data.put("gender", u.getGender());
        user_personal_data.put("name", u.getName());
        user_personal_data.put("height", u.getHeight());
        user_personal_data.put("weight", u.getWeight());
        user_personal_data.put("plan", u.getPlan());
        user_personal_data.put("water_progress", u.getWater_progress());
        user_personal_data.put("doctorEmailConnectedWith", u.getDoctorEmailConnectedWith());
        user_personal_data.put("isDoctorPlanApplied", u.isDoctorPlanApplied());

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
                        // Check user type here
                        if (userTypeCallBack != null) {
                            userTypeCallBack.onCallback();
                        }
                    } else {
                        retrieveDoctorFromFirestore(userEmail);
                    }
                } else {
                    // Handle task failure
                    Exception e = task.getException();
                    if (e != null) {
                        Log.e("Firestore", "Error fetching document: " + e.getMessage());
                    }
                }
            }
        });
    }

    public void retrieveDoctorFromFirestore(String email) {
        Log.d("Firestore", "retrieveDoctorFromFirestore called with email: " + email);

        db.collection("Doctors")
                .document(email)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                Doctor doctor = document.toObject(Doctor.class);
                                if (doctor != null) {
                                    Log.d("Firestore", "Doctor object created: " + doctor.getName());
                                    Doctor.getInstance().setName(doctor.getName());
                                    Doctor.setInstance(doctor);
                                    if (coolback != null) {
                                        coolback.onCallback();
                                    }
                                } else {
                                    Log.e("Firestore", "Doctor object is null");
                                }
                            } else {
                                Log.d("Firestore", "No such document for email: " + email);
                            }
                        } else {
                            Log.e("Firestore", "Task failed: ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Firestore", "Error retrieving doctor data: ", e);
                    }
                });
    }

    // Function to retrieve all doctors
    public void retrieveAllDoctors(final OnCompleteListener<List<Doctor>> onCompleteListener) {
        db.collection("Doctors")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            List<Doctor> doctorList = new ArrayList<>();
                            for (DocumentSnapshot document : task.getResult()) {
                                Doctor doctor = document.toObject(Doctor.class);
                                if (doctor != null) {
                                    doctorList.add(doctor);
                                }
                            }
                            onCompleteListener.onComplete(new Task<List<Doctor>>() {
                                @Override
                                public boolean isComplete() {
                                    return true;
                                }

                                @Override
                                public boolean isSuccessful() {
                                    return true;
                                }

                                @Override
                                public boolean isCanceled() {
                                    return false;
                                }

                                @Override
                                public List<Doctor> getResult() {
                                    return doctorList;
                                }

                                @Override
                                public <X extends Throwable> List<Doctor> getResult(@NonNull Class<X> aClass) throws X {
                                    return doctorList;
                                }

                                @Override
                                public Exception getException() {
                                    return null;
                                }

                                @Override
                                public Task<List<Doctor>> addOnCompleteListener(@NonNull OnCompleteListener<List<Doctor>> onCompleteListener) {
                                    return this;
                                }

                                @Override
                                public Task<List<Doctor>> addOnCompleteListener(@NonNull Executor executor, @NonNull OnCompleteListener<List<Doctor>> onCompleteListener) {
                                    return this;
                                }

                                @Override
                                public Task<List<Doctor>> addOnSuccessListener(@NonNull OnSuccessListener<? super List<Doctor>> onSuccessListener) {
                                    return this;
                                }

                                @NonNull
                                @Override
                                public Task<List<Doctor>> addOnSuccessListener(@NonNull Activity activity, @NonNull OnSuccessListener<? super List<Doctor>> onSuccessListener) {
                                    return null;
                                }

                                @Override
                                public Task<List<Doctor>> addOnSuccessListener(@NonNull Executor executor, @NonNull OnSuccessListener<? super List<Doctor>> onSuccessListener) {
                                    return this;
                                }

                                @Override
                                public Task<List<Doctor>> addOnFailureListener(@NonNull OnFailureListener onFailureListener) {
                                    return this;
                                }

                                @NonNull
                                @Override
                                public Task<List<Doctor>> addOnFailureListener(@NonNull Activity activity, @NonNull OnFailureListener onFailureListener) {
                                    return null;
                                }

                                @Override
                                public Task<List<Doctor>> addOnFailureListener(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
                                    return this;
                                }
                            });
                        } else {
                            Log.e("TAG", "Error retrieving doctors", task.getException());
                            onCompleteListener.onComplete(null);
                        }
                    }
                });
    }


    // Doctor methods
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

    // Method to retrieve patient emails for a specific doctor
    public void getPatientEmails(String doctorEmail, final PatientFirestoreCallback callback) {
        if (doctorEmail != null) {
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
        }
    }

    // Method to store patient email
    public void addPatientEmail(String doctorEmail, String patientEmail, String patientName) {
        // Create a map to store the patient name
        Map<String, Object> patientData = new HashMap<>();
        patientData.put("name", patientName);

        // Add the patient email to the doctor's subcollection
        db.collection("DoctorsPlan").document(doctorEmail).collection("patients_email").document(patientEmail)
                .set(patientData)
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirestoreHelper", "Patient email added successfully to doctor's subcollection.");

                    // Create a subcollection for the patient with their email as the document name
                    db.collection("DoctorsPlan").document(doctorEmail).collection(patientEmail).document("weekly_plan")
                            .set(new WeeklyPlan())
                            .addOnSuccessListener(aVoid1 -> {
                                Log.d("FirestoreHelper", "Subcollection created for the patient.");
                            })
                            .addOnFailureListener(e -> {
                                Log.w("FirestoreHelper", "Error creating subcollection for the patient.", e);
                            });
                })
                .addOnFailureListener(e -> {
                    Log.w("FirestoreHelper", "Error adding patient email to doctor's subcollection.", e);
                });
    }

    public void removePatientEmail(String doctorEmail, String patientEmail) {
        // Delete the patient email from the doctor's subcollection
        Log.d("ehellybnull??", "email?? " + doctorEmail + " " + patientEmail);


        db.collection("DoctorsPlan").document(doctorEmail).collection("patients_email").document(patientEmail)
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Log.d("FirestoreHelper", "Patient email removed successfully from doctor's subcollection.");

                    // Delete the patient's subcollection
                    db.collection("DoctorsPlan").document(doctorEmail).collection(patientEmail).document("weekly_plan")
                            .delete()
                            .addOnSuccessListener(aVoid1 -> {
                                Log.d("FirestoreHelper", "Patient's subcollection deleted successfully.");
                            })
                            .addOnFailureListener(e -> {
                                Log.w("FirestoreHelper", "Error deleting patient's subcollection.", e);
                            });
                })
                .addOnFailureListener(e -> {
                    Log.w("FirestoreHelper", "Error removing patient email from doctor's subcollection.", e);
                });
    }

    public void saveWeeklyPlan(String patient_email, WeeklyPlan weeklyPlan, Context context) {
        db.collection("DoctorsPlan")
                .document(Doctor.getInstance().getEmail())
                .collection(patient_email)
                .document("weekly_plan")
                .set(weeklyPlan)
                .addOnSuccessListener(aVoid -> {
                    // Successfully saved
                    Toast myToast = Toast.makeText(context, "Meal Saved Successfully", Toast.LENGTH_SHORT);
                    myToast.show();
                    System.out.println("Weekly plan successfully saved!" + patient_email);
                })
                .addOnFailureListener(e -> {
                    // Handle the error
                    Toast myToast = Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT);
                    myToast.show();
                    System.err.println("Error saving weekly plan: " + e.getMessage());
                });
    }

    // Retrieve the weekly plan for a patient
    public void getWeeklyPlan(String patient_email, String doctor_email, OnCompleteListener<DocumentSnapshot> onCompleteListener) {
        Log.d("kkdkdd", patient_email + " " + doctor_email);
        DocumentReference doc = db.collection("/DoctorsPlan/").document(doctor_email).collection(patient_email).document("/weekly_plan");
//        db.collection("DoctorsPlan")
//                .document(doctor_email)
//                .collection(patient_email)
//                .document("weekly_plan")
        doc.get()
                .addOnCompleteListener(onCompleteListener);

    }

    public void storeIngredientData(String userEmail, IngredientAppearedRefused ingredientData) {
        String documentPath = "/Users/" + userEmail + "/ingredients_appeared_refused/" + ingredientData.getIngredientName();
        db.runTransaction((Transaction.Function<Void>) transaction -> {
                    DocumentSnapshot snapshot = transaction.get(db.document(documentPath));

                    int appearanceCount = 0;
                    int refuseCount = 0;

                    if (snapshot.exists()) {
                        appearanceCount = snapshot.getLong("appearance_count").intValue();
                        refuseCount = snapshot.getLong("refuse_count").intValue();
                    }

                    appearanceCount += ingredientData.getAppearance_count();
                    refuseCount += ingredientData.getRefuse_count();

                    IngredientAppearedRefused updatedData = new IngredientAppearedRefused(
                            ingredientData.getIngredientName(),
                            appearanceCount,
                            refuseCount
                    );

                    transaction.set(db.document(documentPath), updatedData);
                    return null;
                }).addOnSuccessListener(aVoid -> Log.d("Firestore", "Transaction success!"))
                .addOnFailureListener(e -> Log.w("Firestore", "Transaction failure.", e));
    }

    public void retrieveAllIngredientsData(String userEmail, final OnCompleteListener<Map<String,  ArrayList<Integer>>> onCompleteListener) {
        String collectionPath = "/Users/" + userEmail + "/ingredients_appeared_refused";

        db.collection(collectionPath).get().addOnCompleteListener(task -> {
            TaskCompletionSource<Map<String, ArrayList<Integer>>> taskCompletionSource = new TaskCompletionSource<>();

            if (task.isSuccessful()) {
                QuerySnapshot querySnapshot = task.getResult();
                Map<String,  ArrayList<Integer>> allIngredientsData = new HashMap<>();

                if (querySnapshot != null) {
                    for (DocumentSnapshot document : querySnapshot.getDocuments()) {
                        String ingredientName = document.getId();

                        Long appearanceCountLong = document.getLong("appearance_count");
                        Long refuseCountLong = document.getLong("refuse_count");

                        // Handle potential null values
                        int appearanceCount = appearanceCountLong != null ? appearanceCountLong.intValue() : 0;
                        int refuseCount = refuseCountLong != null ? refuseCountLong.intValue() : 0;

                        ArrayList<Integer> counts = new ArrayList<>();
                        counts.add(appearanceCount);
                        counts.add(refuseCount);
                        allIngredientsData.put(ingredientName, counts);
                    }
                }

                taskCompletionSource.setResult(allIngredientsData);
            } else {
                Log.w("Firestore", "Error getting documents", task.getException());
                taskCompletionSource.setException(task.getException());
            }

            taskCompletionSource.getTask().addOnCompleteListener(onCompleteListener);
        });
    }

    public interface PatientFirestoreCallback {
        void onCallback(ArrayList<String> patientEmails);
    }

    public interface userTypeCallBack {
        void onCallback();
    }

    public interface coolBack {
        void onCallback();

        void updateCalories();
    }

    public interface FirestoreCallback {
        void onSuccess(List<MealModel> meals);

        void onSuccessIngredients(List<IngredientModel> ingredients);

        void onFailure(Exception e);
    }//    public interface FirestoreCallback {
//        void onCallback(List<String> patientEmails);
//    }
//
//    public interface WeeklyPlanCallback {
//        void onCallback(WeeklyPlan weeklyPlan);
//    }

}
