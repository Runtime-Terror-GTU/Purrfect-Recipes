package com.example.purrfectrecipes.Moderator

import android.app.Activity
import android.util.Log
import android.widget.Toast
import com.example.purrfectrecipes.Connectors.RequestsModeratorVMRepConnecter
import com.google.firebase.database.*


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
        ingredientsRef.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                ingredientsRef.child(suggestedIngredient.lowercase()).setValue(true)
                removeInDS(suggestedIngredient)
                connector.suggestionApprove(suggestedIngredient,activity)
            }
            override fun onCancelled(error: DatabaseError) {
               // Toast.makeText( Activity(),"Suggestion has not approved."+error, Toast.LENGTH_SHORT).show()
                Toast.makeText( activity,"Suggestion has not approved."+error, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun denySuggestion(suggestedIngredient:String , activity: Activity){
        suggestionsRef.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){

                    val suggestion= ds.key.toString()
                    if(suggestedIngredient.equals(suggestion)){
                        ds.ref.removeValue() //test etmedim henüz.
                        connector.suggestionDeny(suggestedIngredient,activity)
                    }
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText( activity,"Suggestion has not denied."+error, Toast.LENGTH_SHORT).show()
            }
        })
    }
    private fun removeInDS(suggestedIngredient:String ){
        suggestionsRef.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    val suggestion= ds.key.toString()
                    if(suggestedIngredient.equals(suggestion)){
                        ds.ref.removeValue() //test etmedim henüz.
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }
}