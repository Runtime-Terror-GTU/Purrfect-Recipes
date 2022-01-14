package com.pr.purrfectrecipes.Customer

import com.pr.purrfectrecipes.Connectors.RecipesRetrievedListener
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.Recipe
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.CustomerStatus
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.orhanobut.hawk.Hawk
import java.util.*
import kotlin.collections.ArrayList

class AddedRecipesRepository(val connector: RecipesRetrievedListener)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val commentsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comments")
    private val dayRecipeRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe of The Day")
    private var userId:String="null"

    var seed = Random().nextLong()

    init{
        val retrievedID=Hawk.get<String>(Constants.LOGGEDIN_USERID)
        if(retrievedID!=null)
            userId=retrievedID
    }

    fun retrieveUser()
    {
        if(Hawk.get<String>(Constants.LOGGEDIN_USERID)!=null) {
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
        val addedRecipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child(Constants.R_ADDEDRECIPES)
        addedRecipesRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(addedR: DataSnapshot) {
                val recipesArray=ArrayList<Recipe>()
                var i=0
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
                            var recipeOverview=snapshot.child(Constants.R_RECIPEINGREDIENTSOVERVIEW).value.toString()

                            val recipe= Recipe(id, name, ownerId, difficulty, likes.toInt(), pictureUrl, recipeOverview)
                            for(tag in snapshot.child(Constants.R_RECIPETAGS).children)
                                recipe.addTag(tag.key.toString())
                            for(ingredient in snapshot.child(Constants.R_RECIPEINGREDIENTS).children)
                                recipe.addIngredient(ingredient.key.toString())
                            for(step in snapshot.child(Constants.R_RECIPEPREPARATION).children)
                                recipe.addStage(step.value.toString())

                            recipesArray.add(recipe)

                            usersRef.child(recipe.recipeOwner).addListenerForSingleValueEvent(object :
                                ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    recipe.recipeOwner=snapshot.child(Constants.R_USERNAME).value.toString()
                                    i++
                                    if(i==addedR.childrenCount.toInt())
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

    fun removeRecipe(deletedRecipe:Recipe)
    {
        for(commentId in deletedRecipe.getRecipeComments())
            commentsRef.child(commentId).removeValue()

        usersRef.child(Hawk.get(Constants.LOGGEDIN_USERID)).child(Constants.R_ADDEDRECIPES).child(deletedRecipe.getRecipeID()).removeValue()
        usersRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children)
                {
                    ds.child(Constants.R_PURRFECTEDRECIPES).child(deletedRecipe.getRecipeID()).ref.removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        recipesRef.child(deletedRecipe.getRecipeID()).removeValue()
        dayRecipeRef.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children)
                {
                    if(ds.value.toString()==deletedRecipe.getRecipeID()){
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
        storageRef.child(deletedRecipe.getRecipeID()).delete()
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