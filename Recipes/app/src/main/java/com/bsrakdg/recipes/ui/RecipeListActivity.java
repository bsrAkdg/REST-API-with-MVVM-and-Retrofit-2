package com.bsrakdg.recipes.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.bsrakdg.recipes.R;
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
    private SearchView searchView;

    @Override
    public void onBackPressed() {
        if (recipeListViewModel.onBackPressed()) {
            // turn back categories from recipe list
            super.onBackPressed();
        } else {
            displaySearchCategories();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        searchView = findViewById(R.id.search_view);

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

        setSupportActionBar(findViewById(R.id.toolbar));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipes_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_categories) {
            displaySearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra("recipe", recipeAdapter.getSelectedRecipe(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {
        recipeAdapter.displayLoading();
        recipeListViewModel.searchRecipesApi(category, 1);
        searchView.clearFocus();
    }

    private void displaySearchCategories() {
        recipeListViewModel.setViewingRecipes(false);
        recipeAdapter.displaySearchCategories();
    }

    private void initRecyclerView() {
        recipeAdapter = new RecipeRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        recipeRecyclerView.addItemDecoration(itemDecorator);
        recipeRecyclerView.setAdapter(recipeAdapter);

        recipeRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if (!recipeRecyclerView.canScrollHorizontally(1)) {
                    // search the next page
                    recipeListViewModel.searchNextPage();
                }
            }
        });
    }

    private void initSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                recipeAdapter.displayLoading();
                // new search page number should be 1
                searchRecipesApi(s, 1);
                searchView.clearFocus();
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
            if (recipeListViewModel.isViewingRecipes()) {
                Testing.printRecipes(recipes, TAG);
                recipeListViewModel.setPerformingQuery(false);
                recipeAdapter.setRecipes(recipes);
            }
        });
        
        recipeListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    Log.d(TAG, "onChanged: THE QUERY EXHATUED");
                    recipeAdapter.setQueryExhausted();
                }
            }
        });
    }

    private void testRetrofitRequest() {
        searchRecipesApi("chicken breast", 0);
    }
}
