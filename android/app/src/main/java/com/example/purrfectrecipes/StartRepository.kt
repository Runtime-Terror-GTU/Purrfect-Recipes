package com.example.purrfectrecipes

import android.util.Log
import com.example.purrfectrecipes.User.*
import com.google.firebase.database.*
import java.util.*

class StartRepository(val connector: StartVMRepConnector)
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

                            Log.i("here", status)
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
                            return
                        }
                    connector.onUserRetrieved(null)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }

    fun addUser(user: Customer)
    {
        usersRef.orderByChild(Constants.R_USERNAME).equalTo(user.getUsername()).addListenerForSingleValueEvent(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    connector.onAddUserReturned(AuthenticationStates.FAILED_REGISTER)
                }
                else
                {
                    val defaultPicUrl="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic1.png?alt=media&token=9c1dc943-b8f7-4afc-acd2-d8385f431601"
                    usersRef.child(user.getUserID()).child(Constants.R_USERNAME).setValue(user.getUsername())
                    usersRef.child(user.getUserID()).child(Constants.R_USEREMAIL).setValue(user.getUserEmail())
                    usersRef.child(user.getUserID()).child(Constants.R_USERPASS).setValue(user.getUserPassword())
                    usersRef.child(user.getUserID()).child(Constants.R_USERBIO).setValue(user.getUserBio())
                    usersRef.child(user.getUserID()).child(Constants.R_USERPICTURE).setValue(defaultPicUrl)
                    usersRef.child(user.getUserID()).child(Constants.R_USERSTATUS).setValue(user.getUserStatus())

                    connector.onUserRetrieved(user)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("ERROR", error.message)
            }
        })
    }
}