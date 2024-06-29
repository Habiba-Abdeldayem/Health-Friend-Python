package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.Arrays;
import java.util.List;


public class Change_meal_Fragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    public Change_meal_Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    Change_MealAdapter adapter;
    private static final List<String> meals = Arrays.asList(
            "ss", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    );

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

                 View view =inflater.inflate(R.layout.fragment_change_meal_, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.change_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new Change_MealAdapter (getContext(),meals);
        recyclerView.setAdapter(adapter);

        return  view;
    }
}