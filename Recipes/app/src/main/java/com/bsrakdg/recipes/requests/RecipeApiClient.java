package com.bsrakdg.recipes.requests;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bsrakdg.recipes.AppExecutors;
import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.util.Constants;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class RecipeApiClient {
    // Remote Data Source
    private static RecipeApiClient instance;
    private MutableLiveData<List<Recipe>> recipes;

    private RecipeApiClient() {
        recipes = new MutableLiveData<>();
    }

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public void searchRecipesApi() {
        /* Future : A Future represents the result of an asynchronous computation.
          Methods are provided to check if the computation is complete, to wait for its completion
          and to retrieve the result of the computation. */
        final Future handler = AppExecutors.getInstance().getNetworkIO().submit(() -> {
            // Retrieve data from rest api
            // recipes.postValue();
        });

        // Time out
        AppExecutors.getInstance().getNetworkIO().schedule(() -> {
            // Let the user know its timed out
            // Stop request(handler)
            handler.cancel(true);
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }
}
