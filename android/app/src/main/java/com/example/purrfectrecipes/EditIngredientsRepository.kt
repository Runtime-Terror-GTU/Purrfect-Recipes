package com.example.purrfectrecipes

import com.example.purrfectrecipes.Connectors.EditIngredientsVMRepConnector
import com.google.firebase.database.*

class EditIngredientsRepository(val connector: EditIngredientsVMRepConnector)
{
    private val ingredientsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ingredients")

    fun retrieveIngredients()
    {
        ingredientsRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val ingredients=ArrayList<String>()
                for(ds in snapshot.children)
                {
                    val ingredientName=ds.key.toString()
                    ingredients.add(ingredientName)
                }
                connector.onIngredientsRetrieved(ingredients)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}