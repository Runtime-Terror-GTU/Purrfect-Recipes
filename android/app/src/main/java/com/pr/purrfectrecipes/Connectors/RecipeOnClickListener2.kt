package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.Recipe

interface RecipeOnClickListener2 {
    fun onRecipeClick(recipeId:String)
    fun onPurrfect(recipeId:String, recipeLikes:Int)
    fun unPurrfect(recipeId: String, recipeLikes: Int)
    fun onDelete(recipe: Recipe)
    fun onEditRecipe(recipe: Recipe)
}