package com.bsrakdg.recipes.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.lifecycle.ViewModelProvider;

import com.bsrakdg.recipes.R;
import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.viewmodels.RecipeDetailViewModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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

        showProgressBar(true);

        findViews();

        subscribeObserver();

        getIncomingIntent();
    }

    private void displayErrorScreen(String errorMessage) {
        recipeTitle.setText("Error retrieving recipe...");
        recipeRank.setText("");

        TextView textView = new TextView(this);
        if (!errorMessage.equals("")) {
            textView.setText(errorMessage);
        } else {
            textView.setText("Error");
        }
        textView.setTextSize(15);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        recipeIngredientsContainer.addView(textView);

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_launcher_background);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(R.drawable.ic_launcher_background)
                .into(recipeImage);
        showParent();
        showProgressBar(false);
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

    private void setRecipeDetailViewModel(Recipe recipe) {
        if (recipe != null) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(this)
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipe.getImage_url())
                    .into(recipeImage);

            recipeTitle.setText(recipe.getTitle());
            recipeRank.setText(String.valueOf(Math.round(recipe.getSocial_rank())));

            recipeIngredientsContainer.removeAllViews();

            for (String ingredient : recipe.getIngredients()) {
                TextView textView = new TextView(this);
                textView.setText(ingredient);
                textView.setTextSize(15);
                textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                recipeIngredientsContainer.addView(textView);
            }
        }
        showParent();
        showProgressBar(false);
    }

    private void showParent() {
        scrollView.setVisibility(View.VISIBLE);
    }

    private void subscribeObserver() {
        recipeDetailViewModel.getRecipe().observe(this, recipe -> {
            if (recipe != null && recipe.getRecipe_id()
                    .equals(recipeDetailViewModel.getRecipeId())) {
                setRecipeDetailViewModel(recipe);
                recipeDetailViewModel.setDidRetrieveRecipe(true);
            }
        });

        recipeDetailViewModel.getRecipeRequestTimeout().observe(this, aBoolean -> {
            if (aBoolean == !recipeDetailViewModel.isDidRetrieveRecipe()) {
                // show error time out
                displayErrorScreen("Error retrieving data. Check network connection.");
            }
        });
    }
}
