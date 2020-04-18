package com.bsrakdg.recipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.requests.RecipeApi;
import com.bsrakdg.recipes.requests.ServiceGenerator;
import com.bsrakdg.recipes.requests.responses.RecipeSearchResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {
    private static final String TAG = "RecipeListActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        findViewById(R.id.test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testRetrofitRequest();
            }
        });
    }

    private void testRetrofitRequest() {
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();
        Call<RecipeSearchResponse> responseCall = recipeApi.searchRecipe("chicken breast", "1");

        responseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call,
                                   Response<RecipeSearchResponse> response) {
                Log.d(TAG, "onResponse : " + response.toString());
                if (response.code() == 200) {
                    Log.d(TAG, "onResponse 200: " + response.body().toString());
                    List<Recipe> recipes = new ArrayList<>(response.body().getRecipes());
                    for (Recipe recipe : recipes) {
                        Log.d(TAG, "onResponse : " + recipe.getTitle());
                    }
                } else {
                    try {
                        Log.d(TAG, "onResponse : " + response.errorBody());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {

            }
        });
    }
}
