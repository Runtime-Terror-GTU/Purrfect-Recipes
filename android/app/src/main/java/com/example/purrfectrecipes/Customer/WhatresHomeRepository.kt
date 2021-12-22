package com.example.purrfectrecipes.Customer

import android.util.Log
import com.example.purrfectrecipes.Connectors.RecipeRetrievedListener
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.Recipe
import com.google.firebase.database.*

class WhatresHomeRepository(val connector: RecipeRetrievedListener)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")

    fun retrieveRecipes()
    {
        recipesRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val recipesArray=ArrayList<Recipe>()
                for(ds in snapshot.children)
                {
                    val id=ds.key.toString()
                    val name=ds.child(Constants.R_RECIPENAME).value.toString()
                    val ownerId=ds.child(Constants.R_RECIPEOWNER).value.toString()
                    val difficulty=ds.child(Constants.R_RECIPEDIFFICULTY).value.toString()
                    val likes=ds.child(Constants.R_RECIPEPURRFECTEDCOUNT).value.toString()
                    val pictureUrl=ds.child(Constants.R_RECIPEPICTURE).value.toString()

                    val recipe= Recipe(id, name, ownerId, difficulty, likes.toInt(), pictureUrl)
                    for(tag in ds.child(Constants.R_RECIPETAGS).children)
                        recipe.addTag(tag.key.toString())
                    for(ingredient in ds.child(Constants.R_RECIPEINGREDIENTS).children)
                        recipe.addIngredient(ingredient.key.toString())

                    recipesArray.add(recipe)

                }

                var i=0
                for(recipe in recipesArray) {
                    usersRef.child(recipe.recipeOwner).addValueEventListener(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            recipe.recipeOwner=snapshot.child(Constants.R_USERNAME).value.toString()
                            i++
                            if(i==recipesArray.size)
                            {
                                recipesArray.shuffle()
                                connector.onRecipesRetrieved(recipesArray)
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }
}