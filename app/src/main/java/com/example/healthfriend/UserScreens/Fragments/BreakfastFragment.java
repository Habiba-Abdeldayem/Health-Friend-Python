package com.example.healthfriend.UserScreens.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.healthfriend.Models.UserMeal;
import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Adapters.IngredientAdapter;
import com.example.healthfriend.UserScreens.DoctorMealAdapterInterface;
import com.example.healthfriend.UserScreens.PythonBreakfast;
import com.example.healthfriend.Models.PythonIngredient;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import com.example.healthfriend.UserScreens.IndividualUser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;



public class BreakfastFragment extends Fragment implements DoctorMealAdapterInterface {
    boolean breakfast_fav_isClicked = false;
    private PythonBreakfast pythonBreakfast;
    private ProgressBar caloriesProgressBar, carbsProgressBar , proteinsProgressBar, fatsProgressBar;
    private TextView textview_calories_progress, textview_carbs_progress, textview_proteins_progress, textview_fats_progress,fav_ingredient;
    IndividualUser individualUser = IndividualUser.getInstance();

    public BreakfastFragment() {
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
        return inflater.inflate(R.layout.fragment_breakfast, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageButton favourite_btn = view.findViewById(R.id.breakfast_btn_add_to_favourite);
        ImageButton change_meal_btn = view.findViewById(R.id.breakfast_btn_change_meal);
        caloriesProgressBar = view.findViewById(R.id.breakfast_calories_progressbar);
        carbsProgressBar = view.findViewById(R.id.breakfast_carbs_progressbar);
        proteinsProgressBar = view.findViewById(R.id.breakfast_proteins_progressbar);
        fatsProgressBar = view.findViewById(R.id.breakfast_fats_progressbar);
        textview_calories_progress = view.findViewById(R.id.breakfast_textview_calories_progress);
        textview_carbs_progress = view.findViewById(R.id.breakfast_textview_carbs_progress);
        textview_proteins_progress = view.findViewById(R.id.breakfast_textview_proteins_progress);
        textview_fats_progress = view.findViewById(R.id.breakfast_textview_fats_progress);
        fav_ingredient=view.findViewById(R.id.fav_ingredient);
        updateCaloriesProgress(); updateCarbsProgress(); updateProteinsProgress(); updateFatsProgress();

        pythonBreakfast = PythonBreakfast.getInstance();
        if(pythonBreakfast.getBreakfastPythonIngredients()==null){
        if (! Python.isStarted()) {
            Python.start(new AndroidPlatform(getContext()));
        }
        Python python=Python.getInstance();
        PyObject myModule=python.getModule("chocoo");
        PyObject myfncall=myModule.get("calll");
        PyObject result= myfncall.call(70, 170,"breakfast.csv");
        String f=result.toString();
        //  List<List<Map<String, String>>> Meals = parseJson(f);
        // List<Map<String, String>> meal = Meals.get(0);
//        Type type = new TypeToken<List<List<Map<String, String>>>>() {}.getType();
//        List<List<Map<String, String>>> meals = new Gson().fromJson(f, type);
//        System.out.println(meals);
//        List<PythonIngredient> pythonIngredients = new ArrayList<>();
        Type type = new TypeToken<List<UserMeal>>() {}.getType();

            // Deserialize JSON to List<Meal>
        List<UserMeal> meals = new Gson().fromJson(f, type);
//        Type type = new TypeToken<List<List<Map<String, String>>>>() {}.getType();
//        List<List<Map<String, String>>> meals = new Gson().fromJson(f, type);
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
        // Adding elements to the list
//        meals.get(0).size();
//        for(int i=0;i<meals.get(0).size();i++) {
//            pythonIngredients.add(new PythonIngredient(meals.get(0).get(i).get("ingredient"), Double.valueOf(meals.get(0).get(i).get("carbohydrates")), Double.valueOf(meals.get(0).get(i).get("energy")), Double.valueOf(meals.get(0).get(i).get("fat")), Double.valueOf(meals.get(0).get(i).get("protein"))));
////            pythonIngredients.add(new PythonIngredient("Ingredient 2", 15.0, 150.0, 7.0, 25.0));
////            pythonIngredients.add(new PythonIngredient("Ingredient 3", 20.0, 200.0, 10.0, 30.0));
//        }
            //pythonIngredients.add(new PythonIngredient(meals.get(0).getIngredients().get(0).getName(), meals.get(0).getIngredients().get(0).getCarbs(), meals.get(0).getIngredients().get(0).getCalories(), meals.get(0).getIngredients().get(0).getFats(), meals.get(0).getIngredients().get(0).getProtein(),meals.get(0).getIngredients().get(0).getCount(),meals.get(0).getIngredients().get(0).getCategory()));

            pythonBreakfast.setBreakfastPythonIngredients(pythonIngredients);
        }
        List<PythonIngredient> breakfastIngredients = pythonBreakfast.getBreakfastPythonIngredients();

//        breakfastSingleton = TodaysBreakfastSingleton.getInstance();
//        List<IngredientModel> todaysIngredient = breakfastSingleton.getBreakfastIngredients();

        if (breakfastIngredients != null) {
            RecyclerView recyclerView = view.findViewById(R.id.rv_breakfast_suggested_meals);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            IngredientAdapter adapter = new IngredientAdapter(breakfastIngredients, recyclerView, this);
            recyclerView.setAdapter(adapter);
        }

        favourite_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!breakfast_fav_isClicked) {
                    breakfast_fav_isClicked = true;
                    favourite_btn.setImageResource(R.drawable.ic_favourite_red);
                } else {
                    breakfast_fav_isClicked = false;
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
        fav_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav_ingredient_Fragment fav_fragment = new fav_ingredient_Fragment();
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, fav_fragment).addToBackStack(null).commit();
            }
        });
    }

    @Override
    public void addItem(int position) {
//        if (pythonBreakfast.getTodaysBreakfast() != null) {
            updateCaloriesProgress();
            updateCarbsProgress();
            updateProteinsProgress();
            updateFatsProgress();
//        }
    }

    @Override
    public void removeItem(int position) {
//        if (breakfastSingleton.getTodaysBreakfast() != null) {
            updateCaloriesProgress();
            updateCarbsProgress();
            updateProteinsProgress();
            updateFatsProgress();
//        }

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