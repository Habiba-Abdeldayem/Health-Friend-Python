package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.Models.WeeklyPlanManagerSingleton;
import com.example.healthfriend.R;

import java.util.List;

public class Change_MealAdapter extends RecyclerView.Adapter<ChaneViewHolder> {
    private List<String> meals;
    private Context context;

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
     holder.choice.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

    }
});
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                WeeklyPlanManagerSingleton.getInstance().setCurrentDayIdx(holder.getAdapterPosition());
//                Intent intent = new Intent(context, MealsActivity.class);
//                context.startActivity(intent);
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
        choice=itemView.findViewById(R.id.btn_meal);
    }
}

