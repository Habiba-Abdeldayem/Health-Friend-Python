package com.example.healthfriend.DoctorScreens;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.ArrayList;
import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder> implements Filterable {
    private List<Doctor> doctorList;
    private List<Doctor> doctorListFull;
    private OnDoctorListener onDoctorListener;

    public DoctorAdapter(List<Doctor> doctorList, OnDoctorListener onDoctorListener) {
        this.doctorList = doctorList;
        this.doctorListFull = new ArrayList<>(doctorList);
        this.onDoctorListener = onDoctorListener;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_item, parent, false);
        return new DoctorViewHolder(view, onDoctorListener);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);
        holder.bind(doctor);
    }

    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    @Override
    public Filter getFilter() {
        return doctorFilter;
    }

    private Filter doctorFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Doctor> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(doctorListFull);
            } else {
                String filterPattern = constraint.toString().toLowerCase().trim();
                for (Doctor doctor : doctorListFull) {
                    if (doctor.getName().toLowerCase().contains(filterPattern)) {
                        filteredList.add(doctor);
                    }
                }
            }

            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            doctorList.clear();
            doctorList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public class DoctorViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView doctorNameTextView;
        public TextView doctorSpecialtyTextView;
        public Button followButton;
        OnDoctorListener onDoctorListener;

        public DoctorViewHolder(View itemView, OnDoctorListener onDoctorListener) {
            super(itemView);
            doctorNameTextView = itemView.findViewById(R.id.textViewName);
            doctorSpecialtyTextView = itemView.findViewById(R.id.textViewSpecialty);
            followButton = itemView.findViewById(R.id.buttonFollow);
            this.onDoctorListener = onDoctorListener;

            followButton.setOnClickListener(this);
        }

        public void bind(Doctor doctor) {
            doctorNameTextView.setText(doctor.getName());
//            doctorSpecialtyTextView.setText(doctor.getSpecialty());
            followButton.setText(onDoctorListener.isFollowing(doctor) ? "Unfollow" : "Follow");
        }

        @Override
        public void onClick(View v) {
            onDoctorListener.onDoctorClick(getAdapterPosition());
        }
    }

    public interface OnDoctorListener {
        void onDoctorClick(int position);
        boolean isFollowing(Doctor doctor);
    }
}
