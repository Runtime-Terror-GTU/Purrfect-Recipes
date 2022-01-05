package com.example.purrfectrecipes

import android.util.Log
import com.example.purrfectrecipes.Connectors.RecipeRetrievedListener
import com.example.purrfectrecipes.User.Customer
import com.example.purrfectrecipes.User.CustomerStatus
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk
import java.util.*
import kotlin.collections.ArrayList

class RecipeRepository(val connector: RecipeRetrievedListener)
{
    private val recipesRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val commentsRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comments")
    private val dayRecipeRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe of The Day")

    fun retrieveUser()
    {
        if(Hawk.get<String>(Constants.LOGGEDIN_USERID)!=null) {
            usersRef.child(Hawk.get(Constants.LOGGEDIN_USERID))
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
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

    fun retrieveRecipe(recipeId:String)
    {
        recipesRef.child(recipeId).addListenerForSingleValueEvent(object: ValueEventListener {
            override fun onDataChange(ds: DataSnapshot) {
                val id=ds.key.toString()
                retrieveUser()
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

                usersRef.child(recipe.recipeOwner).addListenerForSingleValueEvent(object :
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

                        for(pRecipe in snapshot.child(Constants.R_PURRFECTEDRECIPES).children)
                            owner.addPurrfectedRecipe(pRecipe.key.toString())

                        val comments=ArrayList<Comment>()
                        var j=0
                        if(recipe.getRecipeComments().size==0)
                            connector.onRecipeRetrieved(recipe, owner, comments)
                        for(commentId in recipe.getRecipeComments())
                        {
                            commentsRef.child(commentId).addListenerForSingleValueEvent(object: ValueEventListener{
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
                                            usersRef.child(comment.getOwnerId()).addListenerForSingleValueEvent(object :ValueEventListener{
                                                override fun onDataChange(snapshot: DataSnapshot) {
                                                    if(!snapshot.exists())
                                                    {
                                                        val defaultPicUrl="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic1.png?alt=media&token=9c1dc943-b8f7-4afc-acd2-d8385f431601"
                                                        comment.ownerName="Deleted User"
                                                        comment.ownerStatus=CustomerStatus.UNVERIFIED
                                                        comment.ownerPicURL=defaultPicUrl
                                                    }
                                                    else {
                                                        comment.ownerName =
                                                            snapshot.child(Constants.R_USERNAME).value.toString()
                                                        if (snapshot.child(Constants.R_USERSTATUS).value.toString() == CustomerStatus.UNVERIFIED.text)
                                                            comment.ownerStatus =
                                                                CustomerStatus.UNVERIFIED
                                                        else if (snapshot.child(Constants.R_USERSTATUS).value.toString() == CustomerStatus.VERIFIED.text)
                                                            comment.ownerStatus =
                                                                CustomerStatus.VERIFIED
                                                        else
                                                            comment.ownerStatus =
                                                                CustomerStatus.PREMIUM
                                                        comment.ownerPicURL =
                                                            snapshot.child(Constants.R_USERPICTURE).value.toString()
                                                    }
                                                    i++
                                                    if(i==comments.size) {
                                                        connector.onRecipeRetrieved(recipe, owner, comments)
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

    fun saveComment(comment:Comment, recipeId: String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPECOMMENTS).child(comment.getId()).setValue("true")
        commentsRef.child(comment.getId()).child(Constants.R_COMMENTCONTENT).setValue(comment.getCommentContent())
        commentsRef.child(comment.getId()).child(Constants.R_COMMENTOWNER).setValue(comment.getOwnerId())
    }

    fun removeComment(comment:Comment?, recipeId: String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPECOMMENTS).child(comment!!.getId()).removeValue()
        commentsRef.child(comment!!.getId()).removeValue()
    }

    fun removeRecipe(deletedRecipe:Recipe)
    {
        for(commentId in deletedRecipe.getRecipeComments())
            commentsRef.child(commentId).removeValue()

        usersRef.child(deletedRecipe.recipeOwner).child(Constants.R_ADDEDRECIPES).child(deletedRecipe.getRecipeID()).removeValue()
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
    }

    fun increasePurrfectedCount(recipeId:String, currentCount:Int, userId:String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue((currentCount+1).toString())
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeId).setValue(true)
    }

    fun decreasePurrfectedCount(recipeId:String, currentCount:Int, userId:String)
    {
        recipesRef.child(recipeId).child(Constants.R_RECIPEPURRFECTEDCOUNT).setValue((currentCount-1).toString())
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeId).removeValue()
    }
}