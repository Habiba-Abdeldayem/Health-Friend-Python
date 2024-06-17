package com.example.healthfriend.DoctorScreens;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.List;

public class UserList extends RecyclerView.Adapter<AvailableViewHolder>{
    public List<User> user;
    private OnItemClickListener itemClickListener;
    private OnItemLongClickListener longClickListener;
    //CategoryDB data=CategoryDB.getInstance(thi)

    public interface OnItemLongClickListener {
        void onItemLongClick(User user);
    }


    public UserList(List<User> user) {
        this.user = user;

    }

    public interface OnItemClickListener {
        void onItemClick(User user);
    }

    @NonNull
    @Override
    public AvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);

        return new AvailableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableViewHolder holder, int position) {

        User u=user.get(position);
        holder.name.setText(u.getName());
        holder.height.setText(Integer.toString(u.getHeight())+ "Cm");
        holder.wight.setText(Integer.toString(u.getWeight())+ "Kg");
        holder.image.setImageResource(u.getImage());
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
        return user.size();
    }
}

class AvailableViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView wight;
    public TextView height;
    public  ImageView image;
    public AvailableViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.user_name);
        wight = itemView.findViewById(R.id.user_weight);
        height = itemView.findViewById(R.id.user_height);
        image=itemView.findViewById(R.id.circleImageView);
        //datetimeTextView = itemView.findViewById(R.id.tv_availableDateTime);
        // Initialize other views here
    }

}