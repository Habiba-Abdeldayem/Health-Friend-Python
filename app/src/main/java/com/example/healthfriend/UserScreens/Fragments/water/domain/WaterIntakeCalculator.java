package com.example.healthfriend.UserScreens.Fragments.water.domain;

public class WaterIntakeCalculator {

    public static double calculateRecommendedWaterIntake(double weightInKg) {
        final double BASE_FACTOR = 0.3;
        return weightInKg * BASE_FACTOR;
    }
}
