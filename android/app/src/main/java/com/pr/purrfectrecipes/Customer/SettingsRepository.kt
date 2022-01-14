package com.pr.purrfectrecipes.Customer

import android.app.Activity
import android.widget.Toast
import com.pr.purrfectrecipes.Connectors.SettingsVMRepConnector
import com.pr.purrfectrecipes.Constants
import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk

class SettingsRepository(val connector: SettingsVMRepConnector){
    private val userID= Hawk.get<String>(Constants.LOGGEDIN_USERID)
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    val ingredients: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Suggested Ingredients")

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
        ingredients.addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Toast.makeText( activity,"Your suggestion has been received.", Toast.LENGTH_SHORT).show()
                ingredients.child(suggestedIngredient.lowercase()).setValue(true)
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText( activity,"Your suggestion has not been received."+error, Toast.LENGTH_SHORT).show()
            }
        })
    }
    fun updateUserEmail(newEmail:String){
        usersRef.child(userID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var email = snapshot.child(Constants.R_USEREMAIL).value.toString()
                usersRef.child(Constants.R_USEREMAIL).setValue(newEmail)
                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

}