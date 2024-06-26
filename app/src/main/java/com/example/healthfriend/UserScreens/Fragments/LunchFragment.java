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
import com.example.healthfriend.Models.UserMeal;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Adapters.IngredientAdapter;
import com.example.healthfriend.UserScreens.Adapters.IngredientModel;
import com.example.healthfriend.Models.Meal;
import com.example.healthfriend.UserScreens.ChangeMealSingelton;
import com.example.healthfriend.UserScreens.MealAdapterInterface;
import com.example.healthfriend.Models.PythonIngredient;
import com.example.healthfriend.UserScreens.PythonLaunch;
import com.example.healthfriend.UserScreens.TodaysLunchSingleton;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;
import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class LunchFragment extends Fragment implements MealAdapterInterface {
    IndividualUser individualUser = IndividualUser.getInstance();
    boolean lunch_fav_isClicked = false;
    boolean eman=false;
    boolean habiba=false;

    private TodaysLunchSingleton lunchSingleton;
    IngredientAdapter adapter;
    private ProgressBar caloriesProgressBar, carbsProgressBar , proteinsProgressBar, fatsProgressBar;
    private TextView textview_calories_progress, textview_carbs_progress, textview_proteins_progress, textview_fats_progress;
    //private PythonBreakfast pythonBreakfast;
    private PythonLaunch pythonLaunch;
    private ChangeMealSingelton changeMealSingelton;

    public LunchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lunch, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageButton favourite_btn = view.findViewById(R.id.lunch_btn_add_to_favourite);
        ImageButton change_meal_btn = view.findViewById(R.id.lunch_btn_change_meal);
        caloriesProgressBar = view.findViewById(R.id.lunch_calories_progressbar);
        carbsProgressBar = view.findViewById(R.id.lunch_carbs_progressbar);
        proteinsProgressBar = view.findViewById(R.id.lunch_proteins_progressbar);
        fatsProgressBar = view.findViewById(R.id.lunch_fats_progressbar);
        textview_calories_progress = view.findViewById(R.id.lunch_textview_calories_progress);
        textview_carbs_progress = view.findViewById(R.id.lunch_carbs_progress);
        textview_proteins_progress = view.findViewById(R.id.lunch_textview_proteins_progress);
        textview_fats_progress = view.findViewById(R.id.lunch_textview_fats_progress);
        updateCaloriesProgress(); updateCarbsProgress(); updateProteinsProgress(); updateFatsProgress();
        lunchSingleton = TodaysLunchSingleton.getInstance();
        List<IngredientModel> todaysIngredient = lunchSingleton.getLunchIngredients();
        pythonLaunch = PythonLaunch.getInstance();
        changeMealSingelton=ChangeMealSingelton.getInstance();
        if(pythonLaunch.getLunchPythonIngredients()==null) {

            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(getContext()));
            }
            Python python=Python.getInstance();
            PyObject myModule=python.getModule("chocoo");
            PyObject myfncall=myModule.get("calll");
            PyObject result= myfncall.call(70, 170,"lunch.csv");
            String f=result.toString();
            //  List<List<Map<String, String>>> Meals = parseJson(f);
            // List<Map<String, String>> meal = Meals.get(0);
            Type type = new TypeToken<List<UserMeal>>() {}.getType();

            // Deserialize JSON to List<Meal>
            List<UserMeal> meals = new Gson().fromJson(f, type);
//          Type type = new TypeToken<List<List<Map<String, String>>>>() {}.getType();
//          List<List<Map<String, String>>> meals = new Gson().fromJson(f, type);
            System.out.println(meals);
            List<PythonIngredient> pythonIngredients = new ArrayList<>();

           // pythonIngredients.add(meals.get(0).getIngredients().get(0));
            for(UserMeal meal : meals){
                for (PythonIngredient ingredient :meal.getIngredients()){
                    pythonIngredients.add(new PythonIngredient(ingredient.getName(),
                            ingredient.getCarbs(),
                            ingredient.getCalories(),
                            ingredient.getFats(),
                            ingredient.getProtein(),
                            ingredient.getCount(),
                            ingredient.getCategory()));
                }
                break;
            }

            pythonLaunch.setLunchPythonIngredients(pythonIngredients);
            changeMealSingelton.setMeals(meals);
        }
        List<PythonIngredient> breakfastIngredients = pythonLaunch.getLunchPythonIngredients();



        if (breakfastIngredients!= null) {
            RecyclerView recyclerView = view.findViewById(R.id.rv_lunch_suggested_meals);
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//            IngredientAdapter adapter = new IngredientAdapter(todaysIngredient, recyclerView, this);
//            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            IngredientAdapter adapter = new IngredientAdapter(breakfastIngredients, recyclerView, this);
            recyclerView.setAdapter(adapter);
        }
//        if (lunchSingleton.getLunchIngredients() != null) {
////            RecyclerView recyclerView = view.findViewById(R.id.rv_lunch_suggested_meals);
////            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////            IngredientAdapter adapter = new IngredientAdapter(todaysIngredient, recyclerView, this);
////            recyclerView.setAdapter(adapter);
//        }
        favourite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!lunch_fav_isClicked){
                    lunch_fav_isClicked = true;
                    favourite_btn.setImageResource(R.drawable.ic_favourite_red);
                }
                else{
                    lunch_fav_isClicked = false;
                    favourite_btn.setImageResource(R.drawable.ic_favourite_grey);
                }
            }
        });

        change_meal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Change_meal_Fragment change_meal_fragment = new Change_meal_Fragment();
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, change_meal_fragment).addToBackStack(null).commit();

                changeMealSingelton=ChangeMealSingelton.getInstance();
                pythonLaunch.setLunchPythonIngredients(changeMealSingelton.getMeals().get(changeMealSingelton.getNext()).getIngredients());
                changeMealSingelton.UpdateIndices();
            }
        });
    }

    @Override
    public void addItem(int position) {
       // if (lunchSingleton.getTodaysLunch() != null) {
            updateCaloriesProgress();
            updateCarbsProgress();
            updateProteinsProgress();
            updateFatsProgress();
       // }
    }

    @Override
    public void removeItem(int position) {
        //if (lunchSingleton.getTodaysLunch() != null) {
            updateCaloriesProgress();
            updateCarbsProgress();
            updateProteinsProgress();
            updateFatsProgress();
        //}

    }

    private void updateCaloriesProgress(){
        double caloriesProgress = (TodaysNutrientsEaten.getEatenCalories() / individualUser.getDaily_calories_need()) * 100;
        caloriesProgressBar.setProgress((int) caloriesProgress);
        String calories_left_string = getString(R.string.calories_progress, TodaysNutrientsEaten.getEatenCalories(), individualUser.getDaily_calories_need());
        textview_calories_progress.setText(calories_left_string);

//        double caloriesLeftValue = Math.max(User.getInstance().getDaily_calories_need() - TodaysNutrientsEaten.getEatenCalories(), 0);
//        String caloriesProgressText = Double.toString(Math.round(TodaysNutrientsEaten.getEatenCalories()*100.0)/100.0);
//        textview_calories_progress.setText(calories_left_string);
    }
    private void updateCarbsProgress(){
        double carbsProgress = (TodaysNutrientsEaten.getEatenCarbs() / individualUser.getDaily_carbs_need()) * 100;
        carbsProgressBar.setProgress((int) carbsProgress);
        String carbs_left_string = getString(R.string.carbs_progress, TodaysNutrientsEaten.getEatenCarbs(), individualUser.getDaily_carbs_need());
        textview_carbs_progress.setText(carbs_left_string);
//        String carbsProgressText = Double.toString(Math.round(TodaysNutrientsEaten.getEatenCarbs()*100.0)/100.0);
//        textview_carbs_progress.setText(carbsProgressText + "/" + "100");
    }
    private void updateProteinsProgress(){
        double proteinsProgress = (TodaysNutrientsEaten.getEatenProteins() / individualUser.getDaily_proteins_need()) * 100;
        proteinsProgressBar.setProgress((int) proteinsProgress);
        String proteins_left_string = getString(R.string.proteins_progress, TodaysNutrientsEaten.getEatenProteins(), individualUser.getDaily_proteins_need());
        textview_proteins_progress.setText(proteins_left_string);
//        String proteinsProgressText = Double.toString(Math.round(TodaysNutrientsEaten.getEatenProteins()*100.0)/100.0);
//        textview_proteins_progress.setText(proteinsProgressText + "/" + "100");
    }
    private void updateFatsProgress(){
        double fatsProgress = (TodaysNutrientsEaten.getEatenFats() / individualUser.getDaily_fats_need()) * 100;
        fatsProgressBar.setProgress((int) fatsProgress);
        String fats_left_string = getString(R.string.fats_progress, TodaysNutrientsEaten.getEatenFats(), individualUser.getDaily_fats_need());
        textview_fats_progress.setText(fats_left_string);

//        String fatsProgressText = Double.toString(Math.round(TodaysNutrientsEaten.getEatenFats()*100.0)/100.0);
//        textview_fats_progress.setText(fatsProgressText + "/" + "100");
    }
}