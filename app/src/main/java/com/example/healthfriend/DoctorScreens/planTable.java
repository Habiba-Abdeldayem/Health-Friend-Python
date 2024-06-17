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


public class planTable extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//
    public planTable() {
        // Required empty public constructor
    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment planTable.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static planTable newInstance(String param1, String param2) {
//        planTable fragment = new planTable();
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
planAdapter adapter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_plan_table, container, false);
        ArrayList<plan_item> items=new ArrayList<plan_item>();
        items.add(new plan_item("Saturday","Tomato+cheese","Shorba","Zabady"));
        items.add(new plan_item("Sunday","Tomato+cheese","Shorba","Zabady"));
        items.add(new plan_item("Monday","Tomato+cheese","Shorba","Zabady"));
        // database=MarketoDb.getInstance(getApplicationContext());
        // categories = database.getCategories();
        // List<Product> products = database.getproductList();
        RecyclerView recyclerView = view.findViewById(R.id.plan_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new planAdapter (items);
        recyclerView.setAdapter(adapter);

       return  view;
    }






}