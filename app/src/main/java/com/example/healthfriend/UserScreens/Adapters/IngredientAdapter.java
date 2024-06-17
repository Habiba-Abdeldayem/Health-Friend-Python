package com.example.healthfriend.UserScreens.Adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.BreakfastAdapterInterface;
import com.example.healthfriend.UserScreens.PythonIngredient;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {
    private List<PythonIngredient> ingredientModelList;
    RecyclerView recyclerView;
    private final BreakfastAdapterInterface breakfastAdapterInterface;

    public IngredientAdapter(List<PythonIngredient> ingredientModelList, RecyclerView recyclerView, BreakfastAdapterInterface breakfastAdapterInterface) {
        this.ingredientModelList = ingredientModelList;
        this.recyclerView = recyclerView;
        this.breakfastAdapterInterface = breakfastAdapterInterface;
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

        holder.textViewIngredientName.setText(currentIngredient.getName());
        holder.textViewCalories.setText(currentIngredient.getCalories() + "Kcal, ");
//        holder.textViewServingSize.setText(currentIngredient.getServingSize());

        holder.imageViewAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (breakfastAdapterInterface != null) {
                    // it was unselected, then selected after press
                    if (!currentIngredient.isIngredientSelected()) {
                        TodaysNutrientsEaten.setEatenCalories(TodaysNutrientsEaten.getEatenCalories() + currentIngredient.getCalories());
                        TodaysNutrientsEaten.setEatenCarbs(TodaysNutrientsEaten.getEatenCarbs() + currentIngredient.getCarbs());
                        TodaysNutrientsEaten.setEatenProteins(TodaysNutrientsEaten.getEatenProteins() + currentIngredient.getProtein());
                        TodaysNutrientsEaten.setEatenFats(TodaysNutrientsEaten.getEatenFats() + currentIngredient.getFats());
                        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                            breakfastAdapterInterface.addItem(holder.getAdapterPosition());
                        }
                    }
                    else  {
                        TodaysNutrientsEaten.setEatenCalories(TodaysNutrientsEaten.getEatenCalories() - currentIngredient.getCalories());
                        TodaysNutrientsEaten.setEatenCarbs(TodaysNutrientsEaten.getEatenCarbs() - currentIngredient.getCarbs());
                        TodaysNutrientsEaten.setEatenProteins(TodaysNutrientsEaten.getEatenProteins() - currentIngredient.getProtein());
                        TodaysNutrientsEaten.setEatenFats(TodaysNutrientsEaten.getEatenFats() - currentIngredient.getFats());
                        if (holder.getAdapterPosition() != RecyclerView.NO_POSITION) {
                            breakfastAdapterInterface.removeItem(holder.getAdapterPosition());
                        }
                    }
                }

                // Notify the adapter that the data has changed to reflect the new image state
                notifyDataSetChanged();
                currentIngredient.setIngredientSelected(!currentIngredient.isIngredientSelected());

            }
        });

        if (currentIngredient.isIngredientSelected()) {
            holder.imageViewAddItem.setImageResource(R.drawable.ic_ingredient_check_green);
            currentIngredient.setIngredientSelected(true);
        } else {
            holder.imageViewAddItem.setImageResource(R.drawable.ic_ingredient_unchecked);
        }

    }

    @Override
    public int getItemCount() {
        return ingredientModelList.size();
    }

    public static class IngredientViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewIngredientName;
        public TextView textViewCalories;
        public TextView textViewServingSize;
        public ImageButton imageViewAddItem;
        public ImageButton imageViewFav;
        public CardView cardView;


        public IngredientViewHolder(View itemView) {
            super(itemView);
            textViewIngredientName = itemView.findViewById(R.id.textViewIngredientName);
            textViewCalories = itemView.findViewById(R.id.textViewCalories);
            textViewServingSize = itemView.findViewById(R.id.textViewServingSize);
            imageViewAddItem = itemView.findViewById(R.id.btn_add_item);
            imageViewFav = itemView.findViewById(R.id.breakfast_btn_add_to_favourite);
            cardView = itemView.findViewById(R.id.cardView);


        }
    }

}
