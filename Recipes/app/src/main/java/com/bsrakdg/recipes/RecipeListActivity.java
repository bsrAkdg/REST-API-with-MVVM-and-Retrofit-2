package com.bsrakdg.recipes;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.bsrakdg.recipes.util.Testing;
import com.bsrakdg.recipes.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel recipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // inital view model
        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        // observe view model changes
        subscribeObservers();

        findViewById(R.id.test).setOnClickListener(view -> testRetrofitRequest());
    }

    private void searchRecipesApi(String query, int pageNumber) {
        recipeListViewModel.searchRecipesApi(query, pageNumber);
    }

    private void subscribeObservers() {
        recipeListViewModel.getRecepies().observe(this, recipes -> {
            // trigger updated, deleted, anything changes
            Testing.printRecipes(recipes, TAG);
        });
    }

    private void testRetrofitRequest() {
        searchRecipesApi("chicken breast", 0);
    }
}
