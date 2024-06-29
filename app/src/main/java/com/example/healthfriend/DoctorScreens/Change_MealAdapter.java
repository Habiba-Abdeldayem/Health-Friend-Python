package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.List;

public class Change_MealAdapter extends RecyclerView.Adapter<ChaneViewHolder> {
    private List<String> meals;
    private Context context;
    private boolean isOneSelected = false;
    private MealPosition mealPosition;
    private int selectedPosition = RecyclerView.NO_POSITION;

    public void setMealPositionListener(MealPosition mealPosition) {
        this.mealPosition = mealPosition;
    }

    public interface MealPosition {
        void onClick(int arrayIdx);
    }

    public Change_MealAdapter(Context context, List<String> meals) {
        this.context = context;
        this.meals = meals;
    }

    @NonNull
    @Override
    public ChaneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.change_ingredient_item, parent, false);
        return new ChaneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChaneViewHolder holder, int position) {
        String day = meals.get(position);
        holder.meal.setText(day);

        if (isOneSelected && position != selectedPosition) {
            holder.choice.setEnabled(false);
        } else {
            holder.choice.setEnabled(true);
        }

        holder.choice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isOneSelected) {
                    holder.choice.setImageResource(R.drawable.ic_ingredient_check_green);
                    selectedPosition = holder.getAdapterPosition();
                    mealPosition.onClick(holder.getAdapterPosition());
                    isOneSelected = true;
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "Only one meal can be selected", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }
}

class ChaneViewHolder extends RecyclerView.ViewHolder {
    public TextView meal;
    public ImageButton choice;

    public ChaneViewHolder(@NonNull View itemView) {
        super(itemView);
        meal = itemView.findViewById(R.id.meal);
        choice = itemView.findViewById(R.id.btn_meal);
    }
}
