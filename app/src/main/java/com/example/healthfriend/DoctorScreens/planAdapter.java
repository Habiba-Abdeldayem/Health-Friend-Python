package com.example.healthfriend.DoctorScreens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.List;

public class planAdapter extends RecyclerView.Adapter<planAdapter.plainViewHolder> {
    public List<plan_item> items;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener longClickListener;
    //CategoryDB data=CategoryDB.getInstance(thi)

    public interface OnItemLongClickListener {
        void onItemLongClick(plan_item item);
    }


    public planAdapter(List<plan_item> items) {
        this.items = items;
//        this.itemClickListener = itemClickListener;
//        this.longClickListener=longClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(plan_item item);
    }
    @NonNull
    @Override
    public plainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_plan, parent, false);

        return new plainViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull plainViewHolder holder, int position) {

        plan_item u=items.get(position);
        holder.Days.setText(u.Days);
        holder.breakfast.setText(u.breakfast);
        holder.lunch.setText(u.lunch);
        holder.dinner.setText(u.dinner);
//        holder.datetimeTextView.setText(trip.getTripTime());
//        holder.priceTextView.setText(Double.toString(trip.getTripFee()) + "$");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(u);
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (longClickListener != null) {
                    longClickListener.onItemLongClick(u);
                }
                return true; // Consume the long click event
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class plainViewHolder extends RecyclerView.ViewHolder {
        public TextView Days;
        public TextView breakfast;
        public TextView lunch;
        public TextView dinner;
        public plainViewHolder(@NonNull View itemView) {
            super(itemView);
            Days = itemView.findViewById(R.id.Days_plan);
            breakfast = itemView.findViewById(R.id.Breakfast_plan);
            lunch = itemView.findViewById(R.id.Lunch_plan);
            dinner=itemView.findViewById(R.id.Dinner_plan);
        }
    }
}
