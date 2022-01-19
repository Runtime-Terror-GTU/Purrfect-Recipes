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
    fun changePictureDefault(defaultPic:String){
        val storageRef= FirebaseStorage.getInstance().getReference().child("User Pictures")
        var imageLink : String?=null
        storageRef.child(userID).delete()
        if(defaultPic.equals("default_pic1")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic1.png?alt=media&token=04ac54da-978e-43a6-8fe5-5dbdd2267da3"

        }
        else if(defaultPic.equals("default_pic2")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic2.jpg?alt=media&token=6ef1f599-b260-49bc-8702-90a305215f14"
        }
        else if(defaultPic.equals("default_pic3")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic3.jpg?alt=media&token=544baa36-9a22-424d-8b8d-9eafbf67043b"
        }
        else if(defaultPic.equals("default_pic4")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic4.jpg?alt=media&token=b96d6762-6133-46e1-a689-253d6a9e723d"
        }
        else if(defaultPic.equals("default_pic5")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic5.jpg?alt=media&token=17a60e2a-4a6b-468a-9c7d-30e70bd5768f"
        }
        else if(defaultPic.equals("default_pic6")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic6.jpg?alt=media&token=c76a9d42-c964-4e0e-b10d-e967d10bbae9"
        }
        else if(defaultPic.equals("default_pic7")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic7.jpg?alt=media&token=c559e178-16c0-4d99-b0f9-4b39fe567a58"
        }
        else if(defaultPic.equals("default_pic8")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic8.jpg?alt=media&token=8bbbd602-6b04-4eb8-8086-8886daf468a4"
        }
        else if(defaultPic.equals("default_pic9")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic9.jpg?alt=media&token=dcb5ccf4-7926-47f4-8dc4-0b2efc499226"
        }
        else if(defaultPic.equals("default_pic10")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic10.jpg?alt=media&token=a85f6c0b-a801-404e-a1a2-196a809e36c2"
        }
        else if(defaultPic.equals("default_pic11")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic11.jpg?alt=media&token=17cd2782-1139-4dac-affb-662612081185"
        }
        else if(defaultPic.equals("default_pic12")){
            imageLink="https://firebasestorage.googleapis.com/v0/b/purrfect-recipes.appspot.com/o/User%20Pictures%2Fdefault_pic12.jpg?alt=media&token=54bb5af2-7994-4820-9f11-09551a84df44"
        }

        usersRef.child(userID).child(Constants.R_USERPICTURE).setValue(imageLink!!)

    }
}