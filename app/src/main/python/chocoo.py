import pandas as pd
import networkx as nx
from sklearn.metrics.pairwise import cosine_similarity
import numpy as np
from os.path import dirname, join
import random
import json


# Step 1: Read the CSV file into a DataFrame
def calll(weight, height, filepath, new_ingredients=['Beef  brain']):
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
                                          current_fat=0, current_carbs=0, used_ingredients=set(), meals=[],
                                          ingredient_counts = {}, vegetables=False, macaroni=False):
        current_meal_tuple = tuple(sorted(current_meal))  # Sort the ingredients to create a hashable representation

        # Check if the current state is in the memoization cache
        if current_meal_tuple in memoization_cache:
            return memoization_cache[current_meal_tuple]

        # Termination condition: Stop if the maximum depth is reached
        if current_calories >= target_calories[1]:
            return []

        # Base case: If the current meal satisfies the criteria, add it to the list of meals
        if (target_calories[0] <= current_calories <= target_calories[1] and
                target_protein[0] <= current_protein <= target_protein[1] and
                target_fat[0] <= current_fat <= target_fat[1] and
                target_carbs[0] <= current_carbs <= target_carbs[1]):
            if current_meal_tuple not in used_ingredients:
                meal_info = []
                # for ingredient in current_meal:
                #     ingredient_data = G.nodes[ingredient]
                #     ingredient_info = {
                #         'ingredient': ingredient,
                #         'category': ingredient_data['category'],
                #         'energy': ingredient_data['energy'],
                #         'protein': ingredient_data['protein'],
                #         'fat': ingredient_data['fat'],
                #         'carbohydrates': ingredient_data['carbohydrates']
                #     }

                for ingredient, count in ingredient_counts.items():
                    if count > 0 :
                        ingredient_data = G.nodes[ingredient]
                        ingredient_info = {
                            'ingredient': ingredient,
                            'quantity': count,
                            'energy': ingredient_data['energy'],
                            'protein': ingredient_data['protein'],
                            'fat': ingredient_data['fat'],
                            'carbohydrates': ingredient_data['carbohydrates'],
                            'category': ingredient_data['category']
                        }
                    meal_info.append(ingredient_info)

                meals.append(meal_info)
                memoization_cache[current_meal_tuple] = meals[-1]
                used_ingredients.add(current_meal_tuple)  # Add the meal to used meals
                return meals[-1]

        # Explore possible combinations of ingredients
        for ingredient in G.nodes():
            # print (ingredient)
            if ingredient not in current_meal or ingredient in new_ingredients:
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
                if any(ingredient not in new_ingredients and G.nodes[meal_ingredient]['category'] == ingredient_category for meal_ingredient in current_meal):
                    continue
                if ingredient_category == 'vegetables' and not macaroni:
                    vegetables = True
                elif ingredient_category == 'Macaroni' and not vegetables:
                    macaroni = True
                elif ingredient_category == 'vegetables' and macaroni:
                    continue
                elif ingredient_category == 'Macaroni' and vegetables:
                    continue

                current_meal.append(ingredient)
                if ingredient not in ingredient_counts:
                    ingredient_counts[ingredient] = 0
                # if ingredient in new_ingredients:
                ingredient_counts[ingredient] += 1

                generate_meals_memoized_optimized(G, target_calories, target_protein, target_fat, target_carbs,
                                                  current_meal, new_calories, new_protein, new_fat, new_carbs,
                                                  used_ingredients, meals, ingredient_counts, vegetables, macaroni)

                current_meal.pop()
                if ingredient_counts[ingredient]==1:
                    del ingredient_counts[ingredient]
                else:
                    ingredient_counts[ingredient] -= 1

        memoization_cache[current_meal_tuple] = meals
        return meals

    # Step 7: Define the function to calculate target nutritional values
    def calc_info(weight, height):
        cal = weight * 30 / 3
        pro = (cal * 0.2) / 4
        fats = (cal * 0.3) / 9
        carbs = (cal * 0.5) / 4
        target_calories = (cal - (cal * 0.15), cal + (cal * 0.15))  # Example: Target calorie range for a meal
        target_protein = (pro - (pro * 0.15), pro + (pro * 0.15))  # Example: Minimum protein content for a meal
        target_fat = (fats - (fats * 0.15), fats + (fats * 0.15))  # Example: Maximum fat content for a meal
        target_carbs = (carbs - (carbs * 0.15), carbs + (carbs * 0.15))

        meals = generate_meals_memoized_optimized(G, target_calories, target_protein, target_fat, target_carbs)
        return meals

    # Step 8: Generate meals based on the optimized memoized function
    meals = calc_info(70, height)

    # Create a list of unique ingredients
    unique_ingredients = list(set(ingredient['ingredient'] for meal in meals for ingredient in meal).union(
        set(new_ingredients)))

    # Step 9: Convert meals and new ingredients to vectors
    def meal_to_vector(meal, unique_ingredients):
        vector = np.zeros(len(unique_ingredients))
        for ingredient_info in meal:
            ingredient = ingredient_info['ingredient']
            quantity = ingredient_info['quantity']
            if ingredient in unique_ingredients:
                index = unique_ingredients.index(ingredient)
                vector[index] = quantity
        return vector

    def ingredients_to_vector(ingredients, unique_ingredients):
        vector = np.zeros(len(unique_ingredients))
        for ingredient in ingredients:
            if ingredient in unique_ingredients:
                index = unique_ingredients.index(ingredient)
                vector[index] += 1
        return vector

    meal_vectors = np.array([meal_to_vector(meal, unique_ingredients) for meal in meals])
    new_ingredients_vector = ingredients_to_vector(new_ingredients, unique_ingredients)

    # Step 10: Compute cosine similarity
    similarity_scores = cosine_similarity([new_ingredients_vector], meal_vectors)[0]

    # Step 11: Pair each meal with its similarity score and store in a dictionary
    # meal_similarity_dict = {}
    # for meal, score in zip(meals, similarity_scores):
    #     meal_key = tuple(sorted(ingredient['ingredient'] for ingredient in meal))
    #     meal_similarity_dict[meal_key] = {
    #         'similarity_score': score,
    #         'ingredients': meal
    #     }
    #
    # # Step 12: Sort the meals by similarity score in descending order
    # sorted_meal_similarity_dict = dict(sorted(meal_similarity_dict.items(), key=lambda item: item[1]['similarity_score'], reverse=True))
    meal_similarity_list = []
    for meal, score in zip(meals, similarity_scores):
        meal_info = {
            'similarity_score': score,
            'ingredients': meal
        }
        meal_similarity_list.append(meal_info)

    # Step 12: Sort the meals by similarity score in descending order
    sorted_meal_similarity_list = sorted(meal_similarity_list, key=lambda item: item['similarity_score'], reverse=True)


    # sorted_meal_similarity_list=sorted_meal_similarity_list[:10]
    json_data = json.dumps(sorted_meal_similarity_list)
    return json_data

    #return sorted_meal_similarity_dict

# # Example call to the function
# result = calll(70, 170, "lunch.csv")
# print(len(result))
# # Print the first 30 meals and their similarity scores with quantities
# i = 0
# for meal, info in result.items():
#     i += 1
#     if i > 30:
#         break
#     print(f"Meal: {meal}")
#     print(f"Similarity Score: {info['similarity_score']}")
#     print("Ingredients:")
#     for ingredient in info['ingredients']:
#         print(f"- {ingredient['ingredient']}: {ingredient['quantity']}")
#     print("---------------------------")
