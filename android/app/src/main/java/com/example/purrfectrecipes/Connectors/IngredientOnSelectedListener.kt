package com.example.purrfectrecipes.Connectors

interface IngredientOnSelectedListener {
    fun onSelectIngredient(selectedIngredient:String)
    fun deselectIngredient(deSelectedIngredient:String)
}