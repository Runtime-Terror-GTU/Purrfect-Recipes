package com.example.purrfectrecipes.Customer

import android.view.View
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.window.PopupPositionProvider
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.*
import com.example.purrfectrecipes.Connectors.RecipesHomeVMRepConnector

class RecipesHomeViewModel: ViewModel(), RecipesHomeVMRepConnector
{
    private var view= MutableLiveData<View?>()
        fun getView(): LiveData<View?> {return view}
    private var allRecipes:ArrayList<Recipe>?=null
    private var recipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getRecipes(): LiveData<ArrayList<Recipe>?> {return recipes}
    private val repository=RecipesHomeRepository(this)
    private var recipeOfTheDay=MutableLiveData<Recipe>()
        fun getRecipeOfTheDay():LiveData<Recipe> {return recipeOfTheDay}

    private val sort=MutableLiveData<Boolean?>()
        fun getSort():LiveData<Boolean?>{return sort}

    private val diffSort=MutableLiveData<SortMethods?>()
        fun getDiffSort():LiveData<SortMethods?> {return diffSort}
    private val popSort=MutableLiveData<SortMethods?>()
        fun getPopSort():LiveData<SortMethods?> {return popSort}

    private val filter=MutableLiveData<Boolean?>()
        fun getFilter():LiveData<Boolean?>{return filter}

    private val heapSort=HeapSort<Recipe>()
    private val diffComparator=DifficultyComparator()
    private val popComparator=PopularityComparator()

    init{
        repository.retrieveRecipes()
    }

    fun setSort(bool:Boolean)
    {
        sort.value=bool
    }

    fun setFilter(bool:Boolean)
    {
        filter.value=bool
    }

    fun setDiffSort(method:SortMethods?)
    {
        diffSort.value=method
    }

    fun sortDiffMin()
    {
        heapSort.sort(recipes.value, diffComparator)
    }

    fun sortDiffMax()
    {
        val tempList=recipes.value
        heapSort.sort(tempList, diffComparator)
        tempList?.reverse()
        recipes.value=tempList

    }

    fun sortPopMin()
    {
        heapSort.sort(recipes.value, popComparator)
    }

    fun sortPopMax()
    {
        val tempList=recipes.value
        heapSort.sort(tempList, popComparator)
        tempList?.reverse()
        recipes.value=tempList

    }

    fun setPopSort(method:SortMethods?)
    {
        popSort.value=method
    }

    fun setView(newView: View?)
    {
        view.value=newView
    }

    fun searchByName(text:String)
    {
        val newArray=ArrayList<Recipe>()
        for(recipe in allRecipes!!)
            if(recipe.recipeName.lowercase().contains(text.lowercase()))
                newArray.add(recipe)
        recipes.value=newArray
    }

    fun searchByUsername(text:String)
    {
        val newArray=ArrayList<Recipe>()
        for(recipe in allRecipes!!)
            if(recipe.recipeOwner==text)
                newArray.add(recipe)
        recipes.value=newArray
    }

    fun resetRecipeArray()
    {
        recipes.value=allRecipes
    }

    override fun onRecipesRetrieved(list: ArrayList<Recipe>?) {
        if(list!=null) {
            recipes.value = list
            allRecipes=list
        }
    }

    override fun onSelectRecipeOfTheDay(recipe: Recipe) {
        recipeOfTheDay.value=recipe
    }
}