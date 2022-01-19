package com.pr.purrfectrecipes.Connectors

interface RequestsModeratorVMRepConnecter {
    fun onSuggestionsRetrieved(list:ArrayList<String>?)
    fun suggestionApprove(suggestedIngredient:String )
    fun suggestionDeny(suggestedIngredient:String)
    fun size(count:Int)
}