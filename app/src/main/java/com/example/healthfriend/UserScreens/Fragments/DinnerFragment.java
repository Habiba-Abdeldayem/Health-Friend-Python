package com.example.healthfriend.UserScreens.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.healthfriend.DoctorScreens.Change_meal_Fragment;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Adapters.IngredientAdapter;
import com.example.healthfriend.UserScreens.Adapters.IngredientModel;
import com.example.healthfriend.UserScreens.MealAdapterInterface;
import com.example.healthfriend.UserScreens.PythonDinner;
import com.example.healthfriend.Models.PythonIngredient;
import com.example.healthfriend.UserScreens.TodaysDinnerSingleton;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;
import com.example.healthfriend.UserScreens.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class DinnerFragment extends Fragment implements MealAdapterInterface {
    User user = User.getInstance();

    boolean dinner_fav_isClicked = false;
    private TodaysDinnerSingleton dinnerSingleton;
    private PythonDinner pythonDinner;
    IngredientAdapter adapter;
    private ProgressBar caloriesProgressBar, carbsProgressBar , proteinsProgressBar, fatsProgressBar;
    private TextView textview_calories_progress, textview_carbs_progress, textview_proteins_progress, textview_fats_progress;


    public DinnerFragment() {
        // Required empty public constructor
    }

    public static DinnerFragment newInstance(String param1, String param2) {
        DinnerFragment fragment = new DinnerFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dinner, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton favourite_btn = view.findViewById(R.id.dinner_btn_add_to_favourite);
        ImageButton change_meal_btn = view.findViewById(R.id.dinner_btn_change_meal);
        caloriesProgressBar = view.findViewById(R.id.dinner_calories_progressbar);
        carbsProgressBar = view.findViewById(R.id.dinner_carbs_progressbar);
        proteinsProgressBar = view.findViewById(R.id.dinner_proteins_progressbar);
        fatsProgressBar = view.findViewById(R.id.dinner_fats_progressbar);
        textview_calories_progress = view.findViewById(R.id.dinner_textview_calories_progress);
        textview_carbs_progress = view.findViewById(R.id.dinner_textview_carbs_progress);
        textview_proteins_progress = view.findViewById(R.id.dinner_textview_proteins_progress);
        textview_fats_progress = view.findViewById(R.id.dinner_textview_fats_progress);
        updateCaloriesProgress(); updateCarbsProgress(); updateProteinsProgress(); updateFatsProgress();

        dinnerSingleton = TodaysDinnerSingleton.getInstance();
        pythonDinner = PythonDinner.getInstance();
        if(pythonDinner.getDinnerPythonIngredients()==null) {

            List<IngredientModel> todaysIngredient = dinnerSingleton.getDinnerIngredients();
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(getContext()));
            }
            Python python = Python.getInstance();
            PyObject myModule = python.getModule("chocoo");
            PyObject myfncall = myModule.get("calll");
            PyObject result = myfncall.call(70, 170, "breakfast.csv");
            String f = result.toString();
            //  List<List<Map<String, String>>> Meals = parseJson(f);
            // List<Map<String, String>> meal = Meals.get(0);
            Type type = new TypeToken<List<List<Map<String, String>>>>() {
            }.getType();
            List<List<Map<String, String>>> meals = new Gson().fromJson(f, type);
            System.out.println(meals);
            List<PythonIngredient> pythonIngredients = new ArrayList<>();

            // Adding elements to the list
            meals.get(0).size();
            for (int i = 0; i < meals.get(0).size(); i++) {
                pythonIngredients.add(new PythonIngredient(meals.get(0).get(i).get("ingredient"), Double.valueOf(meals.get(0).get(i).get("carbohydrates")), Double.valueOf(meals.get(0).get(i).get("energy")), Double.valueOf(meals.get(0).get(i).get("fat")), Double.valueOf(meals.get(0).get(i).get("protein"))));
//            pythonIngredients.add(new PythonIngredient("Ingredient 2", 15.0, 150.0, 7.0, 25.0));
//            pythonIngredients.add(new PythonIngredient("Ingredient 3", 20.0, 200.0, 10.0, 30.0));
            }
        pythonDinner.setBreakfastPythonIngredients(pythonIngredients);
        }
        List<PythonIngredient> breakfastIngredients = pythonDinner.getDinnerPythonIngredients();

//        breakfastSingleton = TodaysBreakfastSingleton.getInstance();
//        List<IngredientModel> todaysIngredient = breakfastSingleton.getBreakfastIngredients();

        if (breakfastIngredients != null) {
            RecyclerView recyclerView = view.findViewById(R.id.rv_dinner_suggested_meals);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            IngredientAdapter adapter = new IngredientAdapter(breakfastIngredients, recyclerView, this);
            recyclerView.setAdapter(adapter);
        }

        favourite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!dinner_fav_isClicked){
                    dinner_fav_isClicked = true;
                    favourite_btn.setImageResource(R.drawable.ic_favourite_red);
                }
                else{
                    dinner_fav_isClicked = false;
                    favourite_btn.setImageResource(R.drawable.ic_favourite_grey);
                }
            }
        });
        change_meal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Change_meal_Fragment change_meal_fragment = new Change_meal_Fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, change_meal_fragment).addToBackStack(null).commit();
            }
        });
    }
    public void addItem(int position) {
       // if (dinnerSingleton.getTodayDinner() != null) {
            updateCaloriesProgress();
            updateCarbsProgress();
            updateProteinsProgress();
            updateFatsProgress();
        //}
    }

    @Override
    public void removeItem(int position) {
       // if (dinnerSingleton.getTodayDinner() != null) {
            updateCaloriesProgress();
            updateCarbsProgress();
            updateProteinsProgress();
            updateFatsProgress();
       // }

    }

    private void updateCaloriesProgress(){
        double caloriesProgress = (TodaysNutrientsEaten.getEatenCalories() / user.getDaily_calories_need()) * 100;
        caloriesProgressBar.setProgress((int) caloriesProgress);
        String calories_left_string = getString(R.string.calories_progress, TodaysNutrientsEaten.getEatenCalories(), user.getDaily_calories_need());
        textview_calories_progress.setText(calories_left_string);

//        double caloriesLeftValue = Math.max(User.getInstance().getDaily_calories_need() - TodaysNutrientsEaten.getEatenCalories(), 0);
//        String caloriesProgressText = Double.toString(Math.round(TodaysNutrientsEaten.getEatenCalories()*100.0)/100.0);
//        textview_calories_progress.setText(calories_left_string);
    }
    private void updateCarbsProgress(){
        double carbsProgress = (TodaysNutrientsEaten.getEatenCarbs() / user.getDaily_carbs_need()) * 100;
        carbsProgressBar.setProgress((int) carbsProgress);
        String carbs_left_string = getString(R.string.carbs_progress, TodaysNutrientsEaten.getEatenCarbs(), user.getDaily_carbs_need());
        textview_carbs_progress.setText(carbs_left_string);
//        String carbsProgressText = Double.toString(Math.round(TodaysNutrientsEaten.getEatenCarbs()*100.0)/100.0);
//        textview_carbs_progress.setText(carbsProgressText + "/" + "100");
    }
    private void updateProteinsProgress(){
        double proteinsProgress = (TodaysNutrientsEaten.getEatenProteins() /user.getDaily_proteins_need()) * 100;
        proteinsProgressBar.setProgress((int) proteinsProgress);
        String proteins_left_string = getString(R.string.proteins_progress, TodaysNutrientsEaten.getEatenProteins(), user.getDaily_proteins_need());
        textview_proteins_progress.setText(proteins_left_string);
//        String proteinsProgressText = Double.toString(Math.round(TodaysNutrientsEaten.getEatenProteins()*100.0)/100.0);
//        textview_proteins_progress.setText(proteinsProgressText + "/" + "100");
    }
    private void updateFatsProgress(){
        double fatsProgress = (TodaysNutrientsEaten.getEatenFats() / user.getDaily_fats_need()) * 100;
        fatsProgressBar.setProgress((int) fatsProgress);
        String fats_left_string = getString(R.string.fats_progress, TodaysNutrientsEaten.getEatenFats(), user.getDaily_fats_need());
        textview_fats_progress.setText(fats_left_string);

//        String fatsProgressText = Double.toString(Math.round(TodaysNutrientsEaten.getEatenFats()*100.0)/100.0);
//        textview_fats_progress.setText(fatsProgressText + "/" + "100");
    }

}