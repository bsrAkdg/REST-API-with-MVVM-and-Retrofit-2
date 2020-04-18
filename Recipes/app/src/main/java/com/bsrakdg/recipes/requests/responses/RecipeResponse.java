package com.bsrakdg.recipes.requests.responses;

import com.bsrakdg.recipes.models.Recipe;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecipeResponse {
    @SerializedName("recipe")
    @Expose()
    private Recipe recipe;

    @Override
    public String toString() {
        return "RecipeResponse{" +
                "recipe=" + recipe +
                '}';
    }

    public Recipe getRecipe() {
        return recipe;
    }
}
