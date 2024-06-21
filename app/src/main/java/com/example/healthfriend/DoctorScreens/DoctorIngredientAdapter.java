package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.DailyPlan;
import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.Models.Meal;
import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.MealAdapterInterface;

import java.util.List;

public class DoctorIngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {
//    private List<String> ingredient;
    private Context context;
    private List<DoctorIngredient> ingredientsList;
    RecyclerView recyclerView;
    private final MealAdapterInterface mealAdapterInterface;
    private int dayIdx =WeeklyPlanManagerSingleton.getInstance().getCurrentDayIdx();
    private DailyPlan currentDayNutrients = WeeklyPlanManagerSingleton.getInstance().getWeeklyPlan().getDailyPlans().get(dayIdx);
//    private Meal currentMeal = currentDayNutrients.get;

    public DoctorIngredientAdapter(Context context, List<DoctorIngredient> ingredientsList, MealAdapterInterface mealAdapterInterface) {
        this.context = context;
        this.ingredientsList = ingredientsList;
//        this.recyclerView = recyclerView;
        this.mealAdapterInterface = mealAdapterInterface;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
//        String categoryName = ingredient.get(position);
//        holder.cat.setText(categoryName);

        DoctorIngredient currentIngredient = ingredientsList.get(position);
        double calories = currentIngredient.getCalories();
        double protein = currentIngredient.getProtein();
        double carbs = currentIngredient.getCarbs();
        double fats = currentIngredient.getFats();

        holder.textViewIngredientName.setText(currentIngredient.getName());
        String ingredientInfo = context.getString(R.string.ingredient_info, calories, protein, carbs,fats);
        holder.textViewIngredientInfo.setText(ingredientInfo);

//        holder.imageViewAddItem.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (mealAdapterInterface != null) {
//                    // it was unselected, then selected after press
//                    if (!currentIngredient.isIngredientSelectedByDoctor()) {
//                        TodaysNutrientsEaten.setEatenCalories(TodaysNutrientsEaten.getEatenCalories() + currentIngredient.getCalories());
//                        TodaysNutrientsEaten.setEatenCarbs(TodaysNutrientsEaten.getEatenCarbs() + currentIngredient.getCarbs());
//                        TodaysNutrientsEaten.setEatenProteins(TodaysNutrientsEaten.getEatenProteins() + currentIngredient.getProtein());
//                        TodaysNutrientsEaten.setEatenFats(TodaysNutrientsEaten.getEatenFats() + currentIngredient.getFats());
//                        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
//                            mealAdapterInterface.addItem(holder.getAdapterPosition());
//                        }
//                    }
//                    else  {
//                        TodaysNutrientsEaten.setEatenCalories(TodaysNutrientsEaten.getEatenCalories() - currentIngredient.getCalories());
//                        TodaysNutrientsEaten.setEatenCarbs(TodaysNutrientsEaten.getEatenCarbs() - currentIngredient.getCarbs());
//                        TodaysNutrientsEaten.setEatenProteins(TodaysNutrientsEaten.getEatenProteins() - currentIngredient.getProtein());
//                        TodaysNutrientsEaten.setEatenFats(TodaysNutrientsEaten.getEatenFats() - currentIngredient.getFats());
//                        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
//                            mealAdapterInterface.removeItem(holder.getAdapterPosition());
//                        }
//                    }
//                }
//
//                // Notify the adapter that the data has changed to reflect the new image state
//                notifyDataSetChanged();
//                currentIngredient.setIngredientSelectedByDoctor(!currentIngredient.isIngredientSelectedByDoctor());
//
//            }
//        });

        if (currentIngredient.isIngredientSelectedByDoctor()) {
            holder.imageViewAddItem.setImageResource(R.drawable.ic_ingredient_check_green);
            currentIngredient.setIngredientSelectedByDoctor(true);
        } else {
            holder.imageViewAddItem.setImageResource(R.drawable.ic_ingredient_unchecked);
        }




    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }
}

class IngredientViewHolder extends RecyclerView.ViewHolder {
//    public TextView cat;
    TextView textViewIngredientName;
    TextView textViewCalories;
     TextView textViewIngredientInfo;
     ImageButton imageViewAddItem;
     ImageButton imageViewFav;
     CardView cardView;


    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
//        cat = itemView.findViewById(R.id.category_tv);
        textViewIngredientName = itemView.findViewById(R.id.textViewIngredientName);
//            textViewCalories = itemView.findViewById(R.id.textViewCalories);
        textViewIngredientInfo = itemView.findViewById(R.id.textViewIngredientInfo);
//            textViewServingSize = itemView.findViewById(R.id.textViewServingSize);
        imageViewAddItem = itemView.findViewById(R.id.btn_add_item);
        cardView = itemView.findViewById(R.id.ingredient_cardView);
    }
}
