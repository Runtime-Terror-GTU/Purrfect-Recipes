package com.example.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditRecipeViewModel: ViewModel()
{
    private val repository=EditRecipeRepository()

    private var recipe= MutableLiveData<Recipe?>()
        fun getRecipe(): LiveData<Recipe?> {return recipe}

    fun setRecipe(retrievedRecipe:Recipe?)
    {
        recipe.value=retrievedRecipe
    }


}