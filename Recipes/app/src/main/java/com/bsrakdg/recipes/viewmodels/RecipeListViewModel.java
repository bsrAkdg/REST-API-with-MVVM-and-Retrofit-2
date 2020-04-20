package com.bsrakdg.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository recipeRepository;

    // firstly show categories
    private boolean isViewingRecipes = false; // for needed show category list

    public RecipeListViewModel() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }

    public LiveData<List<Recipe>> getRecepies() {
        return recipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query, int pageNumber) {
        isViewingRecipes = true; // don't show category
        recipeRepository.searchRecipesApi(query, pageNumber);
    }
}
