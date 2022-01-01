package com.example.purrfectrecipes.Connectors

import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer

interface RecipesHomeVMRepConnector {
    fun onRecipesRetrieved(list:ArrayList<Recipe>?)
    fun onSelectRecipeOfTheDay(recipe:Recipe)
    fun onUserRetrieved(user:Customer?)
}