package com.pr.purrfectrecipes

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pr.purrfectrecipes.Connectors.EditIngredientsVMRepConnector
import com.pr.purrfectrecipes.Connectors.FilterVMRepConnector

class EditRecipeViewModel: ViewModel(),FilterVMRepConnector, EditIngredientsVMRepConnector
{
    private val repository=EditRecipeRepository(this, this)

    var allTags=ArrayList<String>()
    var allIngredients=ArrayList<String>()

    private var recipe= MutableLiveData<Recipe?>()
        fun getRecipe(): LiveData<Recipe?> {return recipe}

    private var recipeTags=MutableLiveData<ArrayList<String>>()
        fun getRecipeTags():LiveData<ArrayList<String>>{return recipeTags}
    private var recipeIngredients=MutableLiveData<ArrayList<String>>()
        fun getRecipeIngredients():LiveData<ArrayList<String>>{return recipeIngredients}
    private var recipeSteps=MutableLiveData<ArrayList<String>>()
        fun getRecipeSteps():LiveData<ArrayList<String>>{return recipeSteps}
    var newRecipePicUri:Uri?=null

    init{
        repository.retrieveTags()
        repository.retrieveIngredients()
        recipeTags.value= ArrayList()
        recipeIngredients.value= ArrayList()
        recipeSteps.value= ArrayList()
    }

    fun setRecipeTags(list:ArrayList<String>)
    {
        recipeTags.value?.addAll(list)
        recipeTags.value=recipeTags.value
    }

    fun setRecipeIngredients(list:ArrayList<String>)
    {
        recipeIngredients.value?.addAll(list)
        recipeIngredients.value=recipeIngredients.value
    }

    fun setRecipeSteps(list:ArrayList<String>)
    {
        recipeSteps.value?.addAll(list)
        recipeSteps.value=recipeSteps.value
    }

    fun setRecipe(retrievedRecipe:Recipe?)
    {
        if(retrievedRecipe==null)
            recipe.value=retrievedRecipe
        else
        {
            recipe.value=Recipe(retrievedRecipe!!.getRecipeID(), retrievedRecipe.recipeName, retrievedRecipe.recipeOwner, retrievedRecipe.recipeDifficulty,
                retrievedRecipe.recipeLikes, retrievedRecipe.recipePictureURL, retrievedRecipe.recipeIngredientsOverview)
            for(step in retrievedRecipe!!.getRecipeStages())
                recipe.value!!.addStage(step)
            for(tag in retrievedRecipe!!.getRecipeTags())
                recipe.value!!.addTag(tag)
            for(ingredient in retrievedRecipe!!.getRecipeIngredients())
                recipe.value!!.addIngredient(ingredient)
            recipe.value=recipe.value
        }
    }

    fun resetInfo()
    {
        recipeTags.value= ArrayList()
        recipeSteps.value= ArrayList()
        recipeIngredients.value= ArrayList()
    }

    fun addStep(stepText:String)
    {
        if(!recipeSteps.value!!.contains(stepText)) {
            recipeSteps.value!!.add(stepText)
            recipeSteps.value = recipeSteps.value
        }
    }

    fun addTag(tag:String)
    {
        if(!recipeTags.value!!.contains(tag)) {
            recipeTags.value!!.add(tag)
            recipeTags.value = recipeTags.value
        }
    }

    fun removeTag(tag:String)
    {
        recipeTags.value!!.remove(tag)
        recipeTags.value=recipeTags.value

    }

    fun addIngredient(ingredient:String)
    {
        if(!recipeIngredients.value!!.contains(ingredient)) {
            recipeIngredients.value!!.add(ingredient)
            recipeIngredients.value = recipeIngredients.value
        }
    }

    fun removeIngredient(ingredient:String)
    {
        recipeIngredients.value!!.remove(ingredient)
        recipeIngredients.value=recipeIngredients.value
    }

    fun removeStep(step:String)
    {
        recipeSteps.value!!.remove(step)
        recipeSteps.value=recipeSteps.value
    }

    fun saveRecipe()
    {
        repository.updateRecipe(recipe.value!!, newRecipePicUri)
    }

    fun updateStep(text:String, no:Int)
    {
        if(no<recipeSteps.value!!.size)
            recipeSteps.value!!.set(no, text)
    }

    override fun onTagsRetrieved(tags: ArrayList<String>) {
        allTags=tags
    }

    override fun onIngredientsRetrieved(ingredients: ArrayList<String>) {
        allIngredients=ingredients
    }
}