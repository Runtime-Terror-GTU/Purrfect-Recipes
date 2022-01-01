package com.example.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.RecipesRetrievedListener
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer
import com.orhanobut.hawk.Hawk

class AddedrecipesProfileViewModel: ViewModel(), RecipesRetrievedListener
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}

    private val repository=AddedRecipesRepository(this)

    private var allRecipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getAllRecipes(): LiveData<ArrayList<Recipe>?> {return allRecipes}
    private var recipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getRecipes(): LiveData<ArrayList<Recipe>?> {return recipes}

    private val shownRecipe=MutableLiveData<String?>()
        fun getShownRecipe():LiveData<String?>{return shownRecipe}

    var user: Customer?=null
    var change=false

    init{
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

    fun purrfectRecipe(recipeId:String, currentLikes:Int)
    {
        for(recipe in recipes.value!!)
            if(recipe.getRecipeID()==recipeId) {
                recipe.recipeLikes = currentLikes + 1
                recipes.value=recipes.value
                user?.addPurrfectedRecipe(recipeId)
            }
        repository.increaseDayPurrfectedCount(recipeId, currentLikes, Hawk.get<String>(Constants.LOGGEDIN_USERID))
    }

    fun unPurrfectRecipe(recipeId:String, currentLikes: Int)
    {
        for(recipe in recipes.value!!)
            if(recipe.getRecipeID()==recipeId) {
                recipe.recipeLikes = currentLikes - 1
                recipes.value=recipes.value
                user?.removePurrfectedRecipe(recipeId)
            }
        repository.decreaseDayPurrfectedCount(recipeId, currentLikes, Hawk.get<String>(Constants.LOGGEDIN_USERID))
    }

    override fun onRecipesRetrieved(list: ArrayList<Recipe>?, user:Customer?) {
        if(list!=null) {
            this.user=user
            allRecipes.value=list
            recipes.value=list
        }
    }
}