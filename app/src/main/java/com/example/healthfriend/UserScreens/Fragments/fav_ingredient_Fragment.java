package com.example.healthfriend.UserScreens.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.healthfriend.R;

public class fav_ingredient_Fragment extends Fragment {

    private String mealType;

    private Spinner spinner1, spinner2, spinner3;
    Button next;
    private String[] selectedIngredients = new String[3];

    public fav_ingredient_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mealType = getArguments().getString("mealType");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav_ingredient_, container, false);
        spinner1 = view.findViewById(R.id.spinner1);
        spinner2 = view.findViewById(R.id.spinner2);
        spinner3 = view.findViewById(R.id.spinner3);
        next = view.findViewById(R.id.next);

        setSpinnerData(mealType);

        // تعيين مستمع للأحداث عند اختيار عنصر من القائمة
        setupSpinnerListener(spinner1, 1);
        setupSpinnerListener(spinner2, 2);
        setupSpinnerListener(spinner3, 3);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Pass selected ingredients to Firestore and algo
                String ingredient1 = selectedIngredients[0];
                String ingredient2 = selectedIngredients[1];
                String ingredient3 = selectedIngredients[2];
                //or pass array selectedIngredients
                // Use the selected ingredients as needed
            }
        });

        return view;
    }

    private void setSpinnerData(String mealType) {
        int arrayResId;
        switch (mealType) {
            case "breakfast":
                arrayResId = R.array.breakfast;
                break;
            case "lunch":
                arrayResId = R.array.launch;
                break;
            case "dinner":
                arrayResId = R.array.breakfast;
                break;
            default:
                arrayResId = R.array.breakfast;
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                arrayResId, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(adapter);
        spinner2.setAdapter(adapter);
        spinner3.setAdapter(adapter);
    }

    private void setupSpinnerListener(Spinner spinner, final int spinnerNumber) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                // Toast.makeText(getContext(), "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

                // Save the selected item in the array
                selectedIngredients[spinnerNumber - 1] = selectedItem;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No item selected
            }
        });
    }
}
