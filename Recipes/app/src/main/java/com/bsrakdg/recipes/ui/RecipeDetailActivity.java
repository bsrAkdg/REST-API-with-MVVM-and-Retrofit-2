package com.bsrakdg.recipes.ui;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

import com.bsrakdg.recipes.R;
import com.bsrakdg.recipes.models.Recipe;

public class RecipeDetailActivity extends BaseActivity {

    // UI Components
    private AppCompatImageView recipeImage;
    private TextView recipeTitle, recipeRank;
    private LinearLayout recipeIngredientsContainer;
    private ScrollView scrollView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        findViews();

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
        }
    }
}
