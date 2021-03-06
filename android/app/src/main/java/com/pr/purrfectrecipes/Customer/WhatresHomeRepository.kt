package com.pr.purrfectrecipes.Customer

import android.util.Log
import com.pr.purrfectrecipes.Connectors.RecipesRetrievedListener
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.Recipe
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.CustomerStatus
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk
import java.util.*
import kotlin.collections.ArrayList

class WhatresHomeRepository(val connector: RecipesRetrievedListener)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private var userId:String="null"

    init{
        val retrievedID=Hawk.get<String>(Constants.LOGGEDIN_USERID)
        if(retrievedID!=null)
            userId=retrievedID
    }

    var seed = Random().nextLong()

    fun retrieveUser()
    {
        if(Hawk.get<String>(Constants.LOGGEDIN_USERID)!=null && Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)!=CustomerStatus.MODERATOR) {
            usersRef.child(Hawk.get(Constants.LOGGEDIN_USERID))
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(Hawk.get<String>(Constants.LOGGEDIN_USERID)==null)
                            return
                        var currentUser: Customer? = null
                        val currentUserId = Hawk.get<String>(Constants.LOGGEDIN_USERID)
                        val currentUserName = snapshot.child(Constants.R_USERNAME).value
                        val currentUserStatus = snapshot.child(Constants.R_USERSTATUS).value
                        val currentUserPic = snapshot.child(Constants.R_USERPICTURE).value
                        val currentUserEmail = snapshot.child(Constants.R_USEREMAIL).value

                        if (currentUserStatus == CustomerStatus.UNVERIFIED.text)
                            currentUser = Customer(
                                currentUserId,
                                currentUserName as String,
                                currentUserEmail as String,
                                status = CustomerStatus.UNVERIFIED,
                                pic = currentUserPic as String
                            )
                        else if (currentUserStatus == CustomerStatus.VERIFIED.text)
                            currentUser = Customer(
                                currentUserId,
                                currentUserName as String,
                                currentUserEmail as String,
                                status = CustomerStatus.VERIFIED,
                                pic = currentUserPic as String
                            )
                        else
                            currentUser = Customer(
                                currentUserId,
                                currentUserName as String,
                                currentUserEmail as String,
                                status = CustomerStatus.PREMIUM,
                                pic = currentUserPic as String
                            )

                        for (pRecipe in snapshot.child(Constants.R_PURRFECTEDRECIPES).children)
                            currentUser.addPurrfectedRecipe(pRecipe.key.toString())
                        connector.onUserRetrieved(currentUser)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }
        else
            connector.onUserRetrieved(null)
    }

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
                var owner: Customer?=null
                for(recipe in recipesArray) {
                    usersRef.child(recipe.recipeOwner).addListenerForSingleValueEvent(object :
                        ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            recipe.recipeOwner=snapshot.child(Constants.R_USERNAME).value.toString()
                            i++
                            if(i==recipesArray.size)
                            {
                                recipesArray.shuffle(Random(seed))
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

    fun increaseDayPurrfectedCount(recipeId:String, currentCount:Int, userId:String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue((currentCount+1).toString())
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeId).setValue(true)

        val addedRecipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child(Constants.R_ADDEDRECIPES)
        addedRecipesRef.child(recipeId).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    addedRecipesRef.child(recipeId).setValue(false)
                    addedRecipesRef.child(recipeId).setValue(true)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun decreaseDayPurrfectedCount(recipeId:String, currentCount:Int, userId:String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue((currentCount-1).toString())
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeId).removeValue()

        val addedRecipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child(Constants.R_ADDEDRECIPES)
        addedRecipesRef.child(recipeId).addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists())
                {
                    addedRecipesRef.child(recipeId).setValue(false)
                    addedRecipesRef.child(recipeId).setValue(true)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}