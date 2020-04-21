package com.bsrakdg.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.repositories.RecipeRepository;

public class RecipeDetailViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private String recipeId;

    public RecipeDetailViewModel() {
        this.recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getRecipe();
    }

    public String getRecipeId() {
        return recipeId;
    }

    public void searchRecipeById(String recipeId) {
        this.recipeId = recipeId;
        recipeRepository.searchRecipeById(recipeId);
    }
}
