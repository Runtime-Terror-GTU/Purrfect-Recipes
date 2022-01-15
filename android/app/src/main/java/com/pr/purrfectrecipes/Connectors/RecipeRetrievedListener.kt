package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.Comment
import com.pr.purrfectrecipes.Recipe
import com.pr.purrfectrecipes.User.Customer

interface RecipeRetrievedListener {
    fun onRecipeRetrieved(recipe: Recipe, recipeOwner:Customer, comments:ArrayList<Comment>)
    fun onUserRetrieved(user:Customer?)
}