package com.example.purrfectrecipes.Customer

import android.util.Log
import com.example.purrfectrecipes.Connectors.RecipesRetrievedListener
import com.example.purrfectrecipes.Constants
import com.example.purrfectrecipes.Recipe
import com.example.purrfectrecipes.User.Customer
import com.example.purrfectrecipes.User.CustomerStatus
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AddedRecipesRepository(val connector: RecipesRetrievedListener)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val addedRecipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(Hawk.get(Constants.LOGGEDIN_USERID)).child(Constants.R_ADDEDRECIPES)

    var seed = Random().nextLong()

    fun retrieveRecipes()
    {
        addedRecipesRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(addedR: DataSnapshot) {
                val recipesArray=ArrayList<Recipe>()
                var i=0
                var owner: Customer?=null
                for(ds in addedR.children)
                {
                    recipesRef.child(ds.key.toString()).addListenerForSingleValueEvent(object : ValueEventListener{
                        override fun onDataChange(snapshot: DataSnapshot) {
                            val id=snapshot.key.toString()
                            val name=snapshot.child(Constants.R_RECIPENAME).value.toString()
                            val ownerId=snapshot.child(Constants.R_RECIPEOWNER).value.toString()
                            val difficulty=snapshot.child(Constants.R_RECIPEDIFFICULTY).value.toString()
                            val likes=snapshot.child(Constants.R_RECIPEPURRFECTEDCOUNT).value.toString()
                            val pictureUrl=snapshot.child(Constants.R_RECIPEPICTURE).value.toString()

                            val recipe= Recipe(id, name, ownerId, difficulty, likes.toInt(), pictureUrl)
                            for(tag in snapshot.child(Constants.R_RECIPETAGS).children)
                                recipe.addTag(tag.key.toString())

                            recipesArray.add(recipe)

                            usersRef.child(recipe.recipeOwner).addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if(Hawk.get<String>(Constants.LOGGEDIN_USERID)==recipe.recipeOwner)
                                    {
                                        val ownerId=recipe.recipeOwner
                                        val ownerName=snapshot.child(Constants.R_USERNAME).value
                                        val ownerStatus=snapshot.child(Constants.R_USERSTATUS).value
                                        val ownerPic=snapshot.child(Constants.R_USERPICTURE).value
                                        val ownerEmail=snapshot.child(Constants.R_USEREMAIL).value

                                        if(ownerStatus== CustomerStatus.UNVERIFIED.text)
                                            owner= Customer(ownerId, ownerName as String, ownerEmail as String, status= CustomerStatus.UNVERIFIED, pic=ownerPic as String)
                                        else if(ownerStatus== CustomerStatus.VERIFIED.text)
                                            owner= Customer(ownerId, ownerName as String, ownerEmail as String, status= CustomerStatus.VERIFIED, pic=ownerPic as String)
                                        else
                                            owner= Customer(ownerId, ownerName as String, ownerEmail as String, status= CustomerStatus.PREMIUM, pic=ownerPic as String)

                                        for(pRecipe in snapshot.child(Constants.R_PURRFECTEDRECIPES).children)
                                            owner!!.addPurrfectedRecipe(pRecipe.key.toString())
                                    }
                                    recipe.recipeOwner=snapshot.child(Constants.R_USERNAME).value.toString()
                                    i++
                                    if(i==addedR.childrenCount.toInt())
                                    {
                                        recipesArray.shuffle(Random(seed))
                                        connector.onRecipesRetrieved(recipesArray, owner)
                                    }
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    TODO("Not yet implemented")
                                }
                            })
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

    fun increaseDayPurrfectedCount(recipeId:String, currentCount:Int, userId:String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue((currentCount+1).toString())
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeId).setValue(true)
    }

    fun decreaseDayPurrfectedCount(recipeId:String, currentCount:Int, userId:String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue((currentCount-1).toString())
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeId).removeValue()
    }
}