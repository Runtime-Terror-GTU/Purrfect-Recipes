package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.Recipe
import com.pr.purrfectrecipes.User.Customer

interface RecipesHomeVMRepConnector {
    fun onRecipesRetrieved(list:ArrayList<Recipe>?)
    fun onSelectRecipeOfTheDay(recipe:Recipe)
    fun onUserRetrieved(user:Customer?)
}