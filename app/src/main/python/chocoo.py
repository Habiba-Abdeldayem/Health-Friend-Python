import pandas as pd
import networkx as nx
from os.path import dirname, join
import random
import json


# Step 1: Read the CSV file into a DataFrame

def calll(weight, height,filepath):
    filename = join(dirname(__file__), filepath)
    data = pd.read_csv(filename)

    # Step 2: Create a graph
    G = nx.Graph()

    # Step 3: Add nodes (ingredients) to the graph with categories
    for index, row in data.iterrows():
        ingredient = row['ingredients']
        category = row['category']
        G.add_node(ingredient, category=category, energy=row['Energy (Kcal)'],
                   protein=row['Protein (g)'], fat=row['Fat (g)'],
                   carbohydrates=row['Carboyhdrates (g)'])

    # Step 4: Create edges between ingredients from different categories
    for node1, data1 in G.nodes(data=True):
        for node2, data2 in G.nodes(data=True):
            if data1['category'] != data2['category']:
                G.add_edge(node1, node2)

    # Step 5: Define the memoization cache using a regular dictionary
    memoization_cache = {}

    # Step 6: Define the memoized function to generate meals with optimizations
    def generate_meals_memoized_optimized(G, target_calories, target_protein, target_fat, target_carbs,
                                          current_meal=[], current_calories=0, current_protein=0,
                                          current_fat=0, current_carbs=0, used_ingredients=set(), meals=[]):
        current_meal_tuple = tuple(sorted(current_meal))  # Sort the ingredients to create a hashable representation

        # Check if the current state is in the memoization cache
        if current_meal_tuple in memoization_cache:
            return memoization_cache[current_meal_tuple]

        # Termination condition: Stop if the maximum depth is reached
        if current_calories >= target_calories[1] or len(meals) >= 3:
            return []

        # Base case: If the current meal satisfies the criteria, add it to the list of meals
        if (target_calories[0] <= current_calories <= target_calories[1] and
                target_protein[0] <= current_protein <= target_protein[1] and
                target_fat[0] <= current_fat <= target_fat[1] and
                target_carbs[0] <= current_carbs <= target_carbs[1]):
            # Ensure the meal has not been used before
            if current_meal_tuple not in used_ingredients:
                meal_info = []
                for ingredient in current_meal:
                    ingredient_data = G.nodes[ingredient]
                    ingredient_info = {
                        'ingredient': ingredient,
                        'category': ingredient_data['category'],
                        'energy': ingredient_data['energy'],
                        'protein': ingredient_data['protein'],
                        'fat': ingredient_data['fat'],
                        'carbohydrates': ingredient_data['carbohydrates']
                    }
                    meal_info.append(ingredient_info)
                    # Update the count for the ingredient
                meals.append(meal_info)
                memoization_cache[current_meal_tuple] = meals[-1]
                used_ingredients.add(current_meal_tuple)  # Add the meal to used meals
            return meals[-1]

        # Explore possible combinations of ingredients
        for ingredient in G.nodes():
            # Check if the ingredient has already been used 5 times
            if ingredient not in current_meal:
                ingredient_data = G.nodes[ingredient]
                new_calories = current_calories + ingredient_data['energy']
                new_protein = current_protein + ingredient_data['protein']
                new_fat = current_fat + ingredient_data['fat']
                new_carbs = current_carbs + ingredient_data['carbohydrates']

                # Prune unnecessary branches
                if (new_calories > target_calories[1] or new_protein > target_protein[1] or
                        new_fat > target_fat[1] or new_carbs > target_carbs[1]):
                    continue

                # Ensure that the selected ingredient is from a different category
                ingredient_category = ingredient_data['category']
                if any(G.nodes[meal_ingredient]['category'] == ingredient_category for meal_ingredient in current_meal):
                    continue

                current_meal.append(ingredient)

                generate_meals_memoized_optimized(G, target_calories, target_protein, target_fat, target_carbs,
                                                  current_meal, new_calories, new_protein, new_fat, new_carbs,
                                                  used_ingredients, meals)

                current_meal.pop()

        # Store the result in the memoization cache before returning
        memoization_cache[current_meal_tuple] = meals
        return meals
    # Step 7: Define the function to calculate target nutritional values
    def calc_info(weight, height):
        cal = weight * 30 / 3
        pro = (cal * 0.2) / 4
        fats = (cal * 0.3) / 9
        carbs = (cal * 0.5) / 4
        target_calories = (cal - (cal * 0.15), cal + (cal * 0.15))  # Example: Target calorie range for a meal
        target_protein = (pro - (pro * 0.75), pro + (pro * 0.15))  # Example: Minimum protein content for a meal
        target_fat = (fats - (fats * 0.75), fats + (fats * 0.15))  # Example: Maximum fat content for a meal
        target_carbs = (carbs - (carbs * 0.75), carbs + (carbs * 0.15))

        meals= generate_meals_memoized_optimized(G, target_calories, target_protein, target_fat, target_carbs)
        return meals

    # Step 8: Generate meals based on the optimized memoized function
    meals =  calc_info(70, 170)


    # for idx, meal in enumerate(meals):
    #     print(f"Meal {idx + 1}:")
    #     for ingredient in meal:
    #         print(f"- {ingredient}")
    #     print("Total Nutritional Information:")
    #     total_calories = sum(G.nodes[ingredient]['energy'] for ingredient in meal)
    #     total_protein = sum(G.nodes[ingredient]['protein'] for ingredient in meal)
    #     total_fat = sum(G.nodes[ingredient]['fat'] for ingredient in meal)
    #     total_carbohydrates = sum(G.nodes[ingredient]['carbohydrates'] for ingredient in meal)
    #     print(f"Total Calories: {total_calories} kcal")
    #     print(f"Total Protein: {total_protein} g")
    #     print(f"Total Fat: {total_fat} g")
    #     print(f"Total Carbohydrates: {total_carbohydrates} g")
    #     print()

    # Select 15 items randomly from the original list
    #selected_meals = random.sample(meals, 5)

    json_data = json.dumps(meals)
    return json_data

# Step 9: Output the generated meals with their nutritional information
