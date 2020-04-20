package com.bsrakdg.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private RecipeRepository recipeRepository;

    // firstly show categories
    private boolean isViewingRecipes = false; // needed show category list
    private boolean isPerformingQuery = false; // cancel request

    public RecipeListViewModel() {
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<List<Recipe>> getRecepies() {
        return recipeRepository.getRecipes();
    }

    public RecipeRepository getRecipeRepository() {
        return recipeRepository;
    }

    public void setRecipeRepository(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    public boolean isPerformingQuery() {
        return isPerformingQuery;
    }

    public void setPerformingQuery(boolean performingQuery) {
        isPerformingQuery = performingQuery;
    }

    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }

    public boolean onBackPressed() {
        if (isPerformingQuery) {
            //cancel the query
            recipeRepository.cancelRequest();
            isPerformingQuery = false;
        }
        if (isViewingRecipes) {
            isViewingRecipes = false;
            return false;
        }
        return true;
    }

    public void searchRecipesApi(String query, int pageNumber) {
        isViewingRecipes = true; // don't show category
        isPerformingQuery = true;
        recipeRepository.searchRecipesApi(query, pageNumber);
    }
}
