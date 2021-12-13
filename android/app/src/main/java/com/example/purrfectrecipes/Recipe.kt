package com.example.purrfectrecipes

import com.example.purrfectrecipes.User.CustomerStatus
import java.util.stream.Collectors

class Recipe(id:String, name:String, owner:String, difficulty:String, likes: Int, pictureURL:String=" ", overview:String="No Ingredients Overview")
{
    private var recipeId:String=id
        fun getRecipeID(): String {return recipeId}
    var recipeName:String=name
    var recipeOwner:String=owner
    var recipeDifficulty:String=difficulty
    var recipeLikes:Int=likes
    var recipeIngredientsOverview:String=overview
    var recipePictureURL:String=pictureURL

    private val recipeTags=HashSet<String>()
    private val recipeIngredients=HashSet<String>()
    private val recipeStages=ArrayList<String>()
    private val recipeComments=HashSet<String>()

    fun isRecipeTag(element:String):Boolean{
        return recipeTags?.contains(element)!!
    }

    fun isRecipeIngredient(element:String):Boolean {
        return recipeIngredients.contains(element)
    }

    fun isRecipeStage(element:String):Boolean {
        return recipeStages.contains(element)
    }

    fun isRecipeComment(element:String):Boolean {
        return recipeComments.contains(element)
    }

    fun addTag(element:String) {
        recipeTags.add(element)
    }

    fun addIngredient(element:String) {
        recipeIngredients.add(element)
    }

    fun addStage(element:String) {
        recipeStages.add(element)
    }

    fun addComment(element:String) {
        recipeComments.add(element)
    }

    fun removeTag(element:String) {
        recipeTags.remove(element)
    }

    fun removeIngredient(element:String) {
        recipeIngredients.remove(element)
    }

    fun removeComment(element:String) {
        recipeComments.remove(element)
    }

    fun removeStage(element:String) {
        recipeStages.remove(element)
    }

    fun getRecipeTags():ArrayList<String> {
        return recipeTags.stream().collect(Collectors.toList()) as (ArrayList<String>)
    }

    fun getRecipeIngredients():ArrayList<String> {
        return recipeIngredients.stream().collect(Collectors.toList()) as (ArrayList<String>)
    }

    fun getRecipeComments():ArrayList<String> {
        return recipeComments.stream().collect(Collectors.toList()) as (ArrayList<String>)
    }

    fun getStage(stageNo:Int):String {
        return recipeStages.get(stageNo)
    }

}