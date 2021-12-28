package com.example.purrfectrecipes.Customer

import android.util.Log
import android.view.View
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.window.PopupPositionProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.*
import com.example.purrfectrecipes.Connectors.RecipesHomeVMRepConnector
import com.example.purrfectrecipes.User.Customer
import com.orhanobut.hawk.Hawk

class RecipesHomeViewModel: ViewModel(), RecipesHomeVMRepConnector
{
    private var view= MutableLiveData<View?>()
        fun getView(): LiveData<View?> {return view}
    private var allRecipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getAllRecipes(): LiveData<ArrayList<Recipe>?> {return allRecipes}
    private var recipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getRecipes(): LiveData<ArrayList<Recipe>?> {return recipes}
    private val repository=RecipesHomeRepository(this)
    private var recipeOfTheDay=MutableLiveData<Recipe>()
        fun getRecipeOfTheDay():LiveData<Recipe> {return recipeOfTheDay}

    var user: Customer?=null

    private val sort=MutableLiveData<Boolean?>()
        fun getSort():LiveData<Boolean?>{return sort}

    private val filter=MutableLiveData<Boolean?>()
        fun getFilter():LiveData<Boolean?>{return filter}

    var change=false

    private val shownRecipe=MutableLiveData<String?>()
        fun getShownRecipe():LiveData<String?>{return shownRecipe}

    private val heapSort=HeapSort<Recipe>()
    private val diffComparator=DifficultyComparator()
    private val popComparator=PopularityComparator()

    init{
        repository.retrieveRecipes()
    }

    fun setShownRecipe(id:String?)
    {
        shownRecipe.value=id
    }

    fun setSort(bool:Boolean)
    {
        sort.value=bool
    }

    fun setFilter(bool:Boolean)
    {
        filter.value=bool
    }

    fun applyDifficultyFilters(diffs:ArrayList<String>)
    {
        val tempList=ArrayList<Recipe>()
        for(recipe in recipes.value!!)
            for(diff in diffs)
                if(recipe.recipeDifficulty==diff)
                {
                    tempList.add(recipe)
                    break
                }
        recipes.value=tempList
    }

    fun applyTagFilters(tags:ArrayList<String>)
    {
        val tempList=ArrayList<Recipe>()
        for(recipe in recipes.value!!)
            for(tag in tags)
                if(recipe.isRecipeTag(tag))
                {
                    tempList.add(recipe)
                    break
                }
        recipes.value=tempList
    }

    fun sortDiffMin()
    {
        val tempList=ArrayList<Recipe>()
        tempList.addAll(recipes.value!!)
        heapSort.sort(tempList, diffComparator)
        recipes.value=tempList
    }

    fun sortDiffMax()
    {
        val tempList=ArrayList<Recipe>()
        tempList.addAll(recipes.value!!)
        heapSort.sort(tempList, diffComparator)
        tempList?.reverse()
        recipes.value=tempList
    }

    fun sortPopMin()
    {
        val tempList=ArrayList<Recipe>()
        tempList.addAll(recipes.value!!)
        heapSort.sort(tempList, popComparator)
        recipes.value=tempList
    }

    fun sortPopMax()
    {
        val tempList=ArrayList<Recipe>()
        tempList.addAll(recipes.value!!)
        heapSort.sort(tempList, popComparator)
        tempList?.reverse()
        recipes.value=tempList

    }

    fun setView(newView: View?)
    {
        view.value=newView
    }

    fun searchByName(text:String)
    {
        val newArray=ArrayList<Recipe>()
        for(recipe in allRecipes.value!!)
            if(recipe.recipeName.lowercase().contains(text.lowercase()))
                newArray.add(recipe)
        recipes.value=newArray
    }

    fun searchByUsername(text:String)
    {
        val newArray=ArrayList<Recipe>()
        for(recipe in allRecipes.value!!)
            if(recipe.recipeOwner==text)
                newArray.add(recipe)
        recipes.value=newArray
    }

    fun resetRecipeArray()
    {
        recipes.value=allRecipes.value
    }

    fun purrfectRecipe(recipeId:String, currentLikes:Int)
    {
        for(recipe in recipes.value!!)
            if(recipe.getRecipeID()==recipeId) {
                recipe.recipeLikes = currentLikes + 1
                recipes.value=recipes.value
                user?.addPurrfectedRecipe(recipeId)
                if(recipeId==recipeOfTheDay.value!!.getRecipeID())
                {
                    recipeOfTheDay.value!!.recipeLikes=currentLikes + 1
                    recipeOfTheDay.value=recipeOfTheDay.value
                }
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
                if(recipeId==recipeOfTheDay.value!!.getRecipeID())
                {
                    recipeOfTheDay.value!!.recipeLikes=currentLikes - 1
                    recipeOfTheDay.value=recipeOfTheDay.value
                }
            }
        repository.decreaseDayPurrfectedCount(recipeId, currentLikes, Hawk.get<String>(Constants.LOGGEDIN_USERID))
    }


    override fun onRecipesRetrieved(list: ArrayList<Recipe>?, owner:Customer?) {
        if(list!=null) {
            user=owner
            allRecipes.value=list
            recipes.value = list
        }
    }

    override fun onSelectRecipeOfTheDay(recipe: Recipe) {
        recipeOfTheDay.value=recipe
    }
}