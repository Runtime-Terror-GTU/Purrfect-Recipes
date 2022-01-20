package com.pr.purrfectrecipes.Connectors

import com.pr.purrfectrecipes.Recipe

interface ModDeleteRecipeOnClickListener {
    fun onDeleteClick(recipe:Recipe)
    fun onRecipeClick(recipeId:String)
}