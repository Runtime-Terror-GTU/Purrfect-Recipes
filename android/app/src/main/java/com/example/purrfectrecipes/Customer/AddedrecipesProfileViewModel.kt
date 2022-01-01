package com.example.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.RecipesRetrievedListener
import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer

class AddedrecipesProfileViewModel: ViewModel(), RecipesRetrievedListener
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}

    private val repository=AddedRecipesRepository(this)

    private var allRecipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getAllRecipes(): LiveData<ArrayList<Recipe>?> {return allRecipes}
    private var recipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getRecipes(): LiveData<ArrayList<Recipe>?> {return recipes}

    var user: Customer?=null
    var change=false

    init{
        repository.retrieveRecipes()
    }

    fun setView(newView: View?)
    {
        view.value=newView
    }

    override fun onRecipesRetrieved(list: ArrayList<Recipe>?, user:Customer?) {
        if(list!=null) {
            this.user=user
            allRecipes.value=list
            recipes.value=list
        }
    }
}