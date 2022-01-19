package com.pr.purrfectrecipes

import android.app.Activity
import android.net.Uri
import android.widget.Toast
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
        usersRef.child(userID).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val name = snapshot.child(Constants.R_USERNAME).value.toString()
                val bio = snapshot.child(Constants.R_USERBIO).value.toString()
                val pic = snapshot.child(Constants.R_USERPICTURE).value.toString()
                val pass=snapshot.child(Constants.R_USERPASS).value.toString()
                val user = Customer(userID, name, "email",pass, userStatus, bio, pic)
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
    fun changeBio(bio:String){
        usersRef.child(userID).child(Constants.R_USERBIO).setValue(bio)

    }
    fun changeUserName(name:String,activity: Activity){
        usersRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var isUnique=true
                for(ds in snapshot.children){
                    val userName = ds.child(Constants.R_USERNAME).value
                    if(name.equals(userName.toString())){
                        Toast.makeText(activity,"There is already a user with this username", Toast.LENGTH_SHORT).show()
                        isUnique=false
                        return
                    }

                }
                if(isUnique){
                    usersRef.child(userID).child(Constants.R_USERNAME).setValue(name)
                    Toast.makeText(activity,"Your username is changed.", Toast.LENGTH_SHORT).show()
                    return
                }
                return
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}