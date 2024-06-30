package com.example.healthfriend.UserScreens.Fragments;

import android.os.Bundle;
import android.util.Log;
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

import com.example.healthfriend.R;
import com.example.healthfriend.UserScreens.Adapters.IngredientAdapter;
import com.example.healthfriend.UserScreens.DayMealManager;
import com.example.healthfriend.UserScreens.DoctorMealAdapterInterface;
import com.example.healthfriend.Models.PythonIngredient;
import com.example.healthfriend.UserScreens.PythonBreakfast;
import com.example.healthfriend.UserScreens.PythonDinner;
import com.example.healthfriend.UserScreens.PythonLunch;
import com.example.healthfriend.UserScreens.TodaysNutrientsEaten;
import com.example.healthfriend.UserScreens.IndividualUser;

import java.util.List;


public class LunchFragment extends Fragment implements DoctorMealAdapterInterface {
    IndividualUser individualUser = IndividualUser.getInstance();
    IngredientAdapter adapter;
    private String mealType;
    private ProgressBar caloriesProgressBar, carbsProgressBar , proteinsProgressBar, fatsProgressBar;
    private ImageButton fav_ingredient,change_meal_btn;
    private TextView meal_name,textview_calories_progress, textview_carbs_progress, textview_proteins_progress, textview_fats_progress;
    //private PythonBreakfast pythonBreakfast;
    private PythonLunch pythonLunch;
    private PythonBreakfast pythonBreakfast;
    private PythonDinner pythonDinner;
    DayMealManager dayMealManager;
    List<PythonIngredient> mealIngredients;

    public LunchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("lunchfrag",""+ getArguments());
        if (getArguments() != null) {
            mealType = getArguments().getString("mealType");
        }
        if (mealType == null) {
            // Set a default value or handle the error as appropriate
            mealType = "dinner";
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lunch, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
          initUI(view);
        mealIngredients = null;
        dayMealManager = DayMealManager.getInstance(getContext());
          switch (mealType){
              case "breakfast":
                  meal_name.setText("Breakfast");
                  dayMealManager.setCurrent_meal("breakfast");
                  getBreakfast();
                  mealIngredients  = pythonBreakfast.getBreakfastPythonIngredients();
                  break;
              case "lunch":
                  meal_name.setText("Lunch");
                  dayMealManager.setCurrent_meal("lunch");
                  getLunch();
                  mealIngredients  = pythonLunch.getLunchPythonIngredients();
                  break;
              case "dinner":
                  meal_name.setText("Dinner");
                  dayMealManager.setCurrent_meal("dinner");
                  getDinner();
                  mealIngredients  = pythonDinner.getDinnerPythonIngredients();
                  break;
              default:
                  getBreakfast();
                  break;
          }



//        dayMealManager.setPythonLunch();
//        List<DoctorIngredient> dd =  IndividualUser.getInstance().getWeeklyPlan().getDailyPlans().get(0).getLunch().getIngredients();
//        List<PythonIngredient> breakfastIngredients = DayMealManager.getInstance(getContext()).getPythonLaunch().getLunchPythonIngredients();



        if (mealIngredients!= null) {
            RecyclerView recyclerView = view.findViewById(R.id.rv_lunch_suggested_meals);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new IngredientAdapter(mealIngredients, recyclerView, this);
            recyclerView.setAdapter(adapter);
        }
//        dayMealManager.dayAppearedIngredients();
//        dayMealManager.dayRejectedIngredients();

        change_meal_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Change_meal_Fragment fragment = new Change_meal_Fragment();
                    Bundle bundle = new Bundle();
                    bundle.putString("mealType", mealType);
                    fragment.setArguments(bundle);
                    requireActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.home_frame_layout, fragment)
                            .addToBackStack(null)
                            .commit();

//                Change_meal_Fragment change_meal_fragment = new Change_meal_Fragment();
//                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, change_meal_fragment).addToBackStack(null).commit();

//                changeMealSingelton=ChangeMealSingelton.getInstance();
//                pythonLunch.setLunchPythonIngredients(changeMealSingelton.getMeals().get(changeMealSingelton.getNext()).getIngredients());
//                changeMealSingelton.UpdateIndices();
            }
        });
        fav_ingredient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fav_ingredient_Fragment fav_fragment = new fav_ingredient_Fragment();
                Bundle bundle = new Bundle();
                bundle.putString("mealTypee", mealType); // Replace "breakfast" with the actual meal type
                // fav_ingredient_Fragment fragment = new fav_ingredient_Fragment();
                fav_fragment.setArguments(bundle);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame_layout, fav_fragment).addToBackStack(null).commit();
            }
        });
    }

    private void  initUI(View view){
//        ImageButton favourite_btn = view.findViewById(R.id.lunch_btn_add_to_favourite);
        meal_name = view.findViewById(R.id.meal_name);
        change_meal_btn = view.findViewById(R.id.lunch_btn_change_meal);
        caloriesProgressBar = view.findViewById(R.id.lunch_calories_progressbar);
        carbsProgressBar = view.findViewById(R.id.lunch_carbs_progressbar);
        proteinsProgressBar = view.findViewById(R.id.lunch_proteins_progressbar);
        fatsProgressBar = view.findViewById(R.id.lunch_fats_progressbar);
        textview_calories_progress = view.findViewById(R.id.lunch_textview_calories_progress);
        textview_carbs_progress = view.findViewById(R.id.lunch_carbs_progress);
        textview_proteins_progress = view.findViewById(R.id.lunch_textview_proteins_progress);
        textview_fats_progress = view.findViewById(R.id.lunch_textview_fats_progress);
        fav_ingredient=view.findViewById(R.id.fav_ingredient);

        updateCaloriesProgress(); updateCarbsProgress(); updateProteinsProgress(); updateFatsProgress();
    }

    private void getBreakfast(){
        pythonBreakfast = PythonBreakfast.getInstance();
        if(dayMealManager.isDoctorPlanApplied())
            dayMealManager.setDoctorBreakfast();
        else{
//            dayMealManager.setPythonBreakfast();
        }
    }
    private void getLunch(){
        pythonLunch = PythonLunch.getInstance();
        if(dayMealManager.isDoctorPlanApplied())
            dayMealManager.setDoctorLunch();
        else{
//            dayMealManager.setPythonLunch();
        }
    }
    private void getDinner(){
        pythonDinner = PythonDinner.getInstance();
        if(dayMealManager.isDoctorPlanApplied())
            dayMealManager.setDoctorDinner();
        else{
//            dayMealManager.setPythonDinner();
        }
    }

    @Override
    public void addItem(int position) {
       // if (lunchSingleton.getTodaysLunch() != null) {
            updateCaloriesProgress();
            updateCarbsProgress();
            updateProteinsProgress();
            updateFatsProgress();
            DayMealManager.getInstance(getContext()).setPythonLunch(PythonLunch.getInstance());
       // }
    }

    @Override
    public void removeItem(int position) {
        //if (lunchSingleton.getTodaysLunch() != null) {
            updateCaloriesProgress();
            updateCarbsProgress();
            updateProteinsProgress();
            updateFatsProgress();
        DayMealManager.getInstance(getContext()).setPythonLunch(PythonLunch.getInstance());

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

    @Override
    public void onResume() {
        super.onResume();
        updateCaloriesProgress(); updateCarbsProgress(); updateProteinsProgress(); updateFatsProgress();

        if (mealIngredients!= null) {
            RecyclerView recyclerView = getView().findViewById(R.id.rv_lunch_suggested_meals);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            adapter = new IngredientAdapter(mealIngredients, recyclerView, this);
            recyclerView.setAdapter(adapter);
        }
    }
}