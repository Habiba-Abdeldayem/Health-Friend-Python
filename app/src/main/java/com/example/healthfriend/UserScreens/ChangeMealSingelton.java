package com.example.healthfriend.UserScreens;

import com.example.healthfriend.Models.UserMeal;

import java.util.List;

public class ChangeMealSingelton {
    private static ChangeMealSingelton instance;

    private  List<UserMeal> meals;

    public  List<UserMeal> getMeals() {
        return meals;
    }

    public  void setMeals(List<UserMeal> meals) {
        this.meals = meals;
    }

    public  int getPrevious() {
        return previous;
    }

    public  void setPrevious(int previous) {
        this.previous = previous;
    }

    public  int getNext() {
        next=current+1;
        return next;
    }

    public  void setNext(int next) {
        this.next = next;
    }

    public  int getCurrent() {
        return current;
    }

    public  void setCurrent(int current) {
        this.current = current;
    }

    public  void UpdateIndices() {
        this.setPrevious(current);
        this.setCurrent(next);
    }

    private  int previous;
    private  int next;
    private  int current;

    private ChangeMealSingelton() {
        this.current=0;

    }

    public static  ChangeMealSingelton getInstance() {
        if (instance == null) {
            instance = new ChangeMealSingelton();
        }
        return instance;
    }

}
