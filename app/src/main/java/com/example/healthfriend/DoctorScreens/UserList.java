package com.example.healthfriend.DoctorScreens;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.FireStoreManager;
import com.example.healthfriend.UserScreens.IndividualUser;

import java.util.List;

public class UserList extends RecyclerView.Adapter<AvailableViewHolder> {
    public List<String> user;
    private OnItemClickListener itemClickListener;
//    private OnItemLongClickListener longClickListener;
    FireStoreManager fireStoreManager = new FireStoreManager();

//    public interface OnItemLongClickListener {
//        void onItemLongClick(User user);
//    }


    public UserList(List<String> user, OnItemClickListener itemClickListener) {
        this.user = user;
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(IndividualUser user);
    }

    @NonNull
    @Override
    public AvailableViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new AvailableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AvailableViewHolder holder, int position) {
        String userEmail = user.get(position);
        holder.name.setText(userEmail);

//        holder.height.setText(Double.toString(individualUser.getHeight())+ "Cm");
//        Log.d("lkl",Double.toString(individualUser.getHeight()));
//        holder.wight.setText(Double.toString(individualUser.getWeight())+ "Kg");
//        holder.image.setImageResource(u.getImage());
//        holder.datetimeTextView.setText(trip.getTripTime());
//        holder.priceTextView.setText(Double.toString(trip.getTripFee()) + "$");

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (itemClickListener != null) {
                    IndividualUser individualUser = IndividualUser.getInstance();
                    individualUser.setEmail(userEmail);
                    fireStoreManager.getUserPersonalInfo(userEmail);
                    itemClickListener.onItemClick(individualUser);
                }
            }
        });
//        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                if (longClickListener != null) {
//                    longClickListener.onItemLongClick(u);
//                }
//                return true; // Consume the long click event
//            }
//        });
    }


    @Override
    public int getItemCount() {
        return user.size();
    }
}

class AvailableViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    //    public TextView wight;
//    public TextView height;
    public ImageView image;

    public AvailableViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.user_name);
//        wight = itemView.findViewById(R.id.user_weight);
//        height = itemView.findViewById(R.id.user_height);
        image = itemView.findViewById(R.id.circleImageView);
        //datetimeTextView = itemView.findViewById(R.id.tv_availableDateTime);
        // Initialize other views here
    }

}