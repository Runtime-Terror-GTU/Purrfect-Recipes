package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.Recipe
import com.pr.purrfectrecipes.User.Customer

interface RecipesRetrievedListener {
    fun onRecipesRetrieved(list:ArrayList<Recipe>?)
    fun onUserRetrieved(user:Customer?)

}