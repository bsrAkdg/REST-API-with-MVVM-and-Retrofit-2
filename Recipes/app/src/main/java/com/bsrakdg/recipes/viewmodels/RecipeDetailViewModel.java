package com.bsrakdg.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.repositories.RecipeRepository;

public class RecipeDetailViewModel extends ViewModel {
    private RecipeRepository recipeRepository;

    public RecipeDetailViewModel() {
        this.recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipe() {
        return recipeRepository.getRecipe();
    }

    public void searchRecipeById(String recipeId) {
        recipeRepository.searchRecipeById(recipeId);
    }
}
