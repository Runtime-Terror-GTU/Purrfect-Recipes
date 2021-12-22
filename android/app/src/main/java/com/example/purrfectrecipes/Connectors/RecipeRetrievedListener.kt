package com.example.purrfectrecipes.Connectors

import com.example.purrfectrecipes.Recipe

interface RecipeRetrievedListener {
    fun onRecipesRetrieved(list:ArrayList<Recipe>?)
}