package com.pr.purrfectrecipes.Customer

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.*
import com.pr.purrfectrecipes.Connectors.RecipesRetrievedListener
import com.pr.purrfectrecipes.User.Customer
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

    private val sort=MutableLiveData<Boolean?>()
        fun getSort():LiveData<Boolean?>{return sort}

    private val filter=MutableLiveData<Boolean?>()
        fun getFilter():LiveData<Boolean?>{return filter}

    private val getVerified=MutableLiveData<Boolean?>()
        fun getGetVerified():LiveData<Boolean?>{return getVerified}

    private val editRecipe=MutableLiveData<Boolean?>()
        fun getEditRecipe():LiveData<Boolean?>{return editRecipe}

    private val editedRecipe=MutableLiveData<Recipe?>()
        fun getEditedRecipe():LiveData<Recipe?>{return editedRecipe}

    private val heapSort= HeapSort<Recipe>()
    private val diffComparator= DifficultyComparator()
    private val popComparator= PopularityComparator()

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

    fun setSort(bool:Boolean)
    {
        sort.value=bool
    }

    fun setFilter(bool:Boolean)
    {
        filter.value=bool
    }

    fun setGetVerified(bool:Boolean)
    {
        getVerified.value=bool
    }

    fun setEditRecipe(bool:Boolean)
    {
        editRecipe.value=bool
    }

    fun setEditedRecipe(recipe:Recipe?)
    {
        editedRecipe.value=recipe
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

    fun deleteRecipe(recipe:Recipe)
    {
        repository.removeRecipe(recipe)
    }

    override fun onUserRetrieved(user:Customer?)
    {
        this.user=user
    }

    override fun onRecipesRetrieved(list: ArrayList<Recipe>?) {
        if(list!=null) {
            allRecipes.value=list
            recipes.value=list
        }
    }
}