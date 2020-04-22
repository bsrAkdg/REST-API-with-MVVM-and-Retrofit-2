package com.bsrakdg.recipes.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bsrakdg.recipes.R;
import com.bsrakdg.recipes.models.Recipe;
import com.bsrakdg.recipes.util.Constants;

import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;
    private static final int EXHAUSTED_TYPE = 4;

    private List<Recipe> recipes;
    private OnRecipeListener listener;

    public RecipeRecyclerAdapter(OnRecipeListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if (viewType == LOADING_TYPE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_loading_list_item, parent, false);
            return new LoadingViewHolder(view);
        } else if (viewType == CATEGORY_TYPE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_category_list_item, parent, false);
            return new CategoryViewHolder(view, listener);
        } else if (viewType == EXHAUSTED_TYPE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_exhausted_list_item, parent, false);
            return new ExhaustedViewHolder(view);
        }
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_recipe_list_item, parent, false);
        return new RecipeViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        int viewType = getItemViewType(position);
        if (viewType == RECIPE_TYPE) {
            ((RecipeViewHolder) holder).onBind(recipes.get(position));
        } else if (viewType == CATEGORY_TYPE) {
            ((CategoryViewHolder) holder).onBind(recipes.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (recipes.get(position).getSocial_rank() == -1) {
            return CATEGORY_TYPE;
        } else if (recipes.get(position).getTitle().equals("LOADING...")) {
            return LOADING_TYPE;
        } else if (recipes.get(position).getTitle().equals("EXHAUSTED...")) {
            return EXHAUSTED_TYPE;
        } else if (position == recipes.size() - 1
                && position != 0
                && !recipes.get(position).getTitle().equals("EXHAUSTED...")) {
            return LOADING_TYPE;
        } else {
            return RECIPE_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        return recipes == null ? 0 : recipes.size();
    }

    private boolean isLoading() {
        if (recipes != null && recipes.size() > 0) {
            return recipes.get(recipes.size() - 1).getTitle().equals("LOADING...");
        }
        return false;
    }

    public void displayLoading() {
        if (!isLoading()) {
            Recipe recipe = new Recipe();
            recipe.setTitle("LOADING...");
            List<Recipe> list = new ArrayList<>();
            list.add(recipe);
            recipes = list;
            notifyDataSetChanged();
        }
    }

    private void hideLoading() {
        if (isLoading()) {
            for (Recipe recipe : recipes) {
                if (recipe.getTitle().equals("LOADING...")) {
                    recipes.remove(recipe);
                }
            }
            notifyDataSetChanged();
        }
    }

    public void displaySearchCategories() {
        List<Recipe> categories = new ArrayList<>();
        for (int i = 0; i < Constants.DEFAULT_SEARCH_CATEGORIES.length; i++) {
            Recipe recipe = new Recipe();
            recipe.setTitle(Constants.DEFAULT_SEARCH_CATEGORIES[i]);
            recipe.setImage_url(Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i]);
            recipe.setSocial_rank(-1);
            categories.add(recipe);
        }
        recipes = categories;
        notifyDataSetChanged();
    }

    public Recipe getSelectedRecipe(int position) {
        if (recipes != null && recipes.size() > 0) {
            return recipes.get(position);
        }
        return null;
    }

    public void setQueryExhausted() {
        hideLoading();
        Recipe exhaustedRecipe = new Recipe();
        exhaustedRecipe.setTitle("EXHAUSTED...");
        recipes.add(exhaustedRecipe);
        notifyDataSetChanged();
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
