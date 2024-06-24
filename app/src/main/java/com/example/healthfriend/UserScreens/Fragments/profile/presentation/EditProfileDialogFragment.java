package com.example.healthfriend.UserScreens.Fragments.profile.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Fragments.profile.domain.DialogueCallback;
import com.example.healthfriend.UserScreens.IndividualUser;

public class EditProfileDialogFragment extends DialogFragment {

    private String fieldToEdit;
    private DialogueCallback callback;
    private String gender = IndividualUser.getInstance().getGender();
    private String selectedPlan = IndividualUser.getInstance().getPlan();



    public EditProfileDialogFragment(DialogueCallback callback) {
        this.callback = callback;
    }

    public static EditProfileDialogFragment newInstance(String fieldToEdit, DialogueCallback callback) {
        EditProfileDialogFragment fragment = new EditProfileDialogFragment(callback);
        Bundle args = new Bundle();
        args.putString("field", fieldToEdit);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        fieldToEdit = getArguments().getString("field");  // Get the field to edit
        View view = null;
//        String gender;


        if(fieldToEdit.equals("Gender")){
            view = inflater.inflate(R.layout.edit_gender_dialogue, container, false);
            RadioGroup genderRadioGroup = view.findViewById(R.id.profile_gender_radio_group);
            genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    // Check which radio button is selected
                    if (checkedId == R.id.profile_male_radio_button) {
                        gender="Male";
                        // Male option selected
                    } else if (checkedId == R.id.profile_female_radio_button) {
                        // Female option selected
                        gender="Female";
                    }
                }
            });
            TextView labelTextView = view.findViewById(R.id.gender_edit_field_label);
            labelTextView.setText("Edit " + fieldToEdit);
            Button saveButton = view.findViewById(R.id.gender_save_button);
            saveButton.setOnClickListener(v -> {
                callback.onSave(gender);
                dismiss();
            });
        }
        else if(fieldToEdit.equals("Target")){
            view = inflater.inflate(R.layout.edit_plan_dialogue, container, false);

            String[] healthGoals = {"Health & Wellness", "Weight Control", "Weight Gain", "Easy Monitoring"};

            // Create ArrayAdapter
            ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, healthGoals);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            Spinner healthGoalSpinner;


            // Get Spinner reference
            healthGoalSpinner = view.findViewById(R.id.edit_plan_spinner);

            // Set ArrayAdapter to Spinner
            healthGoalSpinner.setAdapter(adapter);
            // Set spinner selection to current plan
            int selectionIndex = java.util.Arrays.asList(healthGoals).indexOf(selectedPlan);
            if (selectionIndex >= 0) {
                healthGoalSpinner.setSelection(selectionIndex);
            }

            // Optionally, handle item selection using a listener
            healthGoalSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    selectedPlan = parent.getItemAtPosition(position).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Handle case when nothing is selected
                }
            });
            TextView labelTextView = view.findViewById(R.id.plan_edit_field_label);
            labelTextView.setText("Edit " + fieldToEdit);
            Button saveButton = view.findViewById(R.id.plan_save_button);
            saveButton.setOnClickListener(v -> {
                callback.onSave(selectedPlan);
                dismiss();
            });
        }
        else{
            view = inflater.inflate(R.layout.edit_profile_dialog, container, false);
            TextView labelTextView = view.findViewById(R.id.edit_field_label);
            labelTextView.setText("Edit " + fieldToEdit);
            EditText editText = view.findViewById(R.id.edit_field);
            Button saveButton = view.findViewById(R.id.save_button);
            saveButton.setOnClickListener(v -> {
                callback.onSave(editText.getText().toString());
                dismiss();
            });
        }

        Button cancelButton = view.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(v -> dismiss());

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((WindowManager.LayoutParams) params);
    }
}

