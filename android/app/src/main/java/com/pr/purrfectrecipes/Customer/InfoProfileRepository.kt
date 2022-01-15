package com.pr.purrfectrecipes.Customer

import com.google.firebase.database.*
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.Connectors.InfoProfileConnector
import com.pr.purrfectrecipes.Constants
import com.pr.purrfectrecipes.User.CustomerStatus

class InfoProfileRepository(val connector: InfoProfileConnector) {
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val usersRef2: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")

    private val userID = Hawk.get<String>(Constants.LOGGEDIN_USERID)
    private val userStatus = Hawk.get<String>(Constants.LOGGEDIN_USER_STATUS)
    fun getUserName(){
        usersRef.child(userID).child(Constants.R_USERNAME).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val name= snapshot.getValue().toString()
                    connector.getName(name)
                }
                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getUserBio(){
        usersRef.child(userID).child(Constants.R_USERBIO).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val bio= snapshot.getValue().toString()

                    connector.getBio(bio)
                }
                return

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getUserPic(){
        usersRef.child(userID).child(Constants.R_USERPICTURE).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if(snapshot.exists()){
                    val pictureUrl= snapshot.getValue().toString()
                    connector.getProfilePic(pictureUrl)
                }
                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun getUserAddedRecipeNum(){
        if(!userStatus.equals(CustomerStatus.PREMIUM.text)){
            connector.getAddedRecipeNum(0)
            return
        }
        usersRef.child(userID).child(Constants.R_ADDEDRECIPES).addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var count : Int =0
                for(ds in snapshot.children){
                    count+=1
                }
                connector.getAddedRecipeNum(count)
                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}