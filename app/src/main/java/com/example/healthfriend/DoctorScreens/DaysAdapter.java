package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.healthfriend.R;

import java.util.List;

public class DaysAdapter extends RecyclerView.Adapter<DayViewHolder> {
    private List<String> days;
    private Context context;

    public DaysAdapter(Context context, List<String> days) {
        this.context = context;
        this.days = days;
    }

    @NonNull
    @Override
    public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.days_item, parent, false);
        return new DayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DayViewHolder holder, int position) {
        String day = days.get(position);
        holder.day.setText(day);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MealsActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return days.size();
    }
}

class DayViewHolder extends RecyclerView.ViewHolder {
    public TextView day;

    public DayViewHolder(@NonNull View itemView) {
        super(itemView);
        day = itemView.findViewById(R.id.day_tv);
    }
}
