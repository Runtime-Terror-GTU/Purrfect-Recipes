package com.example.purrfectrecipes

import android.util.Log
import com.example.purrfectrecipes.Connectors.RecipeRetrievedListener
import com.example.purrfectrecipes.User.Customer
import com.example.purrfectrecipes.User.CustomerStatus
import com.google.firebase.database.*
import java.util.*
import kotlin.collections.ArrayList

class RecipeRepository(val connector: RecipeRetrievedListener)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val commentsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comments")

    fun retrieveRecipe(recipeId:String)
    {
        recipesRef.child(recipeId).addValueEventListener(object: ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {

                val id=ds.key.toString()
                val name=ds.child(Constants.R_RECIPENAME).value.toString()
                val ownerId=ds.child(Constants.R_RECIPEOWNER).value.toString()
                val difficulty=ds.child(Constants.R_RECIPEDIFFICULTY).value.toString()
                val likes=ds.child(Constants.R_RECIPEPURRFECTEDCOUNT).value.toString()
                val pictureUrl=ds.child(Constants.R_RECIPEPICTURE).value.toString()
                var recipeOverview=ds.child(Constants.R_RECIPEINGREDIENTSOVERVIEW).value.toString()

                val recipe= Recipe(id, name, ownerId, difficulty, likes.toInt(), pictureUrl, recipeOverview)
                for(tag in ds.child(Constants.R_RECIPETAGS).children)
                    recipe.addTag(tag.key.toString())
                for(ingredient in ds.child(Constants.R_RECIPEINGREDIENTS).children)
                    recipe.addIngredient(ingredient.key.toString())
                for(step in ds.child(Constants.R_RECIPEPREPARATION).children)
                    recipe.addStage(step.value.toString())
                for(commentId in ds.child(Constants.R_RECIPECOMMENTS).children)
                    recipe.addComment(commentId.key.toString())

                usersRef.child(recipe.recipeOwner).addValueEventListener(object :
                    ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val ownerId=recipe.recipeOwner
                        val ownerName=snapshot.child(Constants.R_USERNAME).value
                        val ownerStatus=snapshot.child(Constants.R_USERSTATUS).value
                        val ownerPic=snapshot.child(Constants.R_USERPICTURE).value
                        val ownerEmail=snapshot.child(Constants.R_USEREMAIL).value

                        var owner:Customer?=null

                        if(ownerStatus==CustomerStatus.UNVERIFIED.text)
                            owner=Customer(ownerId, ownerName as String, ownerEmail as String, status=CustomerStatus.UNVERIFIED, pic=ownerPic as String)
                        else if(ownerStatus==CustomerStatus.VERIFIED.text)
                            owner=Customer(ownerId, ownerName as String, ownerEmail as String, status=CustomerStatus.VERIFIED, pic=ownerPic as String)
                        else
                            owner=Customer(ownerId, ownerName as String, ownerEmail as String, status=CustomerStatus.PREMIUM, pic=ownerPic as String)

                        val comments=ArrayList<Comment>()
                        var j=0
                        if(recipe.getRecipeComments().size==0)
                            connector.onRecipeRetrieved(recipe, owner, comments)
                        for(commentId in recipe.getRecipeComments())
                        {
                            commentsRef.child(commentId).addValueEventListener(object: ValueEventListener{
                                override fun onDataChange(ds: DataSnapshot) {
                                    val commentOwnerId=ds.child(Constants.R_COMMENTOWNER).value
                                    val commentContent=ds.child(Constants.R_COMMENTCONTENT).value

                                    val comment=Comment(ds.key.toString(), commentOwnerId.toString(), commentContent.toString())
                                    comments.add(comment)
                                    j++
                                    if(j==recipe.getRecipeComments().size)
                                    {
                                        var i=0
                                        for(comment in comments)
                                        {
                                            usersRef.child(comment.getOwnerId()).addValueEventListener(object :ValueEventListener{
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    comment.ownerName=snapshot.child(Constants.R_USERNAME).value.toString()
                                                    if(snapshot.child(Constants.R_USERSTATUS).value.toString()==CustomerStatus.UNVERIFIED.text)
                                                        comment.ownerStatus=CustomerStatus.UNVERIFIED
                                                    else if(snapshot.child(Constants.R_USERSTATUS).value.toString()==CustomerStatus.VERIFIED.text)
                                                        comment.ownerStatus=CustomerStatus.VERIFIED
                                                    else
                                                        comment.ownerStatus=CustomerStatus.PREMIUM
                                                    comment.ownerPicURL=snapshot.child(Constants.R_USERPICTURE).value.toString()
                                                    i++
                                                    if(i==comments.size)
                                                        connector.onRecipeRetrieved(recipe, owner, comments)
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
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }
}