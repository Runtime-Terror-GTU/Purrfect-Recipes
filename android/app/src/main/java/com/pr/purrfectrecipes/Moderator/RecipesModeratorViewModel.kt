package com.pr.purrfectrecipes.Moderator

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.RecipesModeratorConnector
import com.pr.purrfectrecipes.DifficultyComparator
import com.pr.purrfectrecipes.HeapSort
import com.pr.purrfectrecipes.PopularityComparator
import com.pr.purrfectrecipes.Recipe

class RecipesModeratorViewModel: ViewModel(), RecipesModeratorConnector
{
    private var view= MutableLiveData<View?>()
    fun getView(): LiveData<View?> {return view}
    private var allRecipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getAllRecipes(): LiveData<ArrayList<Recipe>?> {return allRecipes}
    private var recipes=MutableLiveData<ArrayList<Recipe>?>()
        fun getRecipes(): LiveData<ArrayList<Recipe>?> {return recipes}
    private var repository = RecipesModeratorRepository(this)

    private val sort=MutableLiveData<Boolean?>()
    fun getSort():LiveData<Boolean?>{return sort}

    private val filter=MutableLiveData<Boolean?>()
    fun getFilter():LiveData<Boolean?>{return filter}

    var change=false

    private val shownRecipe=MutableLiveData<String?>()
    fun getShownRecipe():LiveData<String?>{return shownRecipe}

    private val heapSort= HeapSort<Recipe>()
    private val diffComparator= DifficultyComparator()
    private val popComparator= PopularityComparator()

    fun setView(newView: View?)
    {
        view.value=newView
    }
    init{
        repository.recipeRetrive()
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
            if(recipe.recipeOwnerName==text)
                newArray.add(recipe)
        recipes.value=newArray
    }

    fun resetRecipeArray()
    {
        recipes.value=allRecipes.value
    }

    override fun onRecipeRetrieved(list:ArrayList<Recipe>?){
        if(list!=null) {
            allRecipes.value=list
            recipes.value = list
        }
    }
    fun deleteRecipe(recipe:Recipe){
        repository.deleteRecipe(recipe)
    }
}