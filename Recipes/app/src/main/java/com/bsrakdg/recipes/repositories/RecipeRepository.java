package com.bsrakdg.recipes.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient recipeApiClient;

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
}
