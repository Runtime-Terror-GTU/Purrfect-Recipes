package com.pr.purrfectrecipes.Connectors

import android.app.Activity

interface RequestsModeratorVMRepConnecter {
    fun onSuggestionsRetrieved(list:ArrayList<String>?)
    fun suggestionApprove(suggestedIngredient:String , activity: Activity)
    fun suggestionDeny(suggestedIngredient:String , activity: Activity)
    fun size(count:Int)
}