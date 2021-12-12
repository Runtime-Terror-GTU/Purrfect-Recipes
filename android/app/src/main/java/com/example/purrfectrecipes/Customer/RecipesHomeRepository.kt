package com.example.purrfectrecipes.Customer

import android.util.Log
import com.example.purrfectrecipes.Connectors.RecipesHomeVMRepConnector
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.Recipe
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class RecipesHomeRepository(val connector: RecipesHomeVMRepConnector)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    fun retrieveRecipes()
    {
        val id1="7686f73a-7a30-4e90-96d3-5ddda964fbcd"
        val id2="1098788c-f571-4f8a-ab5f-1d84ead8123a"
        val id3="cbb7f974-af8f-4ba8-af10-73813e61fcc0"
        val id4="6e92e147-450c-4fae-bcbe-875c7f86e80e"
        val id5="9f827e72-b637-4628-91ce-0ca9990e1c1e"
        recipesRef.child(id1).child(Constants.R_RECIPENAME).setValue("Cinnamon Buns")
        recipesRef.child(id1).child(Constants.R_RECIPEOWNER).setValue("858d37eb-b52c-416c-b3b0-398cf5818d64")
        recipesRef.child(id1).child(Constants.R_RECIPEDIFFICULTY).setValue("Medium")
        recipesRef.child(id1).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("13")

        recipesRef.child(id2).child(Constants.R_RECIPENAME).setValue("Apple Pie")
        recipesRef.child(id2).child(Constants.R_RECIPEOWNER).setValue("858d37eb-b52c-416c-b3b0-398cf5818d64")
        recipesRef.child(id2).child(Constants.R_RECIPEDIFFICULTY).setValue("Medium")
        recipesRef.child(id2).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("21")

        recipesRef.child(id3).child(Constants.R_RECIPENAME).setValue("Cheesecake")
        recipesRef.child(id3).child(Constants.R_RECIPEOWNER).setValue("858d37eb-b52c-416c-b3b0-398cf5818d64")
        recipesRef.child(id3).child(Constants.R_RECIPEDIFFICULTY).setValue("Hard")
        recipesRef.child(id3).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("35")

        recipesRef.child(id4).child(Constants.R_RECIPENAME).setValue("Lemonade")
        recipesRef.child(id4).child(Constants.R_RECIPEOWNER).setValue("858d37eb-b52c-416c-b3b0-398cf5818d64")
        recipesRef.child(id4).child(Constants.R_RECIPEDIFFICULTY).setValue("Easy")
        recipesRef.child(id4).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("15")

        recipesRef.child(id5).child(Constants.R_RECIPENAME).setValue("Brownie")
        recipesRef.child(id5).child(Constants.R_RECIPEOWNER).setValue("858d37eb-b52c-416c-b3b0-398cf5818d64")
        recipesRef.child(id5).child(Constants.R_RECIPEDIFFICULTY).setValue("Hard")
        recipesRef.child(id5).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue("106")

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

                    val recipe=Recipe(id, name, ownerId, difficulty, likes.toInt())
                    recipesArray.add(recipe)
                }

                for(recipe in recipesArray) {
                    usersRef.child(recipe.recipeOwner).addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            recipe.recipeOwner=snapshot.child(Constants.R_USERNAME).value.toString()
                            connector.onRecipesRetrieved(recipesArray)
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