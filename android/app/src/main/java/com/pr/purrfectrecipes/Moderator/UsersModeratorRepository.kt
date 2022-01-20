package com.example.purrfectrecipes.Moderator

import android.app.Activity
import com.example.purrfectrecipes.Connectors.UsersModeratorVMRepConnecter
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.CustomerStatus


class UsersModeratorRepository(val connector: UsersModeratorVMRepConnecter)  {


    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
   private val commentRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comments")
    private val recipeRef :  DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val commentRef2 : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comments")
    private val recipeOfDayRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe of The Day")

    private val users = ArrayList<Customer>()
    private val recipeIDs = ArrayList<String>()
    init{
        userRetrive()
    }
    fun userRetrive(){
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                users.clear()
                for(ds in snapshot.children){
                    val userID= ds.key.toString()
                    if(ds.child(Constants.R_USERSTATUS).value!=CustomerStatus.ADMIN.text &&
                        ds.child(Constants.R_USERSTATUS).value!=CustomerStatus.MODERATOR.text ){

                        val userName = ds.child(Constants.R_USERNAME).value
                        val userStatus = ds.child(Constants.R_USERSTATUS).value
                        val userPic = ds.child(Constants.R_USERPICTURE).value
                        val userEmail = ds.child(Constants.R_USEREMAIL).value
                        var status = CustomerStatus.UNVERIFIED
                        if(userStatus.toString().equals(CustomerStatus.VERIFIED.text)){
                            status = CustomerStatus.VERIFIED
                        }
                        if(userStatus.toString().equals(CustomerStatus.PREMIUM.text)){
                            status = CustomerStatus.PREMIUM
                        }
                        var user = Customer(userID, userName.toString(), userEmail.toString(), "12345", status, "Insert Bio Here", userPic.toString())

                        for (purrfectedRecipes in ds.child(Constants.R_PURRFECTEDRECIPES).children)
                            user.addPurrfectedRecipe(purrfectedRecipes.key.toString())
                        for (addedRecipes in ds.child(Constants.R_ADDEDRECIPES).children)
                            user.addAddedRecipe(addedRecipes.key.toString())

                        users.add(user!!)
                    }

                }
                connector.onUsersRetrieved(users)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun deleteUser(user:Customer, activity:Activity){

        for(addedrecipe in user.getAddedRecipes()){
            deleteRecipe(addedrecipe,user.getUserID())
        }
        deleteUserComments(user.getUserID())
        usersRef.child(user.getUserID()).removeValue()
        val storageRef= FirebaseStorage.getInstance().getReference().child("User Pictures")
        storageRef.child(user.getUserID()).delete()
    }
    fun deleteRecipe(recipeID:String,userId: String){
        recipeRef.child(recipeID).child(Constants.R_RECIPECOMMENTS).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    deleteComments(ds.key.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        usersRef.child(userId).child(Constants.R_ADDEDRECIPES).child(recipeID).removeValue()
        usersRef.addListenerForSingleValueEvent(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children)
                {
                    ds.child(Constants.R_PURRFECTEDRECIPES).child(recipeID).ref.removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        recipeRef.child(recipeID).removeValue()
        recipeOfDayRef.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    if (ds.value.toString() == recipeID) {
                        recipeRef.addListenerForSingleValueEvent(object : ValueEventListener {
                            override fun onDataChange(snapshot: DataSnapshot) {
                                for (recipe in snapshot.children) {
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
            storageRef.child(recipeID).delete()

    }

    fun deleteComments(commentID:String){
        commentRef.child(commentID).removeValue()
    }

    fun deleteUserComments(userId: String){
        commentRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children) {
                    deleteUserCommentsHelper(userId,ds.key.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun deleteUserCommentsHelper(userId: String,commentID: String){
        commentRef.child(commentID).child(Constants.R_COMMENTOWNER).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val id = snapshot.getValue().toString()
                if(id.equals(userId)){
                    commentRef2.child(commentID).removeValue()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}