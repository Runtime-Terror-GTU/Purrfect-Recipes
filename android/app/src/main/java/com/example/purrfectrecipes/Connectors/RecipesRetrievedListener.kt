package com.example.purrfectrecipes.Connectors

import com.example.purrfectrecipes.Recipe

interface RecipesRetrievedListener {
    fun onRecipesRetrieved(list:ArrayList<Recipe>?)
}