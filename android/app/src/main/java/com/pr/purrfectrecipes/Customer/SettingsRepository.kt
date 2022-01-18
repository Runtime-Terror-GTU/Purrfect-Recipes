package com.pr.purrfectrecipes.Customer

import android.app.Activity
import android.widget.Toast
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.Connectors.SettingsVMRepConnector
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.CustomerStatus

class SettingsRepository(val connector: SettingsVMRepConnector){
    private val userID= Hawk.get<String>(Constants.LOGGEDIN_USERID)
    private val userStatus= Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    val ingredients: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Suggested Ingredients")
    private val addedRecipe = ArrayList<String>()
    private val addedRecipeLike = ArrayList<Int>()

    fun findUserEmail(){

        usersRef.child(userID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var email = snapshot.child(Constants.R_USEREMAIL).value.toString()
                connector.getUserEmail(email)
                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun findUserStatus(){

        usersRef.child(userID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val status = snapshot.child(Constants.R_USERSTATUS).value.toString()
                connector.getUserStatus(status)
                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun suggestedIngredients(suggestedIngredient:String , activity: Activity){
        Toast.makeText( activity,"Your suggestion has been received.", Toast.LENGTH_SHORT).show()
        ingredients.child(suggestedIngredient.lowercase()).setValue(true)

    }
    fun updateUserEmail(newEmail:String){
        usersRef.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    usersRef.child(userID).child(Constants.R_USEREMAIL).setValue(newEmail)
                    usersRef.child(userID).child(Constants.R_USERSTATUS).setValue(CustomerStatus.VERIFIED.text)
                }

                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    fun recipeLikeRetrive(){
        if(userStatus==CustomerStatus.UNVERIFIED || userStatus==CustomerStatus.PREMIUM){
            connector.getMostLike(0)
            return
        }
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (addedRecipes in snapshot.child(userID).child(Constants.R_ADDEDRECIPES).children){
                    addedRecipe.add(addedRecipes.key.toString())
                    addedRecipeLike.add(0)
                }

                for(ds in snapshot.children){
                    val userId = ds.key.toString()

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
                        var user = Customer(userId, userName.toString(), userEmail.toString(), "12345", status, "Insert Bio Here", userPic.toString())

                        for (purrfectedRecipes in ds.child(Constants.R_PURRFECTEDRECIPES).children)
                            user.addPurrfectedRecipe(purrfectedRecipes.key.toString())

                        if(user.getPurrfectedRecipes().size!=0 && addedRecipe.size!=0){
                            val irange=user.getPurrfectedRecipes().size-1
                            val jrange = addedRecipe.size-1
                            for(i in 0..irange){
                                for(j in 0..jrange){
                                    if(addedRecipe.get(j).equals(user.getPurrfectedRecipes().get(i))){
                                        addedRecipeLike.set(j,addedRecipeLike[j]+1)
                                    }
                                }
                            }
                        }

                    }

                }
                connector.getMostLike(addedRecipeLike.maxOrNull()!!)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}