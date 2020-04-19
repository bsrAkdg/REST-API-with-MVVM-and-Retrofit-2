package com.bsrakdg.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository recipeRepository;

    public RecipeListViewModel() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecepies() {
        return recipeRepository.getRecipes();
    }

    public void searchRecipesApi(String query, int pageNumber) {
        recipeRepository.searchRecipesApi(query, pageNumber);
    }
}
