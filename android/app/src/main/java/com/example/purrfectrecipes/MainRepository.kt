package com.example.purrfectrecipes

import android.util.Log
import com.example.purrfectrecipes.User.*
import com.google.firebase.database.*
import java.util.*

class MainRepository(val connector: MainVMRepConnector)
{
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    fun retrieveUser(username:String, password:String)
    {
        usersRef.orderByChild(Constants.R_USERNAME).equalTo(username).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if(!snapshot.exists())
                    connector.onUserRetrieved(null)
                else
                {
                    for(ds in snapshot.children)
                        if(ds.child(Constants.R_USERNAME).value==username && ds.child(Constants.R_USERPASS).value==password)
                        {
                            val id=ds.key.toString()
                            val email=ds.child(Constants.R_USEREMAIL).value.toString()
                            val status=ds.child(Constants.R_USERSTATUS).value.toString()

                            val user: User?
                            if(status==UserStates.MODERATOR.text)
                                user= Moderator(id, username, email, password)
                            else if(status==UserStates.ADMIN.text)
                                user= Admin(id, username, email, password)
                            else if(status==UserStates.PREMIUM.text)
                                user= Customer(id, username, email, password, status=CustomerStatus.PREMIUM)
                            else if(status==UserStates.UNVERIFIED.text)
                                user= Customer(id, username, email, password, status=CustomerStatus.UNVERIFIED)
                            else
                                user= Customer(id, username, email, password, status=CustomerStatus.VERIFIED)

                            connector.onUserRetrieved(user)
                            break
                        }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }
}