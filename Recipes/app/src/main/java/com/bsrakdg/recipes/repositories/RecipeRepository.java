package com.bsrakdg.recipes.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.requests.RecipeApiClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeApiClient recipeApiClient;

    // pagination
    private String query;
    private int pageNumber;

    private MutableLiveData<Boolean> isQueryExhausted = new MutableLiveData<>();

    // to observe recipeApiClient.getRecipes() with MediatorLiveData
    // it needed for if recipes is null look at database cache
    private MediatorLiveData<List<Recipe>> mediatorRecipes = new MediatorLiveData<>();

    private RecipeRepository() {
        recipeApiClient = RecipeApiClient.getInstance();
        initMediators();
    }

    public static RecipeRepository getInstance() {
        if (instance == null) {
            instance = new RecipeRepository();
        }
        return instance;
    }

    private void doneQuery(List<Recipe> list) {
        if (list != null) {
            if (list.size() < 30) {
                isQueryExhausted.setValue(true);
            }
        } else {
            isQueryExhausted.setValue(true);
        }
    }

    private void initMediators() {
        // observe recipeApiClient.getRecipes() live data for decide to show data from local or remote
        mediatorRecipes.addSource(recipeApiClient.getRecipes(), recipes -> {
            if (recipes != null) {
                mediatorRecipes.setValue(recipes);
                doneQuery(recipes);
            } else {
                // search database cache
                doneQuery(null);
            }
        });
    }

    public void cancelRequest() {
        recipeApiClient.cancelRequest();
    }

    public LiveData<Recipe> getRecipe() {
        return recipeApiClient.getRecipe();
    }

    public LiveData<Boolean> getRecipeRequestTimeout() {
        return recipeApiClient.getRecipeRequestTimeout();
    }

    public LiveData<List<Recipe>> getRecipes() {
        return mediatorRecipes;
    }

    public LiveData<Boolean> isQueryExhausted() {
        return isQueryExhausted;
    }

    public void searchNextPage() {
        searchRecipesApi(query, pageNumber + 1);
    }

    public void searchRecipeById(String recipeId) {
        recipeApiClient.getRecipe(recipeId);
    }

    public void searchRecipesApi(String query, int pageNumber) {
        // api needed
        if (pageNumber == 0) {
            pageNumber = 1;
        }
        this.query = query; // pagination
        this.pageNumber = pageNumber; //pagination
        isQueryExhausted.setValue(false);
        recipeApiClient.searchRecipesApi(query, pageNumber);
    }
}
