package com.example.purrfectrecipes.Connectors

import com.example.purrfectrecipes.Recipe

interface RecipesHomeVMRepConnector {
    fun onRecipesRetrieved(list:ArrayList<Recipe>?)
}