package com.example.purrfectrecipes.Customer

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.purrfectrecipes.Connectors.RecipeRetrievedListener
import com.example.purrfectrecipes.DifficultyComparator
import com.example.purrfectrecipes.HeapSort
import com.example.purrfectrecipes.PopularityComparator
import com.example.purrfectrecipes.Recipe
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class WhatresHomeViewModel: ViewModel(), RecipeRetrievedListener
{
    private var view= MutableLiveData<View?>()
        fun getView(): LiveData<View?> {return view}

    private var totalRecipes= MutableLiveData<ArrayList<Recipe>?>()
        fun getTotalRecipes(): LiveData<ArrayList<Recipe>?> {return totalRecipes}
    private var allRecipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getAllRecipes(): LiveData<ArrayList<Recipe>?> {return allRecipes}
    private var recipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getRecipes(): LiveData<ArrayList<Recipe>?> {return recipes}

    private val sort=MutableLiveData<Boolean?>()
        fun getSort():LiveData<Boolean?>{return sort}

    private val filter=MutableLiveData<Boolean?>()
        fun getFilter():LiveData<Boolean?>{return filter}

    private val shownRecipe=MutableLiveData<String?>()
        fun getShownRecipe():LiveData<String?>{return shownRecipe}

    private val heapSort= HeapSort<Recipe>()
    private val diffComparator= DifficultyComparator()
    private val popComparator= PopularityComparator()

    val repository=WhatresHomeRepository(this)

    init{
        repository.retrieveRecipes()
    }

    fun setShownRecipe(id:String?)
    {
        shownRecipe.value=id
    }

    fun setView(newView: View?)
    {
        view.value=newView
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

    fun setRecipes(wantedList:ArrayList<String>, nonWantedList:ArrayList<String>)
    {
        val tempArray=ArrayList<Recipe>()
        tempArray.addAll(totalRecipes.value!!)

        for(recipe in totalRecipes.value!!)
            for(ingredient in wantedList)
                if (!recipe.isRecipeIngredient(ingredient)) {
                    tempArray.remove(recipe)
                    break
                }

        for(recipe in totalRecipes.value!!)
            for(ingredient in nonWantedList)
                if (recipe.isRecipeIngredient(ingredient)) {
                    tempArray.remove(recipe)
                    break
                }
        allRecipes.value=tempArray
        recipes.value=tempArray
    }

    override fun onRecipesRetrieved(list: ArrayList<Recipe>?) {
        if(list!=null) {
            totalRecipes.value=list
        }
    }
}