package com.bsrakdg.recipes.requests;

import com.bsrakdg.recipes.requests.responses.RecipeResponse;
import com.bsrakdg.recipes.requests.responses.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {
    // SEARCH
    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
            @Query("q") String query,
            @Query("page") String page
    );

    // RECIPE
    @GET("api/get")
    Call<RecipeResponse> getRecipe(@Query("rId") String recipe_id);
}
