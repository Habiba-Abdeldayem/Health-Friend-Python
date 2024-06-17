package com.example.healthfriend.DoctorScreens;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.healthfriend.R;

import java.util.ArrayList;


public class CategoryFragment extends Fragment {



    public CategoryFragment() {
        // Required empty public constructor
    }


//    // TODO: Rename and change types and number of parameters
//    public static CategoryFragment newInstance(String param1, String param2) {
//        CategoryFragment fragment = new CategoryFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
CategryAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_category, container, false);
        ArrayList<String> items= new ArrayList<>();
        items.add("Fruit");
        items.add("Chicken");
        items.add("Meet");
        // database=MarketoDb.getInstance(getApplicationContext());
        // categories = database.getCategories();
        // List<Product> products = database.getproductList();
        RecyclerView recyclerView = view.findViewById(R.id.category_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new CategryAdapter (items);
        recyclerView.setAdapter(adapter);

        return  view;

    }
}