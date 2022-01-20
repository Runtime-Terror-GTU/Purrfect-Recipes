package com.pr.purrfectrecipes.Moderator

import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.pr.purrfectrecipes.Connectors.RecipesModeratorConnector
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.Recipe

class RecipesModeratorRepository(val connector: RecipesModeratorConnector) {
    private val recipesRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val recipes = ArrayList<Recipe>()
    private val commentsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comments")
    private val dayRecipeRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe of The Day")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")

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
        for(commentId in recipe.getRecipeComments())
            commentsRef.child(commentId).removeValue()

        usersRef.child(recipe.recipeOwner).child(Constants.R_ADDEDRECIPES).child(recipe.getRecipeID()).removeValue()
        usersRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children)
                {
                    ds.child(Constants.R_PURRFECTEDRECIPES).child(recipe.getRecipeID()).ref.removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        recipesRef.child(recipe.getRecipeID()).removeValue()
        dayRecipeRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children)
                {
                    if(ds.value.toString()==recipe.getRecipeID()){
                        recipesRef.addListenerForSingleValueEvent(object :ValueEventListener{
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for(recipe in snapshot.children)
                                {
                                    ds.ref.setValue(recipe.key.toString())
                                    break
                                }
                            }

                            override fun onCancelled(error: DatabaseError) {
                                TODO("Not yet implemented")
                            }
                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        val storageRef= FirebaseStorage.getInstance().getReference().child("Recipe Pictures")
        storageRef.child(recipe.getRecipeID()).delete()
    }
}