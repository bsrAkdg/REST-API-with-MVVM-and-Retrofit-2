package com.bsrakdg.recipes.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bsrakdg.recipes.models.Recipe;

import java.util.List;

public class RecipeApiClient {
    // Remote Data Source

    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> recipes;

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    private RecipeApiClient() {
        recipes = new MutableLiveData<>();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }
}
