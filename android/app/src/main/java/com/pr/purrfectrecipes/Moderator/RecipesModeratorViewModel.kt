package com.pr.purrfectrecipes.Moderator

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.RecipesModeratorConnector
import com.pr.purrfectrecipes.Recipe

class RecipesModeratorViewModel: ViewModel(), RecipesModeratorConnector
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}
    private var recipes = MutableLiveData<ArrayList<Recipe>?>()
    fun getRecipes(): LiveData<ArrayList<Recipe>?> {return recipes}
    private var repository = RecipesModeratorRepository(this)

    fun setView(newView: View?)
    {
        view.value=newView
    }
    init{
        repository.recipeRetrive()
    }
    override fun onRecipeRetrieved(list:ArrayList<Recipe>?){
        if(list!=null){
            recipes.value=list
        }
    }
    fun deleteRecipe(recipe:Recipe){
        repository.deleteRecipe(recipe)
    }
}