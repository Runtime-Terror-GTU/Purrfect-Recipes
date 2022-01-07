package com.example.purrfectrecipes.Connectors

import com.example.purrfectrecipes.Recipe

interface RecipeOnClickListener2 {
    fun onRecipeClick(recipeId:String)
    fun onPurrfect(recipeId:String, recipeLikes:Int)
    fun unPurrfect(recipeId: String, recipeLikes: Int)
    fun onDelete(recipe: Recipe)
}