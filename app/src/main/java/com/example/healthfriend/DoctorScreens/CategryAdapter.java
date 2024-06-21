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

public class CategryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {
    private List<String> category;
    private Context context;

    public CategryAdapter(Context context, List<String> category) {
        this.context = context;
        this.category = category;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        String categoryName = category.get(position);
        holder.cat.setText(categoryName);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, IngredientsActivity.class);
                intent.putExtra("category_name", categoryName);
                context.startActivity(intent);
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
    }
}
