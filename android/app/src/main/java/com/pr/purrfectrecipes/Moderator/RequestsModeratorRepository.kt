package com.pr.purrfectrecipes.Moderator

import android.app.Activity
import android.util.Log
import android.widget.Toast
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

    fun approveSuggestion(suggestedIngredient:String , activity: Activity){
        ingredientsRef.child(suggestedIngredient.lowercase()).setValue(true)
        suggestionsRef.child(suggestedIngredient.lowercase()).removeValue()
        connector.suggestionApprove(suggestedIngredient,activity)
    }

    fun denySuggestion(suggestedIngredient:String , activity: Activity){
        suggestionsRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){

                    val suggestion= ds.key.toString()
                    if(suggestedIngredient.equals(suggestion)){
                        ds.ref.removeValue()
                        connector.suggestionDeny(suggestedIngredient,activity)
                        return
                    }
                }
                return

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText( activity,"Suggestion has not denied."+error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}