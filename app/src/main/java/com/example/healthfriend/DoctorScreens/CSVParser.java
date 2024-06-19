package com.example.healthfriend.DoctorScreens;

import android.content.Context;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

public class CSVParser {

    // Method to get unique categories from the CSV file
    public static Set<String> getCategories(InputStream inputStream) {
        Set<String> categories = new HashSet<>();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            boolean headerSkipped = false; // Skip the header row

            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 22) { // Assuming 'category' is the 23rd column
                    String category = parts[21].trim(); // Adjust index as per your CSV structure
                    categories.add(category);
                }
            }

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categories;
    }
    public static Set<String> getingraidiant(InputStream inputStream, String category) {
        Set<String> ingradiant = new HashSet<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean headerSkipped = false; // Skip the header row

            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] parts = line.split(",");
                if (parts.length >= 22 && parts[21].trim().equals(category)) { // Assuming 'category' is the 23rd column
                    String ing = parts[0].trim(); // Adjust index as per your CSV structure
                    ingradiant.add(ing);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return ingradiant;
    }
}
