package com.example.healthfriend.UserScreens;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.healthfriend.R;

public class choice_ingredient_Activity extends AppCompatActivity {
    private Spinner spinner1, spinner2, spinner3;
    Button next;
    private String selectedIngredient1, selectedIngredient2, selectedIngredient3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_choice_ingredient);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
next=findViewById(R.id.next);
        // تعيين مستمع للأحداث عند اختيار عنصر من القائمة
        setupSpinnerListener(spinner1, 1);
        setupSpinnerListener(spinner2, 2);
        setupSpinnerListener(spinner3, 3);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass selectedIngredient1, selectedIngredient2, selectedIngredient3
                //for fire store and algo
            }
        });
    }
    private void setupSpinnerListener(Spinner spinner, final int spinnerNumber) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(choice_ingredient_Activity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();

                // حفظ العنصر المختار في المتغير المناسب
                switch (spinnerNumber) {
                    case 1:
                        selectedIngredient1 = selectedItem;
                        break;
                    case 2:
                        selectedIngredient2 = selectedItem;
                        break;
                    case 3:
                        selectedIngredient3 = selectedItem;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // لا شيء محدد
            }
        });
    }
}