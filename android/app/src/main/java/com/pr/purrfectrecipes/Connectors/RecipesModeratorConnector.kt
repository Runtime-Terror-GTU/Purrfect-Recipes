package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.Recipe


interface RecipesModeratorConnector {
    fun onRecipeRetrieved(list:ArrayList<Recipe>?)
}