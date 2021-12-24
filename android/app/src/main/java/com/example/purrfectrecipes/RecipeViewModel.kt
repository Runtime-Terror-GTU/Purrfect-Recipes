package com.example.purrfectrecipes

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.RecipeRetrievedListener
import com.example.purrfectrecipes.User.Customer
import com.orhanobut.hawk.Hawk

class RecipeViewModel : ViewModel(), RecipeRetrievedListener
{
    private var recipe= MutableLiveData<Recipe?>()
        fun getRecipe(): LiveData<Recipe?> {return recipe}
    var recipeOwner:Customer?=null
    var user:Customer?=null
    private var comments= MutableLiveData<ArrayList<Comment>>()
        fun getComments(): LiveData<ArrayList<Comment>> {return comments}

    private val repository=RecipeRepository(this)

    init{
        repository.retrieveUser()
    }

    fun setRecipe(id: String)
    {
        repository.retrieveRecipe(id)
    }

    fun resetRecipe()
    {
        recipe.value=null
    }

    fun addComment(commentText:String)
    {
        repository.saveComment(commentText, recipe.value!!.getRecipeID(), Hawk.get<String>(Constants.LOGGEDIN_USERID))
    }

    fun deleteComment(commentId:String)
    {
        repository.removeComment(commentId, recipe.value!!.getRecipeID())
    }

    fun deleteRecipe()
    {
        repository.removeRecipe(recipe.value!!)
    }

    fun purrfectRecipe()
    {
        repository.increasePurrfectedCount(recipe.value!!.getRecipeID(), recipe.value!!.recipeLikes, Hawk.get<String>(Constants.LOGGEDIN_USERID))
    }

    fun unPurrfectRecipe()
    {
        repository.decreasePurrfectedCount(recipe.value!!.getRecipeID(), recipe.value!!.recipeLikes, Hawk.get<String>(Constants.LOGGEDIN_USERID))
    }

    override fun onUserRetrieved(user:Customer?)
    {
        this.user=user
    }

    override fun onRecipeRetrieved(recipe: Recipe, recipeOwner:Customer, comments:ArrayList<Comment>) {
        this.recipeOwner=recipeOwner
        this.recipe.value=recipe
        this.comments.value=comments
    }
}