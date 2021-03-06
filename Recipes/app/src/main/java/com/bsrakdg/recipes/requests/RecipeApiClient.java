package com.bsrakdg.recipes.requests;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.bsrakdg.recipes.AppExecutors;
import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.requests.responses.RecipeResponse;
import com.bsrakdg.recipes.requests.responses.RecipeSearchResponse;
import com.bsrakdg.recipes.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

public class RecipeApiClient {
    private static final String TAG = "RecipeApiClient";

    // Remote Data Source
    private static RecipeApiClient instance;

    private MutableLiveData<List<Recipe>> recipes; // for recipe list
    private MutableLiveData<Recipe> recipeDetail; // for recipe detail

    private MutableLiveData<Boolean> recipeRequestTimeout = new MutableLiveData<>();

    // Retrieve data from rest api with use custom cancelable runnable
    // This runnable provides receive data from remote, if you want you can cancel with use
    // cancelRequest()
    private RetrieveRecipesRunnable retrieveRecipesRunnable; // for recipe list
    private RetrieveRecipeRunnable retrieveRecipeRunnable; // for recipe detail


    private RecipeApiClient() {
        recipes = new MutableLiveData<>();
        recipeDetail = new MutableLiveData<>();
    }

    public static RecipeApiClient getInstance() {
        if (instance == null) {
            instance = new RecipeApiClient();
        }
        return instance;
    }

    public void cancelRequest() {
        if (retrieveRecipesRunnable != null) {
            retrieveRecipesRunnable.cancelRequest();
        }
        if (retrieveRecipeRunnable != null) {
            retrieveRecipeRunnable.cancelRequest();
        }
    }

    public LiveData<List<Recipe>> getRecipes() {
        return recipes;
    }

    public LiveData<Recipe> getRecipe() {
        return recipeDetail;
    }

    public LiveData<Boolean> getRecipeRequestTimeout() {
        return recipeRequestTimeout;
    }

    public void searchRecipesApi(String query, int pageNumber) {
        // Check runnable object, if it is created assign null
        if (retrieveRecipesRunnable != null) {
            retrieveRecipesRunnable = null;
        }
        retrieveRecipesRunnable = new RetrieveRecipesRunnable(query, pageNumber);

        /* Future : A Future represents the result of an asynchronous computation.
          Methods are provided to check if the computation is complete, to wait for its completion
          and to retrieve the result of the computation. */
        final Future handler = AppExecutors.getInstance().getNetworkIO()
                .submit(retrieveRecipesRunnable);

        // Time out
        AppExecutors.getInstance().getNetworkIO().schedule(() -> {
            // Let the user know its timed out
            // Stop request(handler)
            handler.cancel(true);
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void getRecipe(String recipeId) {
        // Check runnable object, if it is created assign null
        if (retrieveRecipeRunnable != null) {
            retrieveRecipeRunnable = null;
        }
        retrieveRecipeRunnable = new RetrieveRecipeRunnable(recipeId);

        final Future handler = AppExecutors.getInstance().getNetworkIO()
                .submit(retrieveRecipeRunnable);

        // set default timeout before each request
        recipeRequestTimeout.setValue(false);

        // Time out
        AppExecutors.getInstance().getNetworkIO().schedule(() -> {
            // Let the user know its timed out
            // Stop request(handler)
            recipeRequestTimeout.postValue(true);
            handler.cancel(true);
        }, Constants.NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveRecipesRunnable implements Runnable {
        boolean cancelRequest;
        private String query;
        private int pageNumber;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if (cancelRequest) {
                    return;
                }
                // for MutableLiveData updating :
                // setValue : change LiveData on main thread
                // postValue : change LiveData on background thread
                if (response.code() == 200) {
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse) response.body())
                            .getRecipes());
                    if (pageNumber == 1) {
                        // Directly post recipes first time
                        recipes.postValue(list);
                    } else {
                        // Add new recipes to current recipes
                        List<Recipe> currentRecipes = recipes.getValue();
                        currentRecipes
                                .addAll(((RecipeSearchResponse) response.body()).getRecipes());
                        recipes.postValue(currentRecipes);
                    }
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    recipes.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                recipes.postValue(null);
            }
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request");
            cancelRequest = true;
        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber) {
            return ServiceGenerator.getRecipeApi().searchRecipe(query, String.valueOf(pageNumber));
        }
    }

    private class RetrieveRecipeRunnable implements Runnable {
        boolean cancelRequest;
        private String recipeId;

        public RetrieveRecipeRunnable(String recipeId) {
            this.recipeId = recipeId;
            this.cancelRequest = false;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipe(recipeId).execute();
                if (cancelRequest) {
                    return;
                }
                // for MutableLiveData updating :
                // setValue : change LiveData on main thread
                // postValue : change LiveData on background thread
                if (response.code() == 200) {
                    Recipe recipe = ((RecipeResponse) response.body()).getRecipe();
                    recipeDetail.postValue(recipe);
                } else {
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    recipeDetail.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                recipeDetail.postValue(null);
            }
        }

        private void cancelRequest() {
            Log.d(TAG, "cancelRequest: canceling the search request");
            cancelRequest = true;
        }

        private Call<RecipeResponse> getRecipe(String recipeId) {
            return ServiceGenerator.getRecipeApi().getRecipe(recipeId);
        }
    }

}
