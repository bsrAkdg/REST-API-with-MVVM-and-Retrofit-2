package com.bsrakdg.recipes.util;

import android.util.Log;

import com.bsrakdg.recipes.models.Recipe;

import java.util.List;

public class Testing {
    public static void printRecipes(List<Recipe> list, String tag) {
        if (list != null) {
            for (Recipe recipe : list) {
                Log.d(tag, "onChanged: " + recipe.getTitle());
            }
        }
    }
}
