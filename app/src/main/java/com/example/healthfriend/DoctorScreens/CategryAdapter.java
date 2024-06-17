package com.example.healthfriend.DoctorScreens;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.List;

public class CategryAdapter extends RecyclerView.Adapter<CategoryViewHolder>{
    public List<String> category;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener longClickListener;
    //CategoryDB data=CategoryDB.getInstance(thi)

    public interface OnItemLongClickListener {
        void onItemLongClick(String user);
    }


    public CategryAdapter(List<String> category) {
        this.category = category;

    }

    public interface OnItemClickListener {
        void onItemClick(String user);
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);

        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {

        String u=category.get(position);
        holder.cat.setText(u);

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
        return category.size();
    }
}

class CategoryViewHolder extends RecyclerView.ViewHolder {
    public TextView cat;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        cat = itemView.findViewById(R.id.category_tv);

        //datetimeTextView = itemView.findViewById(R.id.tv_availableDateTime);
        // Initialize other views here
    }

}