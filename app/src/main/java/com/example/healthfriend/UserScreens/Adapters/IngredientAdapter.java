package com.example.healthfriend.UserScreens.Adapters;

import static android.provider.Settings.System.getString;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.DoctorIngredient;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.MealAdapterInterface;
import com.example.healthfriend.Models.PythonIngredient;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<PythonIngredient> ingredientModelList;
    RecyclerView recyclerView;
    private final MealAdapterInterface mealAdapterInterface;

    public IngredientAdapter(List<PythonIngredient> ingredientModelList, RecyclerView recyclerView, MealAdapterInterface mealAdapterInterface) {
        this.ingredientModelList = ingredientModelList;
        this.recyclerView = recyclerView;
        this.mealAdapterInterface = mealAdapterInterface;
    }

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_ingredient, parent, false);
        IngredientViewHolder vh = new IngredientViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        PythonIngredient currentIngredient = ingredientModelList.get(position);

        double calories = currentIngredient.getCalories();
        double protein = currentIngredient.getProtein();
        double carbs = currentIngredient.getCarbs();
        double fats = currentIngredient.getFats();

        holder.textViewIngredientName.setText(currentIngredient.getName());
        String ingredientInfo = holder.itemView.getContext().getString(R.string.ingredient_info, calories, protein, carbs,fats);
        holder.textViewIngredientInfo.setText(ingredientInfo);

//        holder.textViewIngredientInfo.setText(currentIngredient.getCalories() + "Kcal, ");
//        holder.textViewServingSize.setText(currentIngredient.getServingSize());

        holder.imageViewAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mealAdapterInterface != null) {
                    // it was unselected, then selected after press
                    if (!currentIngredient.isIngredientSelectedByUser()) {
                        TodaysNutrientsEaten.setEatenCalories(TodaysNutrientsEaten.getEatenCalories() + currentIngredient.getCalories());
                        TodaysNutrientsEaten.setEatenCarbs(TodaysNutrientsEaten.getEatenCarbs() + currentIngredient.getCarbs());
                        TodaysNutrientsEaten.setEatenProteins(TodaysNutrientsEaten.getEatenProteins() + currentIngredient.getProtein());
                        TodaysNutrientsEaten.setEatenFats(TodaysNutrientsEaten.getEatenFats() + currentIngredient.getFats());
                        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                            mealAdapterInterface.addItem(holder.getAdapterPosition());
                        }
                    }
                    else  {
                        TodaysNutrientsEaten.setEatenCalories(Math.max(TodaysNutrientsEaten.getEatenCalories() - currentIngredient.getCalories(),0));
                        TodaysNutrientsEaten.setEatenCarbs(Math.max(TodaysNutrientsEaten.getEatenCarbs() - currentIngredient.getCarbs(),0));
                        TodaysNutrientsEaten.setEatenProteins(Math.max(TodaysNutrientsEaten.getEatenProteins() - currentIngredient.getProtein(),0));
                        TodaysNutrientsEaten.setEatenFats(Math.max(TodaysNutrientsEaten.getEatenFats() - currentIngredient.getFats(),0));
                        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                            mealAdapterInterface.removeItem(holder.getAdapterPosition());
                        }
                    }
                }

                // Notify the adapter that the data has changed to reflect the new image state
                notifyDataSetChanged();
                currentIngredient.setIngredientSelectedByUser(!currentIngredient.isIngredientSelectedByUser());

            }
        });

        if (currentIngredient.isIngredientSelectedByUser()) {
            holder.imageViewAddItem.setImageResource(R.drawable.ic_ingredient_check_green);
            currentIngredient.setIngredientSelectedByUser(true);
        } else {
            holder.imageViewAddItem.setImageResource(R.drawable.ic_ingredient_unchecked);
        }

    }

    @Override
    public int getItemCount() {
        return ingredientModelList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        private TextView textViewIngredientName;
        private TextView textViewCalories;
        private TextView textViewIngredientInfo;
        private ImageButton imageViewAddItem;
        private ImageButton imageViewFav;
        private CardView cardView;


        public IngredientViewHolder(View itemView) {
            super(itemView);
            textViewIngredientName = itemView.findViewById(R.id.textViewIngredientName);
//            textViewCalories = itemView.findViewById(R.id.textViewCalories);
            textViewIngredientInfo = itemView.findViewById(R.id.textViewIngredientInfo);
//            textViewServingSize = itemView.findViewById(R.id.textViewServingSize);
            imageViewAddItem = itemView.findViewById(R.id.btn_add_item);
            imageViewFav = itemView.findViewById(R.id.breakfast_btn_add_to_favourite);
            cardView = itemView.findViewById(R.id.ingredient_cardView);


        }
    }

}
