package com.pr.purrfectrecipes.Moderator

import com.google.firebase.database.*
import com.pr.purrfectrecipes.Connectors.RecipesModeratorConnector
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.Recipe

class RecipesModeratorRepository(val connector: RecipesModeratorConnector) {
    private val recipesRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val recipes = ArrayList<Recipe>()
    fun recipeRetrive(){
       recipesRef.addValueEventListener(object : ValueEventListener {
           override fun onDataChange(snapshot: DataSnapshot) {
               recipes.clear()
               for(ds in snapshot.children){
                   val recipeId=ds.key.toString()
                   val name = ds.child(Constants.R_RECIPENAME).value.toString()
                   val owner = ds.child(Constants.R_RECIPEOWNER).value.toString()
                   val pictureURL = ds.child(Constants.R_RECIPEPICTURE).value.toString()
                   val currentRecipe = Recipe(recipeId, name, owner, "difficulty", 0, pictureURL, "No Ingredients Overview")
                   recipes.add(currentRecipe)
               }
               connector.onRecipeRetrieved(recipes)
           }
           override fun onCancelled(error: DatabaseError) {
               TODO("Not yet implemented")
           }
       })
    }
    fun deleteRecipe(recipe:Recipe){

    }
}