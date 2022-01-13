package com.example.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.RecipesRetrievedListener
import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer

class PurrfectedrecipesProfileViewModel: ViewModel(),RecipesRetrievedListener
{
    private var view= MutableLiveData<View?>()
        fun getView(): LiveData<View?> {return view}

    private val repository=PurrfectedRecipesRepository(this)

    private var allRecipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getAllRecipes(): LiveData<ArrayList<Recipe>?> {return allRecipes}
    private var recipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getRecipes(): LiveData<ArrayList<Recipe>?> {return recipes}

    private val shownRecipe=MutableLiveData<String?>()
        fun getShownRecipe():LiveData<String?>{return shownRecipe}

    var user: Customer?=null
    var change=false

    init{
        repository.retrieveUser()
        repository.retrieveRecipes()
    }

    fun setView(newView: View?)
    {
        view.value=newView
    }

    fun setShownRecipe(id:String?)
    {
        shownRecipe.value=id
        repository.retrieveRecipes()
    }

    override fun onRecipesRetrieved(list: ArrayList<Recipe>?) {
        if(list!=null) {
            allRecipes.value=list
            recipes.value=list
        }
    }

    override fun onUserRetrieved(user: Customer?) {
        this.user=user
    }
}