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

public class IngredientAdapter extends RecyclerView.Adapter<IngredientViewHolder> {
    private List<String> ingredient;
    private Context context;

    public IngredientAdapter(Context context, List<String> ingredient) {
        this.context = context;
        this.ingredient = ingredient;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new IngredientViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        String categoryName = ingredient.get(position);
        holder.cat.setText(categoryName);


    }

    @Override
    public int getItemCount() {
        return ingredient.size();
    }
}

class IngredientViewHolder extends RecyclerView.ViewHolder {
    public TextView cat;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        cat = itemView.findViewById(R.id.category_tv);
    }
}
