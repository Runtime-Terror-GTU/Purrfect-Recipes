package com.pr.purrfectrecipes

import android.net.Uri
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.orhanobut.hawk.Hawk
import com.pr.purrfectrecipes.Connectors.EditVMRepConnector
import com.pr.purrfectrecipes.User.Customer
import com.pr.purrfectrecipes.User.CustomerStatus

class EditRepository(val connector: EditVMRepConnector) {
    private val usersRef: DatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users")
    private val userID = Hawk.get<String>(Constants.LOGGEDIN_USERID)
    private val userStatus = Hawk.get<CustomerStatus>(Constants.LOGGEDIN_USER_STATUS)
    fun userInfoRetrive(){
        usersRef.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child(Constants.R_USERNAME).value.toString()
                val bio = snapshot.child(Constants.R_USERBIO).value.toString()
                val pic = snapshot.child(Constants.R_USERPICTURE).value.toString()
                val user = Customer(userID, name, "email","12345", userStatus, bio, pic)
                connector.userInfo(user)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
    fun changePassword(newPass:String){
        usersRef.child(userID).child(Constants.R_USERPASS).setValue(newPass)
    }
    fun changePicture(newPic: Uri){
        val storageRef= FirebaseStorage.getInstance().getReference().child("User Pictures")
        storageRef.child(userID).delete()
        storageRef.child(userID).putFile(newPic)
            .addOnSuccessListener {
                val result = it.metadata!!.reference!!.downloadUrl;
                result.addOnSuccessListener {

                    var imageLink = it.toString()
                    usersRef.child(userID).child(Constants.R_USERPICTURE).setValue(imageLink)
                }
            }
    }
}