package com.pr.purrfectrecipes.Customer

import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.Connectors.InfoProfileConnector
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.CustomerStatus

class InfoProfileRepository(val connector: InfoProfileConnector) {
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")

    private val userID = Hawk.get<String>(Constants.LOGGEDIN_USERID)
    private val userStatus = Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS).text
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
                        val bio=snapshot.child(Constants.R_USERBIO).value.toString()

                        if (currentUserStatus == CustomerStatus.UNVERIFIED.text)
                            currentUser = Customer(
                                currentUserId,
                                currentUserName as String,
                                currentUserEmail as String,
                                status = CustomerStatus.UNVERIFIED,bio=bio,
                                pic = currentUserPic as String
                            )
                        else if (currentUserStatus == CustomerStatus.VERIFIED.text)
                            currentUser = Customer(
                                currentUserId,
                                currentUserName as String,
                                currentUserEmail as String,
                                status = CustomerStatus.VERIFIED, bio=bio,
                                pic = currentUserPic as String
                            )
                        else
                            currentUser = Customer(
                                currentUserId,
                                currentUserName as String,
                                currentUserEmail as String,
                                status = CustomerStatus.PREMIUM,bio=bio,
                                pic = currentUserPic as String
                            )

                        for (pRecipe in snapshot.child(Constants.R_ADDEDRECIPES).children)
                            currentUser.addAddedRecipe(pRecipe.key.toString())
                        connector.onUserRetrieved(currentUser)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }
                })
        }
    }
}