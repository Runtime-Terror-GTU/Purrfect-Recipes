package com.example.purrfectrecipes.Connectors

interface RecipeOnClickListener {
    fun onRecipeClick(recipeId:String)
    fun onPurrfect(recipeId:String, recipeLikes:Int)
    fun unPurrfect(recipeId: String, recipeLikes: Int)
}