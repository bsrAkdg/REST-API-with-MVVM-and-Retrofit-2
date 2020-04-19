package com.bsrakdg.recipes.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bsrakdg.recipes.models.Recipe;

import java.util.List;

public class RecipeListViewModel extends ViewModel {

    private MutableLiveData<List<Recipe>> recepies = new MutableLiveData<List<Recipe>>();

    public RecipeListViewModel() {
    }

    public LiveData<List<Recipe>> getRecepies() {
        return recepies;
    }
}
