package com.bsrakdg.recipes.repositories;

import androidx.lifecycle.LiveData;

import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient recipeApiClient;

    // pagination
    private String query;
    private int pageNumber;

    private RecipeRepository() {
        recipeApiClient = RecipeApiClient.getInstance();
    }

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipeApiClient.getRecipes();
    }

    public LiveData<Recipe> getRecipe() {
        return recipeApiClient.getRecipe();
    }

    public LiveData<Boolean> getRecipeRequestTimeout() {
        return recipeApiClient.getRecipeRequestTimeout();
    }

    public void searchNextPage() {
        searchRecipesApi(query, pageNumber + 1);
    }

    public void searchRecipesApi(String query, int pageNumber) {
        // api needed
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        this.query = query; // pagination
        this.pageNumber = pageNumber; //pagination
        recipeApiClient.searchRecipesApi(query, pageNumber);
    }

    public void searchRecipeById(String recipeId) {
        recipeApiClient.getRecipe(recipeId);
    }

    public void cancelRequest() {
        recipeApiClient.cancelRequest();
    }
}
