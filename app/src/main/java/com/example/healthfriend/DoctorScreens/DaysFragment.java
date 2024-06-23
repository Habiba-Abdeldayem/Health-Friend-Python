package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.healthfriend.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class DaysFragment extends Fragment {



    public DaysFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    DaysAdapter adapter;
    private static final List<String> DAY_LIST = Arrays.asList(
            "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"
    );
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_days, container, false);
//        ArrayList<String> items= new ArrayList<>();
//
//
//        items.add("Fruit");
//        items.add("Chicken");
//        items.add("Meet");
        // database=MarketoDb.getInstance(getApplicationContext());
        // categories = database.getCategories();
        // List<Product> products = database.getproductList();
        RecyclerView recyclerView = view.findViewById(R.id.days_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new DaysAdapter (getContext(),DAY_LIST);
        recyclerView.setAdapter(adapter);

        return  view;
    }
}