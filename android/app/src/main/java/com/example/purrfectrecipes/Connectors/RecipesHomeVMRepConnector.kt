package com.example.purrfectrecipes.Connectors

import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer

interface RecipesHomeVMRepConnector {
    fun onRecipesRetrieved(list:ArrayList<Recipe>?, owner: Customer?)
    fun onSelectRecipeOfTheDay(recipe:Recipe)
}