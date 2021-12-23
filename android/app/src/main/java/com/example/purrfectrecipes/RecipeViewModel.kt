package com.example.purrfectrecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.RecipeRetrievedListener
import com.example.purrfectrecipes.User.Customer

class RecipeViewModel : ViewModel(), RecipeRetrievedListener
{
    private var recipe= MutableLiveData<Recipe?>()
        fun getRecipe(): LiveData<Recipe?> {return recipe}
    var recipeOwner:Customer?=null
    private var comments= MutableLiveData<ArrayList<Comment>>()
        fun getComments(): LiveData<ArrayList<Comment>> {return comments}

    private val repository=RecipeRepository(this)

    fun setRecipe(id: String)
    {
        repository.retrieveRecipe(id)
    }

    fun resetRecipe()
    {
        recipe.value=null
    }

    override fun onRecipeRetrieved(recipe: Recipe, recipeOwner:Customer, comments:ArrayList<Comment>) {
        this.recipeOwner=recipeOwner
        this.recipe.value=recipe
        this.comments.value=comments
    }
}