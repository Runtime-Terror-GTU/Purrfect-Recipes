package com.example.purrfectrecipes.Moderator

import android.app.Activity
import android.widget.Toast
import com.example.purrfectrecipes.Connectors.UsersModeratorVMRepConnecter
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.CustomerStatus


class UsersModeratorRepository(val connector: UsersModeratorVMRepConnecter)  {


    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val usersRef2: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val commentRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comments")
    private val recipeRef :  DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes")
    private val commentRef2 : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Comments")
    private val recipeOfDayRef : DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Recipe of The Day")

    private val userID = ArrayList<String>()
    private val users = ArrayList<Customer>()
    private val recipeIDs = ArrayList<String>()
    private var addedRecipeNum = 0
    init{
        userRetrive()
    }
    fun userRetrive(){
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
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

                        for (purrfectedRecipes in snapshot.child(Constants.R_PURRFECTEDRECIPES).children)
                            user.addPurrfectedRecipe(purrfectedRecipes.key.toString())
                        for (addedRecipes in snapshot.child(Constants.R_ADDEDRECIPES).children)
                            user.addAddedRecipe(addedRecipes.key.toString())

                        users.add(user!!)

                    }
                  //  userID.add(user)

                }
                connector.onUsersRetrieved(users)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
   /* fun userInfoRetrive(id:String){
        usersRef.child(id).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name  = snapshot.child(Constants.R_USERNAME).value.toString()
                val status = snapshot.child(Constants.R_USERSTATUS).value.toString()
                val profilePic = snapshot.child(Constants.R_USERPICTURE).value.toString()
                addedRecipeNum = 0
                usersRef2.child(id).child(Constants.R_ADDEDRECIPES).addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(ss: DataSnapshot) {
                        for(ds in ss.children){
                            addedRecipeNum += 1
                        }
                        if(!status.equals(CustomerStatus.ADMIN.text) && !status.equals(CustomerStatus.MODERATOR.text) ){
                            val recivedUser = UserClass(id,name,status,profilePic,addedRecipeNum)
                            users.add(recivedUser)
                            addedRecipeNum = 0
                        }

                    }
                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun userRetrive(){
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    val user= ds.key.toString()
                    userInfoRetrive(user)
                }
                connector.onUsersRetrieved(users)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }*/

    fun deleteUser(user:Customer, activity:Activity){

       usersRef.child(user.getUserID()).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    if(ds.key.toString().equals(Constants.R_ADDEDRECIPES)){
                        getRecipeID(user.getUserID())
                    }
                    deleteUserComments(user.getUserID())
                }

            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText( activity,"User cannot be deleted."+error, Toast.LENGTH_SHORT).show()
            }
        })
        usersRef.child(user.getUserID()).removeValue()
        val storageRef= FirebaseStorage.getInstance().getReference().child("User Pictures")
        storageRef.child(user.getUserID()).delete()
    }
    fun getRecipeID(userId: String){
        usersRef.child(userId).child(Constants.R_ADDEDRECIPES).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    recipeIDs.add(ds.key.toString())
                    getCommentID(ds.key.toString())
                    deleteRecipe(ds.key.toString())
                    allUser(ds.key.toString())
                    checkRecipeOfTheDay(ds.key.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getCommentID(recipeID: String){
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
    }
    fun deleteComments(commentID:String){
        commentRef.child(commentID).removeValue()
      /*  commentRef.child(commentID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                commentRef.child(commentID).removeValue()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/
    }
    fun deleteRecipe(recipeID: String){
        recipeRef.child(recipeID).removeValue()
        val storageRef= FirebaseStorage.getInstance().getReference().child("Recipe Pictures")
        storageRef.child(recipeID).delete()
        /*
        recipeRef.child(recipeID).child(Constants.R_RECIPECOMMENTS).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                recipeRef.child(recipeID).removeValue()
                val storageRef= FirebaseStorage.getInstance().getReference().child("Recipe Pictures")
                storageRef.child(recipeID).delete()
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })*/
    }
    fun checkRecipeOfTheDay(recipeID:String){
        recipeOfDayRef.addListenerForSingleValueEvent(object :ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (ds in snapshot.children) {
                    if (ds.value.toString().equals(recipeID)) {
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
                  /*  commentRef2.child(commentID).addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            commentRef2.child(commentID).removeValue()
                        }
                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })*/
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun deleteRecipeInPurrfectedRecipe(userId: String,recipeID: String){
        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    val recipe= ds.key.toString()
                    if(recipe.equals(recipeID)){
                        usersRef.child(userId).child(Constants.R_PURRFECTEDRECIPES).child(recipeID).removeValue()
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun allUser(recipeID: String){
        usersRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for(ds in snapshot.children){
                    val user= ds.key.toString()
                    deleteRecipeInPurrfectedRecipe(user,recipeID)
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}