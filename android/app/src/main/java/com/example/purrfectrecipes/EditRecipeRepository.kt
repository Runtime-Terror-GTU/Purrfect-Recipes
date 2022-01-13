package com.example.purrfectrecipes

import android.net.Uri
import android.util.Log
import com.example.purrfectrecipes.Connectors.EditIngredientsVMRepConnector
import com.example.purrfectrecipes.Connectors.FilterVMRepConnector
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.orhanobut.hawk.Hawk

class EditRecipeRepository(val connectorTags: FilterVMRepConnector, val connectorIngredients: EditIngredientsVMRepConnector)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val tagsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Tags")
    private val ingredientsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Ingredients")
    private var userId:String="null"

    init{
        val retrievedID=Hawk.get<String>(Constants.LOGGEDIN_USERID)
        if(retrievedID!=null)
            userId=retrievedID
    }

    val storageRef= FirebaseStorage.getInstance().getReference().child("Recipe Pictures")

    fun retrieveTags()
    {
        tagsRef.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val tags=ArrayList<String>()
                for(ds in snapshot.children)
                {
                    val tagName=ds.key.toString()
                    tags.add(tagName)
                }
                connectorTags.onTagsRetrieved(tags)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

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
                connectorIngredients.onIngredientsRetrieved(ingredients)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun updateRecipe(recipe:Recipe, imageUri: Uri?)
    {
        recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue(recipe.recipeLikes.toString())
        recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEDIFFICULTY).setValue(recipe.recipeDifficulty)
        recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEINGREDIENTSOVERVIEW).setValue(recipe.recipeIngredientsOverview)
        recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPENAME).setValue(recipe.recipeName)
        recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEOWNER).setValue(recipe.recipeOwner)
        if(imageUri!=null) {
            recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEPICTURE).setValue(
                "https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/Recipe%20Pictures%2Fno_photo.jpg?alt=media&token=e4eff4ad-f601-47b9-9916-1d1cfb908552"
            )
        }

        recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEINGREDIENTS).removeValue()
        for(ingredient in recipe.getRecipeIngredients())
        {
            recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEINGREDIENTS).child(ingredient).setValue(true)
        }
        recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEPREPARATION).removeValue()
        var i=1
        i=1
        for(steps in recipe.getRecipeStages())
        {
            recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEPREPARATION).child(i.toString()).setValue(steps)
            i++
        }
        recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPETAGS).removeValue()
        for(tags in recipe.getRecipeTags())
        {
            recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPETAGS).child(tags).setValue(true)
        }

        if(imageUri!=null) {
            storageRef.child(recipe.getRecipeID()).putFile(imageUri)
                .addOnSuccessListener {
                    val result = it.metadata!!.reference!!.downloadUrl;
                    result.addOnSuccessListener {

                        var imageLink = it.toString()
                        recipesRef.child(recipe.getRecipeID()).child(Constants.R_RECIPEPICTURE)
                            .setValue(imageLink)
                    }
                }
        }

        val addedRecipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child(Constants.R_ADDEDRECIPES)
        addedRecipesRef.child(recipe.getRecipeID()).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    addedRecipesRef.child(recipe.getRecipeID()).setValue(false)
                    addedRecipesRef.child(recipe.getRecipeID()).setValue(true)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}