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


public class Userlist_Fragment extends Fragment {


    public Userlist_Fragment() {
        // Required empty public constructor
    }


    UserList adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_userlist_, container, false);
        ArrayList<User> users=new ArrayList<User>();
        users.add(new User(150,50,R.drawable.ff,"shimaa"));
        users.add(new User(160,80,R.drawable.car4,"aya"));
        users.add(new User(170,60,R.drawable.ff,"ahmed"));
        // database=MarketoDb.getInstance(getApplicationContext());
        // categories = database.getCategories();
        // List<Product> products = database.getproductList();
        RecyclerView recyclerView = view.findViewById(R.id.rv_userList);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new UserList(users);
        recyclerView.setAdapter(adapter);
        return view;

    }
}