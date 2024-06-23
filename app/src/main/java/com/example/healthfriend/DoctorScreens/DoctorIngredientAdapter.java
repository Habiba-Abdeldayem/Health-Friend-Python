package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.Meal;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.MealAdapterInterface;

import java.util.List;

public class DoctorIngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {
    private Context context;
    private List<DoctorIngredient> availableIngredients;
    private List<DoctorIngredient> selectedIngredients;

    private final MealAdapterInterface mealAdapterInterface;
    private int dayIdx;
    private Meal currentMeal;
    private int isItBreakfastLunchDinnerIdx; // 1 for breakfast , 2 for lunch , 3 for dinner

    public DoctorIngredientAdapter(Context context, List<DoctorIngredient> ingredientsList, MealAdapterInterface mealAdapterInterface) {
        this.context = context;
        this.availableIngredients = ingredientsList;
        this.mealAdapterInterface = mealAdapterInterface;
        this.dayIdx = WeeklyPlanManagerSingleton.getInstance().getCurrentDayIdx();
        this.isItBreakfastLunchDinnerIdx = WeeklyPlanManagerSingleton.getInstance().getCurrentMealIdx();
        this.currentMeal = WeeklyPlanManagerSingleton.getInstance().getWeeklyPlan().getDailyPlans().get(dayIdx).isItBreakfastLunchDinner(isItBreakfastLunchDinnerIdx);
        if (this.currentMeal != null) {
            this.selectedIngredients = this.currentMeal.getIngredients();

            // Initialize the selected state for each ingredient
            for (DoctorIngredient availableIngredient : availableIngredients) {
                boolean isSelected = false;
                for (DoctorIngredient selectedIngredient : selectedIngredients) {
                    if (availableIngredient.getName().equals(selectedIngredient.getName())) {
                        isSelected = true;
                        break;
                    }
                }
                availableIngredient.setIngredientSelectedByDoctor(isSelected);
            }
        }
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        DoctorIngredient currentIngredient = availableIngredients.get(position);
        double calories = currentIngredient.getCalories();
        double protein = currentIngredient.getProtein();
        double carbs = currentIngredient.getCarbs();
        double fats = currentIngredient.getFats();

        holder.textViewIngredientName.setText(currentIngredient.getName());
        String ingredientInfo = context.getString(R.string.ingredient_info, calories, protein, carbs, fats);
        holder.textViewIngredientInfo.setText(ingredientInfo);

        // Set the initial state of the add item button based on selection status
        updateAddItemButton(holder.imageViewAddItem, currentIngredient.isIngredientSelectedByDoctor());

        Log.d("opaa", "before " + Boolean.toString(currentIngredient.isIngredientSelectedByDoctor()));

        holder.imageViewAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("opaa", "afteeeer" + Boolean.toString(currentIngredient.isIngredientSelectedByDoctor()));
                if (currentMeal != null) {
                    if (!currentIngredient.isIngredientSelectedByDoctor()) {
                        currentMeal.addIngredient(currentIngredient);
                        currentIngredient.setIngredientSelectedByDoctor(true);
                        Log.d("opaa", "after after" + Boolean.toString(currentIngredient.isIngredientSelectedByDoctor()));
                        Log.d("opaa", "total ing: " + WeeklyPlanManagerSingleton.getInstance().getWeeklyPlan().getDailyPlans().get(dayIdx).getBreakfast().getIngredients().size());
                    } else {
//                        currentMeal.removeIngredient(currentIngredient);
                        currentMeal.removeIngredientByName(currentIngredient.getName());
                        currentIngredient.setIngredientSelectedByDoctor(false);
                        Log.d("opaa", "deselect " + Boolean.toString(currentIngredient.isIngredientSelectedByDoctor()));
                        Log.d("opaa", "total ing after des: " + WeeklyPlanManagerSingleton.getInstance().getWeeklyPlan().getDailyPlans().get(dayIdx).getBreakfast().getIngredients().size());

                    }
                    // Update the weekly plan using the meal position
                    WeeklyPlanManagerSingleton.getInstance().getWeeklyPlan().getDailyPlans().get(dayIdx).updateMeal(isItBreakfastLunchDinnerIdx, currentMeal);

                    // Update the add item button state
                    updateAddItemButton(holder.imageViewAddItem, currentIngredient.isIngredientSelectedByDoctor());

                    // Notify the adapter to refresh the view
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return availableIngredients.size();
    }

    private void updateAddItemButton(ImageButton imageButton, boolean isSelected) {
        if (isSelected) {
            imageButton.setImageResource(R.drawable.ic_ingredient_check_green);
        } else {
            imageButton.setImageResource(R.drawable.ic_ingredient_unchecked);
        }
    }
}

class IngredientViewHolder extends RecyclerView.ViewHolder {
    TextView textViewIngredientName;
    TextView textViewIngredientInfo;
    ImageButton imageViewAddItem;
    CardView cardView;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        textViewIngredientName = itemView.findViewById(R.id.textViewIngredientName);
        textViewIngredientInfo = itemView.findViewById(R.id.textViewIngredientInfo);
        imageViewAddItem = itemView.findViewById(R.id.btn_add_item);
        cardView = itemView.findViewById(R.id.ingredient_cardView);
    }
}
