package com.example.healthfriend.UserScreens.Fragments.profile.presentation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Fragments.profile.domain.DialogueCallback;

public class EditProfileDialogFragment extends DialogFragment {

    private String fieldToEdit;
    private DialogueCallback callback;

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
        View view = inflater.inflate(R.layout.edit_profile_dialog, container, false);

        assert getArguments() != null;
        fieldToEdit = getArguments().getString("field");  // Get the field to edit
        TextView labelTextView = view.findViewById(R.id.edit_field_label);
        labelTextView.setText("Edit " + fieldToEdit);  // Set appropriate label

        EditText editText = view.findViewById(R.id.edit_field);

        Button saveButton = view.findViewById(R.id.save_button);
        saveButton.setOnClickListener(v -> {
            callback.onSave(editText.getText().toString());
            dismiss();
        });

        Button cancelButton = view.findViewById(R.id.cancel_button);

        cancelButton.setOnClickListener(v -> dismiss());

        return view;
    }
}

