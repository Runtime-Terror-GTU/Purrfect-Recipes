package com.example.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class EditRecipeViewModel: ViewModel()
{
    private val repository=EditRecipeRepository()

    private var recipe= MutableLiveData<Recipe?>()
        fun getRecipe(): LiveData<Recipe?> {return recipe}

    private var recipeTags=MutableLiveData<ArrayList<String>>()
        fun getRecipeTags():LiveData<ArrayList<String>>{return recipeTags}
    private var recipeIngredients=MutableLiveData<ArrayList<String>>()
        fun getRecipeIngredients():LiveData<ArrayList<String>>{return recipeIngredients}
    private var recipeSteps=MutableLiveData<ArrayList<String>>()
        fun getRecipeSteps():LiveData<ArrayList<String>>{return recipeSteps}

    init{
        recipeTags.value= ArrayList()
        recipeIngredients.value= ArrayList()
        recipeSteps.value= ArrayList()
    }

    fun setRecipe(retrievedRecipe:Recipe?)
    {
        recipe.value=retrievedRecipe
    }

    fun addStep(stepText:String)
    {
        if(recipe.value!=null)
        {
            recipe.value!!.addStage(stepText)
            recipe.value=recipe.value
        }
        else
        {
            recipeSteps.value!!.add(stepText)
            recipeSteps.value=recipeSteps.value
        }
    }


}