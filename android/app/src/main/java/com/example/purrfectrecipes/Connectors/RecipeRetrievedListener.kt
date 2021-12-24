package com.example.purrfectrecipes.Connectors

import com.example.purrfectrecipes.Comment
import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer

interface RecipeRetrievedListener {
    fun onRecipeRetrieved(recipe: Recipe, recipeOwner:Customer, comments:ArrayList<Comment>)
    fun onUserRetrieved(user:Customer?)
}