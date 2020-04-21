package com.bsrakdg.recipes.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProvider;

import com.bsrakdg.recipes.R;
import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.viewmodels.RecipeDetailViewModel;

public class RecipeDetailActivity extends BaseActivity {

    private static final String TAG = "RecipeDetailActivity";

    // UI Components
    private AppCompatImageView recipeImage;
    private TextView recipeTitle, recipeRank;
    private LinearLayout recipeIngredientsContainer;
    private ScrollView scrollView;
    private RecipeDetailViewModel recipeDetailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeDetailViewModel = new ViewModelProvider(this).get(RecipeDetailViewModel.class);

        findViews();

        subscribeObserver();

        getIncomingIntent();
    }

    private void findViews() {
        recipeImage = findViewById(R.id.recipe_image);
        recipeTitle = findViewById(R.id.recipe_title);
        recipeRank = findViewById(R.id.recipe_social_score);
        recipeIngredientsContainer = findViewById(R.id.ingredients_container);
        scrollView = findViewById(R.id.parent);
    }

    private void getIncomingIntent() {
        if (getIntent().hasExtra("recipe")) {
            Recipe recipe = getIntent().getParcelableExtra("recipe");
            Log.d(TAG, "getIncomingIntent: " + recipe.getTitle());

            //search
            recipeDetailViewModel.searchRecipeById(recipe.getRecipe_id());
        }
    }

    private void subscribeObserver() {
        recipeDetailViewModel.getRecipe().observe(this, recipe -> {
            if (recipe != null) {
                Log.d(TAG, "subscribeObserver: ---------");
                Log.d(TAG, "subscribeObserver: " + recipe.getTitle());
            }
        });
    }
}
