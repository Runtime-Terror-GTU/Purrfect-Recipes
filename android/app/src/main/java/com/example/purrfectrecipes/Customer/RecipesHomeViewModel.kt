package com.example.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.RecipesHomeVMRepConnector
import com.example.purrfectrecipes.Recipe

class RecipesHomeViewModel: ViewModel(), RecipesHomeVMRepConnector
{
    private var view= MutableLiveData<View?>()
        fun getView(): LiveData<View?> {return view}
    private var recipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getRecipes(): LiveData<ArrayList<Recipe>?> {return recipes}
    private val repository=RecipesHomeRepository(this)
    private var recipeOfTheDay=MutableLiveData<Recipe>()
        fun getRecipeOfTheDay():LiveData<Recipe> {return recipeOfTheDay}

    init{
        repository.retrieveRecipes()
    }

    fun setView(newView: View?)
    {
        view.value=newView
    }

    override fun onRecipesRetrieved(list: ArrayList<Recipe>?) {
        if(list!=null)
            recipes.value=list
    }

    override fun onSelectRecipeOfTheDay(recipe: Recipe) {
        recipeOfTheDay.value=recipe
    }
}