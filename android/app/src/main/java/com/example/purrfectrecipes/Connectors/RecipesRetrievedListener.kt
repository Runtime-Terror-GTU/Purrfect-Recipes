package com.example.purrfectrecipes.Connectors

import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer

interface RecipesRetrievedListener {
    fun onRecipesRetrieved(list:ArrayList<Recipe>?)
    fun onUserRetrieved(user:Customer?)

}