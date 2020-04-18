package com.bsrakdg.recipes.requests.responses;

import com.bsrakdg.recipes.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeSearchResponse {
    @SerializedName("count")
    @Expose()
    private int count;

    @SerializedName("recipes")
    @Expose()
    private List<Recipe> recipes;

    @Override
    public String toString() {
        return "RecipeSearchResponse{" +
                "count=" + count +
                ", recipes=" + recipes +
                '}';
    }

    public int getCount() {
        return count;
    }

    public List<Recipe> getRecipes() {
        return recipes;
    }
}
