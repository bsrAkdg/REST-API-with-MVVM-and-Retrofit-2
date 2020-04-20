package com.bsrakdg.recipes;

import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bsrakdg.recipes.adapters.OnRecipeListener;
import com.bsrakdg.recipes.adapters.RecipeRecyclerAdapter;
import com.bsrakdg.recipes.util.Testing;
import com.bsrakdg.recipes.util.VerticalSpacingItemDecorator;
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
        // testRetrofitRequest();

        // init search view on toolbar
        initSearchView();

        // firstly set categories
        if (!recipeListViewModel.isViewingRecipes()) {
            // display categories
            displaySearchCategories();
        }
    }

    @Override
    public void onRecipeClick(int position) {

    }

    @Override
    public void onCategoryClick(String category) {
        recipeAdapter.displayLoading();
        recipeListViewModel.searchRecipesApi(category, 1);
    }

    private void initRecyclerView() {
        recipeAdapter = new RecipeRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        recipeRecyclerView.addItemDecoration(itemDecorator);
        recipeRecyclerView.setAdapter(recipeAdapter);
    }

    private void initSearchView() {
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                recipeAdapter.displayLoading();
                // new search page number should be 1
                searchRecipesApi(s, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
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

    private void displaySearchCategories() {
        recipeListViewModel.setViewingRecipes(false);
        recipeAdapter.displaySearchCategories();
    }

    private void testRetrofitRequest() {
        searchRecipesApi("chicken breast", 0);
    }
}
