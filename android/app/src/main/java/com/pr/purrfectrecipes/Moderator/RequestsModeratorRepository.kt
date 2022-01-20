package com.pr.purrfectrecipes.Moderator

import android.util.Log
import com.google.firebase.database.*
import com.pr.purrfectrecipes.Connectors.RequestsModeratorVMRepConnecter


class RequestsModeratorRepository(val connector: RequestsModeratorVMRepConnecter) {
    private val suggestionsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Suggested Ingredients")
    private val ingredientsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ingredients")

    fun retrieveSuggestions(){
        suggestionsRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val suggestionsArray = ArrayList<String>()
                for(ds in snapshot.children){
                    val suggestion= "Suggested ingredient: " + ds.key.toString()
                    suggestionsArray.add(suggestion)
                }
                connector.onSuggestionsRetrieved(suggestionsArray)
                connector.size(suggestionsArray.size)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }

    fun approveSuggestion(suggestedIngredient:String ){
        ingredientsRef.child(suggestedIngredient.lowercase()).setValue(true)
        suggestionsRef.child(suggestedIngredient.lowercase()).removeValue()
    }

    fun denySuggestion(suggestedIngredient:String){
        suggestionsRef.child(suggestedIngredient).removeValue()

    }
}