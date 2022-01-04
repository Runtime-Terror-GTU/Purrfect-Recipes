package com.example.purrfectrecipes

import android.util.Log
import com.example.purrfectrecipes.User.*
import com.google.firebase.database.*
import java.util.*

class MainRepository(val connector: MainVMRepConnector)
{
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    fun retrieveUser(userID:String)
    {
        usersRef.child(userID).addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists()) {
                    connector.onUserRetrieved(null)
                    return
                }
                val username=snapshot.child(Constants.R_USERNAME).value.toString()
                val password=snapshot.child(Constants.R_USERPASS).value.toString()
                val email=snapshot.child(Constants.R_USEREMAIL).value.toString()
                val status=snapshot.child(Constants.R_USERSTATUS).value.toString()

                val user: User?
                if(status==UserStates.MODERATOR.text)
                    user= Moderator(userID, username, email, password)
                else if(status==UserStates.ADMIN.text)
                    user= Admin(userID, username, email, password)
                else if(status==UserStates.PREMIUM.text)
                    user= Customer(userID, username, email, password, status=CustomerStatus.PREMIUM)
                else if(status==UserStates.UNVERIFIED.text)
                    user= Customer(userID, username, email, password, status=CustomerStatus.UNVERIFIED)
                else
                    user= Customer(userID, username, email, password, status=CustomerStatus.VERIFIED)

                connector.onUserRetrieved(user)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }
}