package com.example.healthfriend.UserScreens.Fragments.profile.presentation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Fragments.profile.domain.DialogueCallback;
import com.example.healthfriend.UserScreens.Fragments.profile.domain.EditUserCallback;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {
    TextView weightTV, heightTV, ageTV, genderTV, targetTV, caloriesTV;
    ImageView weightEditBtn, heightEditBtn, ageEditBtn, genderEditBtn, targetEditBtn, caloriesEditBtn;

    private ProfileViewModel viewModel;
    private FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(requireActivity()).get(ProfileViewModel.class);
        db = FirebaseFirestore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pofile, container, false);

        weightTV = view.findViewById(R.id.weight_tv);
        heightTV = view.findViewById(R.id.height_tv);
        ageTV = view.findViewById(R.id.age_tv);
        genderTV = view.findViewById(R.id.gender_tv);
        targetTV = view.findViewById(R.id.target_tv);
        caloriesTV = view.findViewById(R.id.calories_tv);

        weightEditBtn = view.findViewById(R.id.edit_weight);
        heightEditBtn = view.findViewById(R.id.edit_height);
        ageEditBtn = view.findViewById(R.id.edit_age);
        genderEditBtn = view.findViewById(R.id.edit_gender);
        targetEditBtn = view.findViewById(R.id.edit_target);

        viewModel.initializeManager(db);
        viewModel.getProfileData();

        viewModel.weight.observe(getViewLifecycleOwner(), weight -> weightTV.setText("Weight: " + weight));
        viewModel.height.observe(getViewLifecycleOwner(), height -> heightTV.setText("Height: " + height));
        viewModel.age.observe(getViewLifecycleOwner(), age -> ageTV.setText("Age: " + age));
        viewModel.gender.observe(getViewLifecycleOwner(), gender -> genderTV.setText("Gender: " + gender));
        viewModel.plan.observe(getViewLifecycleOwner(), plan -> targetTV.setText("Target: " + plan));
        Log.d("plaan", IndividualUser.getInstance().getPlan());
        viewModel.calories.observe(getViewLifecycleOwner(), calories -> caloriesTV.setText("Limited Calories: " + calories));

        weightEditBtn.setOnClickListener(view1 -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("Weight",
                    value -> viewModel.editWeight(value, new EditUserCallback() {
                        @Override
                        public void onSuccess() {
                            // Do nothing, LiveData will update the UI
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(requireContext(), "Failed to edit data", Toast.LENGTH_LONG).show();
                        }
                    }));
            dialog.show(requireFragmentManager(), "edit_dialog");
        });

        heightEditBtn.setOnClickListener(view1 -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("Height",
                    value -> viewModel.editHeight(value, new EditUserCallback() {
                        @Override
                        public void onSuccess() {
                            // Do nothing, LiveData will update the UI
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(requireContext(), "Failed to edit data", Toast.LENGTH_LONG).show();
                        }
                    }));
            dialog.show(requireFragmentManager(), "edit_dialog");
        });

        ageEditBtn.setOnClickListener(view1 -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("Age",
                    value -> viewModel.editAge(value, new EditUserCallback() {
                        @Override
                        public void onSuccess() {
                            // Do nothing, LiveData will update the UI
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(requireContext(), "Failed to edit data", Toast.LENGTH_LONG).show();
                        }
                    }));
            dialog.show(requireFragmentManager(), "edit_dialog");
        });

        genderEditBtn.setOnClickListener(view1 -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("Gender", new DialogueCallback() {
                @Override
                public void onSave(String value) {
                    value = value.toLowerCase();
                    if (value.equals("male") || value.equals("female")) {
                        viewModel.editGender(value, new EditUserCallback() {
                            @Override
                            public void onSuccess() {
                                // Do nothing, LiveData will update the UI
                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(requireContext(), "Failed to edit data", Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(requireContext(), "Invalid input", Toast.LENGTH_LONG).show();
                    }
                }
            });
            dialog.show(requireFragmentManager(), "edit_dialog");
        });

        targetEditBtn.setOnClickListener(view1 -> {
            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("Target",
                    value -> viewModel.editTarget(value, new EditUserCallback() {
                        @Override
                        public void onSuccess() {
                            // Do nothing, LiveData will update the UI
                        }

                        @Override
                        public void onError(String error) {
                            Toast.makeText(requireContext(), "Failed to edit data", Toast.LENGTH_LONG).show();
                        }
                    }));
            dialog.show(requireFragmentManager(), "edit_dialog");
        });

//        caloriesEditBtn.setOnClickListener(view1 -> {
//            EditProfileDialogFragment dialog = EditProfileDialogFragment.newInstance("Calories",
//                    value -> viewModel.editCalories(value, new EditUserCallback() {
//                        @Override
//                        public void onSuccess() {
//                            // Do nothing, LiveData will update the UI
//                        }
//
//                        @Override
//                        public void onError(String error) {
//                            Toast.makeText(requireContext(), "Failed to edit data", Toast.LENGTH_LONG).show();
//                        }
//                    }));
//            dialog.show(requireFragmentManager(), "edit_dialog");
//        });

        return view;
    }
}
