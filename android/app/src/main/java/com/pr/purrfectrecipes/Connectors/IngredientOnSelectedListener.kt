package com.pr.purrfectrecipes.Connectors

interface IngredientOnSelectedListener {
    fun onSelectIngredient(selectedIngredient:String)
    fun deselectIngredient(deSelectedIngredient:String)
}