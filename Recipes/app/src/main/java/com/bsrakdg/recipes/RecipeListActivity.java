package com.bsrakdg.recipes;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bsrakdg.recipes.adapters.OnRecipeListener;
import com.bsrakdg.recipes.adapters.RecipeRecyclerAdapter;
import com.bsrakdg.recipes.util.Testing;
import com.bsrakdg.recipes.viewmodels.RecipeListViewModel;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {
    private static final String TAG = "RecipeListActivity";

    private RecipeListViewModel recipeListViewModel;
    private RecyclerView recipeRecyclerView;
    private RecipeRecyclerAdapter recipeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        // initial view model
        recipeListViewModel = new ViewModelProvider(this).get(RecipeListViewModel.class);

        // set adapter
        recipeRecyclerView = findViewById(R.id.recipe_recyclerview);
        initRecyclerView();

        // observe view model changes
        subscribeObservers();

        // test request
        testRetrofitRequest();
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {

    }

    private void initRecyclerView() {
        recipeAdapter = new RecipeRecyclerAdapter(this);
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    private void searchRecipesApi(String query, int pageNumber) {
        recipeListViewModel.searchRecipesApi(query, pageNumber);
    }

    private void subscribeObservers() {
        recipeListViewModel.getRecepies().observe(this, recipes -> {
            // trigger updated, deleted, anything changes
            Testing.printRecipes(recipes, TAG);
            recipeAdapter.setRecipes(recipes);
        });
    }

    private void testRetrofitRequest() {
        searchRecipesApi("chicken breast", 0);
    }
}
